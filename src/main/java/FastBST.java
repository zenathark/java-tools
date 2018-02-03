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
  //Payload

  public FastBST(int label) {
    this(label, null, null, null);
  }

  public FastBST(int label, FastBST left, FastBST right, FastBST parent) {
    this.label  = label;
    this.left   = left;
    this.right  = right;
    this.parent = parent;
  }

  public int getLabel() {
    return label;
  }

  public String toString() {
    String l = (left == null)? "" : left.toString();
    String r = (right == null)? "" : right.toString();
    String p = (parent == null)? "" : parent.toString();
    return String.format("{%d, L: %s, R: %s, P: %s}", label, l, r, p);
  }

  public static class BinaryTreeFn {

    public static FastBST insert(FastBST root, FastBST newNode) {
      FastBST cNode = root;
      while (true) {
        if (newNode.getLabel() < cNode.getLabel()) {
          if (cNode.left != null)
            cNode = cNode.left;
          else {
            cNode.left = newNode;
            break;
          }
        } else {
          if (cNode.right != null)
            cNode = cNode.right;
          else {
            cNode.right = newNode;
            break;
          }
        }
      }
      return root;
    }

  }
}
