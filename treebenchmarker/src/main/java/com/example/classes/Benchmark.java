package com.example.classes;
import java.util.Arrays;
public class Benchmark {
    private static final int RUNS = 6;

    private static final double[]  bstInsertTimes = new double[5];
    private static  final double[] bstContainsTimes = new double[5];
    private static final double[] bstDeleteTimes = new double[5];
    private static final double[] bstSortTimes = new double[5];

    private static final double[] rbtInsertTimes = new double[5];
    private static final double[] rbtContainsTimes = new double[5];
    private static final double[] rbtDeleteTimes = new double[5];
    private static final double[] rbtSortTimes = new double[5];

    private static final double[] quicksortTimes = new double[5];

    public static void main(String[] args) {
        
        runBenchmark("1. Fully Random", Generator.generateRandom());
        runBenchmark("2. Nearly-Sorted (1% swaps)", Generator.generateNearlySorted(1));
        runBenchmark("3. Nearly-Sorted (5% swaps)", Generator.generateNearlySorted(5));
        runBenchmark("4. Nearly-Sorted (10% swaps)", Generator.generateNearlySorted(10));
    }

    private static void runBenchmark(String testName, int[] baseData) {
        System.out.println("===============================================================");
        System.out.println(" BENCHMARKING: " + testName);
        System.out.println("===============================================================");

        int bstHeight = 0 ;
        int rbtHeight = 0 ;

        for(int run = 0; run < RUNS; run++) {
            boolean isWarmup = (run == 0);
            if(!isWarmup){
                System.out.print(".");
            }

            int[] data = baseData.clone();
            int[] toDelete = new int[20000];
            int[] randomSelect = baseData.clone();
            java.util.Random rand = new java.util.Random(42+run);
            for(int i = 0 ; i<20000;i++){
                int swapIdx = i + rand.nextInt(baseData.length - i);
                toDelete[i] = randomSelect[swapIdx];
                randomSelect[swapIdx] = randomSelect[i];
            }

            //bst
            BST bst = new BST();

            //insert
            long start = System.nanoTime();
            for(int v : data){
                bst.insert(v);
            }
            double insertTime = (System.nanoTime() - start) / 1_000_000.0;
            if(!isWarmup){
                bstInsertTimes[run-1] = insertTime;
            }
            bstHeight = bst.Height();

            //contains
            start = System.nanoTime();
            for (int i = 0; i < 50_000; i++){
                bst.contains(data[i]);
            }
            for(int i = 1 ; i<=50_000;i++){
                bst.contains(-i);
            }
            if(!isWarmup){
                bstContainsTimes[run-1] = (System.nanoTime() - start) / 1_000_000.0;
            }

            //delete
            start = System.nanoTime();
            for(int i = 0 ; i < 20_000 ; i++){
                bst.delete(toDelete[i]);
            }
            if(!isWarmup){
                bstDeleteTimes[run-1] = (System.nanoTime() - start) / 1_000_000.0;
            }

            //sort
            start = System.nanoTime();
            bst.inOrder();
            double inOrderTime = (System.nanoTime() - start) / 1_000_000.0;
            if(!isWarmup){
                bstSortTimes[run-1] = insertTime +inOrderTime;
            }

            //rbt
            RedBlackTree rbt = new RedBlackTree();
            //insert
            start = System.nanoTime();
            for(int v: data) rbt.insert(v);
            insertTime = (System.nanoTime() - start) / 1_000_000.0;
            if(!isWarmup){ 
                rbtInsertTimes[run-1] = insertTime;
            }
            rbtHeight = rbt.Height();
            //contains
            start = System.nanoTime();
            for (int i = 0; i < 50_000; i++){
                rbt.contains(data[i]);
            }
            for(int i = 1 ; i<=50_000;i++){
                rbt.contains(-i);
            }
            if(!isWarmup) rbtContainsTimes[run - 1] = (System.nanoTime() - start) / 1_000_000.0;

            //delete
            start = System.nanoTime();
            for (int i = 0; i < 20_000; i++) rbt.delete(toDelete[i]);
            if (!isWarmup) rbtDeleteTimes[run - 1] = (System.nanoTime() - start) / 1_000_000.0;

            //sort
            start = System.nanoTime();
            rbt.inOrder();
            inOrderTime = (System.nanoTime() - start) / 1_000_000.0;
            if (!isWarmup) rbtSortTimes[run - 1] = insertTime + inOrderTime;

            //quicksort

            int[] quickData = baseData.clone();
            start = System.nanoTime();
            QuickSort.sort(quickData);
            if(!isWarmup) quicksortTimes[run - 1] = (System.nanoTime() - start) / 1_000_000.0;
        }
        System.out.println("\n\n--- RESULTS (in milliseconds) ---");
        System.out.println("Final Heights -> BST: " + bstHeight + " | RBT: " + rbtHeight);
        System.out.println();
        // RAW DATA 
        System.out.println("RAW SORTING TIMES (Runs 1-5):");
        System.out.println("BST Sort  : " + Arrays.toString(bstSortTimes));
        System.out.println("RBT Sort  : " + Arrays.toString(rbtSortTimes));
        System.out.println("QuickSort : " + Arrays.toString(quicksortTimes));
        System.out.println("---------------------------------");

        printStats("Insert ", bstInsertTimes, rbtInsertTimes);
        printStats("Contains (100k)", bstContainsTimes, rbtContainsTimes);
        printStats("Delete (20k)", bstDeleteTimes, rbtDeleteTimes);

        System.out.println("-".repeat(130));
        System.out.println(String.format("%-20s | %-35s | %-35s | %-35s", "SORTING", "BST Sort", "RBT Sort", "QuickSort"));
        
        System.out.println(String.format("%-20s | %-35s | %-35s | %-35s", 
                "Mean / Med / SD", 
                formatStats(bstSortTimes), 
                formatStats(rbtSortTimes), 
                formatStats(quicksortTimes)));
        System.out.println("\n");
    }
    private static void printStats(String operation , double[]bst , double[] rbt){
        double bstMean = calculateMean(bst);
        double rbtMean = calculateMean(rbt);
        double speedup = bstMean / rbtMean;

        System.out.println(String.format("%-20s | %-35s | %-35s | RBT Speed-up: %.2fx", 
                operation, "BST: " + formatStats(bst), "RBT: " + formatStats(rbt), speedup));
    }
    private static String formatStats(double[] times){
        return String.format("Mn: %.2f | Md: %.2f | SD: %.2f", 
                calculateMean(times), calculateMedian(times), calculateStdDev(times));
    }
    private static double calculateMean(double[] times){
        double sum = 0 ;
        for(double t: times) sum+=t;
        return sum / times.length;
    }
    private static double calculateMedian(double[] times){
        double[] sorted = times.clone();
        Arrays.sort(sorted);
        return sorted[sorted.length / 2];
    }
    private static double calculateStdDev(double[] times){
        double mean = calculateMean(times);
        double sumSq = 0 ;
        for(double t: times) sumSq += (t - mean) * (t - mean);
        return Math.sqrt(sumSq / times.length);
    }
}