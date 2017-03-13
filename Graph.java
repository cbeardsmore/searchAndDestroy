/***************************************************************************
*	FILE: Graph.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: AMI300
*	PURPOSE: Represents an entire graph of nodes and edges for a search
*   LAST MOD: 13/04/07
*   REQUIRES: NONE
***************************************************************************/

import java.util.List;

public class Graph
{
    //CLASSFIELDS
    private List<Edge> edgeList;
    private List<Node> nodeList;
    private Node initial;
    private Node goal;

//---------------------------------------------------------------------------

    public void setInitial( Node inInit )     { initial = inInit; }
    public void setGoal( Node inGoal )        { goal = inGoal; }

//---------------------------------------------------------------------------

    public void addNode( Node inNode )
    {
        nodeList.add( inNode );
    }

//---------------------------------------------------------------------------

    public void addEdge( Edge inEdge )
    {
        edgeList.add( inEdge );
    }

//---------------------------------------------------------------------------
}
