package org.example.kamus.model;

public class Tree {
    private Node root;

    public Tree() {
        root = null;
    }

    public boolean add(String key, String value) {
        Node newNode = new Node(key, value);
        if (root == null) {
            root = newNode;
            root.setRed(false);
            return true;
        }

        Node parent = null, current = root;
        while (current != null) {
            parent = current;
            if (key.compareTo(current.getKey()) < 0) {
                current = current.getLeft();
            } else if (key.compareTo(current.getKey()) > 0) {
                current = current.getRight();
            } else {
                return false;
            }
        }

        newNode.setParent(parent);
        if (key.compareTo(parent.getKey()) < 0) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }

        balanceAfterInsert(newNode);
        return true;
    }

    public String search(String key) {
        Node current = root;
        while (current != null) {
            if (key.equals(current.getKey())) {
                return current.getValue();
            }
            current = (key.compareTo(current.getKey()) < 0) ? current.getLeft() : current.getRight();
        }
        return null;
    }


    private void balanceAfterInsert(Node node) {
        while (node != root && node.getParent().isRed()) {
            if (node.getParent() == node.getParent().getParent().getLeft()) {
                Node uncle = node.getParent().getParent().getRight();
                if (uncle != null && uncle.isRed()) {
                    node.getParent().setRed(false);
                    uncle.setRed(false);
                    node.getParent().getParent().setRed(true);
                    node = node.getParent().getParent();
                } else {
                    if (node == node.getParent().getRight()) {
                        node = node.getParent();
                        rotateToLeft(node);
                    }
                    node.getParent().setRed(false);
                    node.getParent().getParent().setRed(true);
                    rotateToRight(node.getParent().getParent());
                }
            } else {
                Node uncle = node.getParent().getParent().getLeft();
                if (uncle != null && uncle.isRed()) {
                    node.getParent().setRed(false);
                    uncle.setRed(false);
                    node.getParent().getParent().setRed(true);
                    node = node.getParent().getParent();
                } else {
                    if (node == node.getParent().getLeft()) {
                        node = node.getParent();
                        rotateToRight(node);
                    }
                    node.getParent().setRed(false);
                    node.getParent().getParent().setRed(true);
                    rotateToLeft(node.getParent().getParent());
                }
            }
        }
        root.setRed(false);
    }

    public boolean isExist(String key) {
        Node current = root;
        while (current != null) {
            if (key == current.getKey()) return true;
            current = (key.compareTo(current.getKey()) < 0) ? current.getLeft() : current.getRight();
        }
        return false;
    }

    private void rotateToLeft(Node node) {
        Node rightChild = node.getRight();
        node.setRight(rightChild.getLeft());
        if (rightChild.getLeft() != null) rightChild.getLeft().setParent(node);
        rightChild.setParent(node.getParent());
        if (node.getParent() == null) {
            root = rightChild;
        } else if (node == node.getParent().getLeft()) {
            node.getParent().setLeft(rightChild);
        } else {
            node.getParent().setRight(rightChild);
        }
        rightChild.setLeft(node);
        node.setParent(rightChild);
    }

    private void rotateToRight(Node node) {
        Node leftChild = node.getLeft();
        node.setLeft(leftChild.getRight());
        if (leftChild.getRight() != null) leftChild.getRight().setParent(node);
        leftChild.setParent(node.getParent());
        if (node.getParent() == null) {
            root = leftChild;
        } else if (node == node.getParent().getRight()) {
            node.getParent().setRight(leftChild);
        } else {
            node.getParent().setLeft(leftChild);
        }
        leftChild.setRight(node);
        node.setParent(leftChild);
    }

    public boolean contains(String word) {
        return search(word) != null;
    }

    public boolean remove(String key) {
        Node node = findNode(key);
        if (node == null) return false;

        Node y = node;
        boolean yOriginalColor = y.isRed();
        Node x;

        if (node.getLeft() == null) {
            x = node.getRight();
            transplant(node, node.getRight());
        } else if (node.getRight() == null) {
            x = node.getLeft();
            transplant(node, node.getLeft());
        } else {
            y = minimum(node.getRight());
            yOriginalColor = y.isRed();
            x = y.getRight();
            if (y.getParent() == node) {
                if (x != null) x.setParent(y);
            } else {
                transplant(y, y.getRight());
                y.setRight(node.getRight());
                y.getRight().setParent(y);
            }
            transplant(node, y);
            y.setLeft(node.getLeft());
            y.getLeft().setParent(y);
            y.setRed(node.isRed());
        }

        if (!yOriginalColor) {
            balanceAfterDelete(x);
        }
        return true;
    }

    private Node findNode(String key) {
        Node current = root;
        while (current != null) {
            if (key == current.getKey()) return current;
            current = (key.compareTo(current.getKey()) < 0) ? current.getLeft() : current.getRight();

        }
        return null;
    }

    private void transplant(Node u, Node v) {
        if (u.getParent() == null) {
            root = v;
        } else if (u == u.getParent().getLeft()) {
            u.getParent().setLeft(v);
        } else {
            u.getParent().setRight(v);
        }
        if (v != null) {
            v.setParent(u.getParent());
        }
    }

    private Node minimum(Node node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    private void balanceAfterDelete(Node x) {
        while (x != root && (x == null || !x.isRed())) {
            if (x == x.getParent().getLeft()) {
                Node sibling = x.getParent().getRight();
                if (sibling.isRed()) {
                    sibling.setRed(false);
                    x.getParent().setRed(true);
                    rotateToLeft(x.getParent());
                    sibling = x.getParent().getRight();
                }
                if ((sibling.getLeft() == null || !sibling.getLeft().isRed()) &&
                        (sibling.getRight() == null || !sibling.getRight().isRed())) {
                    sibling.setRed(true);
                    x = x.getParent();
                } else {
                    if (sibling.getRight() == null || !sibling.getRight().isRed()) {
                        if (sibling.getLeft() != null) sibling.getLeft().setRed(false);
                        sibling.setRed(true);
                        rotateToRight(sibling);
                        sibling = x.getParent().getRight();
                    }
                    sibling.setRed(x.getParent().isRed());
                    x.getParent().setRed(false);
                    if (sibling.getRight() != null) sibling.getRight().setRed(false);
                    rotateToLeft(x.getParent());
                    x = root;
                }
            } else {
                Node sibling = x.getParent().getLeft();
                if (sibling.isRed()) {
                    sibling.setRed(false);
                    x.getParent().setRed(true);
                    rotateToRight(x.getParent());
                    sibling = x.getParent().getLeft();
                }
                if ((sibling.getRight() == null || !sibling.getRight().isRed()) &&
                        (sibling.getLeft() == null || !sibling.getLeft().isRed())) {
                    sibling.setRed(true);
                    x = x.getParent();
                } else {
                    if (sibling.getLeft() == null || !sibling.getLeft().isRed()) {
                        if (sibling.getRight() != null) sibling.getRight().setRed(false);
                        sibling.setRed(true);
                        rotateToLeft(sibling);
                        sibling = x.getParent().getLeft();
                    }
                    sibling.setRed(x.getParent().isRed());
                    x.getParent().setRed(false);
                    if (sibling.getLeft() != null) sibling.getLeft().setRed(false);
                    rotateToRight(x.getParent());
                    x = root;
                }
            }
        }
        if (x != null) x.setRed(false);
    }
}
