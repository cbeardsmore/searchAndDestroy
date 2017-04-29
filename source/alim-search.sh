#/bin/bash

#/***************************************************************************
#	FILE: alim-search
#	AUTHOR: Connor Beardsmore - 15504319
#	UNIT: AMI300
#	PURPOSE: Script to provide AlimSearch its initial parameters
#   LAST MOD: 29/04/07
#***************************************************************************/

init=1
goal=50
gfile=./graphs/gg_100x100.al
hfile=./graphs/gg_100x100.heu
memory=13

java AlimSearch $init $goal $gfile $hfile $memory

#***************************************************************************/
