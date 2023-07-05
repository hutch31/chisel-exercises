package exercise7

import chisel3._

/**
 * Write a unit test for this module which covers its full range of output values.
 * Fix any bugs found by the unit test.
 */
class Exercise7a extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(8.W))
    val b = Input(UInt(8.W))
    val z = Output(UInt(9.W))
  })

  io.z := io.a + io.b
}
