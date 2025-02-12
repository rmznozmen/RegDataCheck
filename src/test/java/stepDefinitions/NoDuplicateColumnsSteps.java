package stepDefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import utils.FileValidationUtils;

public class NoDuplicateColumnsSteps {

    private static final String BASE_PATH = "src/test/resources/files/"; // Report files directory
    private final Map<String, Boolean> fileValidationResults = new HashMap<>();

    @Given("I have the report files starting with {string} in {string}")
    public void i_have_the_report_files_starting_with(String prefix, String directoryPath) {
        File dir = new File(BASE_PATH);
        Assert.assertTrue("Report directory does not exist!", dir.exists());
    }

    @When("I check each y_* file for duplicate columns")
    public void i_check_each_y_file_for_duplicate_columns() throws IOException {
        File reportDirectory = new File(BASE_PATH);
        File[] allReportFiles = reportDirectory.listFiles((dir, name) -> name.startsWith("y_") && name.endsWith(".csv"));

        if (allReportFiles == null || allReportFiles.length == 0) {
            Assert.fail("No y_* report files found in the directory!");
        }

        for (File reportFile : allReportFiles) {
            boolean filePassed = FileValidationUtils.checkForDuplicateColumns(reportFile);
            fileValidationResults.put(reportFile.getAbsolutePath(), filePassed);
        }
    }

    @Then("the validation should pass if no columns are duplicated")
    public void the_validation_should_pass() {
        System.out.println("\n🔹 **Duplicate Column Validation Summary** 🔹");

        boolean hasFailures = false;

        for (Map.Entry<String, Boolean> result : fileValidationResults.entrySet()) {
            System.out.println(result.getKey() + " → " + (result.getValue() ? "✅ Passed" : "❌ Failed"));
            if (!result.getValue()) {
                hasFailures = true;
            }
        }

        if (hasFailures) {
            Assert.fail("Some files contain duplicate columns. See summary above.");
        }
    }

    @Then("report any duplicate columns found")
    public void report_any_duplicate_columns() {
        System.out.println("\n🔹 **Duplicate Column Details** 🔹");

        boolean hasDuplicates = false;

        for (Map.Entry<String, Boolean> result : fileValidationResults.entrySet()) {
            if (!result.getValue()) {
                System.out.println("❌ " + result.getKey() + " → Contains duplicate columns!");
                hasDuplicates = true;
            }
        }

        if (!hasDuplicates) {
            System.out.println("✅ No duplicate columns found in any file.");
        }
    }
}
