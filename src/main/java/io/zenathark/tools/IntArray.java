package io.zenathark.tools;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.util.function.*;

class IntArray {
  int size;
  int index;
  int[] data;
  int[] EMPTY_DATA = {};

  public IntArray() {
    this(0);
  }

  public IntArray(int capacity) {
    if (capacity > 0) {
      this.data = new int[capacity];
    } else if (capacity == 0) {
      this.data = EMPTY_DATA;
    } else {
      throw new IllegalArgumentException(String.format("Illegal Capacity: %d", capacity));
    }
  }

  public static IntArray seq(int... data) {
    IntArray arr = new IntArray(data.length);
    System.arraycopy(data, 0, arr.data, 0, data.length);
    arr.size = data.length;
    return arr;
  }

  public boolean add(int e) {
    ensureCapacity(size + 1);
    data[size++] = e;
    return true;
  }

  public void add(int index, int e) {
    rangeCheckForAdd(index);
    ensureCapacity(size + 1);
    System.arraycopy(data, index, data, index + 1, size - index);
    data[index] = e;
    size++;
  }

  public void rangeCheckForAdd(int i) {
    if (index > size || index < 0)
      throw new IndexOutOfBoundsException(String.format("Index: %d, Size %d", i, size));
  }

  public void ensureCapacity(int minCapacity) {
    int oldCapacity = data.length;
    if (minCapacity > oldCapacity) {
      int[] oldData = data;
      int newCapacity = (oldCapacity * 3) / 2 + 1;
      if (newCapacity < minCapacity)
        newCapacity = minCapacity;
      data = new int[newCapacity];
      System.arraycopy(oldData, 0, data, 0, oldCapacity);
    }
  }

  public void trimToSize() {
    if (size < data.length) {
      if (size == 0) {
        data = EMPTY_DATA;
      } else {
        int[] oldData = data;
        data = new int[size];
        System.arraycopy(oldData, 0, data, 0, size);
      }
    }
  }
}
