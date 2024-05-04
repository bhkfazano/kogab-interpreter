#!/bin/bash

# Set the path to your Maven executable
MAVEN_EXECUTABLE="mvn"

# Compile the Java code
$MAVEN_EXECUTABLE compile

# Clear the terminal
clear

# Set the path to your Java executable
JAVA_EXECUTABLE="java"

# Set the path to your compiled Java classes
CLASS_PATH="target/classes"

# Run the appropriate command based on the number of arguments
if [ $# -gt 1 ]; then
    echo "Usage: ./kogab.sh <file.k>"
elif [ $# -eq 1 ]; then
    $JAVA_EXECUTABLE -cp $CLASS_PATH com.kogab.interpreter.Kogab $1
else
    $JAVA_EXECUTABLE -cp $CLASS_PATH com.kogab.interpreter.Kogab
fi
