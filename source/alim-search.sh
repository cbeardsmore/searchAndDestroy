#/bin/bash

init=1
goal=15
gfile=./graphs/g1.al
hfile=./graphs/g1.heu
memory=8
museum=y

java AlimSearch $init $goal $gfile $hfile $memory $museum
