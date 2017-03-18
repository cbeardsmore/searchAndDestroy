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
    private int name;
    private Node parent;
    private List<Node> nodeList;
    private List<Edge> edgeList;

//---------------------------------------------------------------------------
    //ALTERNATE CONSTRUCTOR

    public Node( int inName )
    {
        name = inName;
        parent = null;
        nodeList = new LinkedList<Node>();
        edgeList = new LinkedList<Edge>();
    }

//---------------------------------------------------------------------------
    //GETTERS

    public int getName()         { return name; }
    public Node getParent()      { return parent; }
    public List<Edge> getEdges() { return edgeList; }

//---------------------------------------------------------------------------
    //SETTERS

    public void setName(int inName)    { name = inName; }
    public void setParent(Node inPar)  { parent = inPar; }

//---------------------------------------------------------------------------
    //NAME: getNodes()
    //EXPORT: nodeList (List<Node>)
    //PURPOSE: Retreive a list of nodes in sorted order

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
        if ( source.getName() != name )
            nodeList.add( source );
        else
            nodeList.add( sink );

        // ADD EDGE INTO THE EDGE LIST
        edgeList.add(inEdge);
    }

//---------------------------------------------------------------------------
    //REVERSE ORDER SORT

    @Override
    public int compareTo(Node inNode)
    {
        return ( (Integer)this.name ).compareTo( inNode.getName() );
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
        state += "\n";
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
