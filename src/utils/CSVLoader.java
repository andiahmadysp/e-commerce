package utils;

import java.io.*;
import java.util.*;

public class CSVLoader {

    public static List<String[]> loadCSV(String filename) {
        List<String[]> data = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }

            System.out.println("Berhasil memuat " + data.size() + " data dari " + filename);

        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan: " + filename);
            System.out.println("  Pastikan folder 'data/' ada di root project!");
        } catch (IOException e) {
            System.out.println("Error membaca file: " + e.getMessage());
        }

        return data;
    }
}