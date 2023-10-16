import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Task1_3 {
    public static void main(String[] args) throws InterruptedException {
        int[] array;
        array = new int[10_000];

        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        findMinInSeveralThreads(array, 8);
    }
    public static Integer findMinInSeveralThreads(int[] arr, int numOfThreads)
            throws InterruptedException {
        List<MyThread> threads = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(numOfThreads);
        long start = System.currentTimeMillis();
        for (int i = 0; i < numOfThreads; i++) {
            MyThread thread = new MyThread(Arrays.copyOfRange(arr, i * 10_000 / numOfThreads, (i + 1) * 10_000 / numOfThreads), latch);
            threads.add(thread);
            thread.start();
        }
        latch.await();
        int result = threads.stream().max(Comparator.comparing(MyThread::getMax)).get().max;
        long end = System.currentTimeMillis();
        long usedBytes = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        System.out.println("Used " + usedBytes + " bites\n"
                + "Usage time: " + (end - start) + " milliseconds\n" + "Maximum element of massive: " +
                result);
        return result;
    }
}
