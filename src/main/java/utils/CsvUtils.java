package utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvUtils {

    /**
     * Reads a CSV from resources/data
     */
    public static List<String[]> readResourceCsv(String fileName, boolean hasHeader) {
        List<String[]> data = new ArrayList<>();

        try (CSVReader reader = new CSVReader(
                new InputStreamReader(
                        CsvUtils.class.getClassLoader().getResourceAsStream("data/" + fileName)
                ))) {
            String[] row;
            boolean firstLine = true;

            while ((row = reader.readNext()) != null) {
                if (hasHeader && firstLine) {
                    firstLine = false;
                    continue;
                }
                data.add(row);
            }
        } catch (IOException | CsvValidationException | NullPointerException e) {
            throw new RuntimeException("❌ Failed to read CSV file from resources/data/" + fileName, e);
        }
        return data;
    }
}
