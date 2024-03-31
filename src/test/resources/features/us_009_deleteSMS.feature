@API
Feature: Test Execution of US_009 delete added SMS

  Scenario: TC_001 US_009 delete added SMS
    When user can delete added SMS at "hypnotherapist/sms/delete-sms/"
    Then user verifies status code 200