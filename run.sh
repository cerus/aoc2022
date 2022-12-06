#!/bin/sh

if [ "$2" = '-d' ] ; then
  rm edinaj.jar
  rm -r stdlib
fi

if [ ! -f "edinaj.jar" ]; then
    echo "Downloading EdinaJ from https://github.com/cerus/edina/releases/download/nightly/edinaj.jar"
    curl -LJO -s https://github.com/cerus/edina/releases/download/nightly/edinaj.jar
fi
if [ ! -d "stdlib" ]; then
    echo "Downloading stdlib from https://github.com/cerus/edina/releases/download/nightly/stdlib.tar.gz"
    curl -LJO -s https://github.com/cerus/edina/releases/download/nightly/stdlib.tar.gz
    tar -xf stdlib.tar.gz
    rm stdlib.tar.gz
fi

if [ "$1" = '' ] ; then
  echo "Please enter the day you want to run"
  exit
fi
if [ ! -f "src/day$1.edina" ]; then
  echo "src/day$1.edina does not exist"
  exit
fi

java -jar edinaj.jar -P dev.cerus.aoc2022 -F "src/day$1.edina" -I src -O aoc.jar -Q -R