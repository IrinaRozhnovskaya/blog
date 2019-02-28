# blog [![Build Status](https://travis-ci.org/IrinaRozhnovskaya/blog.svg?branch=master)](https://travis-ci.org/IrinaRozhnovskaya/blog)

[see reference](https://irinarozhnovskaya.github.io/blog/#developer-guide)

_to start everything quickly_

```batch
mvnw
mvnw -f docker\pom.xml -P all-up

http :80
http :80/blog/api

mvnw -f docker\pom.xml -P all-down
```

open http://127.0.0.1/
