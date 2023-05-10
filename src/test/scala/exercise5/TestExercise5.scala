package exercise5

import chisel3._
import chiseltest._
import org.scalatest.freespec.AnyFreeSpec

import scala.util.Random

class TestExercise5 extends AnyFreeSpec with ChiselScalatestTester {
  "swap bytes" in {
    for (width <- Seq(2, 4, 8, 16)) {
      println(s"Running test with width $width")
      test(new Exercise5(width)).withAnnotations(Seq(WriteVcdAnnotation)) {
        c => {
          def swap_word(word: Int, value: Int) = {
            val dataIn = BigInt(width*8, Random)
            val dataOut = dataIn ^ (dataIn & (BigInt(0xFF) << word * 8)) | BigInt(value) << word * 8
            println(f"dataIn=$dataIn%x dataOut=$dataOut%x")
            c.io.in.bits.poke(dataIn)
            c.io.in.valid.poke(1)
            c.io.replace.poke(value)
            c.io.select.poke(word)
            c.clock.step()
            c.io.out.bits.expect(dataOut)
            c.io.out.valid.expect(1)

          }

          for (i <- 0 to 20) {
            swap_word(Random.nextInt(width), Random.nextInt(255))
          }
          println("Test finished")
        }
      }
    }
  }
}
