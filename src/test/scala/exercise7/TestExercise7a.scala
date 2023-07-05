package exercise7

import chiseltest._
import org.scalatest.freespec.AnyFreeSpec

/**
 * As 7a is a very simple block, a single unit test will suffice to cover
 * its functionality.  Use peek/expect methods to exercise the design
 * for the full range of its inputs.
 */
class TestExercise7a extends AnyFreeSpec with ChiselScalatestTester {
  "add values" in {
    test(new Exercise7a) {
      c => {
        // Write your test code here
      }
    }
  }
}
