#!/bin/bash

# List of folders
folders=("service-registry" "api-gateway" "auth-user-service" "tour-service" "day-service" "activity-service" "photo-service")

for folder in "${folders[@]}"; do
    echo "Entering folder: $folder"

    if [ -d "$folder" ]; then
        cd "$folder" || { echo "Failed to enter $folder"; exit 1; }

        if [ -x "run.sh" ]; then
            echo "Running run.sh in $folder..."
            # Run in background and redirect logs
            ./run.sh > "../${folder}.log" 2>&1 &
            echo "Started $folder in background (logs: ${folder}.log)"
        else
            echo "No executable run.sh found in $folder"
        fi

        cd ..
    else
        echo "Folder $folder does not exist"
    fi
done

echo "✅ All services started in background"
