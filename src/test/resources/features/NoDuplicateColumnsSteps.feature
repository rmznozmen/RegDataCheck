Feature: Validate No Duplicate Columns in Report Files

  Scenario: Ensure that no column is reported twice in y_* files
    Given I have the report files starting with "y_" in "src/test/resources/files"
    When I check each y_* file for duplicate columns
    Then the validation should pass if no columns are duplicated
    And report any duplicate columns found