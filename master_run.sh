#!/bin/bash

# Root directory where all services live
BASE_DIR=$(pwd)

for dir in */; do
  if [[ "$dir" == *"service"* ]]; then
    echo "Starting service in $dir ..."
    (
      cd "$dir" || exit
      chmod +x run.sh
      ./run.sh &
    )
  fi
done

echo "All services started!"
