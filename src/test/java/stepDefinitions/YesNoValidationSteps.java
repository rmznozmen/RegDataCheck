package stepDefinitions;

import io.cucumber.java.en.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import utils.FileValidationUtils;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class YesNoValidationSteps {

    private static final String BASE_PATH = "src/test/resources/files/";
    private static final String EXCEL_PATH = "src/test/resources/requirements/Data Fields_New.xlsx";
    private Map<String, String> reportFiles = new HashMap<>();
    private final Map<String, Boolean> fileValidationResults = new HashMap<>();

    private boolean validationPassed = true;

    @Given("I have the data field definitions from {string}")
    public void i_have_the_data_field_definitions(String excelPath) throws IOException {
        reportFiles = FileValidationUtils.extractYesNoColumns(EXCEL_PATH, BASE_PATH);
    }


    @When("I find all report files containing YesNo columns")
    public void i_find_all_report_files() {
        if (reportFiles.isEmpty()) {
            throw new IllegalStateException("No report files found containing Yes/No columns! Please check the Excel file.");
        } else {
            System.out.println("Found Yes/No columns in report files: " + reportFiles);
        }
    }

    @When("I validate those columns for YesNo values")
    public void i_validate_yes_no_columns() throws IOException {
        Map<String, Boolean> fileValidationResults = new HashMap<>();

        File reportDirectory = new File(BASE_PATH);
        File[] allReportFiles = reportDirectory.listFiles((dir, name) -> name.endsWith(".csv"));

        if (allReportFiles == null || allReportFiles.length == 0) {
            Assert.fail("No report files found in the directory!");
        }

        // Normalize paths in reportFiles to absolute paths
        Map<String, String> normalizedReportFiles = new HashMap<>();
        for (Map.Entry<String, String> entry : reportFiles.entrySet()) {
            normalizedReportFiles.put(Paths.get(entry.getKey()).toAbsolutePath().toString(), entry.getValue());
        }

        // Process each file
        for (File reportFile : allReportFiles) {
            String absoluteFilePath = reportFile.getAbsolutePath(); // Normalize path

            if (normalizedReportFiles.containsKey(absoluteFilePath)) {
                // File has a Yes/No column → Validate it
                boolean filePassed = FileValidationUtils.validateYesNoColumn(absoluteFilePath, normalizedReportFiles.get(absoluteFilePath));
                fileValidationResults.put(absoluteFilePath, filePassed);
            } else {
                // File does NOT have a Yes/No column → Mark as Passed
                fileValidationResults.put(absoluteFilePath, true);
            }
        }

        // Print Summary
        System.out.println("\n🔹 **Validation Summary** 🔹");
        for (Map.Entry<String, Boolean> result : fileValidationResults.entrySet()) {
            System.out.println(result.getKey() + " → " + (result.getValue() ? "✅ Passed" : "❌ Failed"));
        }

        // Fail the test only if a file with Yes/No fields contains invalid values
        if (fileValidationResults.containsValue(false)) {
            Assert.fail("Some files with Yes/No fields contain invalid values. See summary above.");
        }
    }

    @Then("the validation should pass if all values are either {string} or {string}")
    public void the_validation_should_pass(String yes, String no) {
        Assert.assertTrue("Validation failed due to incorrect Yes/No values!", validationPassed);
    }

    @Then("report any invalid values found")
    public void report_any_invalid_values() {
        if (!validationPassed) {
            System.out.println("Test failed due to invalid Yes/No values.");
        }
    }

}
