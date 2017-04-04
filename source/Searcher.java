/***************************************************************************
*	FILE: Searcher.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: AMI300
*	PURPOSE: Performs a DFS on for given graph and initial/goal nodes
*   LAST MOD: 13/04/07
*   REQUIRES: NONE
***************************************************************************/

import java.util.Scanner;
import java.util.List;

public class Searcher
{
    public static final int ARGS = 4;
    public static final String BEAM_SEARCH = "-beam";
    public static final String ALIM_SEARCH = "-alim";

//---------------------------------------------------------------------------

    public static void main( String[] args )
    {
        // CHECK COMMAND LINE PARAMTER LENGTH
        if ( args.length <= ARGS )
        {
            System.err.println("\nUSAGE: script <initial> <goal> <graph file>\n");
            System.exit(1);
        }

        // RENAME ARGS FOR SIMPLICITY
        String searchType = args[0];
        int initial = Integer.parseInt( args[1] );
        int goal = Integer.parseInt( args[2] );
        String graphFile = args[3];

        // READ THE FILE
        Graph graph = new Graph();
        FileIO fileReader = new FileIO( graph );

        // READ IN GRAPH
        fileReader.readGraph( graphFile );

        // PERFORM SEARCH AND OUTPUT
        List<Node> path;
        if ( graphFile.equals( BEAM_SEARCH ) )
            path = Search.beamSearch( graph, initial, goal, Integer.parseInt( args[4] ) );
        else if ( graphFile.equals( ALIM_SEARCH ) )
            path = Search.alimSearch( graph, initial, goal );
        else
            System.err.println("you're an idiot congrats");

    }

//---------------------------------------------------------------------------

    public void printPath( List<Node> path )
    {
        for ( Node next : path )
            System.out.print( next.getName() + " " );
        System.out.print("\n");
    }

//---------------------------------------------------------------------------
}
