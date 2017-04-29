/***************************************************************************
*	FILE: AlimSearch.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: AMI300
*	PURPOSE: Performs a memory limited A* search on given graph file
*   LAST MOD: 29/04/17
*   REQUIRES: List, LinkedList
***************************************************************************/

import java.util.List;
import java.util.LinkedList;

public class AlimSearch
{
    //CONSTANTS
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

        //rename args for simplicity
        List<List<String>> paths = new LinkedList<>();
        String initial = args[0];
        String goal = args[1];
        String gFile = args[2];
        String hFile = args[3];
        int numNodes = 0;

        //parse numNodes to ensure valid int
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

        //create object structures
        Graph graph = new Graph();
        FileIO fileReader = new FileIO( graph );

        //read in graph and heuristic files
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

        //perform the actual alimsearch, checking for errors
        try
        {
            paths = Search.alimSearch( graph, initial, goal, numNodes );
        }
        catch ( Exception e)
        {
            System.err.println("ERROR PERFORMING SEARCH:");
            System.err.println( e.getMessage() );
            e.printStackTrace();
            System.exit(1);
        }

        //print the final paths + summary
        printSummary( graph, initial, goal, numNodes );
        if ( paths != null )
            Search.printPaths( paths, goal );
    }

//---------------------------------------------------------------------------
    //NAME: printSummary()
    //IMPORT: graph (Graph), initial (String), goal (String), numNodes (int)
    //PURPOSE: Print search summary block

    public static void printSummary( Graph graph, String initial, String goal, int numNodes )
    {
        System.out.println("\n----------ALIM SEARCH----------");
        System.out.println("INITIAL NODE IS: " + initial);
        System.out.println("   GOAL NODE IS: " + goal);
        System.out.println("   MEMORY NODES: " + numNodes);
        System.out.println("-------------------------------");

    }

//---------------------------------------------------------------------------
}
