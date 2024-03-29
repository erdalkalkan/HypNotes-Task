@UI
Feature: TE of US_004 SMS Section Functionallity

  Scenario: TC_001 US_004 SMS Section Functionallity
    Given User login as tr2
    And User goes to E-mail and SMS section
    And User clicks on Send SMS section
    Then User should be able to see Send SMS button clickable
    Then User should be able to see Sellect All Clients switch box clickable
    Then User should be able to see Test SMS check box clickable
    Then User should be able to see message box functional
    Then User should be able to see Sent SMS Box button clickable