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
        TNULL.left = null ;
        TNULL.right = null;
        root = TNULL;
        size = 0;

    }

    @Override
    public boolean insert(int v){
        logger.debug("tryna insert: {}",v);
        Node node = new Node(v);
        node.parent = null;
        node.left = TNULL;
        node.right = TNULL;

        Node y = null ; 
        Node x = this.root;

        while(x!= TNULL){
            y =x ;
            if(node.value ==x.value){
                logger.debug("already exists so forget about it");
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
            y.value = node;
        }else{
            y.right = node;
        }
        size++;
    }

    
}
