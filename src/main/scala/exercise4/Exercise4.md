Exercise 4
==========

The objective of exercise 4 is to instantiate and connect to a module.  This will also cover how to connect to
ports in a module and the bundle connect operator *<>*.  The solution for this objective is more complex, and 
will require you to use the items from Exercises 1-3 as well.

Exercise 4 should be built using the provided Multiplier code.  As the header documentation notes, the two
Multiplier inputs assert ready at different times, so cannot simply be tied together to perform the correct
calculation.

## Language Background

Modules are declared in Chisel by creating a new object and wrapping it in a Module() call.  The object must be
a subclass of the chisel3.Module class.  Once the module has been declared, its IO ports are accessible using
dot notation.

```scala
  val myModule = Module(new MyModule)

  myModule.io.dataIn := localDataIn
  localDataOut := myModule.io.dataOut
```

When signals are grouped together in a bundle, we can connect all the signals in the bundle using the *<>* operator.
This connects signals by name, respecting the individual direction of signals when connecting.  This is very useful
when connecting Decoupled interfaces.

```scala
  module.a <> io.a

  // the equivalent of the above operator
  module.a.valid := io.a.valid
  module.a.bits := io.a.bits
  io.a.ready := module.a.ready
```

