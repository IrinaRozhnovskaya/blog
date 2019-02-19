
[see reference](https://irinarozhnovskaya.github.io/blog/#rest-api-testing-guide)

```bash
# bootstrap backend
./mvnw ; ./mvnw -f docker/pom.xml -P ci-up

# populate test data
./mvnw -f blog-cli/pom.xml \
    clean package exec:java \
      -Dexec.mainClass=com.github.sigma.blog.TestData

# start frontend ion dev mode
cd frontend ; npm start
```
