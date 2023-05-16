package exercise1

// Import statements bring objects defined in other namespaces into the current namespace
// The chisel3 import brings in the most commonly used items, such as UInt and Module
import chisel3._
// The ChiselStage import is used to access the Chisel RTL Generator
import chisel3.stage.ChiselStage
// The util import contains many useful but not critical elements, such as Decoupled and
// ValidIO.  Most blocks should routinly put chisel3._ and chisel3.util._ imports.
import chisel3.util._

/** Create a block which adds two numbers
 *
 * This block will have two basic inputs.  The block should produce a registered output
 * which contains the sum of the two inputs, delayed by one clock cycle.
 */
class Exercise1 extends Module {
  val io = IO(new Bundle {
    // Declare two input ports, each 16 bits wide
    val in_a = Input(UInt(16.W))
    val in_b = Input(UInt(16.W))
    // Declare a 16-bit wide output port
    val out_z = Output(UInt(16.W))
  })

  // Put your Chisel code here
  // Reference input port values as io.in_a and io.in_b
  io.out_z := 0.U
}
