Exercise 6
==========

The Chisel library has some useful components in it.  The common library elements I have used are:

 - log2Ceil
 - ShiftRegister
 - PopCount
 - PriorityEncoder
 - Reverse
 - Queue
 - RRArbiter

The online documentation for these can be found in the [Chisel3 Docs](https://javadoc.io/static/edu.berkeley.cs/chisel3_2.12/3.2.6/chisel3/util/index.html).

In Exercise 6, you will explore using the RRArbiter and DCDemux, a similar component, to build a parameterized
design.  You will also get an introduction into using Scala generic classes and for-yield constructs.

## Language Background

### log2Ceil

This function has its own category because it is so commonly used.  log2Ceil returns the number of bits needed to hold N
values, and is commonly used to determine the number of address bits needed for memories or FIFOs.  It can be used in
port declarations or in constants:

```scala
class MyModule(width : Int) extends Module {
  val io = IO(new Bundle {
    val foo = Input(UInt(width.W))
    val foosz = Input(UInt(log2Ceil(width).W))
  })
  // Initialize barsz with a sized constant to set its width
  val barsz = RegInit(init=0.U(log2Ceil(width).W))
}
```

### Utility Functions

The next four items in the list are *utility functions*.  These generate inline logic based on a function call.

*ShiftRegister* is straightforward, but can create a shift register of any data type, not just single bits.  It also
can take a shift enable so you can stop the pipe.

*PopCount* counts the number of 1 bits set in its argument.

*PriorityEncoder* returns the bit index of the least-significant bit in its argument.  If you need priority from the
most-significant bit, you can pair it with *Reverse*, which reverse the bit order of its argument.

### Library Modules

*Queue* and *RRArbiter* are modules in the Chisel library.  As such, they need to be instanced using the normal
Module() syntax and create hierarchy in your design.  Both modules take a *type parameter* which is the data type
they will adapt to.  

```scala
class MyModule extends Module {
  val io = IO(new Bundle {
    val in = Flipped(Decoupled(UInt(32.W)))
  })
  
  // Queue can be created by making a module call and connecting it
  val mod = Module(new Queue(UInt(32.W, depth=4)))
  mod.io.enq <> io.in
  
  // Or it can be created by calling a helper function, this copies the 
  // type of its incoming argument, and automatically connects to it
  val func = Queue(io.in, 4)
}
```

### Generic Classes

In Scala, a [Generic Class](https://docs.scala-lang.org/tour/generic-classes.html) takes a type parameter, and alters
its implementation based on the type passed.  This is very useful for hardware designs where the functionality of 
the hardware implemented (such as a queue, or a mux) has few or no dependencies on the data.

In Chisel, a generic class will always have a type limitation on it.  This means that the class passed as a parameter
must be a child class of the limiting type (the *extends* keyword declares a class's parent).  Since everything we 
deal with in Chisel is either a Module or Data, our generic classes will normally have a type specifier limiting them
to a child of one of these types (or a more specific subtype).

Our Exercise 6 class uses a generic class that requires its input to be a child of IndexBundle.  This is because the
one field defined by IndexBundle is required for the operation of the block.

```scala
class Exercise6[D <: IndexBundle](data : D, sources : Int) extends Module {
  // ...
}
```

### for-yield constructs

Scala provides a number of functional programming constructs, one of which is the for-yield construct.  A for-yield
iterates through a set of arguments, and produces (yields) a result on each iteration.  The result of this is a sequence
(Seq) of objects, which can be accessed by an index number.

We can use for-yield constructs to create arrays of Modules, signals, or other Chisel data types.

```scala
  // Create one demux for each input source, with two outputs (one per arbiter)
  val demux = for (i <- 0 until sources) yield Module(new DCDemux(data, 2))
  // Create one arbiter for each output port, with one input per source
  val arb = for (i <- 0 to 1) yield Module(new RRArbiter(data, sources))
```
