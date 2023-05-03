package exercise4

import chisel3._
import chiseltest._
import org.scalatest.freespec.AnyFreeSpec

import scala.util.Random

class TestExercise4 extends AnyFreeSpec with ChiselScalatestTester {
  "compute the square" in {
    test(new Exercise4).withAnnotations(Seq(WriteVcdAnnotation)) {
      c => {
        c.io.dataIn.setSourceClock(c.clock)
        c.io.dataOut.setSinkClock(c.clock)

        val numberSeq = for (i <- 0 to 10) yield Random.nextInt(255)
        val dataInSeq = for (x <- numberSeq) yield x.U
        val dataOutSeq = for (x <- numberSeq) yield (x*x).U

        fork {
          c.io.dataIn.enqueueSeq(dataInSeq)
        }.fork {
          c.io.dataOut.expectDequeueSeq(dataOutSeq)
        }.join()
      }
    }
  }
}
