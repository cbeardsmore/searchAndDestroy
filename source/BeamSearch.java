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
import java.util.LinkedList;

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
        List<List<Node>> paths = new LinkedList<>();
        String initial = args[0];
        String goal = args[1];
        String gFile = args[2];
        String hFile = args[3];
        int k = 0;

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

            e.printStackTrace();

            System.exit(1);
        }

        // DO STUFF WITH THE FINAL PATHS
        printPaths( paths, graph.getNode(goal) );

    }

//---------------------------------------------------------------------------

    public static void printPaths( List<List<Node>> paths, Node goal )
    {
        System.out.print("\n");
        for ( List<Node> nextPath : paths )
        {
            if ( nextPath.contains(goal) )
                System.out.print("SOLUTION PATH: ");
            else
                System.out.print("PARTIAL PATH:  ");
            for ( Node next : nextPath )
                System.out.print( next.getName() + " " );
            System.out.print("\n");
        }
        System.out.print("\n");
    }

//---------------------------------------------------------------------------
}
