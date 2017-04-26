#/bin/bash

init=1
goal=15
gfile=./graphs/graphPrac.txt
hfile=./graphs/heuristicPrac.txt
memory=6
museum=y

java AlimSearch $init $goal $gfile $hfile $memory $museum
