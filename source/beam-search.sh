#/bin/bash

#/***************************************************************************
#	FILE: beam-search
#	AUTHOR: Connor Beardsmore - 15504319
#	UNIT: AMI300
#	PURPOSE: Script to provide BeamSearch its initial parameters
#   LAST MOD: 29/04/07
#***************************************************************************/

init=A
goal=G
gfile=./graphs/graphF1.txt
hfile=./graphs/heuristicF1.txt
beam=3
museum=y

java BeamSearch $init $goal $gfile $hfile $beam $museum

#***************************************************************************/
