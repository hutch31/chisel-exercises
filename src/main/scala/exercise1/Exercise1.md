Exercise 1
==========

The objective of Exercise 1 is to introduce you to the most basic Chisel hardware constructs, and a few 
Scala keywords which are used to create the framework for Chisel.

The objective of the first exercise is to create a Chisel hardware design per the simple specification
written in the Exercise1.scala file.  The correct operation of this will be tested by using the unit
test written under the corresponding test directory.

Scala, like Java, uses and defines a directory hierarchy standard, and files are located in specific
locations based on their names and function.  In Java this hierarchy is strict, and the Java package
name must match the directory hierarchy; Scala is more forgiving in this regard but following Java
conventions is encouraged.

## File Walkthrough

The first line in Exercise1.scala is "package exercise1".  The package declaration indicates where in the
Java name space that all classes defined in this file reside.

The next section of the file is the imports, where classes defined in other name spaces are imported so they
can be accessed by code in this file.  The underscore _ in Scala indicates a wildcard, so tells the compiler
to import all names within a package scope.  Note that this is not recursive, so an import of chisel3.util._ is
still required.

### Class Declaration

The next portion is the class declaration:

```scala
class Exercise1 extends Module {
```

Exercise1 is the name of the class, which is defined by the author.  The extends keyword indicates that
this class inherits from the chisel3.Module class, which is required for all Chisel modules
(there is a caveat for this, which is RawModule, which we will cover later).  Scala supports
only single inheritance, so all Chisel modules need to inherit from Module or from a subclass
of Module.

### IO Declaration

From the class definition, Chisel elements can be defined in any order, but a good convention to follow
is that all ports should be declared at the top of the module.  This statement defines a single block
of ports for the module named "io".  The IO() call below indicates that the contained bundle should all
be IO ports.

```scala
  val io = IO(new Bundle {
  val in_a = Input(UInt(16.W))
  val in_b = Input(UInt(16.W))
  val out_z = Output(UInt(16.W))
})
```

This IO bundle contains three signals, each of which has a defined direction.  The type of
each input is UInt (unsigned integer) with a width of 16 bits.

### Signals and internals

The items in the bundle use the Scala "val" keyword.  Variables in Scala are either constant (val)
or variable (var).  In Chisel we typically use val variables, as we are creating static hardware
elements, and declaring them as val prevents us from accidentally overwriting them at some later time.

For assigning values to signals, Chisel uses the ":=" operator, which is distinct from the Scala equals
operator.  One way to think of these  is that the = operator is used to *declare* an element, whereas the
:= operator is used to *assign to* an element.

To complete the exercise, you need to first declare a register to hold the sum of the two values,
and then assign to the register.

```scala
  val myRegister = Reg(UInt(16.W))
  myRegister := io.in_a + io.in_b
```

This code declares an unsigned 16-bit register, with no reset value, and then assigns a value to that
register on every clock.

Go ahead and update your copy of the Exercise1 code and run the unit test TestExercise1 to
verify that it works correctly.
