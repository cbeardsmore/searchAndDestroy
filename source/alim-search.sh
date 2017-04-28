#/bin/bash

init=1
goal=5000
gfile=./graphs/gg_10000x10000.al
hfile=./graphs/gg_10000x10000.heu
memory=6
museum=n

java AlimSearch $init $goal $gfile $hfile $memory $museum
