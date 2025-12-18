#!/bin/bash

# Install XQuartz if not already installed
if ! command -v xquartz &> /dev/null; then
    echo "XQuartz not found. Please install it:"
    echo "brew install --cask xquartz"
    echo "Then restart your computer and run this script again."
    exit 1
fi

# Start XQuartz
echo "Starting XQuartz..."
open -a XQuartz

# Wait for XQuartz to start
sleep 3

# Allow connections from localhost
xhost +localhost

# Set DISPLAY variable
export DISPLAY=:0

# Run docker-compose
echo "Starting Docker containers..."
docker-compose up --build