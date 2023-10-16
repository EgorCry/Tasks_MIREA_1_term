package Task3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FileFactory implements Runnable {
    FileQueue fileQueue;
    static Queue<String> nameList = new LinkedList<String>();
    String[] typeList={"XML", "JSON", "XLS"};
    FileFactory(FileQueue fileQueue){
        this.fileQueue=fileQueue;
        addAllElements();
    }

    static public void addAllElements(){
        for (int i = 0; i < 6; i++) {
            nameList.add("name" + i);
        }
    }

    public void run() {
        Callable task = () -> {
            Integer time = (int) (Math.random() * 900 + 100);
            TimeUnit.MILLISECONDS.sleep(time);
            String type = typeList[(int) (Math.random() * 2)];
            Integer size = (int) (Math.random() * 900 + 100);
            File file = new File(nameList.poll(), type, size);
            System.out.println("FileFactory: " +
                    file.name + "(Type: "
                    + file.type +
                    ", size: "
                    + file.size
                    + " bite)"
                    + " created by "
                    + time
                    + " milliseconds");
            return file;
        };
        for (int i = 0; i < 6; i++) {
            FutureTask<File> file = new FutureTask<>(task);
            new Thread(file).start();
            try{
                fileQueue.put(file.get());
            } catch(Exception e){

            }
        }
    }
}
