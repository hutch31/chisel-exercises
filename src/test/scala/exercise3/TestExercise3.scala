package exercise3

import chiseltest._
import org.scalatest.freespec.AnyFreeSpec

import scala.util.Random

class TestExercise3  extends AnyFreeSpec with ChiselScalatestTester{
  def sendSeq(c : Exercise3, numZero : Int, numOne : Int) = {
    c.io.data_in.poke(0)
    c.clock.step(numZero)
    c.io.data_in.poke(1)
    c.clock.step(numOne)
    c.io.data_in.poke(0)
    c.clock.step(1)
    c.io.detected.expect(1)
  }

  "detect sequence" in {
    test(new Exercise3).withAnnotations(Seq(WriteVcdAnnotation)) {
      c => {
        for (i <- 0 to 9) {
          sendSeq(c, Random.nextInt(5)+1, Random.nextInt(5)+1)
        }
        sendSeq(c, 1, 1)
      }
    }
  }
}
