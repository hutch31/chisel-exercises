package exercise5

import chisel3._
import chisel3.stage.ChiselStage
import chisel3.util._

/** Create a byte swapper which extracts bits from an incoming word and replaces them.
 *
 * The byte swapper takes in an input word with a variable number of bytes, and replaces
 * one of those bytes with a provided value.
 *
 * It has two three input ports:
 *  - ``in`` is the incoming data, qualified by valid
 *  - ``select`` choses which byte to replace, with little-endian indexing
 *  - ``replace`` provides the new data
 *
 * The block has a single output port *out* on which the resulting data is produced.
 * The block should have a 1-cycle latency, so out is valid the cycle after in is valid.
 * The select and replace inputs are assumed to be valid whenever in is valid.
 *
 * @param byteWidth The width of the incoming and outgoing datapaths, in bytes
 */
class Exercise5(byteWidth : Int) extends Module {
  val io = IO(new Bundle {
    val in = Flipped(Valid(UInt((byteWidth*8).W)))
    val select = Input(UInt(log2Ceil(byteWidth).W))
    val replace = Input(UInt(8.W))
    val out = Valid(UInt((byteWidth*8).W))
  })
  require (byteWidth >= 2)

  // Put your code here
  io.out.bits := 0.U
  io.out.valid := 0.B
}

object GenExercise5 extends App {
  (new ChiselStage).emitVerilog(new Exercise5(4), Array("--target-dir", "rtl/exercise5"))
}
