#/bin/bash

#/***************************************************************************
#	FILE: beam-search
#	AUTHOR: Connor Beardsmore - 15504319
#	UNIT: AMI300
#	PURPOSE: Script to provide BeamSearch its initial parameters
#   LAST MOD: 29/04/07
#***************************************************************************/

init=1
goal=5000
gfile=./graphs/gg_10000x10000.al
hfile=./graphs/gg_10000x10000.heu
beam=100
museum=y

java BeamSearch $init $goal $gfile $hfile $beam $museum

#***************************************************************************/
