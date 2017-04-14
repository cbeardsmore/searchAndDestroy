/***************************************************************************
*	FILE: BeamSearch.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: AMI300
*	PURPOSE: Performs an informed beam search on given graph file
*   LAST MOD: 14/05/17
*   REQUIRES: Scanner, List
***************************************************************************/

import java.util.Scanner;
import java.util.List;

public class BeamSearch
{
    public static final int ARG_NUM = 5;

//---------------------------------------------------------------------------

    public static void main( String[] args )
    {
        // CHECK COMMAND LINE PARAMTER LENGTH
        if ( args.length != ARG_NUM )
        {
            System.err.print("\nUSAGE: java BeamSearch <initial> <goal> <graphfile>");
            System.err.print("<heuristicfile> <num beams (k)>\n");
            System.exit(1);
        }

        // RENAME ARGS FOR SIMPLICITY
        String initial = args[0];
        String goal = args[1];
        String gFile = args[2];
        String hFile = args[3];
        int k = 0;
        List<List<Node>> paths;

        // PARSE k TO ENSURE VALIDITY
        try
        {
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
            System.err.println("ERROR READING FILE:");
            System.err.println( e.getMessage() );
            System.exit(1);
        }

        // PERFROM THE ACTUAL BEAM SEARCH, CHECKING FOR ERRORS
        try
        {
            paths = Search.beamSearch( graph, initial, goal, k );
        }
        catch ( Exception e)
        {
            System.err.println("ERROR PERFORMING SEARCH:");
            System.err.println( e.getMessage() );
            System.exit(1);
        }

        // DO STUFF WITH THE FINAL PATHS
        printPaths( paths );

    }

//---------------------------------------------------------------------------

    public static void printPaths( List<List<Node>> paths )
    {
        for ( List<Node> nextList : paths )
        {
            for ( Node next : nextList )
                System.out.print( next.getName() + " " );
            System.out.print("\n");
        }
        System.out.print("\n");
    }

//---------------------------------------------------------------------------
}
