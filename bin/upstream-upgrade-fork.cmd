@echo off
REM read 1: https://help.github.com/articles/configuring-a-remote-for-a-fork/
REM read 2: https://help.github.com/articles/syncing-a-fork/
REM before you start, make sure you have added proper original repo upstream:
REM git remote add upstream https://github.com/IrinaRozhnovskaya/blog.git
set arg1=%1
if ".%arg1%"==".force" (
  git reset --hard origin/master
  git checkout master
  git fetch --all --tags --prune --prune-tags --force
  git reset --hard upstream/master
  git push --force origin master
) else (
  git checkout master
  git fetch --all --tags --prune --prune-tags
  git merge upstream
)
