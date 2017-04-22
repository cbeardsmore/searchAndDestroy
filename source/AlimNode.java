/***************************************************************************
*	FILE: Node.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: AMI300
*	PURPOSE: Represents a single node for a SMA* search
*   LAST MOD: 22/04/07
*   REQUIRES: List, LinkedList, Collections
***************************************************************************/

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;

//---------------------------------------------------------------------------

public class AlimNode extends Node
{
    //CLASSFIELDS SPECIFIC TO SMA*
    private double bestChild;
    private double aCost;
    private int depth;

    //CONSTANTS
    public static final int DEFAULT_COST = 0;

//---------------------------------------------------------------------------
    //ALTERNATE CONSTRUCTOR

    public AlimNode( String inName )
    {
        super( inName );
        bestChild = DEFAULT_COST;
        aCost = DEFAULT_COST;
        depth = DEFAULT_COST;
    }

//---------------------------------------------------------------------------
    //GETTERS

    public double getBest() { return bestChild; }
    public double getCost() { return aCost; }
    public int getDepth()   { return depth; }

//---------------------------------------------------------------------------
    //SETTERS

    public void setBest(double inBest) { bestChild = inBest; }
    public void setCost(double inCost) { aCost = inCost; }
    public void setDepth(int inDepth)  { depth = inDepth; }

//---------------------------------------------------------------------------
    //NAME: compareTo()
    //IMPORT: inNode (Node)
    //EXPORT: comparison (int)
    //PURPOSE: Compares the two nodes based on the heuristic value, lowest first

    //@Override
    public int compareTo(AlimNode inNode)
    {
        return (int)Math.ceil(this.aCost - inNode.aCost);
    }

//---------------------------------------------------------------------------
    //NAME: toString
    //EXPORT: state (String)
    //PURPOSE: Export state in readable String format

    public String toString()
    {
        String state = super.toString();
        state += "BEST: " + bestChild + " COST: " + aCost;
        state += " DEPTH: " + depth + "\n";
        return state;
    }


//---------------------------------------------------------------------------
}
