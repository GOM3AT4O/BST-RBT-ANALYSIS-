package com.example.classes;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Validator {
    private static final Logger logger = LoggerFactory.getLogger(Validator.class);

    //validate check
    public static void check(BST tree){
        if(tree.getRoot() == null){
            return; //empty tree

        }

        if(!isValidBST(tree.getRoot(), Integer.MIN_VALUE,Integer.MAX_VALUE)){
            logger.error("validation failed , tree is not BST");
            throw new IllegalStateException("tree violates the BST");
        }
    }
    // recursion to make sure all left are smaller and all right are larger
    private static boolean isValidBST(BST.Node node , int min , int max){
        if(node == null){
            return true;
        }
        if(node.value <= min || node.value >= max){
            return false;
        }
        return isValidBST(node.left, min, node.value) &&
        isValidBST(node.right, node.value, max);
    }
    
}
