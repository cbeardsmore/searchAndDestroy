#/bin/bash

#/***************************************************************************
#	FILE: alim-search
#	AUTHOR: Connor Beardsmore - 15504319
#	UNIT: AMI300
#	PURPOSE: Script to provide AlimSearch its initial parameters
#   LAST MOD: 29/04/07
#***************************************************************************/

init=1
goal=15
gfile=./graphs/graphPrac.txt
hfile=./graphs/heuristicPrac.txt
memory=8
museum=n

java AlimSearch $init $goal $gfile $hfile $memory $museum

#***************************************************************************/
