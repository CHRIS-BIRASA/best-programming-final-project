#!/bin/bash

echo "🚛 Starting Cargo Truck Fuel Tracker GUI in Docker..."

# Install XQuartz if not installed (for Mac)
if ! command -v xquartz &> /dev/null; then
    echo "⚠️  XQuartz not found. Installing..."
    brew install --cask xquartz
    echo "✅ XQuartz installed. Please restart your Mac and run this script again."
    exit 1
fi

# Allow X11 connections
xhost + localhost

# Start Docker containers
docker-compose up --build

echo "✅ Application started!"