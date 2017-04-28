#/bin/bash

init=1
goal=5000
gfile=./graphs/gg_10000x10000.al
hfile=./graphs/gg_10000x10000.heu
beam=100
museum=y

java BeamSearch $init $goal $gfile $hfile $beam $museum
