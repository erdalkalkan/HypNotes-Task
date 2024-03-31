@API
Feature: Test Execution of US_007 Send a SMS

  Scenario: TC_001 US_007 Send a SMS
    When user can send SMS at "dashboard/bulkSms-client"
    Then user verifies status code 200