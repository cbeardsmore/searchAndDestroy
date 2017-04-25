/***************************************************************************
*	FILE: AlimSearch.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: AMI300
*	PURPOSE: Performs a memory limited A* search on given graph file
*   LAST MOD: 09/05/17
*   REQUIRES: Scanner, List
***************************************************************************/

import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;

public class AlimSearch
{
    public static final int ARGS = 6;

//---------------------------------------------------------------------------

    public static void main( String[] args )
    {
        // CHECK COMMAND LINE PARAMTER LENGTH
        if ( args.length != ARGS )
        {
            System.err.print("\nUSAGE: java AlimSearch <initial> <goal> <graphfile>");
            System.err.print("<heuristicfile> <numNodes> <y/n for museum>\n");
            System.exit(1);
        }

        // RENAME ARGS FOR SIMPLICITY
        List<List<String>> paths = new LinkedList<>();
        String initial = args[0];
        String goal = args[1];
        String gFile = args[2];
        String hFile = args[3];
        int numNodes = 0;

        // PARSE k TO ENSURE VALIDITY
        try
        {
            numNodes = Integer.parseInt( args[4] );
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
            fileReader.readFiles( gFile, hFile );
        }
        catch ( Exception e )
        {
            System.err.println("ERROR READING FILE:");
            System.err.println( e.getMessage() );
            System.exit(1);
        }

        // PERFROM THE ACTUAL ALIM SEARCH, CHECKING FOR ERRORS
        try
        {
            paths = Search.alimSearch( graph, initial, goal, numNodes );
        }
        catch ( Exception e)
        {
            System.err.println("ERROR PERFORMING SEARCH:");
            //System.err.println( e.getMessage() );
            e.printStackTrace();
            System.exit(1);
        }

        // DO STUFF WITH THE FINAL PATHS
        printSummary( graph, initial, goal, numNodes );
        //Search.printPaths( paths, goal );

    }

//---------------------------------------------------------------------------

    public static void printSummary( Graph graph, String initial, String goal, int k )
    {
        System.out.println("\n----------ALIM SEARCH----------");
        System.out.println("INITIAL NODE IS: " + initial);
        System.out.println("   GOAL NODE IS: " + goal);
        System.out.println("   MEMORY NODES: " + k + "\n");
    }

//---------------------------------------------------------------------------
}
