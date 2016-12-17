package net.dkahn.web.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.struts.upload.FormFile;

public class UploadUtil {

    public static void writeToDisk(FormFile file, String fileName, String destinationPath) throws FileNotFoundException, IOException {

        InputStream stream = null;
        OutputStream bos = null;
        int bytesRead = 0;
        int fileSize = 0;
        byte[] buffer = null;

        stream = file.getInputStream();
        bos = new FileOutputStream(destinationPath + fileName);

        fileSize = file.getFileSize();

        bytesRead = 0;
        buffer = new byte[fileSize];
        while ((bytesRead = stream.read(buffer, 0, fileSize)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }

        bos.close();
        stream.close();
    }
}
