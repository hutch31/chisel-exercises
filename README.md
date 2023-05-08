Chisel Exercises
================

## Overview

This repository contains a series of exercises for learning Chisel.  Each exercise introduces new concepts in
the language.

These exercises describe some parts of the Chisel language, but are not intended to be a manual or stand-alone
reference to the language.  Users should refer to [Digital Design with Chisel](https://www.imm.dtu.dk/~masca/chisel-book.pdf)
for a more comprehensive reference.

The [Chisel Cookbook](https://www.chisel-lang.org/chisel3/docs/cookbooks/cookbook.html) has references on specific 
cases and their answers.

### System Prerequisites

The exercises are designed such that the only prerequisite is sbt (and Java, which is required to run sbt).
The IntelliJ IDE is recommended but not required, and Gtkwave is also recommended to view generated waveforms.

## Exercises

Each exercise consists of a Chisel template block called "Exercise#", which defines the port interface for the 
exercise code, and a markup file which contains background information and introduces the specific language elements
that this exercise is covering.

The hardware problem specification for each exercise is written in the comment block of the Chisel code.

### [Exercise 1](src/main/scala/exercise1/Exercise1.md)

The first exercise introduces basic language concepts such as inputs and outputs, Wire and Reg declarations,
and putting these together to form a working design.  It also has a simple introduction to unit testing.

### [Exercise 2](src/main/scala/exercise2/Exercise2.md)

The second exercise adds composite types and goes into more detail about different Chisel hardware types.

### [Exercise 3](src/main/scala/exercise3/Exercise3.md)

The third exercise adds branching and control statements, and state machine coding.

### [Exercise 4](src/main/scala/exercise4/Exercise4.md)

The fourth exercise shows how to instantiate and connect to child modules, and perform more complex design
work.
