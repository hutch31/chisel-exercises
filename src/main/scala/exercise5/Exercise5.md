Exercise 5
==========

The goal of exercise 5 is to explore Chisel data types and type conversions.  To do this, we will build a block
that converts from a bit vector to an array of bytes, change some of the bytes, and convert back to a bit vector.
Exercise 5 also starts exploring using parameters to create flexible designs, and has a unit test which tests
across multiple parameters.

## Language Background

Refer to [Chisel Type Conversions](https://www.chisel-lang.org/chisel3/docs/cookbooks/cookbook.html#type-conversions)
for details and examples of how to convert to and from various chisel types.  We will use the asTypeOf() method for
performing type conversions in this exercise.

### Converting to and from UInt

One of Chisel's oddities is that it does not allow bit-slice assignment, so to change part of a data value you need
to convert it to another type.  The most straightforward way to do that would be to declare a Vec and then assign
each Vec value using a bit slice of the input bus:

```scala
  val byteVec = Wire(Vec(byteWidth, UInt(8.W)))
  for (i <- 0 until byteWidth) {
    byteVec(i) := io.in.bits(i*8+7,i*8)
  }
```

While this works, a more elegant solution is to use asTypeOf() to *cast* the UInt value into another type:

```scala
  val byteVec = io.in.bits.asTypeOf(Vec(byteWidth, UInt(8.W)))
```

In this case, we know that Scala convert from low to high, such that bits [7:0] will end up in byteVec(0),
however if the default order of a Vec or sequence is not what you want, you can use the .reverse method.

Once we have made our byte modification, we use Cat() to concatenate all the values of the Vec back into 
a single UInt value.  In this case, Cat concatenates from left to right, which is not what we want, so we
must reverse the order of the array before sending it to Cat:

```scala
  io.out.bits := Cat(byteVec.reverse)
```

### Parameterized Design

This is the first exercise where the exercise requires a parameter.  One of the main benefits of Chisel is 
that it parameterizes very well, and you can therefore build design code that can be easily adapted and 
reused.

Thinking in parameters rather than constants takes some getting used to, both from a design and a testing
standpoint.  Part of this is being explicit in your code as to what parameters work and are tested.  The
*require* keyword is useful here, as it creates a compile-time assertion that prevents usage if the 
requirement is not met.  In this case, we require our byte-swapper be at least 16 bits wide, and a multiple
of 8 bits.