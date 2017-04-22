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
    //IMPORT: graph (Graph), initial (int), goal (int), k (int), museum (boolean)
    //EXPORT: path to goal (List<Node>)
    //PURPOSE: Perform beam informed search on the graph

    public static List<List<String>> beamSearch( Graph graph, String initial, String goal,
                                               int k, boolean museum )
    {
        List<List<String>> paths = new LinkedList<>();
        Queue<Node> frontier = new PriorityQueue<>();
        Queue<Node> beam = new PriorityQueue<>();
        boolean done = false;

        // ENSURE ALL FIELDS ARE VALID BEFORE CONTINUING
        validateFields( graph, initial, goal, k );

        // SET THE HEURISTIC OF THE GOAL AND INITIAL NODES
        graph.getNode(initial).setHeuristic(Integer.MAX_VALUE);
        graph.getNode(goal).setHeuristic(0);

        // ADD THE INITIAL NODE TO THE QUEUE
        beam.add( graph.getNode(initial) );

        // LOOP UNTIL GOAL FOUND OR THE BEAM BECOMES EMPTY
        while ( !done )
        {
            // PRINT THE BEAM
            printCollection( "BEAM", beam );

            //LOOP WHILE THE BEAM STILL HAS NODES WITHIN IT
            while ( !beam.isEmpty() )
            {
                // GET THE NEXT ITEM IN THE BEAM AND ADD TO EXPLORED
                Node nextNode = beam.remove();

                // ADD ALL OF ITS CHILDREN FOR CONSIDERATION, SET PARENT FIELD
                for ( Node child : nextNode.getNodes() )
                {
                    //GOAL TEST
                    if ( goal.equals( child.getName() ) )
                    {
                        child.setParent( nextNode );
                        paths.add( createPath( child ) );
                        for ( Node nextBeam : beam )
                            paths.add( createPath( nextBeam ) );
                        if ( !museum )
                            return paths;
                    }
                    // ONLY CONSIDER IF THE NODES ISN'T IN THE FRONTIER OR BEAM
                    else
                    {
                        // MAKE SURE THE CHILD ISN'T IN THE SAME PATH
                        if ( !nextNode.inPath( child ) )
                        {
                            child.setParent( nextNode );
                            frontier.add( (Node)child.clone() );
                        }
                    }
                }

            }

            // PRINT THE FRONTIER
            printCollection( "FRONTIER", frontier );

            // ADD THE k NEXT ITEMS INTO THE BEAM
            int curr = 0;
            while ( ( !frontier.isEmpty() ) && ( curr < k ) )
            {
                Node nextNode = frontier.remove();
                curr++;

                // ADD THE NODE INTO THE BEAM, IF IT'S NOT ALREADY THERE
                if ( !beam.contains( nextNode ) )
                    beam.add( (Node)nextNode.clone() );
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

    public static List<List<String>> alimSearch( Graph graph, String initial, String goal, int numNodes )
    {
        List<List<String>> paths = new LinkedList<>();
        Queue<Node> frontier = new PriorityQueue<>();
        Queue<Node> open = new PriorityQueue<>();
        boolean done = false;
        int count = 0;

        // ENSURE ALL FIELDS ARE VALID BEFORE CONTINUING
        validateFields( graph, initial, goal, numNodes );
        // SET THE HEURISTIC OF THE GOAL AND INITIAL NODES
        graph.getNode(initial).setHeuristic(Integer.MAX_VALUE);
        graph.getNode(goal).setHeuristic(0);


        while ( !done )
        {
            // RUN OUT OF NODES TO CONSIDER, NO SOLUTION
            if ( open.isEmpty() )
                return null;

            // GET THE CURRENT BEST OPTION
            Node next = open.peek();

            // GOAL TEST
            if ( next == graph.getNode(goal) )
                paths.add( createPath(next) );




        }


        return paths;
    }

//---------------------------------------------------------------------------

    public static void printCollection( String label, Collection<Node> collect )
    {
        System.out.print( label + ": ");
        for ( Node next : collect )
            System.out.print( next.getName() + " " );
        System.out.print("\n");
    }

//---------------------------------------------------------------------------
    //NAME: createPath()
    //IMPORT: goal (Node)
    //EXPORT: path to goal (List<Node>)
    //PURPOSE: Given a node, backtracks through the path to the initial node

    private static List<String> createPath( Node goal )
    {
        Node next = goal;
        List<String> path = new LinkedList<>();

        // LOOP UNTIL THE INITIAL NODE IS REACHED
        while ( next != null )
        {
            // ADD TO THE PATH, MOVE TO THE NEXT PARENT
            path.add( 0, next.getName() );
            next = next.getParent();
        }

        return path;
    }

//---------------------------------------------------------------------------
    //NAME: printPaths()
    //IMPORT: paths (List<List<String>>), goal (String)
    //PURPOSE: Print all paths within the List of paths

    public static void printPaths( List<List<String>> paths, String goal )
    {
        for ( List<String> nextPath : paths )
        {
            if ( nextPath.contains(goal) )
            {
                System.out.println("\n-------------------------------");
                System.out.print("SOLUTION PATH: ");
            }
            else
                System.out.print("\nPARTIAL PATH:  ");
            for ( String next : nextPath )
                System.out.print( next + " " );
        }
        System.out.println("\n-------------------------------\n");
    }

//---------------------------------------------------------------------------
    //NAME: validateFields()
    //IMPORT: graph (Graph), initial (int), goal (int), k (int)
    //PURPOSE: Validate imports to the search algorithms to check all

    private static void validateFields( Graph graph, String initial, String goal, int k )
    {
        if ( graph == null )
            throw new IllegalArgumentException("GRAPH IS NULL");
        if ( graph.getNode(initial) == null )
            throw new IllegalArgumentException("INITIAL NODE DOESN'T EXIST");
        if ( graph.getNode(goal) == null )
            throw new IllegalArgumentException("GOAL NODE DOESN'T EXIST");
        if ( ( k < MIN_BEAM ) || ( k > MAX_BEAM ) )
            throw new IllegalArgumentException("INVALID k VALUE");
    }

//---------------------------------------------------------------------------
}
