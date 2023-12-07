package avltree;

import org.omg.CORBA.Any;

import javafx.scene.Node;

class AvlNode<AnyType> {
    AnyType element;
    AvlNode<AnyType> left;
    AvlNode<AnyType> right;
    int height;

    public AvlNode(AnyType e, AvlNode<AnyType> left, AvlNode<AnyType> right) {
        element = e;
        this.left = left;
        this.right = right;
    }

    public String tString() {
        return left + " " + right;
    }
}

class AvlTree<AnyType extends Comparable<? super AnyType>> {
    private AvlNode<AnyType> root;

    public AvlTree() {
        root = null;
    }

    private int height(AvlNode<AnyType> t) {
        return t == null ? -1 : t.height;
    }

    private AnyType elementAt(AvlNode<AnyType> t) {
        return t == null ? null : t.element;
    }

    public int max(int lh, int rh) { // menentukan lh atau rh yang mana lebih tinggi
        if (lh > rh) {
            return lh;
        }
        return rh;
    }

    private AvlNode<AnyType> case1(AvlNode<AnyType> k2) {
        AvlNode<AnyType> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = max(height(k2.left), height(k2.right)) + 1;
        k1.height = max(height(k1.left), k2.height) + 1;
        return k1;
    }

    private AvlNode<AnyType> case4(AvlNode<AnyType> k1) {
        AvlNode<AnyType> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = max(height(k1.left), height(k1.right)) + 1;
        k2.height = max(height(k2.right), k1.height) + 1;
        return k2;
    }

    private AvlNode<AnyType> case2(AvlNode<AnyType> k3) {
        k3.left = case4(k3.left); // Arah rotai ke kiri
        return case1(k3); // Arah rotasi ke kanan
    }

    private AvlNode<AnyType> case3(AvlNode<AnyType> k1) {
        k1.right = case1(k1.right);
        return case4(k1);
    }

    private AvlNode<AnyType> insert(AnyType x, AvlNode<AnyType> t) {
        if (t == null) {
            t = new AvlNode<AnyType>(x, null, null);
        }
        // Case 1 edan Case 2 (Pohon mengalami LH)
        else if (x.compareTo(t.element) < 0) {
            t.left = insert(x, t.left);

            if (height(t.left) - height(t.right) > 1) { // Mengalami LH
                if (x.compareTo(t.left.element) < 0) {
                    t = case1(t); // Single rotasi
                } else {
                    t = case2(t); // Double Rotasi
                }
            }
        }
        // CASE 3 DAM CASE 4 (Pohon mengalami RH)
        else if (x.compareTo(t.element) > 0) {
            t.right = insert(x, t.right);
            if (height(t.right) - height(t.left) > 1) { // tidak seimbang
                if (x.compareTo(t.right.element) > 0) {
                    t = case4(t); // Single rotasi
                } else {
                    t = case3(t); // Double Rotasi
                }
            }
        }
        t.height = max(height(t.left), height(t.right)) + 1;
        return t;
    }

    public void insert(AnyType x) {
        root = insert(x, root);
    }

    private void inOrder(AvlNode<AnyType> t) {
        if (t != null) {
            if (t.left != null) {
                inOrder(t.left);
            }
            System.out.print(t.element + " ");

            if (t.right != null) {
                inOrder(t.right);
            }
        }
    }

    public void inOrder() {
        if (root == null) {
            System.out.println("Empty Avl Tree");
        } else {
            inOrder(root);
        }
    }

    private void preOrder(AvlNode<AnyType> t) {
        System.out.print(t.element + " ");

        if (t != null) {
            if (t.left != null) {
                preOrder(t.left);
            }
            if (t.right != null) {
                preOrder(t.right);
            }
        }
    }

    public void preOrder() {
        if (root == null) {
            System.out.println("Empty Avl Tree");
        } else {
            preOrder(root);
        }
    }

    private void postOrder(AvlNode<AnyType> t) {
        if (t != null) {
            if (t.left != null) {
                postOrder(t.left);
            }
            if (t.right != null) {
                postOrder(t.right);
            }
            System.out.print(t.element + " ");
        }
    }

    public void postOrder() {
        if (root == null) {
            System.out.println("Empty Avl Tree");
        } else {
            postOrder(root);
        }
    }
    private AvlNode<AnyType> balance (AvlNode<AnyType> t){
        if (t == null){
            return null;
        }
        if(height(t.left) - height(t.right) > 1){ //tree LH
            if(height(t.left.left) >= height(t.left.right)){ // left subString LH
                t = case1(t);
            }else{ // Left subtree  RH
                t = case2(t);
            }
        }else if(height(t.right) - height(t.left) >1 ){ //tree RH
            if(height(t.right.right) >= height(t.right.left)){ // right subtree RH
                t = case4(t);
            }else{ // right subtree LH
                t = case3(t);
            }
        }
        t.height = max(height(t.left), height(t.right) + 1);
        return t;
    }

    //delete succesor : kanan terkecil
    private AvlNode<AnyType> findMinimum (AvlNode<AnyType> t){
        if(t == null){
            return null;
        }
        while (t.left != null) {
            t = t.left;
        }
        return t;
    }

    private AvlNode<AnyType> delete_succesor(AvlNode<AnyType> t, AnyType x){
        if(t == null){
            return null;
        }
        if(x.compareTo(t.element) < 0){ // x < element
            t.left = delete_succesor(t.left, x);
        }else if(x.compareTo(t.element) > 0){ // x > element
            t.right = delete_succesor(t.right, x);
        }else if(t.left != null && t.right != null){ //mempunyai 2 anak
            t.element = findMinimum(t.right).element;
            t.left = delete_succesor(t.right, t.element);
        }else{ // mempunyai 1 anak
            t = t.left !=null ? t.left : t.right;
        }
        return balance(t);
    }
    
    public  void delete_succesor(AnyType x){
        root = delete_succesor(root, x);
    }

    //delete_predecessor 
    private AvlNode<AnyType> findMax(AvlNode<AnyType> t){
        while (t.right != null) {
            t = t.right;
        }
        return t;
    }

    private AvlNode<AnyType> deleteMax(AvlNode<AnyType> t){
        if(t == null){
            return null;
        }else if(t.right != null){
            t.right = deleteMax(t.right);
            return t;
        }else{
            return t.left;
        }
    }
    public AvlNode<AnyType> delete_predecessor(AvlNode<AnyType> t, AnyType x){
        if(t == null){
            return null;
        }else{
            if(x.compareTo(t.element) < 0){
                t.left = delete_predecessor(t.left, x);
            }else if(x.compareTo(t.element) > 0){
                t.right = delete_predecessor(t.right, x);
            }else if(t.left != null && t.right != null){
                t.height = findMax(t.left).height;
            }
        }
        return t;
    }
    
}

public class Implementasi_AVL_Tree {

    public static void main(String[] args) {
        AvlTree avl = new AvlTree();

        avl.insert(10);
        avl.insert(85);
        avl.insert(15);
        avl.insert(70);
        avl.insert(20);
        avl.insert(60);
        avl.insert(30);
        avl.insert(50);
        avl.insert(65);
        avl.insert(80);
        avl.insert(90);
        avl.insert(40);
        avl.insert(5);
        avl.insert(55);

        System.out.println("Insert : 10, 85, 15, 70, 20, 60, 30, 50, 65, 80, 90, 40, 5, 55");
        System.out.println();
        System.out.println("-----------------------InOrder---------------------");
        avl.inOrder();
        System.out.println();
        System.out.println();

        System.out.println("-----------------------PreOrder------------------------");
        avl.preOrder();
        System.out.println();

        System.out.println();

        System.out.println("----------------------PostOrder------------------------");
        avl.postOrder();
        System.out.println();

        System.out.println();
        System.out.println("[--------------------Setelah delete 60-----------------]");
        avl.delete_succesor(60);

        System.out.println();
        System.out.println("---------------------Inorder----------------------------");
        avl.inOrder();
        System.out.println();
        System.out.println();
        
        System.out.println("--------------------Preorder--------------------------------");
        avl.preOrder();
        System.out.println();
        System.out.println();

        System.out.println("--------------------post-------------------------------------");
        avl.postOrder();

    }
}