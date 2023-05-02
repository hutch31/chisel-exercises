package exercise1

import chiseltest._
import org.scalatest.freespec.AnyFreeSpec

class TestExercise1 extends AnyFreeSpec with ChiselScalatestTester {

  // Adds numbers
  // You can add ".withAnnotations(Seq(WriteVcdAnnotation))" to get a VCD from any unit test
  // Output from the unit test will be in test_run_dir/test_name/ModuleName.vcd
  "add two numbers" in {
    test(new Exercise1).withAnnotations(Seq(WriteVcdAnnotation)) {
      c => {
        val testValues = Seq((1, 1), (5,7), (21,45), (17,16), (0, 99))

        def addNumbers(a : Int, b : Int) = {
          c.io.in_a.poke(a)
          c.io.in_b.poke(b)
          c.clock.step()
          c.io.out_z.expect(a+b)
        }

        for (i <- 0 until testValues.length) {
          addNumbers(testValues(i)._1, testValues(i)._2)
        }
      }
    }
  }
}
