# blog [![Build Status](https://travis-ci.org/IrinaRozhnovskaya/blog.svg?branch=master)](https://travis-ci.org/IrinaRozhnovskaya/blog)

build

```cmd
mvnw
```

run

```cmd
mvnw -f docker\pom.xml -P up
```

test

```cmd
mvnw -f docker\pom.xml -P ci-up
mvnw -f integration-tests\pom.xml -P it
```

cleanup

```cmd
mvnw -f docker\pom.xml -P ci-down
```

