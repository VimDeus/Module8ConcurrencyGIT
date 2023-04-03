import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelSum {

    private static final int ARRAY_SIZE = 200_000_000;
    private static final int THREAD_COUNT = 4;
    private static final int MAX_NUMBER = 10;

    public static void main(String[] args) {
    	
             int[] array = generateRandomArray(ARRAY_SIZE);

        long startTime = System.currentTimeMillis();
        int parallelSum = computeParallelSum(array, THREAD_COUNT);
        long parallelTime = System.currentTimeMillis() - startTime;

        startTime = System.currentTimeMillis();
        int singleThreadSum = computeSingleThreadSum(array);
        long singleThreadTime = System.currentTimeMillis() - startTime;

        System.out.println("Parallel sum: " + parallelSum);
        System.out.println("Parallel time: " + parallelTime + " ms");
        System.out.println("Single thread sum: " + singleThreadSum);
        System.out.println("Single thread time: " + singleThreadTime + " ms");
    }

    static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(MAX_NUMBER) + 1;
        }
        return array;
    }

    static int computeParallelSum(int[] array, int threadCount) {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        int chunkSize = array.length / threadCount;
        int[] partialSums = new int[threadCount];

        for (int i = 0; i < threadCount; i++) {
            int startIndex = i * chunkSize;
            int endIndex = (i == threadCount - 1) ? array.length : startIndex + chunkSize;
            executor.submit(new PartialSumTask(array, startIndex, endIndex, partialSums, i));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int finalSum = 0;
        for (int i = 0; i < threadCount; i++) {
            finalSum += partialSums[i];
        }
        return finalSum;
    }

    static int computeSingleThreadSum(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    private static class PartialSumTask implements Runnable {
        private final int[] array;
        private final int startIndex;
        private final int endIndex;
        private final int[] partialSums;
        private final int index;

        public PartialSumTask(int[] array, int startIndex, int endIndex, int[] partialSums, int index) {
            this.array = array;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.partialSums = partialSums;
            this.index = index;
        }

        @Override
        public void run() {
            int partialSum = 0;
            for (int i = startIndex; i < endIndex; i++) {
                partialSum += array[i];
            }
            partialSums[index] = partialSum;
        }
    }
}
