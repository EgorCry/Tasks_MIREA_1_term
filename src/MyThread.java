import java.util.concurrent.CountDownLatch;

class MyThread extends Thread {

    public int[] arr;
    public int max;
    CountDownLatch latch;

    public MyThread(int[] arr, CountDownLatch latch) {
        this.arr = arr;
        this.max = arr[0];
        this.latch = latch;
    }

    public int getMax() {
        return max;
    }

    @Override
    public void run() {
        for (int num : arr) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (num > max) {
                max = num;
            }
        }
        latch.countDown();
    }
}
