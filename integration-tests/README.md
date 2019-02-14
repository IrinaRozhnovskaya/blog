# integration tests 

```bash
mvnw -f backend\pom.xml clean package 
mvnw -f docker\pom.xml -P up
mvnw -f integration-tests\pom.xml -P it
```

