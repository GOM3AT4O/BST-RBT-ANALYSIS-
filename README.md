# Data Structures Benchmark: BST vs. RBT

![Determined to Code](https://cdn.devdojo.com/images/july2021/sibling.gif)

## Overview
This project is a comprehensive Java benchmarking suite designed to analyze and compare the performance of a standard **Binary Search Tree (BST)**, a self-balancing **Red-Black Tree (RBT)**, and **QuickSort**. 

The suite measures execution time for core operations (Insertion, Search, Deletion, and Sorting) across datasets of 100,000 integers with varying levels of entropy (Fully Random vs. Nearly-Sorted).

## Features
* **Custom Implementations:** From-scratch implementations of a standard BST and a Red-Black Tree (with Sentinel `TNULL` nodes).
* **Controlled Data Generation:** Uses a fixed-seed PRNG (Fisher-Yates shuffle) to generate four distinct datasets: Fully Random, 1% nearly-sorted, 5% nearly-sorted, and 10% nearly-sorted.
* **Rigorous Benchmarking:** 
  * Discards "Run 0" to account for JVM JIT compilation warmup.
  * Runs all tests 5 times and calculates Mean, Median, and Standard Deviation.
  * Ensures a 50/50 split for `Contains` lookups (50k guaranteed hits, 50k guaranteed misses).
  * Randomly selects 20% of elements for deletion *prior* to starting the timer to isolate tree performance.
* **Structural Validation:** A dedicated internal `Validator` class mathematically verifies all Red-Black Tree invariants (Root Property, No Double-Reds, Uniform Black-Height).

## Project Structure
* `Benchmark.java`: The main execution engine that coordinates data generation, runs the algorithms, and prints the statistical tables.
* `BST.java`: The standard Binary Search Tree implementation.
* `RedBlackTree.java`: The self-balancing Red-Black Tree implementation (includes the inner `Validator` class).
* `Generator.java`: The input generation engine.
* `StructureTest.java`: A standalone runner to visually prove the Red-Black Tree structural invariants hold true.
* `QuickSort.java`: The array-based sorting baseline.

## How to Run

### 1. Running the Benchmark
Before running the performance benchmark, ensure that the validation flag is turned **OFF** in both `BST.java` and `RedBlackTree.java` to prevent recursive validation from skewing the timed results:
```java
static final boolean VALIDATE = false;
