package structures;

import javax.lang.model.util.ElementScanner6;

public class ScapegoatTree<T extends Comparable<T>> extends BinarySearchTree<T> {
  private int upperBound;


  @Override
  public void add(T t) {
    BSTNode<T> newNode = new BSTNode<T>(t, null, null);
    BSTNode<T> scapegoat;
    BSTNode<T> scapegoatParent;
    upperBound++;
    if (t == null) {
      throw new NullPointerException();
    }
    root = addToSubtree(root, newNode);
    root.setParent(null);
    if (!obeyRule1() || !obeyRule2()){
      scapegoat = findScapegoat(newNode);
      if (scapegoat == root){
        root = null;
      }
      else{
        scapegoatParent = scapegoat.getParent();
        if (scapegoatParent.getLeft() == scapegoat)
          scapegoatParent.setLeft(null);
        else
          scapegoatParent.setRight(null);
      }
      upperBound = size();
      balanceSubtree(scapegoat);
      upperBound = size();
    }
  }

  @Override 
  protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd){
    if (node == null) {
      return toAdd;
    }
    int result = toAdd.getData().compareTo(node.getData());
    if (result <= 0) {
      node.setLeft(addToSubtree(node.getLeft(), toAdd));
      node.getLeft().setParent(node);
    } else {
      node.setRight(addToSubtree(node.getRight(), toAdd));
      node.getRight().setParent(node);
    }
    return node;
  }

  private boolean obeyRule1(){
    int size = size(); 
    boolean check1 = (size * 2 >= upperBound);
    return (check1 && (size <= upperBound));
  }

  private boolean obeyRule2(){
    int height = height();
    double n = Math.log((double)upperBound);
    double d = Math.log((double)3/2);
    double heightBound = n / d;
    return height <= heightBound;
  }

  private BSTNode<T> findScapegoat(BSTNode<T> newNode){
    if ((double)subtreeSize(newNode) / subtreeSize(newNode.getParent()) > (double)2/3)
      return newNode.getParent();
    else
      return findScapegoat(newNode.getParent());
  }


  @Override
  public boolean remove(T element) {
    if (element == null) {
      throw new NullPointerException();
    }
    boolean result = contains(element);
    if (result) {
      root = removeFromSubtree(root, element);
    }
    if (!obeyRule1() || !obeyRule2()){
      upperBound = 0;
      balance();
      upperBound = size();
    }
    return result;
  }

  public static void main(String[] args) {
    BSTInterface<String> tree = new ScapegoatTree<String>();
    /*
    You can test your Scapegoat tree here.*/
    for (String r : new String[] {"0", "1", "2", "3", "4"}) {
      tree.add(r);
      System.out.println(toDotFormat(tree.getRoot()));
    }
    
  }
}
