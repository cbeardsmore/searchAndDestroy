/***************************************************************************
*	FILE: FileIO.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: AMI300
*	PURPOSE: Methods to read in and write out a graph
*   LAST MOD: 13/04/07
*   REQUIRES: java.io
***************************************************************************/

import java.io.*;
import java.util.Scanner;

public class FileIO
{
    //CLASSFIELDS
    private Graph graph;

//---------------------------------------------------------------------------
    //ALTERNATE CONSTRUCTOR

    public FileIO( Graph inGraph )
    {
        if ( inGraph == null )
            throw new IllegalArgumentException("GRAPH IS NULL");

        graph = inGraph;
    }

//---------------------------------------------------------------------------
    //NAME: readGraph()
    //IMPORT: filename (String)
    //PURPOSE: Read in a graph from a given filename

    public void readGraph( String filename )
    {
        // OPEN FILE
        File file = new File( filename );

        try
        {
            // PARSE EACH LINE INDIVIDUALLY
            Scanner sc = new Scanner( file );
            while ( sc.hasNextLine() )
                parseLine( sc.nextLine() );
        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
    }

//---------------------------------------------------------------------------
    //NAME: parseLine()
    //IMPORT: line (String)
    //PURPOSE: Parse a given line with format <SOURCE SINK WEIGHT>

    public void parseLine( String line )
    {
        String[] tokens = line.split(" ");

        if ( tokens.length != 3 )
            throw new IllegalArgumentException("FILE FORMAT INVALID");

        // SIMPLIFY NAMING
        int source = Integer.parseInt( tokens[0] );
        int sink = Integer.parseInt( tokens[1] );
        int weight = Integer.parseInt( tokens[2] );

        // GET REFERENCES TO THE NODES
        Node sourceNode = graph.getNode(source);
        Node sinkNode = graph.getNode(sink);

        // ADD EDGES TO THE GRAPH IF THEY DON'T ALREADY EXIST
        if ( sourceNode == null )
        {
            sourceNode = new Node( source );
            graph.addNode( sourceNode );
        }
        if ( sinkNode == null )
        {
            sinkNode = new Node( sink );
            graph.addNode( sinkNode );
        }

        // ADD THE NEW EDGE INTO THE GRAPH 
        Edge edge = new Edge( sourceNode, sinkNode, weight );
        graph.addEdge( edge );
    }

//---------------------------------------------------------------------------
}
