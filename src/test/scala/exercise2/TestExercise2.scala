package exercise2

import chiseltest._
import org.scalatest.freespec.AnyFreeSpec

class TestExercise2 extends AnyFreeSpec with ChiselScalatestTester {

  // Adds numbers with both inputs being valid
  // You can add ".withAnnotations(Seq(WriteVcdAnnotation))" to get a VCD from any unit test
  // Output from the unit test will be in test_run_dir/test_name/ModuleName.vcd
  "add two numbers" in {
    test(new Exercise2).withAnnotations(Seq(WriteVcdAnnotation)) {
      c => {
        val testValues = Seq((1, 1), (5,7), (21,45), (17,16), (0, 99))

        def addNumbers(a : Int, b : Int) = {
          c.io.in_a.valid.poke(1)
          c.io.in_a.bits.poke(a)
          c.io.in_b.valid.poke(1)
          c.io.in_b.bits.poke(b)
          c.clock.step()
          c.io.in_a.valid.poke(0)
          c.io.in_b.valid.poke(0)

          c.io.out_z.valid.expect(1)
          c.io.out_z.bits.expect(a+b)
        }

        for (i <- 0 until testValues.length) {
          addNumbers(testValues(i)._1, testValues(i)._2)
        }
      }
    }
  }

  // This test verifies that the user has implemented the save register on the B input
  "test for saved values" in {
    test(new Exercise2) {
      c => {
        val a_values = Seq(8, 99, 42, 61, 214)
        val b_values = Seq(3, 5, 22, 17, 6)

        for (b_value <- b_values) {
          c.io.in_b.valid.poke(1)
          c.io.in_b.bits.poke(b_value)
          c.clock.step()
          c.io.in_b.valid.poke(0)

          for (a_value <- a_values) {
            c.io.in_a.valid.poke(1)
            c.io.in_a.bits.poke(a_value)
            c.clock.step()
            c.io.out_z.valid.expect(1)
            c.io.out_z.bits.expect(b_value + a_value)
          }
          c.io.in_a.valid.poke(0)
        }
      }
    }
  }
}
