##Курсовой проект по модулю «Автоматизация тестирования» для профессии «Инженер по тестированию»
Курсовой проект представляет собой автоматизацию тестирования комплексного сервиса,
взаимодействующего с СУБД и API Банка.

### **Инструкция по запуску**
**Предварительные условия:**
1. Установить [Docker](https://www.docker.com/).

   [Руководство по установке Docker](https://github.com/netology-code/aqa-homeworks/blob/master/docker/installation.md)
2. Открыть Intellij IDEA.

   [Руководство по установке IntelliJ IDEA](https://github.com/netology-code/javaqa-homeworks/blob/master/intro/idea.md)
3. Создать проект в IDEA на базе Gradle.

### **Для запуска MySQL**

- **1.** Выполнить в окне Терминала команду: ```docker-compose -f docker-compose-mysql.yml up```
- **2.** Открыть новую вкладку в окне Терминала.
- **3.** Выполнить в окне Терминала команду: ```java -jar ./artifacts/aqa-shop.jar```
- **4.** Выполнить команду:```gradle clean test```
- **5.** Выполнить команду:```gradle allureReport```
- **6.** Выполнить команду:```gradle allureServe```
- **7.** Остановить работу приложения сочетанием клавиш:```Ctrl + C```

### **Описание приложения**
Приложение представляет из себя веб-сервис "Путешествие дня".

Приложение предлагает купить тур по определённой цене с помощью двух способов:

1. Обычная оплата по дебетовой карте
2. Уникальная технология: выдача кредита по данным банковской карты

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:

* сервису платежей (далее - Payment Gate)
* кредитному сервису (далее - Credit Gate)
Приложение должно в собственной СУБД сохранять информацию о том, каким способом был совершён платёж и успешно ли он был совершён (при этом данные карт сохранять не допускается).


### **Запуск тестов**
**1.** Склонировать [репозиторий](https://github.com/irikras/CourseProject.git) `git clone`;

**2.** Для запуска контейнера с MySql использовать команду `docker-compose up -d`. Параметры для запуска хранятся в файле `docker-compose.yml`;

**3.** Приложение запускается на порту 8080;

**4.** Запустить SUT для MySQL:

    java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar ./artifacts/aqa-shop.jar    
        
**5.** В новой вкладке терминала ввести команду запущенной ранее БД MySQL:
   ```
   gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app 
   ```
**6.** После окончания тестов завершить работу приложения (Ctrl+C), удалить контейнеры командой `docker-compose down`.

### **Подготовка отчета Allure**
* При необходимости создания отчета тестирования, запустить тесты для БД MysSQL:
  ``` 
  gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app allureReport
  ```  

* При повторной генерации отчета необходимо запустить тесты командой:    
  ```
   gradlew test -Ddb.url=jdbc:mysql://localhost:3306/app allureServe
  ``` 
Отчет открывается после прохождения тестов автоматически в браузере по умолчанию.


### **Документация**

[План тестирования]()

[Отчёт по итогам тестирования]()

[Отчёт по итогам автоматизации]()