#/bin/bash

init=A
goal=G
gfile=./graphs/graphF1.txt
hfile=./graphs/heuristicF1.txt
memory=15
museum=y

java AlimSearch $init $goal $gfile $hfile $memory $museum
