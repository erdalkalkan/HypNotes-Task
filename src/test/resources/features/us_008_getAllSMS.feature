@API
Feature: Test Execution of US_008 Get All SMS

  Scenario: TC_001 US_008 Get All SMS
    When user can get all SMS history "hypnotherapist/sms/sms-history"
    Then user verifies status code 200