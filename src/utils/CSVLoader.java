package utils;

import java.io.*;
import java.util.*;

/**
 * Class to load data from CSV files.
 */
public class CSVLoader {
    /**
     * Load CSV file and return list of string arrays.
     * @param filename
     * @return List of string arrays representing CSV rows
     */
    public static List<String[]> loadCSV(String filename) {
        List<String[]> data = new ArrayList<>();
        String line;

        // Read file in a try-with-resources block
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Skip header
            br.readLine();

            // Read each line and split by comma
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // Push values to data list
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