@QA_regression
Feature: Petstore API

  Scenario: List pets API
    Given user has petstore endpoint
    When user sends GET request to list pets
    Then status code is 200
    And response contains list of pets

  @QA_smoke
  Scenario Outline: Get non-existing pet
    Given user has petstore endpoint
    When user sends <petIdType> pet by id GET request
    Then status code is 404
    And error message is '<errorMessage>'

    Examples:
      | petIdType    | errorMessage                                                       |
      | invalid      | java.lang.NumberFormatException: For input string: \"52a3da35a23\" |
      | non-existing | Pet not found                                                      |


