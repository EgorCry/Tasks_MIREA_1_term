package Task3;

import Task3.File;
import java.util.LinkedList;
import java.util.Queue;

public class FileQueue {
    Queue<File> queue = new LinkedList<>();

    public synchronized File get() {
        while (queue.size()<1) {
            try{
                wait();
            }
            catch (InterruptedException e) {
            }
        }
        notify();
        return queue.peek();
    }

    public synchronized File cut() { //delete head of queue
        while (queue.size()<1) {
            try{
                wait();
            }
            catch (InterruptedException e) {
            }
        }
        notify();
        System.out.println("FileQueue: File " + queue.peek().name + " deleted from the queue");

        return queue.poll();
    }
    public synchronized void put(File file) {
        while (queue.size()>=5) {
            try {
                wait();
            }
            catch (InterruptedException e) {
            }
        }
        System.out.println("FileQueue: File "
                + file.name +
                " added to the queue");
        notify();
        queue.add(file);
    }
}
