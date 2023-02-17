#!/bin/bash

BUILD_DIR="./ninegag-shared-lib/build/XCFrameworks"
DEV_DIR="../9gag-ios/under9-shared-kmm-export-ios"

echo "Cleaning build directory $BUILD_DIR"
rm -rf $BUILD_DIR
echo "Cleaning build directory done"

echo "Building XCFrameworks from sources"
# ./gradlew assembleNineGagKmmXCFramework 
./gradlew assembleNineGagKmmReleaseXCFramework 
echo "Build XCFrameworks done"

if [ -d "$DEV_DIR" ]; then
	echo "Removing files in $DEV_DIR"
	rm -rf $DEV_DIR/debug
	rm -rf $DEV_DIR/release

	echo "Copying XCFrameworks to $DEV_DIR"
	cp -R $BUILD_DIR/* $DEV_DIR

	ls -al $DEV_DIR
	echo "Copying XCFrameworks done"
else
	echo "ERROR: Directory /path/to/dir does not exists."
fi
