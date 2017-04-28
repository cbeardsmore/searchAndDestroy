#/bin/bash

init=1
goal=50
gfile=./graphs/gg_100x100.al
hfile=./graphs/gg_100x100.heu
beam=10
museum=n

java BeamSearch $init $goal $gfile $hfile $beam $museum
