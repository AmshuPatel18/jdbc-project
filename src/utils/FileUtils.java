package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
    public static byte[] readFileToBytes(String path) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }

    public static void writeBytesToFile(byte[] data, String path) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(new File(path))) {
            fos.write(data);
        }
    }
}
