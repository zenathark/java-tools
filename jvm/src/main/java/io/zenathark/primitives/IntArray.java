/* MIT License
 *
 * Copyright (c) 2018 Juan Carlos Galan Hernandez
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.zenathark.primitives;

/**
 * This class is a re-implementation of a variable length integer array. Its purpose is
 * mainly for programming contests.
 *
 * <p>The problem with the standard JDK implementations, in particular with
 * <tt>ArrayList</tt> is that it requires the use of Boxed numbers. Such restriction
 * can potentially impact the running time of a program. In order to avoid such
 * problem, this class uses an internal <tt>int</tt> primitive array. If the internal
 * array runs out of space, it auto grows at a ratio of <tt>array.length</tt>*3/2 + 1.
 * Notice that this formula is the same used for<tt>ArrayList</tt> on the OpenJDK
 * implementation. The old array is copied to the new bigger array using the
 * <tt>System.arraycopy</tt> method that has a native implementation which guarantees
 * a faster copy than when using any loop.
 *
 * <p>This class is a member of the <a href="https://github.com/zenathark/ptools">
 * Personal Tools</a> project which contains several implementations of useful
 * algorithms for programming contests and small projects.
 *
 * @author Juan Carlos Galan Hernandez (Zenathark)
 * @see java.util.ArrayList
 * @since 1.8
 */

class IntArray {

// Private
  private int[] EMPTY_DATA = {};

//  Public
//  Instance variables are kept public in order to improve access performance.
//  Notice that if direct modifications are not handled with care, further array
//  operations can have undesired side effects.

  /** Amount of stored array, size <= array.length */
  public int   size;
  /** Internal array storage */
  public int[] data;


  /**
   * Creates an instance of a primitive integer array with 0 size and 0 length.
   */
  public IntArray() {
    this(0);
  }

  /**
   * Creates an instance of a primitive integer array with a given length.
   *
   * @param capacity the initial capacity of the array
   * @throws IllegalArgumentException if <tt>capacity</tt> is less than zero
   */
  public IntArray(int capacity) {
    if (capacity > 0) {
      this.data = new int[capacity];
    } else if (capacity == 0) {
      this.data = EMPTY_DATA;
    } else {
      throw new IllegalArgumentException(String.format("Illegal Capacity: %d", capacity));
    }
  }

  /**
   * Creates an instance of a primitive integer array which initial content will
   * be copied from the <tt>array</tt> argument. Its initial length will be equal to
   * initial number of elements.
   *
   * @param data the initial elements to be copied to the array.
   * @return a new instance of IntArray
   */
  public static IntArray seq(int... data) {
    IntArray arr = new IntArray(data.length);
    System.arraycopy(data, 0, arr.data, 0, data.length);
    arr.size = data.length;
    return arr;
  }

  /**
   * Appends a new element into the array. If the internal capacity is exceeded,
   * a new bigger array is created and all array is copied into the new array. The
   * old array is discarded.
   *
   * @param e the new element to be added to the array
   * @return this instance
   */
  final public int add(int e) {
    ensureCapacity(size + 1);
    data[size++] = e;
    return size-1;
  }

  /**
   * Inserts a new element into the array at the given index. If the internal
   * capacity is exceeded, a new bigger array is created and all array is copied
   * into the new array. The old array is discarded.
   *
   * @param index position of the new element
   * @param e the new element to be added to the array
   * @return this instance
   */
  final public IntArray add(int e, int index) {
    rangeCheckForAdd(index);
    ensureCapacity(size + 1);
    System.arraycopy(data, index, data, index + 1, size - index);
    data[index] = e;
    size++;
    return this;
  }

  /**
   * Inserts a new element into the array at the given index. If the internal
   * capacity is exceeded, a new bigger array is created and all array is copied
   * into the new array. The old array is discarded.
   *
   * @param e the new elements to be added to the array
   * @return this instance
   */
  final public IntArray addAll(int... e) {
    ensureCapacity(size + e.length);
    System.arraycopy(data, size, e,0, e.length);
    size += e.length;
    return this;
  }

  /**
   * Checks if the given index fits into the array.
   *
   * @param index The position to be checked
   */
  private void rangeCheckForAdd(int index) {
    if (index > size || index < 0)
      throw new IndexOutOfBoundsException(String.format("Index: %d, Size %d", index, size));
  }

  /**
   * Checks if the array has enough capacity. If the given capacity exceeds the length
   * of the array, another one is created with a length enough to accommodate the
   * new length. The old array is discarded.
   *
   * @param  minCapacity The capacity to be tested against the length of the internal array
   */
  final protected void ensureCapacity(int minCapacity) {
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

  /**
   * Reduces the length of the internal array to match the number of elements given
   * by <tt>size</tt>.
   */
  final public void trimToSize() {
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

  /**
   * Checks if the array is empty.
   *
   * @return true if the internal array is empty, false otherwise
   */
  final public boolean empty() {
    return size <= 0;
  }
}
