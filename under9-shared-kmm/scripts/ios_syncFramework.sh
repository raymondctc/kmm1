#/bin/sh

# 
# This is copied from ninegag_shared_lib.podspec, for a quick debug purpose
# 
./gradlew :ninegag-shared-lib:syncFramework \
-Pkotlin.native.cocoapods.platform=iphonesimulator \
-Pkotlin.native.cocoapods.archs=x86_64 \
-Pkotlin.native.cocoapods.configuration=Debug \
-Pkotlin.native.cocoapods.cflags= \
-Pkotlin.native.cocoapods.paths.headers= \
-Pkotlin.native.cocoapods.paths.frameworks="./ninegag-shared-lib/build/cocoapods/framework"