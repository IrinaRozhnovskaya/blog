#!/usr/bin/env bash
# read 1: https://help.github.com/articles/configuring-a-remote-for-a-fork/
# read 2: https://help.github.com/articles/syncing-a-fork/
# before you start, make sure you have added proper original repo upstream:
# git remote add upstream https://github.com/IrinaRozhnovskaya/blog.git
agr1=$1
if [[ ".$1"=".force" ]] ; then
  git reset --hard origin/master
  git checkout master
  git fetch --all --tags --prune --prune-tags --force
  git reset --hard upstream/master
  git push --force origin master
else
  git checkout master
  git fetch --all --tags --prune --prune-tags --force
  git merge upstream
fi
