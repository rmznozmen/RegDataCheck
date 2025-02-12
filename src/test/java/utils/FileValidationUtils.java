package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileValidationUtils {

    public static boolean checkForDuplicateColumns(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());

        if (lines.isEmpty()) {
            System.out.println("⚠️ File is empty: " + file.getName());
            return true; // Accept empty files as passed
        }

        String[] headers = lines.get(0).split(",");
        Set<String> uniqueColumns = new HashSet<>();
        boolean filePassed = true;

        for (String header : headers) {
            String trimmedHeader = header.trim();
            if (!uniqueColumns.add(trimmedHeader)) {
                System.out.println("❌ ERROR: Duplicate column '" + trimmedHeader + "' found in file: " + file.getName());
                filePassed = false;
            }
        }

        return filePassed;
    }

    public static Map<String, String> extractYesNoColumns(String excelPath, String basePath) throws IOException {
        Map<String, String> reportFiles = new HashMap<>();

        FileInputStream fis = new FileInputStream(excelPath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet("Field Definitions");

        for (Row row : sheet) {
            Cell typeCell = row.getCell(2);  // Column C (Type)
            Cell columnCodeCell = row.getCell(0);  // Column A (Column Code)

            if (typeCell != null && columnCodeCell != null) {
                // Normalize the "Type" value
                String typeValue = typeCell.getStringCellValue().trim().replaceAll("[^a-zA-Z/]", "").toLowerCase();
                String columnCode = columnCodeCell.getStringCellValue().trim();

                // Check if the column type is "yes/no"
                if (typeValue.equalsIgnoreCase("yes/no") || typeValue.equalsIgnoreCase("yesno")) {
                    // Extract related CSV file based on column code prefix
                    String[] parts = columnCode.split("\\.");
                    if (parts.length >= 3) {
                        String reportFile = parts[0] + "." + parts[1] + ".csv"; // Example: y_02.02.csv
                        String filePath = basePath + reportFile;

                        if (Files.exists(Paths.get(filePath))) {
                            reportFiles.put(filePath,"c"+ parts[2]); // Store file path + column name (e.g., "0020")
                        }
                    }
                }
            }
        }

        workbook.close();
        fis.close();

        return reportFiles;
    }

    public static boolean validateYesNoColumn(String filePath, String columnName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        String[] headers = lines.get(0).split(",");
        int columnIndex = -1;

        // Find the correct column index
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].trim().equalsIgnoreCase(columnName)) {
                columnIndex = i;
                break;
            }
        }

        if (columnIndex == -1) {
            System.out.println("⚠️ Column " + columnName + " NOT FOUND in " + filePath);
            return false; // Fail if column is missing
        }

        boolean filePassed = true;
        System.out.println("\n🔹 Validating column: " + columnName + " in file: " + filePath);

        for (int i = 1; i < lines.size(); i++) {
            String[] values = lines.get(i).split(",");

            if (values.length > columnIndex) {
                String value = values[columnIndex].trim();
                System.out.println("Row " + i + " → " + columnName + ": [" + value + "]"); // Debug print

                if (!value.equalsIgnoreCase("Yes") && !value.equalsIgnoreCase("No")) {
                    System.out.println("❌ ERROR: Invalid value '" + value + "' in " + columnName + " (Row " + (i+1) + ") of file " + filePath);
                    filePassed = false;
                }
            }
        }
        return filePassed;
    }
}
