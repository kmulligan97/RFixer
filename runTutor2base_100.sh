#!/bin/bash

t="timeout 300"
output_folder="results2/tutor2base/"
count=0
stop=100
for filename in tests/clean_AutoTutor/*; do
  echo $filename
  echo $output_folder${filename##*/}
  count=$((count+1))
  if [ "$count" -gt "$stop" ]; then
    exit 1
  else
    $t java -jar target/regfixer.jar --mode 2 -base fix --file $filename > $output_folder${filename##*/}
  fi
done
