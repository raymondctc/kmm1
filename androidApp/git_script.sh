#/!bin/sh

git remote add kmm git@github.com:9gag/under9-shared-kmm.git
git subtree add --prefix=under9-shared-kmm --squash kmm/master

# To pull changes from kmm upstream and merge to local repo
git subtree pull --prefix=under9-shared-kmm kmm milestone/8.2.0 --squash