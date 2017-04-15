/***************************************************************************
*	FILE: Search.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: AMI300
*	PURPOSE: Implementations of various search algorithms
*   LAST MOD: 13/04/07
*   REQUIRES: List, LinkedList, Stack
***************************************************************************/

import java.util.List;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

public class Search
{
    public static final int MIN_BEAM = 0;
    public static final int MAX_BEAM = 10000;

//---------------------------------------------------------------------------
    //NAME: beamSearch()
    //IMPORT: graph (Graph), initial (int), goal (int), k (int)
    //EXPORT: path to goal (List<Node>)
    //PURPOSE: Perform beam informed search on the graph

    public static List<List<Node>> beamSearch( Graph graph, String initial, String goal, int k )
    {
        List<List<Node>> paths = new LinkedList<>();
        List<Node> explored = new LinkedList<>();
        Set<Node> terminals = new HashSet<>();
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
                // GET THE NEXT ITEM IN THE BEAM AND ADD TO EXPLORED + TERMINALS
                Node nextNode = beam.remove();
                explored.add( nextNode );
                terminals.add( nextNode );

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
                Node nextNode = frontier.remove();
                // REMOVE PARENT FROM THE LIST OF TERMINAL NODES
                terminals.remove( nextNode.getParent() );

                // GOAL TEST
                if ( goal.equals( nextNode.getName() ) )
                {
                    paths.add( createPath( nextNode ) );
                    break;
                }
                // ADD THE NODE INTO THE BEAM, IF IT'S NOT ALREADY THERE
                if ( !beam.contains( nextNode ) )
                {
                    beam.add( nextNode );
                    curr++;
                }
            }

            // THROW OUT ALL NODES WHO ARE OUTSIDE OF THE BEAM
            for ( Node terminalNode : terminals )
                paths.add( createPath( terminalNode ) );
            terminals.clear();
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

    public static List<Node> alimSearch( Graph graph, int initial, int goal )
    {
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
