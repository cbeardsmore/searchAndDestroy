#/bin/bash

init=1
goal=500
gfile=./graphs/gg_1000x1000.al
hfile=./graphs/gg_1000x1000.heu
memory=15
museum=n

java AlimSearch $init $goal $gfile $hfile $memory $museum
