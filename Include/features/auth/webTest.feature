
@webTest
Feature: Login Web Sauce Demo

  @webLoginTest
  Scenario: Login Success Website Sauce Demo
    Given user open url in the browser
    When user input valid credentials username "standard_user" and password "secret_sauce"
    And user click login button
    Then user should be logged in successfully
