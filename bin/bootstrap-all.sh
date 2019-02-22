#!/usr/bin/env bash

if ! [[ -d "/root/jdk8..." ]]; then

fi

if [[ -d "./blog" ]]; then
  git -C blog pull origin/master;
elif
  git clone "";
fi

cd blod
./mvnw
./mvnw -
...