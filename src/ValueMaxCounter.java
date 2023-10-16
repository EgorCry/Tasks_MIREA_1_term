import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

class ValueMaxCounter extends RecursiveTask<Integer> {

    private int[] array;
    public ValueMaxCounter(int[] array) {
        this.array = array;
    }

    @Override
    protected Integer compute() {
        if(array.length <= 2) {
            try{
                System.out.printf("Task %s execute in thread %s%n", this, Thread.currentThread().getName());
                Thread.sleep(1);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Arrays.stream(array).max().getAsInt();
        }
        ValueMaxCounter firstHalfArrayValueMaxCounter = new ValueMaxCounter(Arrays.copyOfRange(array, 0, array.length/2));
        ValueMaxCounter secondHalfArrayValueMaxCounter = new ValueMaxCounter(Arrays.copyOfRange(array, array.length/2, array.length));
        firstHalfArrayValueMaxCounter.fork();
        secondHalfArrayValueMaxCounter.fork();
        firstHalfArrayValueMaxCounter.join();
        secondHalfArrayValueMaxCounter.join();
        return Arrays.stream(array).max().getAsInt();
    }
}
