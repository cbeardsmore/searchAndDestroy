/***************************************************************************
*	FILE: Node.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: AMI300
*	PURPOSE: Represents a single node in a graph
*   LAST MOD: 13/04/07
*   REQUIRES: NONE
***************************************************************************/

import java.util.List;
import java.util.LinkedList;

public class Node
{
    //CLASSFIELDS
    private List<Edge> edgeList;
    private Node parent;
    private int name;
    private boolean visited;

//---------------------------------------------------------------------------
    //ALTERNATE CONSTRUCTOR

    public Node( int inName )
    {
        name = inName;
        visited = false;
        parent = null;
        edgeList = new LinkedList<Edge>();
    }

//---------------------------------------------------------------------------
    //GETTERS

    public int getName()        { return name; }
    public boolean isVisited()  { return visited; }

//---------------------------------------------------------------------------
    //SETTERS

    public void setName(int inName)    { name = inName; }
    public void setVisited()           { visited = true; }

//---------------------------------------------------------------------------
    //NAME: toString
    //EXPORT: state (String)
    //PURPOSE: Export state in readable String format

    public String toString()
    {
        String state = name + "\n";
        state += "PARENT: " + parent.getName();
        state += "VISITED: " + visited + "\n";
        return state;
    }
//---------------------------------------------------------------------------
}
