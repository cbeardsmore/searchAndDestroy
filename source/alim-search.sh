#/bin/bash

#/***************************************************************************
#	FILE: alim-search
#	AUTHOR: Connor Beardsmore - 15504319
#	UNIT: AMI300
#	PURPOSE: Script to provide AlimSearch its initial parameters
#   LAST MOD: 29/04/07
#***************************************************************************/

# COMMAND LINE PARAMETERS ARE IN THE FOLLOWING ORDER:
# 1 = init node
# 2 = goal node
# 3 = graph filename
# 4 = heuristic filename
# 5 = number of memory nodes

java AlimSearch $1 $2 $3 $4 $5

#***************************************************************************/
