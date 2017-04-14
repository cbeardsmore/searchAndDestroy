/***************************************************************************
*	FILE: Graph.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: AMI300
*	PURPOSE: Represents an entire graph of nodes and edges for a search
*   LAST MOD: 13/04/07
*   REQUIRES: List, LinkedList, Map, HashMap
***************************************************************************/

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

public class Graph
{
    //CLASSFIELDS
    private List<Edge> edgeList;
    private Map<String,Node> nodeMap;
    private int numNodes;

//---------------------------------------------------------------------------
    //DEFAULT CONSTRUCTOR

    public Graph()
    {
        edgeList = new LinkedList<Edge>();
        nodeMap = new HashMap<String,Node>();
        numNodes = 0;
    }

//---------------------------------------------------------------------------

    public int getNumNodes()
    {
        return numNodes;
    }

//---------------------------------------------------------------------------
    //NAME: getNode()
    //IMPORT: inName (String)
    //PURPOSE: Get a node from the map given its integer name

    public Node getNode( String inName )
    {
        return nodeMap.get(inName);
    }

//---------------------------------------------------------------------------
    //NAME: addNode()
    //IMPORT: inNode (Node)
    //PURPOSE: Add a new node into the node map ( or overwrites existing )

    public void addNode( Node inNode )
    {
        nodeMap.put( inNode.getName(), inNode );
        numNodes++;
    }

//---------------------------------------------------------------------------
    //NAME: addEdge()
    //IMPORT: inEdge (Edge)
    //PURPOSE: Add a new edge into the edge list + the nodes edge list

    public void addEdge( Edge inEdge )
    {
        inEdge.getSource().addEdge( inEdge );
        inEdge.getSink().addEdge( inEdge );
        edgeList.add( inEdge );
    }

//---------------------------------------------------------------------------
    //NAME: toString()
    //EXPORT: state (String)
    //PURPOSE: Provide string representation of internal state

    public String toString()
    {
        String state = "GRAPH\n-----(" + numNodes + ")\n";

        state += "EDGES:\n------\n";
        for ( Edge next : edgeList )
        {
            state += next.getSource().getName() + " ";
            state +=  next.getSink().getName() + " ";
            state += next.getWeight() + "\n";
        }

        state += "NODES:\n------\n";
        for (Map.Entry<String, Node> entry : nodeMap.entrySet())
        {
            Node value = entry.getValue();
            state += value.getName() + " -> ";
            state += value.connectedTo() + " -> ";
            state += value.getHeuristic() + "\n";
        }

        return state;
    }

//---------------------------------------------------------------------------
}
