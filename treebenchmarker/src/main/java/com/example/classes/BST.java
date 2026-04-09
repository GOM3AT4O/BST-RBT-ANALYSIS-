package com.example.classes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BST implements TreeInterface{

    private static final Logger logger = LoggerFactory.getLogger(BST.class);

    //THE VALIDATION FLAG
    static final boolean VALIDATE = false;

    //SMALL CLASS FOR THE NODE 
    public class Node {
        int value;
        Node left;
        Node right;

        Node(int value){
            this.value = value ;
            this.left = null ;
            this.right = null;
        }
    }
    private Node root;
    private int size ;//for the Size method

    public BST(){
        this.root = null ; 
        this.size = 0 ;
    }

    @Override
    public boolean insert(int v){
        logger.debug("trynna insert the value : {}",v);

        //if the tree is empty just insert the node as the root 
        if(root ==null){
            root = new Node(v);
            size++;
            if (VALIDATE) Validator.check(this);
            return true;
        }

        Node current = root;
        while(true){
            if(v ==current.value){
                logger.debug("value {} already there in the tree so i ignore",v);
                return false;

            }else if(v < current.value){
                if (current.left ==null){
                    current.left = new Node(v);
                    size++;
                    break;
                }
                current = current.left;
            }else{
                if(current.right ==null){
                    current.right =new Node(v);
                    size++;
                    break;
                }
                current = current.right;
            }
        }
        if(VALIDATE) Validator.check(this);
        return true;
    }

    @Override
    public boolean delete(int v){
        logger.debug("tryna delete the value {}",v);
        int initialSize = size ; 

        // USE RECURSION 
        root = deleteRecursive(root,v);
        if(VALIDATE){
            Validator.check(this);
        }
        //if the size was decreased we are good to go
        return size <initialSize;
    }

    private Node deleteRecursive(Node current , int v){
        if(current == null){
            return null; // no value to delete
        }
        if(v == current.value){
            // case 1 no childern  or only 1 child
            if(current.left == null){
                size--;
                return current.right;
            }
            if(current.right == null){
                size--;
                return current.left;
            }

            //case 3 2 childern 
            // the smallest in the right subtree
            int smallestValue = findSmallestValue(current.right);
            current.value = smallestValue;

            current.right = deleteRecursive(current.right, smallestValue);
            return current;
        }
        if(v <current.value){
            current.left =deleteRecursive(current.left, v);
            return current;
        }
        current.right = deleteRecursive(current.right, v);
        return current;
    }

    private int findSmallestValue(Node root){
        return root.left ==null ? root.value :findSmallestValue(root.left);
    }

    @Override
    public boolean contains(int v){
        Node current = root ; 
        while (current!= null){
            if(v == current.value){
                return true;
            }
            else if (v < current.value){
                current = current.left;
            }else{
                current = current.right;
            }

        }
        return false;
    }

    @Override
    public int[] inOrder(){
        int[] result = new int[size];
        int[] index = {0}; // Using an array allows pass-by-reference in Java
        inOrderRecursive(root, result, index);
        return result;
    }

    private void inOrderRecursive(Node node , int[] result , int[] index){
        if(node !=null){
            inOrderRecursive(node.left, result, index);
            result[index[0]++] = node.value;
            inOrderRecursive(node.right, result, index);
        }
    }
    @Override
    public int Height(){
        return heightRecursive(root);
    }

    private int heightRecursive(Node node){
        if(node == null){
            return 0 ;
        }
        return 1 + Math.max(heightRecursive(node.left), heightRecursive(node.right));
    }
    @Override
    public int Size(){
        return size;
    }
    public Node getRoot(){
        return root;
    }
}
