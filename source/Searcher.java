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
    public static void main( String[] args )
    {
        // SETUP
        Graph graph = new Graph();
        FileIO fileReader = new FileIO( graph );

        // READ IN GRAPH
        String filename = ( "./graphs/graph1.txt" );
        fileReader.readGraph( filename );

        // GET PARAMETERS
        /*Scanner sc = new Scanner(System.in);
        System.out.println("Enter Initial Node: ");
        int initial = sc.nextInt();
        System.out.println("Enter Goal Node: ");
        int goal = sc.nextInt();*/

        // PERFORM SEARCH AND OUTPUT
        List<Node> path = Search.bfs( graph, 1, 15 );
        for ( Node next : path )
            System.out.print( next.getName() + " " );
        System.out.print("\n");
    }

//---------------------------------------------------------------------------
}
