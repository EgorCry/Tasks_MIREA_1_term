import java.util.Scanner;
import java.util.concurrent.*;

public class Task2 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter number of just 'q' to quit: ");
            String input = scanner.nextLine();

            if ("q".equalsIgnoreCase(input)) {
                break;
            }

            try {
                int number = Integer.parseInt(input);

                Future<Integer> future = executorService.submit(() -> {
                    int delay = (int) (Math.random() * 5) + 1;
                    Thread.sleep(delay * 1000);
                    return number * number;
                });

                try {
                    int result = future.get();
                    System.out.println("Result: " + result);
                } catch (InterruptedException | ExecutionException e) {
                    System.err.println("There was an error during computing your request.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Enter correct number.");
            }
        }

        executorService.shutdown();
        scanner.close();
    }
}
