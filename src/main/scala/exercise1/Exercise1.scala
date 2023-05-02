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
 * This block will have two inputs using the ValidIO protocol.  Any time input A is valid,
 * the block adds the last valid value from input B to A, and produces a valid output one
 * cycle later.
 *
 * When inputs A and B are valid in the same cycle, the output should be A+B.  When only B
 * is asserted, the value should be stored for future computations.
 */
class Exercise1 extends Module {
  val io = IO(new Bundle {
    // The Flipped() function reverses the direction of all signals underneath it.
    // ValidIO() wraps the signals with a xxx.valid signal
    val in_a = Flipped(ValidIO(UInt(16.W)))
    val in_b = Flipped(ValidIO(UInt(16.W)))
    // By convention, Decoupled() and ValidIO() are output interfaces, and must be Flipped() to
    // make them input interfaces
    val out_z = ValidIO(UInt(16.W))
  })

  // Remove the output assignments below, and put your chisel code for the exercise here
  io.out_z.valid := 0.B
  io.out_z.bits := 0.U
}

/** This object allows you to generate RTL based on the exercise you have coded, above.
 *
 * If you are using IntelliJ, you can run by pressing the green triangle to the left of
 * the object keyword.
 */
object GenExercise1 extends App {
  (new ChiselStage).emitVerilog(new Exercise1, Array("--target-dir", "rtl/exercise1"))
}