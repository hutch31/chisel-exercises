package exercise6

import chisel3._
import chisel3.util.ImplicitConversions.intToUInt
import chisel3.util._

class IndexBundle extends Bundle {
  val index = UInt(8.W)
}

class Exercise6IO[D <: IndexBundle](data : D, sources : Int) extends Bundle {
  val in = Vec(sources, Flipped(Decoupled(data)))
  val out = Vec(2, Decoupled(data))
  val level = Input(UInt(8.W))

}
/** Create a sorting block to sort input streams into two output streams
 *
 * Exercise6 takes input from *sources* number of inputs, and sorts that input
 * into two output streams based on the index parameter.
 *
 * The provided DCDemux can be used to split each input stream into two
 * streams based on the index value.  The Chisel RRArbiter can then be used
 * to combine all of these into two output streams.
 */
class Exercise6[D <: IndexBundle](data : D, sources : Int) extends Module {
  val io = IO(new Exercise6IO(data, sources))

  // Put your module instances and connection code here
  for (i <- 0 until sources) {
    io.in(i).ready := 0.B
  }
  for (i <- 0 until 2) {
    io.out(i).valid := 0.B
    io.out(i).bits := 0.asTypeOf(io.out(i).bits)
  }
}
