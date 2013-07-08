package org.example.helpers;


import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

public class PathHelper {
    final static String TEMP_DIR = "temp";
    final static String PDF_DIR = "temp/answers";
    final static String FRAGMENT_DIR = "temp/fragments";

    public final static File temp = new File(TEMP_DIR);
    public final static File pdf = new File(PDF_DIR);
    public final static File fragments = new File("temp/fragments");

    public static String getFilename(String filepath) {
        return FilenameUtils.getBaseName(filepath);
    }

    public static String getFileExtension(String filepath) {
        return FilenameUtils.getExtension(filepath);
    }

    public static File getTempFile(String filename, String ext) throws IOException {
        if (filename == null )
            filename = "";

        return File.createTempFile(filename, "." + ext, temp);
    }

    public static File getPdfFile(String filename) throws IOException {
        if (filename == null)
            filename = "";
        return File.createTempFile(filename, ".pdf", pdf);
    }
}
