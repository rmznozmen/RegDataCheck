RegDatCheck - Automated Regulatory Data Validation
Version: 1.0
Author: Ramazan Ozmen
Date: [13/02/2025]

Project Overview
RegDatCheck is a Cucumber-based test automation framework designed to validate regulatory report files for compliance. It ensures data integrity by detecting:
✅ Duplicate columns in report files.
✅ Incorrect "Yes/No" values in predefined columns.

This solution provides fast, accurate, and scalable test execution to help businesses maintain data accuracy and compliance.

Project Structure
RegDatCheck/  (Project Root)
 ├── src/
 │   ├── test/
 │   │   ├── java/
 │   │   │   ├── step_definitions/   # 📌 Cucumber Step Definitions
 │   │   │   │   ├── NoDuplicateColumnsSteps.java  # Scenario 1: Duplicate Column Check
 │   │   │   │   ├── YesNoValidationSteps.java     # Scenario 2: Yes/No Validation
 │   │   │   ├── utils/              # 📌 Utility Functions (Reusable Methods)
 │   │   │   │   ├── FileValidationUtils.java     # Common validation logic
 │   │   │   ├── runners/            # 📌 Cucumber Test Runner
 │   │   │   │   ├── TestRunner.java  # Main Test Runner File
 │   ├── resources/
 │   │   ├── features/           # 📌 Cucumber Feature Files
 │   │   │   ├── NoDuplicateColumnsSteps.feature
 │   │   │   ├── Validate_yes_no.feature                
 │   │   ├── files/              # 📌 Test Data (CSV Report Files)
 │   │   │   ├── y_01.01.csv  ✅ Sample Report File
 │   │   │   ├── y_02.01.csv  ✅ Sample Report File
 │   │   │   ├── y_02.02.csv  ✅ Sample Report File
 │   │   │   ├── y_02.03.csv  ✅ Sample Report File
 │   │   │   ├── y_07.01.csv  ✅ Sample Report File
 │   │   │   ├── y_99.01.csv  ✅ Sample Report File
 │   │   ├── requirements/        
 │   │   │   ├── Data Fields_New.xlsx  ✅ Regulatory Definition File
 ├── target/  # 📌 Test Reports (Generated After Running Tests)
 │   ├── cucumber-reports/
 │   │   ├── cucumber.html  ✅ Test Report
 │   │   ├── cucumber.json
 │   │   ├── cucumber.xml
 ├── pom.xml  # 📌 Maven Dependencies
 ├── README.md  # 📌 Project Documentation


Setup & Installation
Prerequisites
Java 11+
Maven
IntelliJ IDEA (Recommended)
Cucumber for Java Plugin (IntelliJ Plugin)
Apache POI (For Excel file processing)


Clone the Repository
Copy
git clone https://github.com/rmznozmen/RegDataCheck
cd RegDatCheck

Install Dependencies
If using Maven, install all required dependencies


Run Tests from IntelliJ
Open TestRunner.java (src/test/java/runners/)
Right-click → Run TestRunner 

Viewing Test Reports
After running tests, the Cucumber HTML report is generated in:
target/cucumber-reports/cucumber.html

To view the report:
Navigate to target/cucumber-reports/
Open cucumber.html in a web browser

Test Scenarios
Scenario 1: No Duplicate Columns
Feature File: validate_no_duplicate_columns.feature

Objective: Ensures that no column appears twice in any report file. 
Test passes if all files have unique column names.
Test fails if duplicate columns exist.

Scenario 2: Validate Yes/No Type
Feature File: validate_yes_no.feature
Objective: Ensures that Yes/No columns only contain valid values.
✅ Test passes if all values are "Yes" or "No".
❌ Test fails if any invalid values exist (e.g., "Maybe").

Future Enhancements
✔ Add more validation rules for date formats,numeric fields, list options
✔ Add more validation for mandatory/optional
✔ Integrate with CI/CD pipelines (Jenkins, GitHub Actions)
✔ Implement a configurable rules engine for flexible testing

Contact
For questions or support, contact:
📧 rmzan.ozmen@gmail.com
📂 https://github.com/rmznozmen/RegDataCheck