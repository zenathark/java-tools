package io.zenathark.tools;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.util.function.*;

public class ParameterBST<T extends Comparable<T>> {
  public ParameterBST<T> right;
  public ParameterBST<T> left;
  public ParameterBST<T> parent;
  public T payload;

  public static <T extends Comparable<T>> ParameterBST<T> createRoot(T payload) {
    return new ParameterBST<>(payload);
  }

  private ParameterBST(T payload) {
    this(payload, null, null, null);
  }

  private ParameterBST(
      T payload, ParameterBST<T> left, ParameterBST<T> right, ParameterBST<T> parent) {
    this.payload = payload;
    this.left = left;
    this.right = right;
    this.parent = parent;
  }

  public T getPayload() {
    return payload;
  }

  /** Simple representation of the node. Mostly used for debugging. */
  public String toString() {
    String l = (left == null) ? "" : left.toString();
    String r = (right == null) ? "" : right.toString();
    String p = (parent == null) ? "" : parent.toString();
    return String.format("{%d, L: %s, R: %s, P: %s}", payload, l, r, p);
  }

  public static <T extends Comparable<T>> ParameterBST<T> insert(ParameterBST<T> root, T payload) {
    return insert(root, new ParameterBST<>(payload));
  }
  /**
   * Inserts a new leaf into the tree.
   *
   * @param root Root of the BST tree.
   * @param newNode Node to be inserted as a leaf.
   */
  public static <T extends Comparable<T>> ParameterBST<T> insert(
      ParameterBST<T> root, ParameterBST<T> newNode) {
    if (root == null) return newNode;
    ParameterBST<T> cNode = root;
    while (true) {
      if (newNode.getPayload().compareTo(cNode.getPayload()) < 0) {
        if (cNode.left != null) cNode = cNode.left;
        else {
          cNode.left = newNode;
          break;
        }
      } else if (newNode.getPayload().compareTo(cNode.getPayload()) > 0) {
        if (cNode.right != null) cNode = cNode.right;
        else {
          cNode.right = newNode;
          break;
        }
      } else { // Same payloads are not allowed
        break;
      }
    }
    return root;
  }

  public static <T extends Comparable<T>> ParameterBST<T> search(ParameterBST<T> root, T payload) {
    ParameterBST<T> ans = root;
    while (ans != null) {
      if (ans.payload == payload) break;
      else if (ans.payload.compareTo(payload) < 0) {
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
   * @param payload Node's payload to be removed
   */
  public static <T extends Comparable<T>> ParameterBST<T> remove(ParameterBST<T> root, T payload) {
    ParameterBST<T> d = search(root, payload);
    if (d.left == null && d.right == null) {
      if (d.parent.left.payload == d.payload) d.parent.left = null;
      else d.parent.right = null;

    } else if (d.left == null && d.right != null) {
      if (d.parent.left.payload == d.payload) d.parent.left = d.right;
      else d.parent.right = d.right;

    } else if (d.left != null && d.right == null) {
      if (d.parent.left.payload == d.payload) d.parent.left = d.left;
      else d.parent.right = d.left;

    } else {
      ParameterBST<T> rep = d.left;
      while (rep.right != null) rep = rep.right;
      if (d.parent.left.payload == d.payload) d.parent.left = rep;
      else d.parent.right = rep;
    }
    return root;
  }

  public static <T extends Comparable<T>> ParameterBST<T> rotateLeft(
      ParameterBST<T> root, ParameterBST<T> pivot) {
    ParameterBST<T> parent = pivot.parent;
    parent.right = pivot.left;
    pivot.left = parent;
    return root;
  }

  public static <T extends Comparable<T>> ParameterBST<T> rotateRight(
      ParameterBST<T> root, ParameterBST<T> pivot) {
    ParameterBST<T> parent = pivot.parent;
    parent.left = pivot.right;
    pivot.right = parent;
    return root;
  }

  public Iterable<ParameterBST<T>> preorderIterator() {
    return new Iterable<ParameterBST<T>>() {
      public Iterator<ParameterBST<T>> iterator() {
        return new PreOrder(ParameterBST.this);
      }
    };
  }

  // Static pre order traversal, only for hard code editing
  // This is not faster than the iterator, see benchmark
  public static <T extends Comparable<T>> void preOrder(ParameterBST<T> root) {
    ArrayDeque<ParameterBST<T>> queue = new ArrayDeque<>();
    queue.addLast(root);
    while (!queue.isEmpty()) {
      ParameterBST<T> ans = queue.removeFirst();
      // Test function
      // int t = ans.payload + 5;
      // End Test Function
      if (ans.left != null) queue.addFirst(ans.left);
      if (ans.right != null) queue.addFirst(ans.right);
    }
  }

  class PreOrder implements Iterator<ParameterBST<T>> {
    ArrayDeque<ParameterBST<T>> queue;

    public PreOrder(ParameterBST<T> root) {
      queue = new ArrayDeque<>();
      queue.addLast(root);
    }

    @Override
    public boolean hasNext() {
      return !queue.isEmpty();
    }

    @Override
    public ParameterBST<T> next() {
      ParameterBST<T> ans = queue.removeFirst();
      if (ans.left != null) queue.addFirst(ans.left);
      if (ans.right != null) queue.addFirst(ans.right);
      return ans;
    }
  }

  class InOrder implements Iterator<ParameterBST<T>> {
    ArrayDeque<ParameterBST<T>> queue;
    ParameterBST<T> cNode;

    public InOrder(ParameterBST<T> root) {
      queue = new ArrayDeque<>();
      cNode = root;
    }

    @Override
    public boolean hasNext() {
      return !queue.isEmpty() || cNode != null;
    }

    @Override
    public ParameterBST<T> next() {
      ParameterBST<T> ans;
      while (cNode != null) {
        queue.addFirst(cNode);
        cNode = cNode.left;
      }
      ans = queue.removeFirst();
      cNode = ans.right;
      return ans;
    }
  }

  class PostOrder implements Iterator<ParameterBST<T>> {
    ArrayDeque<ParameterBST<T>> queue;
    ParameterBST<T> cNode;

    public PostOrder(ParameterBST<T> root) {
      queue = new ArrayDeque<>();
      cNode = root;
    }

    @Override
    public boolean hasNext() {
      return !queue.isEmpty() || cNode != null;
    }

    @Override
    public ParameterBST<T> next() {
      ParameterBST<T> ans;
      while (cNode != null) {
        queue.addFirst(cNode);
        cNode = cNode.left;
      }
      ans = queue.removeFirst();
      cNode = ans.right;
      return ans;
    }
  }

  class LevelOrder implements Iterator<ParameterBST<T>> {
    ArrayDeque<ParameterBST<T>> queue;
    ParameterBST<T> cNode;

    public LevelOrder(ParameterBST<T> root) {
      queue = new ArrayDeque<>();
      cNode = root;
    }

    @Override
    public boolean hasNext() {
      return !queue.isEmpty() || cNode != null;
    }

    @Override
    public ParameterBST<T> next() {
      ParameterBST<T> ans;
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
