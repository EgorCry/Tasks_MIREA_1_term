package Task3;

import java.util.concurrent.TimeUnit;

public class FileHandler implements Runnable {
    FileQueue fileQueue;
    FileHandler(FileQueue fileQueue){
        this.fileQueue=fileQueue;
    }

    public void run() {
        for (int i = 0; i < 6; i++) {
            File file = fileQueue.get();
            try{
                Integer time = file.size*7;
                TimeUnit.MILLISECONDS.sleep(time);
                System.out.println("FileHandler: File "
                        + file.name
                        + " executed for "
                        + time + " milliseconds");
                fileQueue.cut();
            }
            catch (Exception e){
            }
        }
    }
}
