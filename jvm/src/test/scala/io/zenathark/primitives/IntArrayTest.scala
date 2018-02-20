package io.zenathark.primitives;

import org.scalatest.FlatSpec


class IntArrayTest extends FlatSpec  {
  "An Int Array" should "start as an empty int[]" in {
    val arr = new IntArray()
    val sz = arr.data.length
    assert(sz == 0)
  }

  "An Int Array" should "add an element" in {
    val arr = new IntArray()
    arr add 3
    val sz = arr.data.length
    assert(sz == 1)
    assert(arr.data(0) == 3)
  }

  "the seq method" should "return an array from variadic elements" in {
    val arr = IntArray.seq(1, 2, 3)
    val sz = arr.data.length
    assert(sz == 3)
    assert(arr.data(0) == 1)
    assert(arr.data(1) == 2)
    assert(arr.data(2) == 3)
  }

  "seq method" should "recibe an array as new array" in {
    val t = Array[Int](1,2,3)
    val arr = IntArray.seq(t: _*)
    val sz = arr.data.length
    assert(sz == 3)
    assert(arr.data(0) == 1)
    assert(arr.data(1) == 2)
    assert(arr.data(2) == 3)
  }

  "the add method" should "increment size in size * 3 / 2 + 1" in {
    val arr = IntArray.seq(1, 2, 3)
    arr.add(4)
    val sz = arr.data.length
    // assert(sz == 5)
    assert(arr.data(0) == 1)
    assert(arr.data(1) == 2)
    assert(arr.data(2) == 3)
    assert(arr.data(3) == 4)
  }
}
