package com.example.classes;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Validator {
    private static final Logger logger = LoggerFactory.getLogger(Validator.class);

    //validate check
    public static void check(BST tree){
        if (tree.getRoot() == null) return;


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

    public static void check(RedBlackTree tree) {
        RedBlackTree.Node root = tree.getRoot();
        RedBlackTree.Node TNULL = tree.getTNULL();

        if(root == TNULL) return;

        if(!isValidRBBST(root , TNULL , Integer.MIN_VALUE , Integer.MAX_VALUE)){
            logger.error("VALIDATION FAILED: Red Black Tree violates standard BST properties");
            throw new IllegalStateException("RBT is not a valid binary search tree");
        }

        if(root.color == true){
            logger.error("VALIDATION FAILED: Root node is not black");
            throw new IllegalStateException("RBT Rule 2 Violated: Root must be BLACK");
        }

        validateRedBlackProperties(root, TNULL);
    }

    private static boolean isValidRBBST(RedBlackTree.Node node, RedBlackTree.Node TNULL, int min, int max) {
        if(node ==TNULL) return true;
        if(node.value <=min || node.value >= max) return false;
        return isValidRBBST(node.left, TNULL, min, node.value) &&
         isValidRBBST(node.right, TNULL, node.value, max);
    }
    private static int validateRedBlackProperties(RedBlackTree.Node node ,RedBlackTree.Node TNULL){
        if(node == TNULL){
            return 1;
        }

        if(node.color == true){
            if(node.left.color == true || node.right.color ==true){
                logger.error("VALIDATION FAILED: Double RED found at node {}", node.value);
                throw new IllegalStateException("RBT Rule 3 Violated: RED node has RED child.");
            }
        }
        int leftBlackHeight = validateRedBlackProperties(node.left, TNULL);
        int rightBlackHeight = validateRedBlackProperties(node.right, TNULL);

        if(leftBlackHeight != rightBlackHeight){
            logger.error("VALIDATION FAILED: Black-height mismatch at node {}", node.value);
            throw new IllegalStateException("RBT Rule 4 Violated: All paths from a node to its descendant leaves must have the same number of BLACK nodes.");
        }
        return leftBlackHeight + (node.color == false ? 1 : 0);
    }
        
}
