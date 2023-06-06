package exercise6

import chisel3._
import chisel3.util.ImplicitConversions.intToUInt
import chisel3.util._

class IndexBundle extends Bundle {
  val index = UInt(8.W)
}

/** IO block for Exercise 6
 *
 * This contains the inputs and outputs for the exercise block below.  This is a
 * generic class, and takes another class as a parameter.  The type restriction
 * parameter in square brackets ([D <: IndexBundle]) puts a limitation on the class
 * which can be passed as a parameter, which is that it must be a subclass of class
 * IndexBundle.
 *
 * The effect of this class restriction is that any users of this class can access
 * any of the methods or fields defined in IndexBundle and know that, whatever class
 * is passed, that these fields/methods will be present.
 *
 * In this exercise, this is used to allow a generic bundle of data which must have
 * an index field, which is then used to sort the data.
 */
class Exercise6IO[D <: IndexBundle](data : D, sources : Int) extends Bundle {
  val in = Vec(sources, Flipped(Decoupled(data)))
  val out = Vec(2, Decoupled(data))
  val level = Input(UInt(8.W))
}

/** Create a sorting block to sort input streams into two output streams
 *
 * Exercise6 takes input from *sources* number of inputs, and sorts that input
 * into two output streams based on the index parameter.  If the io.in(#).bits.index
 * field is less than io.level, then should be sorted to io.out(0), otherwise
 * it should go to io.out(1).
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
