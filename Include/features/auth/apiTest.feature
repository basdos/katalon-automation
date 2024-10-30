@apiTest
Feature: Login Api Dummy Json

  @apiLoginTest
  Scenario: Login Success Api Dummy Json
    Given user set api request login
    When user hit request login
    Then user verify status code and response body
