Exercise 3
==========

The objective of exercise 3 is introducing the branching and control flow statements when() and switch().
You will implement a zero-one-zero detector, a simple state machine that detects two transitions on an 
input signal.

## Language Background

### when statements

Chisel uses the keyword *when* to form a conditional in hardware.  This causes confusion because both Verilog and VHDL
use the keyword *if* for the same functionality.  Unfortunately *if* is a keyword in Scala, requiring Chisel to use
a new keyword.  Additional confusion comes when module code uses both *if* and *when* keywords in its construction.

For hardware designers, you can think of the *if* as an "if-generate" statement -- this conditionally builds hardware.
We will return to this in a later exercise, for now you should only use *when* statements.

*when* statements can be chained to form a series of tests:

```scala
  when (firstCondition) {
    // do something
  }.elsewhen (secondCondition) {
    // do something else
  }.otherwise {
    // Do this if none of the above are true
  }
```

Like Verilog, these are tested in order, and the first matching value is assigned.

### switch statements and state machines

Chisel uses the keyword *switch* to evaluate the argument against multiple constants, similar to a Verilog case
statement.  This is used primarily for state machines.

Chisel defines states for a state machine using the *Enum* keyword.  The initial state can then be assigned using
a RegInit.  The example below toggles between the two states alpha and beta based on the input signal my_input.

```scala
  // Enum parameter must match the number of states listed, and the list must end with a Nil keyword
  val s_alpha :: s_beta :: Nil = Enum(2)
  val state = RegInit(init=s_idle)

  switch (state) {
    is (s_alpha) {
      when (io.my_input) {
        state := s_beta
      }
    }
    
    is (s_beta) {
      when (!io.my_input) {
        state := s_alpha
      }
    }
  }
```

