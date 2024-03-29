@UI
Feature: TE of US_002 send SMS all client functionality

  Scenario: TC_01 US_002 send SMS all client functionality
    Given User login as tr2
    And User goes to E-mail and SMS section
    And User clicks on Send SMS section
    When User send all client "Hallo leute, wie gehts?"
    Then User should see a success message