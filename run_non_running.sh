#!/bin/bash

# List of folders
folders=("service-registry" "api-gateway" "auth-user-service" "tour-service" "day-service" "activity-service" "photo-service")

for folder in "${folders[@]}"; do
    echo "Entering folder: $folder"

    if [ -d "$folder" ]; then
        cd "$folder" || { echo "Failed to enter $folder"; exit 1; }

        if [ -x "run.sh" ]; then
            # Check if process already running (match folder name in process list)
            if pgrep -f "$folder" > /dev/null; then
                echo "⚠️  $folder is already running, skipping..."
            else
                echo "🚀 Starting $folder..."
                ./run.sh > "../${folder}.log" 2>&1 &
                echo "✅ Started $folder in background (logs: ${folder}.log)"
            fi
        else
            echo "No executable run.sh found in $folder"
        fi

        cd ..
    else
        echo "Folder $folder does not exist"
    fi
done

echo "🎉 All services processed"
