/***************************************************************************
*	FILE: BeamSearch.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: AMI300
*	PURPOSE: Performs an informed beam search on given graph file
*   LAST MOD: 14/05/17
*   REQUIRES: List, LinkedList
***************************************************************************/

import java.util.List;
import java.util.LinkedList;

public class BeamSearch
{
    //CONSTANTS
    public static final int ARG_NUM = 6;
    public static final String YES = "y";
    public static final String NO = "n";

//---------------------------------------------------------------------------

    public static void main( String[] args )
    {
        //check command line parameters
        if ( args.length != ARG_NUM )
        {
            System.err.print("\nUSAGE: java BeamSearch <initial> <goal> <graphfile>");
            System.err.print(" <heuristicfile> <beams(k)> <y/n for museum>\n");
            System.exit(1);
        }

        //rename args for simplicity
        List<List<String>> paths = new LinkedList<>();
        String initial = args[0];
        String goal = args[1];
        String gFile = args[2];
        String hFile = args[3];
        String museum = args[5];
        boolean flag = false;
        int k = 0;

        //parse k and museum to ensure validity
        try
        {
            k = Integer.parseInt( args[4] );
            if ( museum.equals(YES) )
                flag = true;
            else if ( !museum.equals(NO) )
                throw new IllegalArgumentException("INVALID MUSEUM FLAG");
        }
        catch ( NumberFormatException e )
        {
            System.err.println("INVALID BEAM VALUE: " + e.getMessage() );
            System.exit(1);
        }
        catch ( IllegalArgumentException e )
        {
            System.err.println("INVALID MUSEUM FLAG: " + e.getMessage() );
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
            e.printStackTrace();
            System.exit(1);
        }

        //perform the actual Beam search, catching all possible errors
        try
        {
            printSummary( graph, initial, goal, k );
            paths = Search.beamSearch( graph, initial, goal, k, flag );
        }
        catch ( Exception e)
        {
            System.err.println("ERROR PERFORMING SEARCH:");
            System.err.println( e.getMessage() );
            System.exit(1);
        }

        //print all final paths given
        Search.printPaths( paths, goal );
    }

//---------------------------------------------------------------------------
    //NAME: printSummary()
    //IMPORT: graph (Graph), initial (String), goal (String), k (int)
    //PURPOSE: Print search summary block

    public static void printSummary( Graph graph, String initial, String goal, int k )
    {
        System.out.println("\n----------BEAM SEARCH----------");
        System.out.println("INITIAL NODE IS: " + initial);
        System.out.println("   GOAL NODE IS: " + goal);
        System.out.println("  BEAM WIDTH IS: " + k);
        System.out.println("-------------------------------");
    }

//---------------------------------------------------------------------------
}
