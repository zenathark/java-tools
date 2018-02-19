package io.zenathark.tools;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.util.function.*;

public class FastBSTp {
  public FastBSTp right;
  public FastBSTp left;
  public FastBSTp parent;
  public int label;
  // Payload

  public static FastBSTp createRoot(int label) {
    return new FastBSTp(label);
  }

  private FastBSTp(int label) {
    this(label, null, null, null);
  }

  private FastBSTp(int label, FastBSTp left, FastBSTp right, FastBSTp parent) {
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

  public static FastBSTp insert(FastBSTp root, int label) {
    return insert(root, new FastBSTp(label));
  }
  /**
   * Inserts a new leaf into the tree.
   *
   * @param root Root of the BST tree.
   * @param newNode Node to be inserted as a leaf.
   */
  public static FastBSTp insert(FastBSTp root, FastBSTp newNode) {
    if (root == null) return newNode;
    FastBSTp cNode = root;
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

  public static FastBSTp search(FastBSTp root, int label) {
    FastBSTp ans = root;
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
  public static FastBSTp remove(FastBSTp root, int label) {
    FastBSTp d = search(root, label);
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
      FastBSTp rep = d.left;
      while (rep.right != null) rep = rep.right;
      if (d.parent.left.label == d.label) d.parent.left = rep;
      else d.parent.right = rep;
    }
    return root;
  }

  public static FastBSTp rotateLeft(FastBSTp root, FastBSTp pivot) {
    FastBSTp parent = pivot.parent;
    parent.right = pivot.left;
    pivot.left = parent;
    return root;
  }

  public static FastBSTp rotateRight(FastBSTp root, FastBSTp pivot) {
    FastBSTp parent = pivot.parent;
    parent.left = pivot.right;
    pivot.right = parent;
    return root;
  }

  public static void preOrder(FastBSTp root) {
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
}
