package io.zenathark.tools;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.util.function.*;

public class FastBST {
  public FastBST right;
  public FastBST left;
  public FastBST parent;
  public int label;
  // Payload

  public static FastBST createRoot(int label) {
    return new FastBST(label);
  }

  private FastBST(int label) {
    this(label, null, null, null);
  }

  private FastBST(int label, FastBST left, FastBST right, FastBST parent) {
    this.label = label;
    this.left = left;
    this.right = right;
    this.parent = parent;
  }

  public int getLabel() {
    return label;
  }

  /** Simple representation of the node. Mostly used for debugging. */
  public String toString() {
    String l = (left == null) ? "" : left.toString();
    String r = (right == null) ? "" : right.toString();
    String p = (parent == null) ? "" : parent.toString();
    return String.format("{%d, L: %s, R: %s, P: %s}", label, l, r, p);
  }

  public static FastBST insert(FastBST root, int label) {
    return insert(root, new FastBST(label));
  }
  /**
   * Inserts a new leaf into the tree.
   *
   * @param root Root of the BST tree.
   * @param newNode Node to be inserted as a leaf.
   */
  public static FastBST insert(FastBST root, FastBST newNode) {
    if (root == null) return newNode;
    FastBST cNode = root;
    while (true) {
      if (newNode.getLabel() < cNode.getLabel()) {
        if (cNode.left != null) cNode = cNode.left;
        else {
          cNode.left = newNode;
          break;
        }
      } else if (newNode.getLabel() > cNode.getLabel()) {
        if (cNode.right != null) cNode = cNode.right;
        else {
          cNode.right = newNode;
          break;
        }
      } else { // Same labels are not allowed
        break;
      }
    }
    return root;
  }

  public static FastBST search(FastBST root, int label) {
    FastBST ans = root;
    while (ans != null) {
      if (ans.label == label) break;
      else if (ans.label < label) {
        ans = ans.right;
        break;
      } else {
        ans = ans.left;
        break;
      }
    }
    return ans;
  }

  /**
   * Remove a node. This function removes a node and replaces it with is in-order predecessor.
   *
   * @param root BST's root
   * @param label Node's label to be removed
   */
  public static FastBST remove(FastBST root, int label) {
    FastBST d = search(root, label);
    if (d.left == null && d.right == null) {
      if (d.parent.left.label == d.label) d.parent.left = null;
      else d.parent.right = null;

    } else if (d.left == null && d.right != null) {
      if (d.parent.left.label == d.label) d.parent.left = d.right;
      else d.parent.right = d.right;

    } else if (d.left != null && d.right == null) {
      if (d.parent.left.label == d.label) d.parent.left = d.left;
      else d.parent.right = d.left;

    } else {
      FastBST rep = d.left;
      while (rep.right != null) rep = rep.right;
      if (d.parent.left.label == d.label) d.parent.left = rep;
      else d.parent.right = rep;
    }
    return root;
  }

  public static FastBST rotateLeft(FastBST root, FastBST pivot) {
    FastBST parent = pivot.parent;
    parent.right = pivot.left;
    pivot.left = parent;
    return root;
  }

  public static FastBST rotateRight(FastBST root, FastBST pivot) {
    FastBST parent = pivot.parent;
    parent.left = pivot.right;
    pivot.right = parent;
    return root;
  }

  public Iterable<FastBST> preorderIterator() {
    return new Iterable<FastBST>() {
      public Iterator<FastBST> iterator() {
        return new PreOrder(FastBST.this);
      }
    };
  }

  public static void preOrder(FastBST root) {
    for (FastBST e : root.preorderIterator()) {
      // Test function
      int t = e.label + 5;
      // End Test Function
    }
  }

  public static void preOrder2(FastBSTp root) {
    ArrayDeque<FastBSTp> queue = new ArrayDeque<>();
    queue.addLast(root);
    while (!queue.isEmpty()) {
      FastBSTp ans = queue.removeFirst();
      // Test function
      int t = ans.label + 5;
      // End Test Function
      if (ans.left != null) queue.addFirst(ans.left);
      if (ans.right != null) queue.addFirst(ans.right);
    }
  }

  class PreOrder implements Iterator<FastBST> {
    ArrayDeque<FastBST> queue;

    public PreOrder(FastBST root) {
      queue = new ArrayDeque<>();
      queue.addLast(root);
    }

    @Override
    public boolean hasNext() {
      return !queue.isEmpty();
    }

    @Override
    public FastBST next() {
      FastBST ans = queue.removeFirst();
      if (ans.left != null) queue.addFirst(ans.left);
      if (ans.right != null) queue.addFirst(ans.right);
      return ans;
    }
  }

  class InOrder implements Iterator<FastBST> {
    ArrayDeque<FastBST> queue;
    FastBST cNode;

    public InOrder(FastBST root) {
      queue = new ArrayDeque<>();
      cNode = root;
    }

    @Override
    public boolean hasNext() {
      return !queue.isEmpty() || cNode != null;
    }

    @Override
    public FastBST next() {
      FastBST ans;
      while (cNode != null) {
        queue.addFirst(cNode);
        cNode = cNode.left;
      }
      ans = queue.removeFirst();
      cNode = ans.right;
      return ans;
    }
  }

  class PostOrder implements Iterator<FastBST> {
    ArrayDeque<FastBST> queue;
    FastBST cNode;

    public PostOrder(FastBST root) {
      queue = new ArrayDeque<>();
      cNode = root;
    }

    @Override
    public boolean hasNext() {
      return !queue.isEmpty() || cNode != null;
    }

    @Override
    public FastBST next() {
      FastBST ans;
      while (cNode != null) {
        queue.addFirst(cNode);
        cNode = cNode.left;
      }
      ans = queue.removeFirst();
      cNode = ans.right;
      return ans;
    }
  }

  class LevelOrder implements Iterator<FastBST> {
    ArrayDeque<FastBST> queue;
    FastBST cNode;

    public LevelOrder(FastBST root) {
      queue = new ArrayDeque<>();
      cNode = root;
    }

    @Override
    public boolean hasNext() {
      return !queue.isEmpty() || cNode != null;
    }

    @Override
    public FastBST next() {
      FastBST ans;
      while (cNode != null) {
        queue.addFirst(cNode);
        cNode = cNode.left;
      }
      ans = queue.removeFirst();
      cNode = ans.right;
      return ans;
    }
  }
}
