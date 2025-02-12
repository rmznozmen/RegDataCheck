Feature: Validate Yes/No Type Columns

  Scenario: Verify Yes/No values dynamically from report files
    Given I have the data field definitions from "requirements/Data Fields_New.xlsx"
    When I find all report files containing YesNo columns
    And I validate those columns for YesNo values
    Then the validation should pass if all values are either "Yes" or "No"
    And report any invalid values found