package Task3;

public class Programme {
    public static void main(String[] args) {
        FileQueue fileQueue = new FileQueue();
        FileFactory fileFactory = new FileFactory(fileQueue);
        FileHandler fileHandler = new FileHandler(fileQueue);
        new Thread(fileFactory).start();
        new Thread(fileHandler).start();
    }
}

