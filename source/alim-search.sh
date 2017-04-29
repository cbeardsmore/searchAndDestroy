#/bin/bash

#/***************************************************************************
#	FILE: alim-search
#	AUTHOR: Connor Beardsmore - 15504319
#	UNIT: AMI300
#	PURPOSE: Script to provide AlimSearch its initial parameters
#   LAST MOD: 29/04/07
#***************************************************************************/

init=1
goal=5000
gfile=./graphs/gg_10000x10000.al2
hfile=./graphs/gg_10000x10000.heu2
memory=9

java AlimSearch $init $goal $gfile $hfile $memory

#***************************************************************************/
