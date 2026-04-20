package com.example.classes;

import java.util.Arrays;

public class TESTS {

    public static void main(String[] args) {
        System.out.println(" Starting Manual Tree Validations...\n");
        
        try {
            testBST();
            testRBT();
            System.out.println("\n SUCCESS: All tests passed perfectly! Tress are bullet proof");
        } catch (AssertionError | Exception e) {
            System.err.println("\n FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==========================================
    // BINARY SEARCH TREE TESTS
    // ==========================================
    private static void testBST() {
        System.out.println("-> Testing Binary Search Tree...");
        BST tree = new BST();
        int[] values = {50, 30, 70, 20, 40, 60, 80};

        for (int v : values) {
            assertTrue(tree.insert(v), "Failed to insert new value: " + v);
            Validator.check(tree); // Will throw if rules break
        }

        assertFalse(tree.insert(50), "Failed: Allowed duplicate insertion");
        assertEquals(7, tree.Size(), "Failed: Size is incorrect");

        // Deletion Tests
        assertTrue(tree.delete(20), "Failed to delete leaf node");
        Validator.check(tree);
        
        tree.delete(40); 
        assertTrue(tree.delete(30), "Failed to delete node with one child");
        Validator.check(tree);

        assertTrue(tree.delete(50), "Failed to delete root with two children");
        Validator.check(tree);
        assertFalse(tree.contains(50), "Failed: Deleted value still found");

        // InOrder Test
        BST sortTree = new BST();
        sortTree.insert(10); sortTree.insert(5); sortTree.insert(15);
        assertArrayEquals(new int[]{5, 10, 15}, sortTree.inOrder(), "Failed: BST inOrder not sorted");
    }

    // ==========================================
    // RED-BLACK TREE TESTS
    // ==========================================
    private static void testRBT() {
        System.out.println("-> Testing Red-Black Tree...");
        RedBlackTree tree = new RedBlackTree();
        
        // Insertion & Structural Tests (Forces massive rotations)
        for (int i = 1; i <= 20; i++) {
            assertTrue(tree.insert(i), "Failed to insert: " + i);
            Validator.check(tree); 
        }
        
        assertEquals(20, tree.Size(), "Failed: RBT Size incorrect");
        assertFalse(tree.insert(10), "Failed: RBT Allowed duplicate");

        // Deletion Tests
        int[] toDelete = {1, 15, 20, 10, 5};
        for (int v : toDelete) {
            assertTrue(tree.delete(v), "Failed to delete: " + v);
            Validator.check(tree);
        }

        assertFalse(tree.delete(999), "Failed: Deleted non-existent value");

        // InOrder Test
        RedBlackTree sortTree = new RedBlackTree();
        sortTree.insert(30); sortTree.insert(10); sortTree.insert(20);
        assertArrayEquals(new int[]{10, 20, 30}, sortTree.inOrder(), "Failed: RBT inOrder not sorted");
    }

    // ==========================================
    // ZERO-DEPENDENCY ASSERTION HELPERS
    // ==========================================
    
    private static void assertTrue(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static void assertFalse(boolean condition, String message) {
        if (condition) throw new AssertionError(message);
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " | Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static void assertArrayEquals(int[] expected, int[] actual, String message) {
        if (!Arrays.equals(expected, actual)) {
            throw new AssertionError(message + " | Expected: " + Arrays.toString(expected) + ", Actual: " + Arrays.toString(actual));
        }
    }
}