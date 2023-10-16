import java.util.concurrent.TimeUnit;

public class Task1_1 {
    public static void main(String[] args) throws InterruptedException {
        long usedBytes = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();

        int[] array;
        array = new int[10_000];

        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        long start = System.currentTimeMillis();
        int max = array[0];
        for(int i = 0; i < array.length; i++){
            if (i > max)
                max = i;
            TimeUnit.MILLISECONDS.sleep(1);
        }
        long finish = System.currentTimeMillis();
        long time = finish - start;
        System.out.println("Used " + usedBytes + " bites\n"
                + "Usage time: " + time + " milliseconds\n" + "Maximum element of massive: " + max);
    }
}
