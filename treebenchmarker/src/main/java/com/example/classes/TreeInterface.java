package com.example.classes;

public interface TreeInterface {

    boolean insert(int v); //can't insert duplicates 
    
    boolean delete(int v); // return false if no existance

    boolean contains(int v); // retrun true if V is in the tree

    int[] inOrder(); // return all values sorted

    int Height();// return the height of the tree

    int Size(); // return the number of elements
    
} 

