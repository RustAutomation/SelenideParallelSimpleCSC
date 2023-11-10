#language: ru
Функционал: Логин 2 на сайт

  Сценарий: Успешный логин2
    Когда Я захожу на "https://simileyskiy.com/login-form/"
    И Я захожу с пользователем "user" и паролем "user"
    И Я нажимаю Submit
    Затем Я вижу сообщение "Login successful"

  Сценарий: Неуспешный логин2
    Когда Я захожу на "https://simileyskiy.com/login-form/"
    И Я захожу с пользователем "bad-user" и паролем "bad-password"
    И Я нажимаю Submit
    И Я вижу сообщение "Invalid credentials"