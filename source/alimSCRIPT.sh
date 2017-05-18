#/bin/bash

#/***************************************************************************
#	FILE: alim-search
#	AUTHOR: Connor Beardsmore - 15504319
#	UNIT: AMI300
#	PURPOSE: Script to provide AlimSearch its initial parameters
#   LAST MOD: 29/04/07
#***************************************************************************/

init=Arad
goal=Bucharest
gfile=./graphs/graphRomania.txt
hfile=./graphs/heuristicRomania.txt
memory=10

./alim-search $init $goal $gfile $hfile $memory

#***************************************************************************/
