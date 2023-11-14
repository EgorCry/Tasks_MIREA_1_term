package Tasks_31102023.text;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;

import java.util.HashMap;
import java.util.Map;

public class Task_4 {
    private WatchService watchService;
    private Map<Path, FileInfo> fileInfoMap;

    public Task_4() throws IOException {
        this.watchService = FileSystems.getDefault().newWatchService();
        this.fileInfoMap = new HashMap<>();
    }

    public void watchDirectory(Path path) throws IOException {
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
        System.out.println("Watching directory: " + path);
        startWatching();
    }

    private void startWatching() throws IOException {
        WatchKey key;
        while (true) {
            try {
                key = watchService.take();
            } catch (InterruptedException e) {
                return;
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }

                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path filename = ev.context();

                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    handleFileCreation(filename);
                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    handleFileDeletion(filename);
                }
            }

            key.reset();
        }
    }

    private void handleFileCreation(Path filename) throws IOException {
        Path filePath = Paths.get("directory_path", filename.toString());
        File file = filePath.toFile();
        if (file.exists() && file.isFile()) {
            FileInfo fileInfo = new FileInfo(file);
            fileInfoMap.put(filePath, fileInfo);
            System.out.println("New file created: " + filename);
        }
    }

    private void handleFileDeletion(Path filename) {
        Path filePath = Paths.get("directory_path", filename.toString());
        if (fileInfoMap.containsKey(filePath)) {
            FileInfo fileInfo = fileInfoMap.get(filePath);
            System.out.println("File deleted: " + filename);
            System.out.println("Size: " + fileInfo.getSize() + " bytes");
            System.out.println("Checksum: " + fileInfo.getChecksum());
            fileInfoMap.remove(filePath);
        }
    }

    public static void main(String[] args) throws IOException {
        Task_4 fileWatcher = new Task_4();
        Path directoryPath = Paths.get("directory_path");
        fileWatcher.watchDirectory(directoryPath);
    }
}

class FileInfo {
    private final long size;
    private final int checksum;

    public FileInfo(File file) throws IOException {
        this.size = file.length();
        this.checksum = calculateChecksum(file);
    }

    public long getSize() {
        return size;
    }

    public int getChecksum() {
        return checksum;
    }

    private int calculateChecksum(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        FileChannel fc = fis.getChannel();

        int sz = (int) fc.size();
        MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz);

        int sum = sum(bb);

        fc.close();
        fis.close();

        return sum;
    }

    private int sum(ByteBuffer bb) {
        int sum = 0;
        while (bb.hasRemaining()) {
            if ((sum & 1) != 0)
                sum = (sum >> 1) + 0x8000;
            else
                sum >>= 1;
            sum += bb.get() & 0xff;
            sum &= 0xffff;
        }
        return sum;
    }
}

