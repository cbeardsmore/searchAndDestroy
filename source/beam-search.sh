#/bin/bash

init=1
goal=21
gfile=./graphs/g1.al
hfile=./graphs/g1.heu
beam=2
museum=y

java BeamSearch $init $goal $gfile $hfile $beam $museum
