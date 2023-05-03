package exercise4

import chisel3._
import chisel3.util._

/** Compute the square of the input value
 *
 * Use the provided Multiplier block to create a module which computes the square of the input.
 *
 * Hint: the way the multiplier responds to ready/valid may not be straightforward
 */
class Exercise4 extends Module {
  val io = IO(new Bundle {
    val dataIn = Flipped(Decoupled(UInt(8.W)))
    val dataOut = Decoupled(UInt(16.W))
  })
  //val mult = Module(new Multiplier(8))

  // put your code to compute the square here
  io.dataIn.ready := 0.B
  io.dataOut.valid := 0.B
  io.dataOut.bits := 0.U
}
