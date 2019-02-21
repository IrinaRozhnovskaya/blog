
[see reference](https://irinarozhnovskaya.github.io/blog/#rest-api-testing-guide)

```bash
./mvnw -f blog-cli/pom.xml \
    clean package exec:java \
      -Dexec.mainClass=com.github.sigma.blog.TestData
```
