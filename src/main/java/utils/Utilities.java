package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Utilities {






    public static String getFormattedJson(JsonObject object) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Pretty print на JSON
        String prettyJson = gson.toJson(object);
        return prettyJson;
    }


    public static String getDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH-mm");
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        String date = dateFormatter.format(localDate);
        String time = timeFormatter.format(localTime);
        return date + "_" + time;
    }


    public static void zip(String sourceDirPath, String zipFilePath) throws IOException {

        Path p = Files.createFile(Paths.get(zipFilePath));
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
            Path pp = Paths.get(sourceDirPath);
            Files.walk(pp)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            zs.closeEntry();
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                    });
        }
    }
}
