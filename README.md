# Instructions on how to run the application

- On Windows:

After cloning the project from the Github repository navigate to "exchange-rates-app" directory and run commands:

##### gradlew.bat clean build
##### gradlew.bat clean bootRun

- On Linux

After cloning the project from the Github repository navigate to "exchange-rates-app" directory and run commands:

##### chmod 755 gradlew
##### ./gradlew clean build
##### ./gradlew clean bootRun


## Brief explanation

This is the small rest-based web application which allows to display a list of changes for currency rates for the selected date. The user is be able to input a date, then click a button and get a list of changes for currency rate of each currency calculated by comparing currency rate on selected date and one day before.

The application uses Gradle as dependency management tool. Gradle allows flexibly generate Java classes from the supplied WSDL file and separate project with WSDL-generated classes from SpringBoot application project. 
Also Gradle allows some scripting and is more powerful tool compared to Maven and Ant.

The backend core of the application is SpringBoot application which allows develop web application easily and fast.

The frontend is AngularJS application which normally would be launched separately on Node.js environment to separate UI from the backend so that UI would communicate with the backend only through REST API calls.
For such small project UI is integrated into the SpringBoot application though UI still communicates with the backend through REST API calls.

The complete list of REST API methods and return types can be found at http://host:port/swagger-ui.html
For the API documentation Swagger library is used which allows to create REST API documentation with minimum configuration.

Different ports are configured for different branches:

dev -    8802
test -   8801
master - 8800

On the master branch the application is implemented exactly as in described task (only 1 date input available), the test branch allows user to choose 2 dates to make comparison presentable and easier to see