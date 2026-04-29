package com.example.classes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class RedBlackTree implements TreeInterface {
    private static final Logger logger = LoggerFactory.getLogger(RedBlackTree.class);
    static final boolean VALIDATE = false;

    //colors of the nodes 
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    public class Node {
        int value ;
        Node parent;
        Node left ; 
        Node right;
        boolean color ;

        Node(int value){
            this.value = value;
            this.color =RED; //new node is red
        }
    
        
    }
    
    private Node root;
    private final Node TNULL;// Sentinel node for NIL leaves
    private int size;

    public RedBlackTree(){
        TNULL = new Node(0);
        TNULL.color = BLACK;
        TNULL.left = TNULL ;
        TNULL.right = TNULL;
        root = TNULL;
        size = 0;

    }

    @Override
    public boolean insert(int v){
     //   logger.debug("tryna insert: {}",v);
        Node node = new Node(v);
        node.parent = null;
        node.left = TNULL;
        node.right = TNULL;

        Node y = null ; 
        Node x = this.root;

        while(x!= TNULL){
            y =x ;
            if(node.value ==x.value){
             //   logger.debug("already exists so forget about it");
                return false ; 

            }else if(node.value<x.value){
                x = x.left;
            }else{
                x= x.right;
            }
        }

        node.parent =y;
        if(y ==null){
            root = node ;
        }else if(node.value <y.value){
            y.left = node;
        }else{
            y.right = node;
        }
        size++;

        //if the new node is red this might violate the 2 reds in a raw
        if(node.parent ==null){
            node.color = BLACK;
            if(VALIDATE) Validator.check(this);
            return true;
        }
        if(node.parent.parent ==null){
            if(VALIDATE) Validator.check(this);
            return true;
        }

        fixInsert(node);
        if(VALIDATE) Validator.check(this);
        return true;
    }

    @Override
    public boolean delete(int v) {
    //   logger.debug("tryna delete: {}",v);
        Node z = TNULL;
        Node x = this.root;

        //find the node to delete
        while(x!= TNULL){
            if(x.value == v){
                z = x ; 
            }
            if(x.value <= v){
                x = x.right;
            }else{
                x = x.left;
            }
        }
        if(z == TNULL){
        //    logger.debug("not found so forget about it");
            return false ; 
        }
        Node y = z;
        Node xToFix;
        boolean yOriginalColor = y.color;

        if(z.left == TNULL){
            xToFix = z.right;
            transplant(z, z.right);
        }else if(z.right == TNULL){
            xToFix = z.left;
            transplant(z, z.left);
        }else{
            y = minimum(z.right);
            yOriginalColor = y.color;
            xToFix = y.right;
            if(y.parent ==z){
                xToFix.parent = y;

            }else{
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        size--;

        // if we deleted a black node we might violate the black height property
        if(yOriginalColor == BLACK){
            fixDelete(xToFix);
        }
        if(VALIDATE) Validator.check(this);
        return true;
    }

    @Override
    public boolean contains(int v){
        Node current  = root;
        while(current != TNULL){
            if(v== current.value){
                return true;
        }
        current = (v< current.value) ? current.left : current.right;
        }
        return false;
    }
    //balance logic 
    private void fixInsert(Node k){
        Node u;
        while(k.parent.color == RED){
            if(k.parent == k.parent.parent.right){
                u = k.parent.parent.left; //uncle
                if(u.color ==RED){
                    u.color = BLACK;
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                }else{
                    if(k==k.parent.left){
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.color =BLACK;
                    k.parent.parent.color =RED;
                    leftRotate(k.parent.parent);

                }
            }
            else{
                u = k.parent.parent.right;
                if(u.color ==RED){
                    u.color = BLACK ;
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                }else{
                    if(k == k.parent.right){
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    rightRotate(k.parent.parent);
                }
            }
            if(k == root){
                break;
            }
        }
        root.color = BLACK; 
    }
    private void fixDelete(Node x){
        Node s ; 
        while(x != root && x.color == BLACK){
            if(x== x.parent.left){
                s = x.parent.right;
                if(s.color ==RED){
                    s.color = BLACK;
                    x.parent.color =RED;
                    leftRotate(x.parent);
                    s = x.parent.right;
                }
                if(s.left.color == BLACK && s.right.color == BLACK){
                    s.color = RED;
                    x = x.parent;
            }else{
                if(s.right.color ==BLACK){
                    s.left.color =BLACK;
                    s.color = RED;
                    rightRotate(s);
                    s = x.parent.right;
                }
                s.color = x.parent.color;
                x.parent.color = BLACK;
                s.right.color = BLACK;
                leftRotate(x.parent);
                x = root;
            }
        }else{
            s =x.parent.left;
            if(s.color == RED){
                s.color = BLACK;
                x.parent.color =RED;
                rightRotate(x.parent);
                s = x.parent.left;
            }
            if(s.right.color == BLACK && s.left.color ==BLACK){
                s.color = RED;
                x = x.parent;

            }else{
                if(s.left.color ==BLACK){
                    s.right.color = BLACK;
                    s.color = RED;
                    leftRotate(s);
                    s = x.parent.left;
                }
                s.color = x.parent.color;
                x.parent.color = BLACK;
                s.left.color = BLACK;
                rightRotate(x.parent);
                x = root;
            }
        }
    }
    x.color = BLACK;

    }
    //rotations
    private void leftRotate(Node x){
     //  logger.debug("left rotate on: {}",x.value);
        Node y = x.right;
        x.right = y.left;
        if(y.left != TNULL)           y.left.parent = x;
        y.parent = x.parent;
        if(x.parent ==null) this.root = y;
        else if(x ==x.parent.left) x.parent.left = y ;
        else x.parent.right = y;
        y.left = x;
        x.parent = y;
        }
    private void rightRotate(Node x){
      //  logger.debug("right rotate on: {}",x.value);
        Node y = x.left;
        x.left = y.right;
        if(y.right != TNULL)          y.right.parent = x;
        y.parent = x.parent;
        if(x.parent ==null) this.root = y;
        else if(x == x.parent.right) x.parent.right = y ;
        else x.parent.left = y;
        y.right = x;
        x.parent = y;

    }

    private void transplant(Node u , Node v){
        if(u.parent == null) this.root = v;
        else if(u == u.parent.left) u.parent.left = v;
        else u.parent.right = v;
        v.parent = u.parent;
    }
    private Node minimum(Node node){
        while(node.left != TNULL) node = node.left;
        return node;
    }

    @Override
    public int[] inOrder(){
        int[] result = new int[size];
        int[] index = {0};
        inOrderRecursive(this.root, result, index);
        return result;
    }

    private  void inOrderRecursive(Node node , int[] result , int[] index){
        if(node != TNULL){
            inOrderRecursive(node.left, result, index);
            result[index[0]++] = node.value;
            inOrderRecursive(node.right, result, index);
        }
    }

    @Override
    public int Height(){
        return heightRecursive(this.root);
    }
    private int heightRecursive(Node node){
        if(node == TNULL) return 0;
        return 1 + Math.max(heightRecursive(node.left), heightRecursive(node.right));
    }

    @Override
    public int Size(){
        return size;
    }

    public Node getRoot(){return root;}
    public Node getTNULL(){return TNULL;}

    
}
