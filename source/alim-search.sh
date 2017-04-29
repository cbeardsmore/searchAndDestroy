#/bin/bash

#/***************************************************************************
#	FILE: alim-search
#	AUTHOR: Connor Beardsmore - 15504319
#	UNIT: AMI300
#	PURPOSE: Script to provide AlimSearch its initial parameters
#   LAST MOD: 29/04/07
#***************************************************************************/

init=1
goal=21
gfile=./graphs/g1.al
hfile=./graphs/g1.heu
memory=50
museum=n

java AlimSearch $init $goal $gfile $hfile $memory $museum

#***************************************************************************/
