Feature: Login4Fails in practice site

  Scenario: Successful4Fail login
    When I navigate to "https://simileyskiy.com/login-form/"
    And I login with the username "user1" and password "user"
    And I click Submit
    Then I should be see the message "Login successful"

  Scenario: Failure4Fail login
    When I navigate to "https://simileyskiy.com/login-form/"
    And I login with the username "user" and password "user"
    And I click Submit
    Then I should be see the message "Invalid credentials"