/***************************************************************************
*	FILE: Node.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: AMI300
*	PURPOSE: Represents a single node in a graph
*   LAST MOD: 22/04/07
*   REQUIRES: List, LinkedList, Collections
***************************************************************************/

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;

//---------------------------------------------------------------------------

public class Node implements Comparable<Node>
{
    //CLASSFIELDS
    private String name;
    private Node parent;
    private double heuristic;
    protected List<Node> nodeList;
    protected List<Edge> edgeList;

    //CLASSFIELDS SPECIFIC TO SMA*
    private double best;
    private double cost;
    private int depth;
    private int childCounter;

    //CONSTANTS
    public static final int DEFAULT = 0;

//---------------------------------------------------------------------------
    //ALTERNATE CONSTRUCTOR

    public Node( String inName )
    {
        name = inName;
        parent = null;
        nodeList = new LinkedList<Node>();
        edgeList = new LinkedList<Edge>();
        heuristic = DEFAULT;
        best = DEFAULT;
        cost = DEFAULT;
        depth = DEFAULT;
        childCounter = DEFAULT;
    }

//---------------------------------------------------------------------------
    //ALTERNATE CONSTRUCTOR

    public Node( Node inNode )
    {
        name = inNode.getName();
        parent = inNode.getParent();
        nodeList = inNode.getNodes();
        edgeList = inNode.getEdges();
        heuristic = inNode.getHeuristic();
        best = inNode.getBest();
        cost = inNode.getCost();
        depth = inNode.getDepth();
        childCounter = inNode.getChildCount();
    }

//---------------------------------------------------------------------------
    //GETTERS

    public String getName()         { return name; }
    public Node getParent()         { return parent; }
    public List<Edge> getEdges()    { return edgeList; }
    public double getHeuristic()    { return heuristic; }
    public double getBest()         { return best; }
    public double getCost()         { return cost; }
    public int getDepth()           { return depth; }
    public int getChildCount()      { return childCounter; }

//---------------------------------------------------------------------------
    //SETTERS

    public void setName(String inName)        { name = inName; }
    public void setParent(Node inPar)         { parent = inPar; }
    public void setNodes(List<Node> inNodes)  { nodeList = inNodes; }
    public void setEdges(List<Edge> inEdges)  { edgeList = inEdges; }
    public void setHeuristic(double inHeur)   { heuristic = inHeur; }
    public void setCost(double inCost)        { cost = inCost; }
    public void setDepth(int inDepth)         { depth = inDepth; }
    public void setCCount(int inCCount)       { childCounter = inCCount; }

//---------------------------------------------------------------------------

    public void setBest(double inBest)
    {
        best = Math.max( best, inBest );
    }

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
        Collections.sort(nodeList);
    }

//---------------------------------------------------------------------------

    public void removeNode(Node inNode)
    {
        nodeList.remove( inNode );
        Collections.sort(nodeList);
    }

//---------------------------------------------------------------------------
    //NAME: addEdge()
    //IMPORT: inEdge (Edge)
    //PURPOSE: Add edge into the current edge list and fix dependencies

    public void addEdge(Edge inEdge)
    {
        // GET REFERENCES FOR CONNECTED NODES
        Node source = inEdge.getSource();
        Node sink = inEdge.getSink();

        // ADD THE CONNECTED NODE DEPENDENCIES
        if ( sink.getName().equals(name) )
            nodeList.add( source );
        else if ( source.getName().equals(name) )
            nodeList.add( sink );
        else
            throw new IllegalArgumentException("EDGE INVALID FOR NODE");

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
        return (int)Math.ceil(this.heuristic - inNode.heuristic);
    }

//---------------------------------------------------------------------------
    //NAME: connectedTo()
    //EXPORT: connected (String)
    //PURPOSE: Return list of all connected node names

    public String connectedTo()
    {
        String state = "";
        for ( Node next : nodeList )
            state += next.getName() + " ";
        return state;
    }

//---------------------------------------------------------------------------

    public boolean hasNextChild()
    {
        boolean hasChild = true;
        if ( ( childCounter < 0 ) || ( childCounter > nodeList.size() ) )
            hasChild = false;
        return hasChild;
    }

//---------------------------------------------------------------------------

    public Node getNextChild()
    {
        Node next = null;
        if ( hasNextChild() )
            next = nodeList.get(childCounter);
        return next;
    }

//---------------------------------------------------------------------------
    //NAME: inPath()
    //IMPORT: inNode (Node)
    //EXPORT: found (boolean)
    //PURPOSE: Check if inNode is in the current path from this Node

    public boolean inPath( Node inNode )
    {
        if ( this.name.equals( inNode.getName() ) )
            return true;
        if ( parent == null )
            return false;
        return parent.inPath( inNode );
    }

//---------------------------------------------------------------------------

    public double getEdgeCost( Node eNode )
    {
        for ( Edge next : edgeList )
            if ( ( next.getSource() == eNode ) || ( next.getSink() == eNode ) )
                return next.getWeight();

        return 0.0;
    }

//---------------------------------------------------------------------------

    public double aCost()
    {
        return cost + heuristic;
    }

//---------------------------------------------------------------------------
    //NAME: clone()
    //EXPORT: newNode (Object)

    @Override
    public Object clone()
    {
        Node newNode = new Node(name);
        newNode.setParent(parent);
        newNode.setNodes(nodeList);
        newNode.setEdges(edgeList);
        newNode.setHeuristic(heuristic);
        newNode.setBest(best);
        newNode.setCost(cost);
        newNode.setDepth(depth);
        newNode.setCCount(childCounter);
        return newNode;
    }

//---------------------------------------------------------------------------
    //NAME: toString
    //EXPORT: state (String)
    //PURPOSE: Export state in readable String format

    public String toString()
    {
        String state = "NAME: " + name + " -> ";
        state += connectedTo();
        state += "\n\tH: " + heuristic + " PARENT: ";
        if ( parent != null )
            state += parent.getName() + "\n";
        else
            state += "null\n";
        state += "\tBEST: " + best + " COST: " + cost;
        state += " DEPTH: " + depth + "\n";
        return state;
    }

//---------------------------------------------------------------------------
}
