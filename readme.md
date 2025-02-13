ReDatCheck - Automated Regulatory Data Validation
Version: 1.0
Author: Ramazan Ozmen
Date: [13/02/2025]

📌 Project Overview
ReDatCheck is a Cucumber-based test automation framework designed to validate regulatory report files for compliance. It ensures data integrity by detecting:
✅ Duplicate columns in report files.
✅ Incorrect "Yes/No" values in predefined columns.
✅ Missing required columns based on regulatory definitions.

This solution provides fast, accurate, and scalable test execution to help businesses maintain data accuracy and compliance.

📂 Project Structure
RegDatCheck/  (Project Root)
 ├── src/
 │   ├── test/
 │   │   ├── java/
 │   │   │   ├── step_definitions/   
 │   │   │   │   ├── NoDuplicateColumnsSteps.java  # Scenario 1: Duplicate Column Check
 │   │   │   │   ├── YesNoValidationSteps.java     # Scenario 2: Yes/No Validation
 │   │   │   ├── utils/              
 │   │   │   │   ├── FileValidationUtils.java     # Common validation logic
 │   │   │   ├── runners/            
 │   │   │   │   ├── TestRunner.java  # Main Cucumber Test Runner
 │   ├── resources/
 │   │   ├── features/           
 │   │   │   ├── validate_no_duplicate_columns.feature  
 │   │   │   ├── validate_yes_no.feature                
 │   │   ├── files/              
 │   │   │   ├── y_01.01.csv  ✅ Sample Report File
 │   │   │   ├── y_02.01.csv  ✅ Sample Report File
 │   │   │   ├── y_02.02.csv  ✅ Sample Report File
 │   │   │   ├── y_02.03.csv  ✅ Sample Report File
 │   │   │   ├── y_07.01.csv  ✅ Sample Report File
 │   │   │   ├── y_99.01.csv  ✅ Sample Report File
 │   │   ├── requirements/        
 │   │   │   ├── Data Fields_New.xlsx  ✅ Regulatory Definition File
 ├── target/  
 │   ├── cucumber-reports/
 │   │   ├── cucumber.html  ✅ Test Report
 ├── pom.xml  # 📌 Maven Dependencies
 ├── README.md  # 📌 Project Documentation


🔧 Setup & Installation
1️⃣ Prerequisites
Java 11+
Maven
IntelliJ IDEA (Recommended)
Cucumber for Java Plugin (IntelliJ Plugin)
Apache POI (For Excel file processing)


2️⃣ Clone the Repository
Copy
git clone https://github.com/rmznozmen/RegDataCheck
cd ReDatCheck

3️⃣ Install Dependencies
If using Maven, install all required dependencies:

Copy
mvn clean install

🚀 Running Tests
1️⃣ Run Tests Using Maven
Copy
mvn test

2️⃣ Run Tests from IntelliJ
Open TestRunner.java (src/test/java/runners/)
Right-click → Run TestRunner
📊 Viewing Test Reports
After running tests, the Cucumber HTML report is generated in:
Copy
target/cucumber-reports/cucumber.html
To view the report:

Navigate to target/cucumber-reports/
Open cucumber.html in a web browser

🔍 Test Scenarios
1️⃣ Scenario: No Duplicate Columns
Feature File: validate_no_duplicate_columns.feature

Objective: Ensures that no column appears twice in any report file.
✅ Test passes if all files have unique column names.
❌ Test fails if duplicate columns exist.

2️⃣ Scenario: Validate Yes/No Type
Feature File: validate_yes_no.feature
Objective: Ensures that Yes/No columns only contain valid values.
✅ Test passes if all values are "Yes" or "No".
❌ Test fails if any invalid values exist (e.g., "Maybe").

🛠 Troubleshooting
Issue: Step undefined when running tests
✅ Fix: Ensure glue = {"StepDefinitions"} is correctly set in TestRunner.java.

Issue: cucumber.html report is missing
✅ Fix: Ensure "html:target/cucumber-reports/cucumber.html" is included in @CucumberOptions and re-run tests.

📌 Future Enhancements
✔ Add more validation rules for date formats and numeric fields
✔ Integrate with CI/CD pipelines (Jenkins, GitHub Actions)
✔ Implement a configurable rules engine for flexible testing

👨‍💻 Contact
For questions or support, contact:
📧 rmzan.ozmen@gmail.com
📂 https://github.com/rmznozmen/RegDataCheck