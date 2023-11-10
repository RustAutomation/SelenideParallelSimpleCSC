Feature: Login3 in practice site

  Scenario: Successful3 login
    When I navigate to "https://simileyskiy.com/login-form/"
    And I login with the username "user" and password "user"
    And I click Submit
    Then I should be see the message "Login successful"

  Scenario: Failure3 login
    When I navigate to "https://simileyskiy.com/login-form/"
    And I login with the username "bad-user" and password "bad-password"
    And I click Submit
    Then I should be see the message "Invalid credentials"