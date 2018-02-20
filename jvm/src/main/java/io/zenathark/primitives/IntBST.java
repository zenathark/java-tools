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
import static io.zenathark.tools.Misc.*;

/**
 * This class is an implementation of a Binary Search Tree (BST) using
 * primitive int indexes and int content. Its purpose is
 * mainly for programming contests.
 *
 * <p>The problem with using Objects and Classes for BST implementation is that in
 * large enough trees the garbage collector could end up giving long pauses while
 * it searches for all the three looking for non-referenced objects. Instead, this
 * implementation uses a primitive int for indexation and it takes another int for
 * sorting. It will hold two integer arrays internally, a hash index and its associated
 * values.
 *
 * <p>This class is a member of the <a href="https://github.com/zenathark/ptools">
 * Personal Tools</a> project which contains several implementations of useful
 * algorithms for programming contests and small projects.
 *
 * @author Juan Carlos Galan Hernandez (Zenathark)
 * @since 1.8
 */

public class IntBST {
  // Private
  private static final int ROOT = 0;

  /** Array holding the stored values */
  final private IntArray data = new IntArray();
  /**
   * A hash table referencing the data. Each data is a triad:
   * <tt>table[i]</tt> The index of the data
   * <tt>table[i+1]</tt> Left node
   * <tt>table[i+2]</tt> Right node
   *
   */
  final private IntArray table = new IntArray();

  final public IntBST push(int val) {
    final int idx = data.add(val);
    if (idx == ROOT) {
      table.addAll(idx, -1, -1, -1);
    }
    int cNode = ROOT;
    while (true) {
      if (val < get(cNode)) {
        if (hasLeftNode(cNode)) cNode = leftNode(cNode);
        else {
          setLeftNode(cNode, idx);
          setParent(idx, cNode);
          break;
        }
      } else if (val > get(cNode)) {
        if (hasRightNode(cNode)) cNode = rightNode(cNode);
        else {
          setRightNode(cNode, idx);
          setParent(idx, cNode);
          break;
        }
      } else { // Same labels are not allowed
        throw new IllegalArgumentException(Sprintf("Error: Repeated value %i", val));
      }
    }
    return this;
  }

  final public int search(int val) {
    int ans = ROOT;
    while (ans >= 0) {
      if (val == get(ans)) break;
      if (val < get(ans)) {
        ans = leftNode(ans);
      } else {
        ans = rightNode(ans);
      }
    }
    return ans;
  }

  /**
   * Remove a node. This function removes a node and replaces it with is in-order predecessor.
   *
   * @param val the value to be removed
   */
  final public IntBST remove(int val) {
    int d = search(val);
    if (d < 0) return this;
    if (!hasLeftNode(d) && !hasRightNode(d)) {
      if (leftNode(parent(d)) == val) setLeftNode(d, -1);
      else setRightNode(d, -1);
    } else if (!hasLeftNode(d) && hasRightNode(d)) {
      if (leftNode(parent(d)) == val) setLeftNode(parent(d), rightNode(d));
      else setRightNode(parent(d), rightNode(d));
    } else if (hasLeftNode(d) && !hasRightNode(d)) {
      if (leftNode(parent(d)) == val) setLeftNode(parent(d), leftNode(d));
      else setRightNode(parent(d), leftNode(d));
    } else {
      int rep = leftNode(d);
      while (rightNode(rep) >= 0) rep = rightNode(rep);
      if (leftNode(parent(d)) == val) setLeftNode(parent(d), rep);
      else setRightNode(parent(d), rep);
    }
    return this;
  }

  final public IntBST rotateLeft(int pivot) {
    int parent = parent(pivot);
    setRightNode(parent, leftNode(pivot));
    setLeftNode(pivot, parent);
    return this;
  }

  final public IntBST rotateRight(int pivot) {
    int parent = parent(pivot);
    setLeftNode(parent, rightNode(pivot));
    setRightNode(pivot, parent);
    return this;
  }

  private void setParent(int i, int v) {
    table.data[i*3 + 1] = v;
  }

  private void setLeftNode(int i, int v) {
    table.data[i*3 + 2] = v;
  }

  private void setRightNode(int i, int v) {
    table.data[i*3 + 3] = v;
  }

  private int parent(int i) {
    return table.data[i*3+1];
  }

  private int leftNode(int i) {
    return table.data[i*3+2];
  }

  private int rightNode(int i) {
    return table.data[i*3+3];
  }

  private boolean hasParent(int i) {
    return table.data[i*3+1] < 0;
  }

  private boolean hasLeftNode(int i) {
    return table.data[i*3+2] < 0;
  }

  private boolean hasRightNode(int i) {
    return table.data[i*3+3] < 0;
  }

  private int size() {
    return table.size;
  }

  private int get(int idx) {
    return data.data[table.data[idx]];
  }
}
