# Webqa project

Webqa project проводит 2 автотеста формы «Заполните анкету» сайта https://www.tinkoff.ru/career/vacancies/ :
1. Прокликивает все поля анкеты, не заполняя, чтобы получить все имеющиеся сообщения об ошибке и проверить текст ошибок.
2. Заполняет поля анкеты (фамилия и имя, дата рождения, электронная почта, мобильный телефон) невалидными значениями,
чтобы получить все сообщения об ошибке и проверить текст ошибок.

Webqa project также тестирует страницу https://www.tinkoff.ru/mobile-operator/tariffs/

## Installation

!!! Для корректной работы необходимо добавить в системную переменную PATH
    путь к директории со скачанными драйверами для вашей ОС

Запуск тестов в браузере Chrome из командной строки:
```mvn -Dtest=WindowHandleTests -Dbrowser=chrome verify```
```mvn -Dtest=VacancyFormTests -Dbrowser=chrome verify```

## Authors
[Ирина Гендлер](https://github.com/Capri84)
