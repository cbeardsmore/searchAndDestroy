#/bin/bash

#/***************************************************************************
#	FILE: beam-search
#	AUTHOR: Connor Beardsmore - 15504319
#	UNIT: AMI300
#	PURPOSE: Script to provide BeamSearch its initial parameters
#   LAST MOD: 29/04/07
#***************************************************************************/

init=1
goal=21
gfile=./graphs/g1.al
hfile=./graphs/g1.heu
beam=2
museum=y

./beam-search.sh $init $goal $gfile $hfile $beam $museum

#***************************************************************************/
