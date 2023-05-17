package exercise6

import chisel3._
import chisel3.util._
import chiseltest._
import org.scalatest.freespec.AnyFreeSpec

import scala.util.Random

class ValueIndexBundle extends IndexBundle {
  val value = UInt(16.W)
}

class WrapExercise6[D <: IndexBundle](data : D, sources : Int) extends Module {
  val io = IO(new Exercise6IO(data, sources))
  val e6 = Module(new Exercise6(data, sources))

  for (i <- 0 until sources) {
    e6.io.in(i) <> Queue(io.in(i), 1)
  }
  e6.io.level := io.level
  io.out <> e6.io.out
}

class TestExercise6 extends AnyFreeSpec with ChiselScalatestTester {
  "sort data" in {
    for (sources <- Seq(2, 5, 10)) {
      println(s"Running test with $sources sources")
      test(new WrapExercise6(new ValueIndexBundle, sources)).withAnnotations(Seq(WriteVcdAnnotation)) {
        c => {
          def createStream(sources : Int, length : Int) : Seq[Seq[(Int,Int)]] = {
            for (src <- 0 until sources)
              yield for (i <- 0 until length) yield (Random.nextInt(255), Random.nextInt(1000))
          }
          def calcResult(index : Int, data : Seq[Seq[(Int,Int)]]) : Array[Int] = {
            val sums = new Array[Int](2)
            for (i <- data) {
              for (j <- i) {
                if (j._1 >= index)
                  sums(1) += j._2
                else
                  sums(0) += j._2
              }
            }
            sums
          }

          val dataLength = 2
          val sourceData = createStream(sources, dataLength)
          var pointer = new Array[Int](sources)
          val receiveSums = new Array[Int](2)
          var receiveCount = 0

          println(s"Length = ${pointer.length}")
          println(s"Receive sums = ${receiveSums(0)}, ${receiveSums(1)}")

          val level = Random.nextInt(128)+64
          c.io.level.poke(level)
          println(s"Level = $level")

          fork {
          while (pointer.sum < dataLength*sources) {
            for (i <- 0 until sources) {
              if (pointer(i) < dataLength) {
                c.io.in(i).valid.poke(1)
                c.io.in(i).bits.index.poke(sourceData(i)(pointer(i))._1)
                c.io.in(i).bits.value.poke(sourceData(i)(pointer(i))._2)

                if (c.io.in(i).ready.peekBoolean()) {
                  println(s"Sent port $i index ${sourceData(i)(pointer(i))._1} value ${sourceData(i)(pointer(i))._2}")
                  pointer(i) += 1
                }
              } else {
                c.io.in(i).valid.poke(0)
              }
            }
            c.clock.step()
          }
            for (i <- 0 until sources) c.io.in(i).valid.poke(0)
          }.fork {
            while (receiveCount < dataLength*sources) {
              for (i <- 0 to 1) {
                c.io.out(i).ready.poke(1)
                if (c.io.out(i).valid.peekBoolean()) {
                  val peekvalue = c.io.out(i).bits.value.peekInt().toInt
                  receiveSums(i) += peekvalue
                  println(s"Received port $i value $peekvalue, total ${receiveSums(i)}")
                  receiveCount += 1
                }
              }
              c.clock.step()
            }
          }.join()


          // Check total value received
          val results = calcResult(level, sourceData)
          println(s"Receive sums = ${receiveSums(0)}, ${receiveSums(1)}")
          println(s"Expected results = ${results(0)}, ${results(1)}")
          for (i <- 0 to 1)
            assert(results(i) == receiveSums(i))

          println("Finished $source source test")
        }
      }
    }
  }
}
