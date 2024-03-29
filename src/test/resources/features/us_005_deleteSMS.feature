@UI
Feature: TE of US_005 Delete SMS

  Scenario: TC_01 US_005 Delete SMS
    Given User login as tr2
    And User goes to E-mail and SMS section
    And User clicks on Send SMS section
    And User clicks on Send SMS Box button
    Then User should be able to delete all saved SMS