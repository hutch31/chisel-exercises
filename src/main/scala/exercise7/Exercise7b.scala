package exercise7

import chisel3._
import chisel3.util._

/**
 * This module implements a basic queue.  It has at least two implementation
 * bugs in it.  Write unit tests to find the bugs, then fix them and use
 * the unit tests to confirm the fix.
 */
class Excercise7b(depth : Int) extends Module {
  val io = IO(new Bundle {
    val in = Flipped(Decoupled(UInt(16.W)))
    val out = Decoupled(UInt(16.W))
  })
  val usage = RegInit(init=0.U(log2Ceil(depth).W))
  val storage = Reg(Vec(depth, UInt(16.W)))

  io.in.ready := (usage =/= depth.U)
  when (io.in.fire) {
    usage := usage + 1.U
    storage(usage) := io.in.bits
  }
  when (io.out.fire) {
    usage := usage - 1.U
    for (i <- 1 until depth) {
      storage(i-i) := storage(i)
    }
  }
  io.out.valid := (usage =/= 0.U)
  io.out.bits := storage(0)
}
