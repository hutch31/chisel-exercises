Exercise 2
==========

The objective of Exercise 2 is to introduce composite IO types and some more about Chisel primitive types.
This exercise uses ValidIO, which is a composite type which takes a parameter and adds a valid signal 
to it to qualify the data.  The two most commmon composite types in Chisel are ValidIO and DecoupledIO.
DecoupledIO adds both valid and ready signals to its parameterized type.

## Language Background

All classes in Scala can take *parameters*, which are variables which can change the functionality of
the class.  Chisel uses parameters extensively to increase the power and flexibility of the language.
Any valid object can be passed as a parameter, including a class.

The call in the IO portion of the class calls the [Valid Factory](https://javadoc.io/doc/edu.berkeley.cs/chisel3_2.13/3.5.6/chisel3/util/Valid$.html).

```scala
  val io = IO(new Bundle {
    val in_a = Flipped(Valid(UInt(16.W)))
    val in_b = Flipped(Valid(UInt(16.W)))
    val out_z = Valid(UInt(16.W))
  })
```

Valid takes a Chisel class (UInt) as a parameter, and then creates a bundle with two elements, a new
valid signal, and a bits signal containing the original type.

## Types

Chisel mainly uses five types:
  - Bool
  - SInt
  - UInt
  - Vec
  - Bundle

Bool is a single-bit Boolean value.  It is distinct because some statements explicitly produce or
require a Bool value, particularly comparision and branch operations.

UInt is Unsigned Integer, and the most commonly used Chisel type.  Both UInt and SInt take *width* as
a parameter.  All UInt and SInt values have a bit width, either explicitly from their declaration or
implicitly from the other values they were created from.

SInt is a Signed Integer.

Vec creates an array of elements which can be indexed by hardware.  All the elements in the array need
to be the same type.  Chisel can create multidimensonal arrays by having a Vec of Vec.  Elements of the
array are accessed by position using the parentheses () operator.

Bundle creates a composite type of elements, which can be any type, including other bundles.  Elements
are accessed by name using the dot . operator.

Some of these types (Bundle, UInt) you have already seen in Exercise1 -- the IO for the module was
declared as a bundle, and accessed as io.in_a or io.out_z

## Hardware Elements

Chisel has six hardware elements

 - Wire
 - Reg
 - Module
 - RawModule
 - Clock
 - Reset

Unlike Verilog and VHDL, Chisel explicitly differentiates storage elements (Reg) from combinatorial
elements (Wire).  Clock and Reset are special cases of a wire, and can be converted to or from wires.

RawModule is the base class for a Chisel module.  It is not commonly used; the Module class (which
is a subclass of RawModule) is most common.  Module defines an implicit clock and reset, which means
that any Reg declarations in that class are automatically hooked up to that clock and reset.

### Reg Declarations

Reg has several variations which allow compact creation of registers.

 - Reg (no reset)
 - RegInit (with a reset value)
 - RegNext (with a next-state value)
 - RegEnable (with a load enable pin)

These are listed in order, as each adds an additional argument.

Registers are declared using a variable declaration of their name, followed by their type parameters:

```scala
  val foo = RegEnable(init=0.U(6.W), next=nextFoo, enable=fooEnable)
```

This statement creates a new register foo, with an initial value of 0.  Note the width definition on
the constant, which implicitly sets the bit width of the register.  Without this, foo would implicitly
get its bit width from the bit width of nextFoo.

### Wire Declarations

Wires are declared just as Reg values.  The most common Wire declarations are Wire and WireDefault.

```scala
  val wire1 = Wire(Bool())
  val wire2 = WireDefault(4.U(4.W))
```

Wire takes a Chisel type, from above.  Wire default takes a constant value and implicitly sets its type
from that value.  As wire1 does not have a default, somewhere in the module body it must be assigned a
value, otherwise Chisel will report an error.

## Completing the Exercise

The objective of this exercise is to use Reg declarations to create storage elements for the incoming
data, and qualify when that storage is used.  You will need the Mux operator, which builds a two-input
mux:

```scala
  val mydata = Mux(valid, new_data, stored_data)
```