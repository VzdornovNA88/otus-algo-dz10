
package vzdornov.algo;

public class AVLTree {

    private Node root;

    public void scan() {
        if (root != null) {
            scanNode(root);
        }
    }

    private void scanNode(Node node) {
        if (node.getLeft() != null) {
            scanNode(node.getLeft());
        }
        System.out.print(node.getValue() + ",(H" + node.getHeight() + ",B" + node.getBalance() + "), ");
        if (node.getRight() != null) {
            scanNode(node.getRight());
        }
    }

    public void insert(int value) {
        Node insNode = new Node(value, null, 0, 1);
        if (root != null) {
            insertNode(root, insNode);
        } else {
            root = insNode;
        }
        reCalcBalanse(insNode.getParent());
    }

    private void insertNode(Node node, Node insNode) {
        if (node.getValue() > insNode.getValue()) {
            if (node.getLeft() != null) {
                insertNode(node.getLeft(), insNode);
            } else {
                insNode.setParent(node);
                node.setLeft(insNode);
            }
        } else {
            if (node.getRight() != null) {
                insertNode(node.getRight(), insNode);
            } else {
                insNode.setParent(node);
                node.setRight(insNode);
            }
        }
    }

    private void reCalcBalanse(Node node) {
        while (node != null) {
            reCalcNodeHeight(node);
            node.setBalance(calcNodeBalanse(node));
            if (node.getBalance() < -1 || node.getBalance() > 1) {
                rebalance(node);
            } else {
                node = node.getParent();
            }
        }
    }

    private void reCalcNodeHeight(Node node) {
        int h = Math.max(getNodeHeight(node.getLeft()), getNodeHeight(node.getRight()));
        node.setHeight(h + 1);
    }

    private int getNodeHeight(Node node) {
        if (node != null) {
            return node.getHeight();
        } else {
            return 0;
        }
    }

    private int calcNodeBalanse(Node node) {
        return getNodeHeight(node.getRight()) - getNodeHeight(node.getLeft());
    }

    private Node find(int value) {
        if (root != null) {
            return findNode(root, value);
        }
        return null;
    }

    private Node findNode(Node node, int value) {
        if (node.getValue() == value) {
            return node;
        } else {
            if (node.getValue() > value) {
                if (node.getLeft() != null) {
                    return findNode(node.getLeft(), value);
                }
                return null;
            } else {
                if (node.getRight() != null) {
                    return findNode(node.getRight(), value);
                }
                return null;
            }
        }
    }

    public void remove(int value) {
        Node node = find(value);
        if (node != null) {
            Node parent = node.getParent();
            if (parent != null) {
                if (parent.getLeft() == node) {
                    parent.setLeft(null);
                }
                if (parent.getRight() == node) {
                    parent.setRight(null);
                }
                if (node.getLeft() != null) {
                    insertNode(parent, node.getLeft());
                    reCalcBalanse(node.getLeft().getParent());
                }
                if (node.getRight() != null) {
                    insertNode(parent, node.getRight());
                    reCalcBalanse(node.getRight().getParent());
                }
            } else {
                if ((node.getLeft() != null) && (node.getRight() != null)) {
                    root = node.getLeft();
                    root.setParent(null);
                    insertNode(node.getLeft(), node.getRight());
                    reCalcBalanse(node.getRight().getParent());
                } else {
                    root = null;
                    if (node.getLeft() != null) {
                        root = node.getLeft();
                        root.setParent(null);
                    }
                    if (node.getRight() != null) {
                        root = node.getRight();
                        root.setParent(null);
                    }
                }
            }
        } else {
            System.out.println("в дереве нет элемента со значением " + value);
        }
    }

    public void smallLeftRotation(Node node) {
        Node a = node.getRight();
        a.setParent(node.getParent());
        if (node.getParent() != null) {
            if (node.getParent().getLeft() == node) {
                node.getParent().setLeft(a);
            } else {
                node.getParent().setRight(a);
            }
        } else {
            root = a;
        }
        node.setParent(a);
        if (a.getLeft() != null) {
            node.setRight(a.getLeft());
            a.getLeft().setParent(node);
        } else {
            node.setRight(null);
        }
        a.setLeft(node);
    }

    public void smallRightRotation(Node node) {
        Node a = node.getLeft();
        a.setParent(node.getParent());
        if (node.getParent() != null) {
            if (node.getParent().getLeft() == node) {
                node.getParent().setLeft(a);
            } else {
                node.getParent().setRight(a);
            }
        } else {
            root = a;
        }
        node.setParent(a);
        if (a.getRight() != null) {
            node.setLeft(a.getRight());
            a.getRight().setParent(node);
        } else {
            node.setLeft(null);
        }
        a.setRight(node);
    }

    public void bigLeftRotation(Node node) {
        Node a = node.getRight();
        smallRightRotation(a);
        reCalcNodeHeight(a);
        a.setBalance(calcNodeBalanse(a));
        smallLeftRotation(node);
    }

    public void bigRightRotation(Node node) {
        Node a = node.getLeft();
        smallLeftRotation(a);
        reCalcNodeHeight(a);
        a.setBalance(calcNodeBalanse(a));
        smallRightRotation(node);
    }

    public void rebalance(Node node) {
        if (node.getBalance() < -1) {
            if (node.getLeft().getBalance() <= 0) {
                smallRightRotation(node);
            } else {
                bigRightRotation(node);
            }
        }
        if (node.getBalance() > 1) {
            if (node.getRight().getBalance() >= 0) {
                smallLeftRotation(node);
            } else {
                bigLeftRotation(node);
            }
        }
    }
}
