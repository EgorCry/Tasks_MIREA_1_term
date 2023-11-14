package Tasks_31102023.text;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Task_2 {
    public static void copyUsingFileInputStreamOutputStream(String source, String destination) throws IOException {
        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(destination)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }

    public static void copyUsingFileChannel(String source, String destination) throws IOException {
        try (FileChannel sourceChannel = new FileInputStream(source).getChannel();
             FileChannel destinationChannel = new FileOutputStream(destination).getChannel()) {
            sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
        }
    }

    public static void copyUsingApacheCommonsIO(String source, String destination) throws IOException {
        try (InputStream is = new FileInputStream(source);
             OutputStream os = new FileOutputStream(destination)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }

    public static void copyUsingFilesClass(String source, String destination) throws IOException {
        Files.copy(new File(source).toPath(), new File(destination).toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void main(String[] args) throws IOException {
        System.setProperty("file.encoding", "UTF-8");

        String sourceFile = "src/Tasks_31102023/text/java_book.pdf";
        String destinationFile = "new_java_book.pdf";
        long startTime, endTime;

        startTime = System.currentTimeMillis();
        copyUsingFileInputStreamOutputStream(sourceFile, destinationFile);
        endTime = System.currentTimeMillis();
        System.out.println("FileInputStream/FileOutputStream time: " + (endTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        copyUsingFileChannel(sourceFile, destinationFile);
        endTime = System.currentTimeMillis();
        System.out.println("FileChannel time: " + (endTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        copyUsingApacheCommonsIO(sourceFile, destinationFile);
        endTime = System.currentTimeMillis();
        System.out.println("Apache Commons IO time: " + (endTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        copyUsingFilesClass(sourceFile, destinationFile);
        endTime = System.currentTimeMillis();
        System.out.println("Files class time: " + (endTime - startTime) + " ms");
    }
}
