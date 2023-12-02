package avltree;

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
        System.out.println("-----------InOrder----------- : ");
        avl.inOrder();
        System.out.println();
        System.out.println();

        System.out.println("---------PreOrder------------ : ");
        avl.preOrder();
        System.out.println();

        System.out.println();

        System.out.println("--------PostOrder---------- : ");
        avl.postOrder();

    }

}