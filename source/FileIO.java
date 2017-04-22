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

    public static final int GRAPH_FIELDS = 3;
    public static final int HEURISTIC_FIELDS = 2;

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
    //IMPORT: gFilename (String), hFilename (String)
    //PURPOSE: Read in graph and heuristic files

    public void readFiles( String gFilename, String hFilename )
    {
        // OPEN FILES
        File gFile = new File( gFilename );
        File hFile = new File( hFilename );

        try
        {
            // PARSE EACH LINE OF THE GRAPH FILE
            Scanner gScan = new Scanner( gFile );
            while ( gScan.hasNextLine() )
                parseLine( gScan.nextLine() );
            // PARSE EACH LINE OF THE HEURISTIC FILE
            Scanner fScan = new Scanner( hFile );
            while ( fScan.hasNextLine() )
                parseHeuristic( fScan.nextLine() );
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

        // IGNORE EMPTY LINES
        if ( line.equals("") )
            return;

        // FORMAT MUST BE 2 NODE NAMES FOLLOWED BY A VALUE
        if ( tokens.length != GRAPH_FIELDS )
            throw new IllegalArgumentException("GRAPH FILE FORMAT INVALID");

        // SIMPLIFY NAMING
        String source = tokens[0];
        String sink = tokens[1];
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
    //NAME: parseHeuristic()
    //IMPORT: line (String)
    //PURPOSE: Parse a given line with format <NODE WEIGHT>

    public void parseHeuristic( String line )
    {
        String[] tokens = line.split(" ");

        // IGNORE EMPTY LINES
        if ( line.equals("") )
            return;

        if ( tokens.length != HEURISTIC_FIELDS )
            throw new IllegalArgumentException("HEURISTIC FILE FORMAT INVALID");

        // SIMPLIFY NAMING
        String nodeName = tokens[0];
        double weight = Double.parseDouble( tokens[1] );

        Node node = graph.getNode( nodeName );
        node.setHeuristic( weight );
    }

//---------------------------------------------------------------------------
}
