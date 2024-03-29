@UI
Feature: TE of US_001 send SMS to a client functionality

  Scenario: TC_01 US_001 send SMS to a client functionality
    Given User login as tr2
    And User goes to E-mail and SMS section
    And User clicks on Send SMS section
    When User send a SMS with valid data
      | deneme sms | erdal@yopmail.com |
    Then User should see a success message
