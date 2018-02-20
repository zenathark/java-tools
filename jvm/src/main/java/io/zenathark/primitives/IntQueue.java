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
 * This class is a re-implementation of a variable length Queue. Its purpose is
 * mainly for programming contests.
 *
 * <p>The problem with the standard JDK implementations, in particular with
 * <tt>Deque</tt> is that it requires the use of Boxed numbers. Such restriction
 * can potentially impact the running time of a program. In order to avoid such
 * problem, this class uses <tt>IntArray</tt> internally.
 *
 * <p>This class is a member of the <a href="https://github.com/zenathark/ptools">
 * Personal Tools</a> project which contains several implementations of useful
 * algorithms for programming contests and small projects.
 *
 * @author Juan Carlos Galan Hernandez (Zenathark)
 * @see IntArray
 * @since 1.8
 */

public class IntQueue {
  // Private

  // Public
  /** Internal array storage */
  final public IntArray array;

  public IntQueue() {
    this(0);
  }

  public IntQueue(int initialCapacity) {
    array = new IntArray(initialCapacity);
  }

  /**
   * Appends a new element to the Queue.
   *
   * @param e The element to be added at the end of the queue
   * @return this instance
   */
  public IntQueue push(int e) {
    array.add(e);
    return this;
  }

  /**
   * Returns the first element of the queue and deletes it. The deletion
   * is done using <tt>System.arraycopy</tt> for performance. The length of
   * the internal array is not modified.
   *
   * @return the element at the beginning of the queue
   * @see java.lang.System
   */
  public int pop() {
    int[] oldData = array.data;
    array.data = new int[array.size - 1];
    System.arraycopy(oldData, 1, array.data, 0, array.size - 1);
    array.size--;
    return oldData[0];
  }

  /**
   * Adds multiple elements at once into the queue. The order of the
   * given elements is preserved, inserting index 0 first, index 1
   * later and so on.
   *
   * @param es the new elements to be inserted in order to the queue
   * @return this instance
   */
  public IntQueue push(int... es) {
    array.ensureCapacity(array.size + es.length);
    System.arraycopy(es, 0, array.data, array.size, es.length);
    array.size += es.length;
    return this;
  }

  /**
   * Checks if the queue is empty.
   *
   * @return true if the internal array is empty, false otherwise
   */
  public boolean empty() {
    return array.size <= 0;
  }
}
