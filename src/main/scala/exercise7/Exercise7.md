Exercise 7
==========

## Exercise Overview

This exercise looks at unit testing.  You have several design modules which have
bugs in them and you will create unit tests to exercise the blocks and find the
bugs.

A unit test is normally a short, focused test on one aspect of your class or
module.  Unit testing in software ties into the philosophy of test-driven
development and continuous integration.

For those interested, here are some articles going more into depth about
unit testing practices:

 - [A Deep Dive into Unit Testing](https://semaphoreci.com/blog/unit-testing#:~:text=The%20philosophy%20of%20unit%20testing,the%20result%20of%20their%20execution.)
 - [What is your philosophy on unit testing?](https://codegorilla.com/2020/08/13/what-is-your-philosophy-on-unit-testing/)

A good set of unit tests on your code should tell you that:

 - All required features have been implemented
 - Expected outputs are produced for good input
 - Design limits are exercised

Unit tests can also be a good way of reproducing and debugging errors found in higher
level block and system tests.  Once the error is reproduced, the fix can be quickly
implemented and verified.  The resulting unit test then serves as additional 
documentation about the bug.

## Language Background

The Chisel unit testing framework is built on Scalatest, which is Scala's unit testing
library.  Scalatest supports multiple unit testing philosophies with supporting
structures, which we will not go into.  Our unit tests will be built on top of
[AnyFreeSpec](https://www.scalatest.org/scaladoc/3.2.5/org/scalatest/freespec/AnyFreeSpec.html),
which puts no restrictions on the structure of the test.

### Chiseltest Framework

[Chiseltest](https://github.com/ucb-bar/chiseltest) is built on Scalatest and provides
methods and routines specific to testing Chisel code.  A Chiseltest always tests one
Chisel Module.  For this exercise we will directly test the module we are interested in, 
but it is common to have test-specific Module classes which wrap our DUT with 
test-specific logic or combine DUTs together for integration testing.

Each test starts with a string which concisely describes what the unit test is doing.
In the body of the test the DUT module is instanced within the test() call, and
then a code block is provided to excerise the test module.

```scala
  "send data" in {
    test(new MyAdder) {
      c => {
        // test operations go here
        c.io.a.poke(10)
        c.io.b.poke(5)
        c.clock.step()
        c.io.z.expect(15)
      }
    }
  }
```

### Creating Stimulus

The block is exercised by using the 'poke' method, and its outputs can be checked
using the 'expect' method.  Expect is an *assertion*, meaning the test will fail if
the correct value is not seen.

If your test needs to interact with the code, it can use the peekInt and peekBoolean
methods to get a result.  A common case is to wait for a flow-control signal:

```scala
  "look for flow control" in {
    test(new MyModule) {
      c => {
        c.io.in.valid.poke(1)
        c.io.in.bits.poke(22)
        while (!c.io.in.ready.peekBoolean()) c.clock.step()
      }
    }
  }
```

### Signal Types

When using peek/expect, tests can either use Scala types or Chisel types for values.
The above examples all used Scala types, the example could be recoded as below using Chisel 
types (.U suffix for unsigned).

```scala
  "send data" in {
    test(new MyAdder) {
      c => {
        // test operations go here
        c.io.a.poke(10.U)
        c.io.b.poke(5.U)
        c.clock.step()
        c.io.z.expect(15.U)
      }
    }
  }
```

If you are using complex types like Bundles, then you can poke/expect each element in the
bundle independently.

### Debugging Unit Tests

Each test can take a list of annotations to modify its behavior.  For debugging, the WriteVcdAnnotation enables
the test to write a VCD file.  The resulting file will be in test_run_dir/test_name.

```scala
  "fill and empty" in {
    val depth = 10
    test(new Excercise7b(depth)).withAnnotations(Seq(WriteVcdAnnotation)) {
      c => {
        // Your test code here
      }
    }
  }
```

The chiseltest framework drives and samples all data on the negative edge of the clock to provide sufficient setup/hold
margin.  Normally this works well but designs which have combinatorial outputs may find this to be an issue.  In this
case the test may need to emulate sampling by peeking and saving the output values prior to changing the inputs.