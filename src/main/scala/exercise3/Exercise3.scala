package exercise3

import chisel3._
// The util import contains many useful but not critical elements, such as Decoupled and
// ValidIO.  Most blocks should routinly put chisel3._ and chisel3.util._ imports.
import chisel3.util._

/** Exercise3 creates a zero-one-zero detector
 *
 * Build a state machine which detects a zero-one-zero sequence.  The length of the zero and one
 * sequences is indeterminate.
 *
 * The state machine should assert detected the cycle after the second zero is detected.
 */
class Exercise3 extends Module {
  val io = IO(new Bundle {
    val data_in = Input(Bool())
    val detected = Output(Bool())
  })

  // Insert your FSM code here
  io.detected := 0.B
}
