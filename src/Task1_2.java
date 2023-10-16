import java.util.Date;
import java.util.concurrent.ForkJoinPool;

public class Task1_2 {
    public static void main(String[] args) throws Exception {
        long usedBytes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        int[] array = getInitArray(10_000);

        ValueMaxCounter counter = new ValueMaxCounter(array);

        System.out.println(new Date());
        long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.out.println(new Date());
        long finish = System.currentTimeMillis();
        long time = finish - start;
        System.out.println(new Date());

        System.out.println("Used " + usedBytes + " bites\n"
                + "Usage time: " + time + " milliseconds\n" + "Maximum element of massive: " +
                forkJoinPool.invoke(counter));
    }

    public static int[] getInitArray(int capacity) {
        int[] array = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            array[i] = i;
        }
        return array;
    }
}
