/***************************************************************************
*	FILE: Edge.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: AMI300
*	PURPOSE: Represents a single edge in a graph
*   LAST MOD: 13/04/07
*   REQUIRES: NONE
***************************************************************************/

public class Edge
{
    //CLASSFIELDS
    private Node source;
    private Node sink;
    private double weight;

//---------------------------------------------------------------------------
    //ALTERNATE CONSTRUCTOR

    public Edge( Node inSource, Node inSink, double inWeight )
    {
        if ( ( inSource == null ) || ( inSink == null ) )
            throw new IllegalArgumentException("NULL NODE IN EDGE");
        if ( inWeight < 0 )
            throw new IllegalArgumentException("NEGATIVE EDGE WEIGHT");

        source = inSource;
        sink = inSink;
        weight = inWeight;
    }

//---------------------------------------------------------------------------
    //GETTERS

    public Node getSource()        { return source; }
    public Node getSink()          { return sink; }
    public double getWeight()      { return weight; }

//---------------------------------------------------------------------------
    //SETTERS

    public void setSource(Node inSource)        { source = inSource; }
    public void setSink(Node inSink)            { sink = inSink; }
    public void setWeight(int inWeight)         { weight = inWeight; }

//---------------------------------------------------------------------------
    //NAME: toString
    //EXPORT: state (String)
    //PURPOSE: Export state in readable String format

    public String toString()
    {
        String state = "WEIGHT: " + weight + "\n";
        state += "SOURCE: " + source.getName();
        state += "SINK: " + sink.getName();
        return state;
    }
//---------------------------------------------------------------------------
}
