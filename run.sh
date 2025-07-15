#!/bin/bash
echo "🔄 Cleaning and building the project..."
mvn clean install

echo "🚀 Running the application..."
mvn exec:java "-Dexec.mainClass=com.logdensor.Main"
