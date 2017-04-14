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

public class Search
{
    public static final int MIN_BEAM = 0;
    public static final int MAX_BEAM = 10000;

//---------------------------------------------------------------------------
    //NAME: beamSearch()
    //IMPORT: graph (Graph), initial (int), goal (int), k (int)
    //EXPORT: path to goal (List<Node>)
    //PURPOSE: Perform beam informed search on the graph

    public static List<Node> beamSearch( Graph graph, String initial, String goal, int k )
    {
        List<Node> explored = new LinkedList<>();
        Queue<Node> children = new PriorityQueue<>();
        Queue<Node> beam = new PriorityQueue<>();
        boolean done = false;

        // ENSURE ALL FIELDS ARE VALID BEFORE CONTINUING
        validateFields( graph, initial, goal, k );

        // ADD THE INITIAL NODE TO THE QUEUE
        beam.add( graph.getNode(initial) );

        // LOOP UNTIL GOAL FOUND - DONE SET TO TRUE
        while ( !done )
        {
            //ADD ALL CHILDREN OF THE CURRENT BEAM INTO A PRIORITYQUEUE
            while ( !beam.isEmpty() )
            {
                // GET THE NEXT ITEM IN THE BEAM AND ADD TO EXPLORED
                Node nextNode = beam.remove();

                System.out.println( "REMOVE FROM BEAM:" + nextNode.getName() );

                explored.add( nextNode );
                // ADD ALL OF ITS CHILDREN FOR CONSIDERATION, SET PARENT FIELD
                for ( Node child : nextNode.getNodes() )
                {

                    if ( !explored.contains(child) )
                    {
                        System.out.println("ADD TO CHILDREN:" + child.getName());
                        System.out.println("\t" + child.getName() + " SET TO " + nextNode.getName() );
                        child.setParent( nextNode );
                        children.add( child );
                    }
                }

            }

            // ADD THE NEXT ITEMS INTO THE BEAM
            int curr = 0;
            while ( ( !children.isEmpty() ) && ( curr < k ) )
            {
                Node nextNode = children.remove();

                if ( goal.equals( nextNode.getName() ) )
                    done = true;
                if ( !beam.contains( nextNode ) )
                {
                    System.out.println( "MOVE CHILD INTO BEAM:" + nextNode.getName() );

                    beam.add( nextNode );
                    curr++;
                }
            }

            // EMPTY ALL THE OTHER ELEMENTS OUT, NOT WITHIN THE BEAM
            children.clear();

            // IF THE BEAM IS EMPTY, WE MUST BE DONE
            if ( beam.isEmpty() )
                done = true;
        }

        return createPath( graph.getNode(goal) );
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
