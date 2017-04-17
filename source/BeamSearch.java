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
    public static final int ARG_NUM = 6;
    public static final String YES = "y";
    public static final String NO = "n";

//---------------------------------------------------------------------------

    public static void main( String[] args )
    {
        // CHECK COMMAND LINE PARAMTER LENGTH
        if ( args.length != ARG_NUM )
        {
            System.err.print("\nUSAGE: java BeamSearch <initial> <goal> <graphfile>");
            System.err.print(" <heuristicfile> <beams(k)> <y/n for museum>\n");
            System.exit(1);
        }

        // RENAME ARGS FOR SIMPLICITY
        List<List<Node>> paths = new LinkedList<>();
        String initial = args[0];
        String goal = args[1];
        String gFile = args[2];
        String hFile = args[3];
        String museum = args[5];
        boolean flag = false;
        int k = 0;

        // PARSE k + museum TO ENSURE VALIDITY
        try
        {
            k = Integer.parseInt( args[4] );
            if ( museum.equals(YES) )
                flag = true;
            else if ( !museum.equals(NO) )
                throw new NumberFormatException("INVALID MUSEUM FLAG");
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
            paths = Search.beamSearch( graph, initial, goal, k, flag );
        }
        catch ( Exception e)
        {
            System.err.println("ERROR PERFORMING SEARCH:");
            System.err.println( e.getMessage() );

            e.printStackTrace();

            System.exit(1);
        }

        // DO STUFF WITH THE FINAL PATHS
        printSummary( graph, initial, goal, k );
        printPaths( paths, graph.getNode(goal) );

    }

//---------------------------------------------------------------------------

    public static void printSummary( Graph graph, String initial, String goal, int k )
    {
        System.out.println("\n----------BEAM SEARCH----------");
        System.out.println("INITIAL NODE IS: " + initial);
        System.out.println("   GOAL NODE IS: " + goal);
        System.out.println("  BEAM WIDTH IS: " + k + "\n");
    }

//---------------------------------------------------------------------------

    public static void printPaths( List<List<Node>> paths, Node goal )
    {
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
        System.out.println("--------------------------------\n");
    }

//---------------------------------------------------------------------------
}
