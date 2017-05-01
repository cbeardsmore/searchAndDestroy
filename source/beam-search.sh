#/bin/bash

#/***************************************************************************
#	FILE: beam-search
#	AUTHOR: Connor Beardsmore - 15504319
#	UNIT: AMI300
#	PURPOSE: Script to provide BeamSearch its initial parameters
#   LAST MOD: 29/04/07
#***************************************************************************/

# COMMAND LINE PARAMETERS ARE IN THE FOLLOWING ORDER:
# 1 = init node
# 2 = goal node
# 3 = graph filename
# 4 = heuristic filename
# 5 = number of beams
# 6 = british museum mode (alternate paths)

java BeamSearch $1 $2 $3 $4 $5 $6

#***************************************************************************/
