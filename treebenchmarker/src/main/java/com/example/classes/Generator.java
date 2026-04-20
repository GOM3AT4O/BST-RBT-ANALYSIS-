package com.example.classes;
import java.util.Random;
public class Generator {
    public static final int N = 100000;

    private static final long SEED = 42L; // Fixed seed for reproducibility
    //random [0-10n]
    public static int[] generateRandom(){
        Random rand = new Random(SEED);
        int[] arr = new int[N];
        int maxRange = 10 * N;
        for(int i = 0 ; i < N ; i++){
            arr[i] = rand.nextInt(maxRange+1);
        }
    return arr;
    }

    public static int[] generateNearlySorted(int swapPercentage){
        Random rand = new Random(SEED);
        int[] arr = new int[N];
        for(int i = 0 ; i < N ; i++){
            arr[i] = i;
        }
        //do random swaps
        int numSwaps = (int) ((swapPercentage / 100.0) * N);

        for(int i = 0 ; i< numSwaps;i++){
            int index1 = rand.nextInt(N);
            int index2 = rand.nextInt(N);

            int temp = arr[index1];
            arr[index1] = arr[index2];
            arr[index2] = temp;
        }
        return arr;

    }


}
