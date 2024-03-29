@UI
Feature: TE of US_003 send test SMS functionality

  Scenario: TC_01 US_003 send test SMS functionality
    Given User login as tr2
    And User goes to E-mail and SMS section
    And User clicks on Send SMS section
    When User send test SMS "test SMS" to "+491784443506"
    Then User should see a success message