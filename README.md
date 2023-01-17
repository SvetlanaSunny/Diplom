# Дипломный проект «Тестировщик ПО»

Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса,
взаимодействующего с СУБД и API Банка. Приложение предлагает купить тур по определённой цене с помощью двух способов:

- Оплата по дебетовой карте
- Оплата в кредит по данным банковской карты

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:

- сервису платежей (далее - Payment Gate)
- кредитному сервису (далее - Credit Gate)

## Документация
* [План автоматизации тестирования](https://github.com/SvetlanaSunny/Diplom/blob/main/docs/Plan.md)
* [Отчет по итогам тестирования](https://github.com/SvetlanaSunny/Diplom/blob/main/docs/Summury.md)
* [Отчет по итогам автоматизированного тестирования](https://github.com/SvetlanaSunny/Diplom/blob/main/docs/TestingReport.md)

На локальном компьютере заранее должны быть установлены IntelliJ IDEA и Docker.

## Подготовка среды и тестирование:

1. Склонировать проект с удаленного репозитория, с помощью команды
`git clone https://github.com/SvetlanaSunny/Diplom.git`

2. Открыть проект в IntelliJ Idea

3. В терминале запустить контейнеры с помощью команды:
`docker-compose up -d`

4.Запустить целевой веб-сервис командой:

    для mySQL: 
    java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar

    для postgresgl:
    java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar

5. Запустить автотесты командой 


    для mySQL:
    ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"

    для postgresgl: 
    ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"

6. Создать отчёт Allure и открыть в браузере:


    ./gradlew allureServe

# После прохождения авто-тестов:

1. Завершить работу allureServe командой:


    Ctrl+C

2. Остановить контейнер в терминале с помощью команды:



    docker-compose down