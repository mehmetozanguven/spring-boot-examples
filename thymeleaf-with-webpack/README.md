# Demo project for integration gulp and webpack with Spring MVC



If you want to create jar file directly, instead of running the project from IDEA. Please run the following command:

```bash
mvn clean install -Prelease -DskipTests

java -jar target/thymeleaf-with-webpack.jar
```

Before running the project from the IDEA, make sure that you have:
- Installed the node and npm on your computer
- Installed gulp-cli as global dependency

Then, first run the project from your favorite IDEA, after that run the following command: `npm run build && npm run watch`