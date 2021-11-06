package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T> {
  protected BSTNode<T> root;

  public boolean isEmpty() {
    return root == null;
  }

  public int size() {
    return subtreeSize(root);
  }

  protected int subtreeSize(BSTNode<T> node) {
    if (node == null) {
      return 0;
    } else {
      return 1 + subtreeSize(node.getLeft()) + subtreeSize(node.getRight());
    }
  }

  public boolean contains(T t) {
    if (t == null)
      throw new NullPointerException();
    BSTNode<T> curNode = root;

    while (curNode != null){
      if (curNode.getData().compareTo(t) == 0)
        return true;
      if (curNode.getData().compareTo(t) < 0)
        curNode = curNode.getRight();
      else 
        curNode = curNode.getLeft();
    } 
    return false;
  }


  public boolean remove(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    boolean result = contains(t);
    if (result) {
      root = removeFromSubtree(root, t);
    }
    return result;
  }

  protected BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
    // node must not be null
    int result = t.compareTo(node.getData());
    if (result < 0) {
      node.setLeft(removeFromSubtree(node.getLeft(), t));
      return node;
    } else if (result > 0) {
      node.setRight(removeFromSubtree(node.getRight(), t));
      return node;
    } else { // result == 0
      if (node.getLeft() == null) {
        return node.getRight();
      } else if (node.getRight() == null) {
        return node.getLeft();
      } else { // neither child is null
        T predecessorValue = getHighestValue(node.getLeft());
        node.setLeft(removeRightmost(node.getLeft()));
        node.setData(predecessorValue);
        return node;
      }
    }
  }

  private T getHighestValue(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getData();
    } else {
      return getHighestValue(node.getRight());
    }
  }

  private BSTNode<T> removeRightmost(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getLeft();
    } else {
      node.setRight(removeRightmost(node.getRight()));
      return node;
    }
  }

  public T get(T t) {
    // TODO: Implement the get() method
    if (t == null)
      throw new NullPointerException();
    if (contains(t))
      return t;
    return null;
  }


  public void add(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    root = addToSubtree(root, new BSTNode<T>(t, null, null));
  }

  protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
    if (node == null) {
      return toAdd;
    }
    int result = toAdd.getData().compareTo(node.getData());
    if (result <= 0) {
      node.setLeft(addToSubtree(node.getLeft(), toAdd));
    } else {
      node.setRight(addToSubtree(node.getRight(), toAdd));
    }
    return node;
  }

  @Override
  public T getMinimum() {
    // TODO: Implement the getMinimum() method
    if (isEmpty())
      return null;
    if (size() == 1)
      return root.getData();

    BSTNode<T> curNode = root;
    while (curNode.getLeft() != null)
      curNode = curNode.getLeft();
    return curNode.getData();
  }


  @Override
  public T getMaximum() {
    // TODO: Implement the getMaximum() method
    if (isEmpty())
      return null;
    if (size() == 1)
      return root.getData();
    
    BSTNode<T> curNode = root;
    while (curNode.getRight() != null)
      curNode = curNode.getRight();
    return curNode.getData();
  }


  @Override
  public int height() {
    // TODO: Implement the height() method
    if (isEmpty())
      return -1;
    return heightOfNode(root);
  }

  private int heightOfNode(BSTNode<T> node){
    if (node == null)
      return -1;
    int leftHeight = heightOfNode(node.getLeft());
    int rightHeight = heightOfNode(node.getRight());
    if (leftHeight < rightHeight)
      return 1 + rightHeight;
    else
      return 1 + leftHeight;
  }


  public Iterator<T> preorderIterator() {
    // TODO: Implement the preorderIterator() method
    Queue<T> queue = new LinkedList<T>();
    preorderTraverse(queue, root);
    return queue.iterator();
  }


  private void preorderTraverse(Queue<T> queue, BSTNode<T> node){
    if (node != null){
      queue.add(node.getData());
      preorderTraverse(queue, node.getLeft());
      preorderTraverse(queue, node.getRight());
    }
  }


  public Iterator<T> inorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    inorderTraverse(queue, root);
    return queue.iterator();
  }


  public Iterator<T> inorderIterator(BSTNode<T> subtreeRoot){
    Queue<T> queue = new LinkedList<T>();
    inorderTraverse(queue, subtreeRoot);
    return queue.iterator();
  }


  private void inorderTraverse(Queue<T> queue, BSTNode<T> node){
    if (node != null) {
      inorderTraverse(queue, node.getLeft());
      queue.add(node.getData());
      inorderTraverse(queue, node.getRight());
    }
  }

  public Iterator<T> postorderIterator() {
    // TODO: Implement the postorderIterator() method
    Queue<T> queue= new LinkedList<T>();
    postorderTraverse(queue, root);
    return queue.iterator();
  }

  private void postorderTraverse(Queue<T> queue, BSTNode<T> node){
    if (node != null){
      postorderTraverse(queue, node.getLeft());
      postorderTraverse(queue, node.getRight());
      queue.add(node.getData());
    }
  }


  @Override
  public boolean equals(BSTInterface<T> other) {
    // TODO: Implement the equals() method
    if(other == null)
      throw new NullPointerException();
    BSTNode<T> thisCurNode = root;
    BSTNode<T> otherCurNode = other.getRoot();

    return subtreeEquals(thisCurNode, otherCurNode);
  }

  private boolean subtreeEquals(BSTNode<T> thisCurNode, BSTNode<T> otherNode){
    boolean leftEqual;
    boolean rightEqual;

    if (thisCurNode == null && otherNode == null)
      return true;
    if (thisCurNode == null || otherNode == null)
      return false;
    if (thisCurNode.getData().compareTo(otherNode.getData()) != 0){
      return false;
    }
    leftEqual = subtreeEquals(thisCurNode.getLeft(), otherNode.getLeft());
    rightEqual = subtreeEquals(thisCurNode.getRight(), otherNode.getRight());
    return leftEqual && rightEqual;
  }


  @Override
  public boolean sameValues(BSTInterface<T> other) {
    if(other == null)
      throw new NullPointerException();
    Iterator<T> thisIterator = inorderIterator();
    Iterator<T> otherIterator = other.inorderIterator();

    if ((!thisIterator.hasNext() && otherIterator.hasNext()) ||
        (thisIterator.hasNext() && !otherIterator.hasNext()))
      return false;

    while(thisIterator.hasNext() && otherIterator.hasNext()){
      if (thisIterator.next().compareTo(otherIterator.next()) != 0)
        return false;
    }
    return true;
  }


  @Override
  public boolean isBalanced(){
    if (isEmpty())
      return true;
    return (subtreeHeight(root) != -2);
  }


  private int subtreeHeight(BSTNode<T> node){
    int leftHeight;
    int rightHeight;
    int balancedFactor;

    if (node == null)
      return -1;
    leftHeight = subtreeHeight(node.getLeft());
    rightHeight = subtreeHeight(node.getRight());
    balancedFactor = leftHeight - rightHeight;
    if (leftHeight == -2 || rightHeight == -2)
      return -2;
    if (balancedFactor > 1 || balancedFactor < -1)
      return -2;
    if (leftHeight < rightHeight)
      return 1 + rightHeight;
    else
      return 1 + leftHeight;
  }


  @Override
  @SuppressWarnings("unchecked")
  public void balance() {
    Iterator<T> inorderIter = inorderIterator();
    int size = size();
    T[] values = (T[]) new Comparable[size];

    for (int i = 0; i < size; i++){
      values[i] = inorderIter.next();
    }
    root = null;
    balanceHelper(values, 0, size - 1);  
  }


  protected void balanceSubtree(BSTNode<T> subtreeRoot){
    Iterator<T> inorderIter = inorderIterator(subtreeRoot);
    int size = subtreeSize(subtreeRoot);
    T[] values = (T[]) new Comparable[size];

    for (int i = 0; i < size; i++){
      values[i] = inorderIter.next();
    }
    balanceHelper(values, 0, size - 1); 
  }


  protected void balanceHelper(T[] values, int low, int high){
    int mid;

    if (low == high){
      add(values[low]);
    }
    else if((low + 1) == high){
      add(values[low]);
      add(values[high]);
    }
    else{
      mid = (low + high) / 2;
      add(values[mid]);
      balanceHelper(values, low, mid - 1);
      balanceHelper(values, mid + 1, high);
    }
  }

  @Override
  public BSTNode<T> getRoot() {
    // DO NOT MODIFY
    return root;
  }

  public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
    // header
    int count = 0;
    String dot = "digraph G { \n";
    dot += "graph [ordering=\"out\"]; \n";
    // iterative traversal
    Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
    queue.add(root);
    BSTNode<T> cursor;
    while (!queue.isEmpty()) {
      cursor = queue.remove();
      if (cursor.getLeft() != null) {
        // add edge from cursor to left child
        dot += cursor.getData().toString() + " -> " + cursor.getLeft().getData().toString() + ";\n";
        queue.add(cursor.getLeft());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count + ";\n";
        count++;
      }
      if (cursor.getRight() != null) {
        // add edge from cursor to right child
        dot +=
            cursor.getData().toString() + " -> " + cursor.getRight().getData().toString() + ";\n";
        queue.add(cursor.getRight());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count + ";\n";
        count++;
      }
    }
    dot += "};";
    return dot;
  }

  public static void main(String[] args) {
    for (String r : new String[] {"a", "b", "c", "d", "e", "f", "g"}) {
      BSTInterface<String> tree = new BinarySearchTree<String>();
      for (String s : new String[] {"d", "b", "a", "c", "f", "e", "g"}) {
        tree.add(s);
      }
      Iterator<String> iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.preorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.postorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();

      System.out.println(tree.remove(r));

      iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
    }

    BSTInterface<String> tree = new BinarySearchTree<String>();
    for (String r : new String[] {"a", "b", "c", "d", "e", "f", "g"}) {
      tree.add(r);
    }
    System.out.println(toDotFormat(tree.getRoot()));
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
    tree.balance();
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
  }
}
