/***************************************************************************
*	FILE: Search.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: AMI300
*	PURPOSE: Implementations of various search algorithms
*   LAST MOD: 13/04/07
*   REQUIRES: List, LinkedList, Stack
***************************************************************************/

import java.util.*;

public class Search
{
    public static final int MIN_BEAM = 0;
    public static final int MAX_BEAM = 10000;

//---------------------------------------------------------------------------
    //NAME: beamSearch()
    //IMPORT: graph (Graph), initial (int), goal (int), k (int)
    //EXPORT: path to goal (List<Node>)
    //PURPOSE: Perform beam informed search on the graph

    public static List<List<Node>> beamSearch( Graph graph, String initial, String goal,
                                               int k, boolean museum )
    {
        List<List<Node>> paths = new LinkedList<>();
        List<Node> explored = new LinkedList<>();
        Queue<Node> frontier = new PriorityQueue<>();
        Queue<Node> beam = new PriorityQueue<>();
        boolean done = false;

        // ENSURE ALL FIELDS ARE VALID BEFORE CONTINUING
        validateFields( graph, initial, goal, k );

        // ADD THE INITIAL NODE TO THE QUEUE
        beam.add( graph.getNode(initial) );

        // LOOP UNTIL GOAL FOUND OR THE BEAM BECOMES EMPTY
        while ( !done )
        {
            //LOOP WHILE THE BEAM STILL HAS NODES WITHIN IT
            while ( !beam.isEmpty() )
            {
                System.out.print("BEAM IS: ");
                for ( Node next : beam )
                    System.out.print( next.getName() + " " );
                System.out.print("\n");

                // GET THE NEXT ITEM IN THE BEAM AND ADD TO EXPLORED
                Node nextNode = beam.remove();
                explored.add( nextNode );

                // PRINT PATH IS NODE IS TERMINAL
                if ( nextNode.getNodes().isEmpty() )
                    paths.add( createPath( nextNode ) );

                // ADD ALL OF ITS CHILDREN FOR CONSIDERATION, SET PARENT FIELD
                for ( Node child : nextNode.getNodes() )
                {
                    // ONLY CONSIDER IF THE NODES HASN'T BEEN EXPLOTED AND
                    // THE NODE IS NOT CURRENTLY WITHIN THE FRONTIER
                    if ( ( !explored.contains(child) ) && ( !frontier.contains(child) ) )
                    {
                        child.setParent( nextNode );
                        frontier.add( child );
                    }
                }

            }

            // ADD THE k NEXT ITEMS INTO THE BEAM
            int curr = 0;
            while ( ( !frontier.isEmpty() ) && ( curr < k ) )
            {
                System.out.print("FRONTIER IS: ");
                for ( Node next : frontier )
                    System.out.print( next.getName() + " " );
                System.out.print("\n");

                Node nextNode = frontier.remove();
                curr++;

                // GOAL TEST
                if ( goal.equals( nextNode.getName() ) )
                {
                    // PRINT GOAL PATH + ALL PARTIAL PATHS STORED
                    paths.add( createPath( nextNode ) );
                    int ii = 1;

                    for ( Node current : frontier )
                    {
                        ii++;
                        paths.add( createPath(current) );
                        if ( ii >= k )
                            break;
                    }

                    // STOP IF USER DIDN'T SPECIFY TO CONTINUE
                    if ( !museum )
                        done = true;

                }
                // ADD THE NODE INTO THE BEAM, IF IT'S NOT ALREADY THERE
                else if ( !beam.contains( nextNode ) )
                    beam.add( nextNode );
            }

            frontier.clear();

            // IF THE BEAM IS EMPTY, WE MUST BE DONE
            if ( beam.isEmpty() )
                done = true;
        }

        return paths;
    }

//---------------------------------------------------------------------------
    //NAME: alimSearch()
    //IMPORT: graph (Graph), initial (int), goal (int)
    //EXPORT: path to goal (List<Node>)
    //PURPOSE: Perform memory limited A* search on a graph

    public static List<List<Node>> alimSearch( Graph graph, String initial, String goal, int numNodes )
    {
        validateFields( graph, initial, goal, numNodes );
        return null;
    }

//---------------------------------------------------------------------------
    //NAME: createPath()
    //IMPORT: goal (Node)
    //EXPORT: path to goal (List<Node>)
    //PURPOSE: Backtracks from goal to determine path to the goal

    private static List<Node> createPath( Node goal )
    {
        Node next = goal;
        List<Node> path = new LinkedList<>();

        // LOOP UNTIL THE INITIAL NODE IS REACHED
        while ( next != null )
        {
            // ADD TO THE PATH, MOVE TO THE NEXT PARENT
            path.add( 0, next );
            next = next.getParent();
        }

        return path;
    }

//---------------------------------------------------------------------------
    //NAME: validateFields()
    //IMPORT: graph (Graph), initial (int), goal (int), k (int)
    //PURPOSE: Validate imports to the beam search algorithm to check all
    //         the error checking contained in one spot

    private static void validateFields( Graph graph, String initial, String goal, int k )
    {
        if ( graph == null )
            throw new IllegalArgumentException("GRAPH IS NULL");
        if ( graph.getNode(initial) == null )
            throw new IllegalArgumentException("INITIAL NODE DOESN'T EXIST");
        if ( graph.getNode(goal) == null )
            throw new IllegalArgumentException("GOAL NODE DOESN'T EXIST");
        if ( ( k < MIN_BEAM ) || ( k > MAX_BEAM ) )
            throw new IllegalArgumentException("INVALID BEAM VALUE");
    }

//---------------------------------------------------------------------------
}
