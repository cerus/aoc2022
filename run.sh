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
if [ -f "src/main/edina/day$1.edina" ]; then
  java -jar edinaj.jar -P dev.cerus.aoc2022 -F "src/main/edina/day$1.edina" -I src/main/edina -O aoc.jar -Q -R
elif [ -f "src/main/java/aoc/day$1/Day$1.java" ]; then
  mkdir temp
  javac -d temp/ -cp src/main/java/ src/main/java/aoc/*.java
  javac -d temp/ -cp src/main/java/ src/main/java/aoc/day$1/*.java
  java -cp temp "aoc/day$1/Day$1"
  rm -r temp
else
  echo "No source file for day $1 found"
  exit
fi