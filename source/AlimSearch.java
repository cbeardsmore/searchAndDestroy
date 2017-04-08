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

public class AlimSearch
{
    public static final int ARGS = 4;

//---------------------------------------------------------------------------

    public static void main( String[] args )
    {
        // CHECK COMMAND LINE PARAMTER LENGTH
        if ( args.length != ARGS )
        {
            System.err.print("\nUSAGE: java AlimSearch <initial> <goal> <graphfile>");
            System.err.print("<heuristicfile>\n");
            System.exit(1);
        }

        // RENAME ARGS FOR SIMPLICITY
        String gFile = null, hFile = null;
        int initial = 0, goal = 0;

        try
        {
            initial = Integer.parseInt( args[0] );
            goal = Integer.parseInt( args[1] );
            gFile = args[2];
            hFile = args[3];
        }
        catch ( NumberFormatException e )
        {
            System.err.println("INVALID COMMAND LINE ARGUMENTS:");
            System.err.println( e.getMessage() );
        }

        // CREATE OBJECT STRUCTURES
        Graph graph = new Graph();
        FileIO fileReader = new FileIO( graph );

        // READ IN GRAPH + HEURSTIC FILES
        try
        {
            fileReader.readGraph( gFile );
            fileReader.readHeuristic( hFile );
        }
        catch ( Exception e ) { }


        // PERFORM SEARCH AND OUTPUT
        List<Node> path = Search.alimSearch( graph, initial, goal );
        printPath( path );
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
