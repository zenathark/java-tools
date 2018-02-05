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

  public FastBST(int label) {
    this(label, null, null, null);
  }

  public FastBST(int label, FastBST left, FastBST right, FastBST parent) {
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

  /**
   * Inserts a new leaf into the tree.
   *
   * @param root Root of the BST tree.
   * @param newNode Node to be inserted as a leaf.
   */
  public static FastBST insert(FastBST root, FastBST newNode) {
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

  public class PreOrder implements Iterator<FastBST> {}

  public class InOrder implements Iterator<FastBST> {}

  public class PostOrder implements Iterator<FastBST> {}

  public class LevelOrder implements Iterator<FastBST> {}
}
