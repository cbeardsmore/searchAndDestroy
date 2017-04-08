/***************************************************************************
*	FILE: BeamSearch.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: AMI300
*	PURPOSE: Performs an informed beam search on given graph file
*   LAST MOD: 09/05/17
*   REQUIRES: Scanner, List
***************************************************************************/

import java.util.Scanner;
import java.util.List;

public class BeamSearch
{
    public static final int ARGS = 5;

//---------------------------------------------------------------------------

    public static void main( String[] args )
    {
        // CHECK COMMAND LINE PARAMTER LENGTH
        if ( args.length != ARGS )
        {
            System.err.print("\nUSAGE: java BeamSearch <initial> <goal> <graphfile>");
            System.err.print("<heuristicfile> <num beams (k)>\n");
            System.exit(1);
        }

        // RENAME ARGS FOR SIMPLICITY
        String gFile = null, hFile = null;
        String initial = null, goal = null;
        int k = 0;

        try
        {
            initial = args[0];
            goal = args[1];
            gFile = args[2];
            hFile = args[3];
            k = Integer.parseInt( args[4] );
        }
        catch ( NumberFormatException e )
        {
            System.err.println("INVALID COMMAND LINE ARGUMENTS:");
            System.err.println( e.getMessage() );
            System.exit(1);
        }

        // CREATE OBJECT STRUCTURES
        Graph graph = new Graph();
        FileIO fileReader = new FileIO( graph );

        // READ IN GRAPH + HEURISTIC FILES
        try
        {
            fileReader.readGraph( gFile );
            fileReader.readHeuristic( hFile );
        }
        catch ( Exception e )
        {
            System.err.println("ERROR READING FILE");
            System.exit(1);
        }


        System.out.println( graph.toString() );

        // PERFORM SEARCH AND OUTPUT
        //List<Node> path = Search.beamSearch( graph, initial, goal, k );
        //printPath( path );
    }

//---------------------------------------------------------------------------

    public static void printPath( List<Node> path )
    {
        for ( Node next : path )
            System.out.print( next.getName() + " " );
        System.out.print("\n");
    }

//---------------------------------------------------------------------------
}
