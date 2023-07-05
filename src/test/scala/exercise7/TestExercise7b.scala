package exercise7

import chiseltest._
import org.scalatest.freespec.AnyFreeSpec

/**
 * Exercise 7b has several features which need to be tested.  Per unit testing
 * philosophy, each feature should be tested independently with a simple,
 * directed test.
 *
 * The class below has been filled in with the names of suggested tests for the
 * block.  Use poke/expect to create unit tests for the block functionality.
 * peekBoolean() may also be useful for testing flow control conditions.
 */
class TestExercise7b extends AnyFreeSpec with ChiselScalatestTester{
  "fill and empty" in {
    val depth = 10
    test(new Excercise7b(depth)).withAnnotations(Seq(WriteVcdAnnotation)) {
      c => {
        // Your test code here
      }
    }
  }

  "read and write simultaneously" in {
    val depth = 10
    test(new Excercise7b(depth)) {
      c => {
       // Your test code here
      }
    }
  }
}
