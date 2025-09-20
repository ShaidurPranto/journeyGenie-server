#!/bin/bash

# List of folders
folders=("service-registry" "api-gateway" "auth-user-service" "tour-service" "day-service" "activity-service" "photo-service")

echo "🔎 Stopping all running Spring Boot services..."

# Kill Java processes from these folders
for folder in "${folders[@]}"; do
    pid=$(ps aux | grep "[j]ava.*$folder" | awk '{print $2}')
    if [ -n "$pid" ]; then
        echo "Killing process for $folder (PID: $pid)"
        kill -9 $pid
    else
        echo "No running process found for $folder"
    fi
done

echo "🧹 Cleaning up log files..."
for folder in "${folders[@]}"; do
    log_file="${folder}.log"
    if [ -f "$log_file" ]; then
        rm "$log_file"
        echo "Deleted $log_file"
    else
        echo "No log file for $folder"
    fi
done

echo "✅ All services stopped and logs cleared."
