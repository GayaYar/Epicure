Feature: restaurant feature

  Scenario: the user gets the popular restaurants
    Given all the restaurants are in the database
    When the user requests the popular restaurants
    Then the user gets the top 3 popular restaurants