#/bin/bash

#/***************************************************************************
#	FILE: alim-search
#	AUTHOR: Connor Beardsmore - 15504319
#	UNIT: AMI300
#	PURPOSE: Script to provide AlimSearch its initial parameters
#   LAST MOD: 29/04/07
#***************************************************************************/

init=A
goal=G
gfile=./graphs/graphF3.txt
hfile=./graphs/heuristicF3.txt
memory=50

java AlimSearch $init $goal $gfile $hfile $memory

#***************************************************************************/
