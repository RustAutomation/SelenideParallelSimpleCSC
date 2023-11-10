Feature: Login0 in practice site

  Scenario: Successful0 login
    When I navigate to "https://simileyskiy.com/login-form/"
    And I login with the username "user" and password "user"
    And Я жду "5000" мсек
    And I click Submit
    Then I should be see the message "Login successful"

  Scenario: Failure0 login
    When I navigate to "https://simileyskiy.com/login-form/"
    And I login with the username "bad-user" and password "bad-password"
    And Я жду "5500" мсек
    And I click Submit
    Then I should be see the message "Invalid credentials"