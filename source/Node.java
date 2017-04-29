/***************************************************************************
*	FILE: Node.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: AMI300
*	PURPOSE: Represents a single node in a graph
*   LAST MOD: 22/04/07
*   REQUIRES: List, LinkedList, Collections, Comparator
***************************************************************************/

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;

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
    private double best;       //the best of this nodes childrens f(n) costs
    private double fn;         //the A* cost, where f(n) = g(n) + h(n)
    private double cost;       //the accumulated path cost to this node
    private int depth;         //depth in the search tree from the initial node
    private int childCounter;
    private boolean visited;

    //CONSTANTS
    public static final int DEFAULT = 0;

//---------------------------------------------------------------------------
    //DEFAULT CONSTRUCTOR

    public Node( String inName )
    {
        name = inName;
        parent = null;
        nodeList = new LinkedList<Node>();
        edgeList = new LinkedList<Edge>();
        heuristic = DEFAULT;
        best = Double.POSITIVE_INFINITY;
        fn = DEFAULT;
        cost = DEFAULT;
        depth = DEFAULT;
        childCounter = DEFAULT;
        visited = false;
    }

//---------------------------------------------------------------------------
    //COPY CONSTRUCTOR

    public Node( Node inNode )
    {
        name = inNode.getName();
        parent = inNode.getParent();
        nodeList = inNode.getNodes();
        edgeList = inNode.getEdges();
        heuristic = inNode.getHeuristic();
        best = inNode.getBest();
        fn = inNode.getFN();
        cost = inNode.getCost();
        depth = inNode.getDepth();
        childCounter = inNode.getChildCount();
        visited = inNode.isVisited();
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
    //NAME: removeNode()
    //IMPORT: inNode(node)
    //PURPOSE: Add new node into the current node list

    public void removeNode(Node inNode)
    {
        nodeList.remove( inNode );
        childCounter--;
        Collections.sort(nodeList);
    }

//---------------------------------------------------------------------------
    //NAME: addEdge()
    //IMPORT: inEdge (Edge)
    //PURPOSE: Add edge into the current edge list and fix dependencies

    public void addEdge(Edge inEdge)
    {
        //get references for connected nodes
        Node source = inEdge.getSource();
        Node sink = inEdge.getSink();

        //add connectred node dependencies
        if ( sink.getName().equals(name) )
            nodeList.add( source );
        else if ( source.getName().equals(name) )
            nodeList.add( sink );
        else
            throw new IllegalArgumentException("EDGE INVALID FOR NODE");

        edgeList.add(inEdge);
    }

//---------------------------------------------------------------------------
    //BASIC GETTERS

    public String getName()         { return name; }
    public Node getParent()         { return parent; }
    public List<Edge> getEdges()    { return edgeList; }
    public double getHeuristic()    { return heuristic; }
    public double getBest()         { return best; }
    public double getFN()           { return fn; }
    public double getCost()         { return cost; }
    public int getDepth()           { return depth; }
    public int getChildCount()      { return childCounter; }
    public boolean isVisited()      { return visited; }

//---------------------------------------------------------------------------
    //NAME: getNodes()
    //EXPORT: nodeList (List<Node>)
    //PURPOSE: Retreive a list of nodes in sorted order alphabetically

    public List<Node> getNodes()
    {
        Collections.sort( nodeList );
        return nodeList;
    }

//---------------------------------------------------------------------------
    //BASIC SETTERS

    public void setName(String inName)        { name = inName; }
    public void setParent(Node inPar)         { parent = inPar; }
    public void setNodes(List<Node> inNodes)  { nodeList = inNodes; }
    public void setEdges(List<Edge> inEdges)  { edgeList = inEdges; }
    public void setHeuristic(double inHeur)   { heuristic = inHeur; }
    public void setCost(double inCost)        { cost = inCost; }
    public void setDepth(int inDepth)         { depth = inDepth; }
    public void setCCount(int inCCount)       { childCounter = inCCount; }
    public void setVisited()                  { visited = true; }
    public void setFN(double inFN)            { fn = inFN; }
    public void setBest(double inBest)        { best = inBest; }

//---------------------------------------------------------------------------
    //NAME: setBestChild()
    //PURPOSE: Find the best f(n) cost of all my children

    public void setBestChild()
    {
        this.best = Double.POSITIVE_INFINITY;
        for ( Node next : nodeList )
            if ( next.getFN() < this.best  )
                if ( next.parent == this )
                    this.best = next.getFN();
    }

//---------------------------------------------------------------------------
    //NAME: compareTo()
    //IMPORT: inNode (Node)
    //EXPORT: comparison (int)
    //PURPOSE: Compares the two nodes based alphabetically

    @Override
    public int compareTo(Node inNode)
    {
        return this.name.compareTo( inNode.getName() );
    }

//---------------------------------------------------------------------------
    //CUSTOM Comparator
    //used in beam search to compare nodes based on HEURISTIC values

    public static Comparator<Node> NodeComparatorBeam = new Comparator<Node>()
    {
	    public int compare(Node n1, Node n2)
        {
             return (int)Math.ceil( n1.getHeuristic() - n2.getHeuristic() );
        }
	};

//---------------------------------------------------------------------------
    //CUSTOM Comparator
    //used in SMA* search to compare nodes based on f(n) first, depth second

    public static Comparator<Node> NodeComparatorAStar = new Comparator<Node>()
    {
	    public int compare(Node n1, Node n2)
        {
            Double n1Double = n1.getFN();
            int value = n1Double.compareTo( n2.getFN() );
            if ( value == 0 )
                value = n2.getDepth() - n1.getDepth();
            return value;
        }
	};

//---------------------------------------------------------------------------
    //NAME: reset()
    //PURPOSE: reset a nodes child counters once all child have been seen

    public void reset()
    {
        System.out.println("RESETTING NODE: " + this.name );
        childCounter = DEFAULT;
        setBestChild();
        //the nodes f(n) cost now becomes the best of all it's children
        this.fn = this.best;
    }

//---------------------------------------------------------------------------
    //NAME: hasNextChild()
    //EXPORT: hasChild (boolean)
    //PURPOSE: Returns true if this node has more children to explore

    public boolean hasNextChild()
    {
        boolean hasChild = true;
        if ( ( childCounter < 0 ) || ( childCounter >= nodeList.size() ) )
            hasChild = false;
        return hasChild;
    }

//---------------------------------------------------------------------------
    //NAME: getNextChild()
    //EXPORT: next (Node)
    //PURPOSE: Export the nodes next children to visit, if one exists

    public Node getNextChild()
    {
        Node next = null;
        if ( hasNextChild() )
            next = nodeList.get(childCounter);
        childCounter++;
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
    //NAME: getEdgeCost()
    //IMPORT: eNode (Node)
    //EXPORT weight (double)
    //PURPOSE: Given a node, return the cost of the edge weight to that node

    public double getEdgeCost( Node eNode )
    {
        for ( Edge next : edgeList )
            if ( ( next.getSource() == eNode ) || ( next.getSink() == eNode ) )
                return next.getWeight();
        //indicates that no edge was found
        return -1.0;
    }

//---------------------------------------------------------------------------
    //NAME: clone()
    //EXPORT: newNode (Object)
    //PURPOSE: Clone a node object and all state when it needs copying

    @Override
    public Object clone()
    {
        Node newNode = new Node(name);
        newNode.setParent(parent);
        newNode.setNodes(nodeList);
        newNode.setEdges(edgeList);
        newNode.setHeuristic(heuristic);
        newNode.setBest(best);
        newNode.setFN(fn);
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
        String state = name + " -> " + connectedTo();
        if ( parent != null )
            state += "\t\t(P=" + parent.getName();
        else
            state += "\t\t(P=null";
        state += ")\n\tDEPTH: " + depth + " VISIT: " + visited;
        state += " H:" + heuristic + " COST: " + cost;
        state += "\n\tFN: " + fn + " BEST: " + best;
        return state;
    }

//---------------------------------------------------------------------------
    //NAME: connectedTo()
    //EXPORT: connected (String)
    //PURPOSE: Return list of all connected node names

    public String connectedTo()
    {
        String state = "";
        Collections.sort( nodeList );
        for ( Node next : nodeList )
            state += next.getName() + " ";
        return state;
    }

//---------------------------------------------------------------------------
}
