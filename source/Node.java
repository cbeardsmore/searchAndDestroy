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
import java.util.Collections;

public class Node implements Comparable<Node>
{
    //CLASSFIELDS
    private String name;
    private Node parent;
    private List<Node> nodeList;
    private List<Edge> edgeList;
    private int heuristic;

//---------------------------------------------------------------------------
    //ALTERNATE CONSTRUCTOR

    public Node( String inName )
    {
        name = inName;
        parent = null;
        nodeList = new LinkedList<Node>();
        edgeList = new LinkedList<Edge>();
        heuristic = -1;
    }

//---------------------------------------------------------------------------
    //GETTERS

    public String getName()         { return name; }
    public Node getParent()      { return parent; }
    public List<Edge> getEdges() { return edgeList; }
    public int getHeuristic()    { return heuristic; }

//---------------------------------------------------------------------------
    //SETTERS

    public void setName(String inName)    { name = inName; }
    public void setParent(Node inPar)  { parent = inPar; }
    public void setHeuristic(int inHeur) { heuristic = inHeur; }

//---------------------------------------------------------------------------
    //NAME: getNodes()
    //EXPORT: nodeList (List<Node>)
    //PURPOSE: Retreive a list of nodes in sorted order based on heuristic

    public List<Node> getNodes()
    {
        Collections.sort( nodeList );
        return nodeList;
    }

//---------------------------------------------------------------------------
    //NAME: addNode()
    //IMPORT: inNode(node)
    //PURPOSE: Add new node into the current node list

    public void addNode(Node inNode)
    {
        nodeList.add( inNode );
    }

//---------------------------------------------------------------------------
    //NAME: addEdge()
    //IMPORT: inEdge (Edge)
    //PURPOSE: Add edge into the crrent edge list and fix dependencies

    public void addEdge(Edge inEdge)
    {
        // GET REFERENCES FOR CONNECTED NODES
        Node source = inEdge.getSource();
        Node sink = inEdge.getSink();

        // ADD THE CONNECTED NODE DEPENDENCIES
        if ( ! source.getName().equals(name) )
            nodeList.add( source );
        else
            nodeList.add( sink );

        // ADD EDGE INTO THE EDGE LIST
        edgeList.add(inEdge);
    }

//---------------------------------------------------------------------------
    //NAME: compareTo()
    //IMPORT: inNode (Node)
    //EXPORT: comparison (int)
    //PURPOSE: Compares the two nodes based on the heurstic value, lowest first

    @Override
    public int compareTo(Node inNode)
    {
        return this.heuristic - inNode.heuristic;
    }

//---------------------------------------------------------------------------
    //NAME: toString
    //EXPORT: state (String)
    //PURPOSE: Export state in readable String format

    public String toString()
    {
        String state = "NAME: " + name + " -> ";
        for ( Node next : nodeList )
            state += next.getName() + " ";
        state += "H: " + heuristic + "\n";
        return state;
    }

//---------------------------------------------------------------------------
    //NAME: connectedTo()
    //EXPORT: connected (String)
    //PURPOSE: Return line of all connected node name

    public String connectedTo()
    {
        String state = "";
        Collections.sort(nodeList);
        for ( Node next : nodeList )
            state += next.getName() + " ";
        return state;
    }

//---------------------------------------------------------------------------
}
