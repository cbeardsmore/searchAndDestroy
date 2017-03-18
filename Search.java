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
import java.util.Collections;

public class Search
{
    public static List<Node> dfs( Graph graph, int initial, int goal )
    {
        boolean done = false;
        List<Node> explored = new LinkedList<>();
        Stack<Node> stack = new Stack<>();
        stack.push( graph.getNode(initial) );

        // LOOP UNTIL GOAL FOUND - DONE SET TO TRUE
        // OR STACK IS EMPTY - ALL PATHS EXPORED, NO SOLUTION
        while ( ( !stack.empty() ) && ( !done ) )
        {
            //POP THE TOP NODE FROM THE STACK
            Node next = stack.pop();

            //ADD NEXT NODE TO THE EXPLORED LIST
            explored.add(next);

            //ADD ALL CONNECTED NODES TO THE STACK
            List<Node> nodes = next.getNodes();
            Collections.reverse( nodes );
            for ( Node child : nodes  )
            {
                //GOAL TEST
                if ( child == graph.getNode(goal) )
                    done = true;

                //DON'T BACKTRACK
                if ( !explored.contains(child) )
                {
                    child.setParent( next );
                    stack.push( child );
                }
            }

        }

        return createPath( graph.getNode(goal) );
    }

//---------------------------------------------------------------------------

public static List<Node> bfs( Graph graph, int initial, int goal )
{
    boolean done = false;
    List<Node> explored = new LinkedList<>();
    Queue<Node> queue = new LinkedList<>();
    queue.add( graph.getNode(initial) );

    // LOOP UNTIL GOAL FOUND - DONE SET TO TRUE
    // OR STACK IS EMPTY - ALL PATHS EXPORED, NO SOLUTION
    while ( ( !queue.isEmpty() ) && ( !done ) )
    {
        //POP THE TOP NODE FROM THE STACK
        Node next = queue.remove();

        System.out.println( next.getName() );

        //ADD NEXT NODE TO THE EXPLORED LIST
        explored.add(next);

        //ADD ALL CONNECTED NODES TO THE STACK
        List<Node> nodes = next.getNodes();
        for ( Node child : nodes  )
        {
            //GOAL TEST
            if ( child == graph.getNode(goal) )
                done = true;

            //DON'T BACKTRACK
            if ( !explored.contains(child) )
            {
                child.setParent( next );
                queue.add( child );
            }
        }

    }

    return createPath( graph.getNode(goal) );
}

//---------------------------------------------------------------------------


    private static List<Node> createPath( Node goal )
    {
        Node next = goal;
        List<Node> path = new LinkedList<>();

        while ( next != null )
        {
            path.add( 0, next );
            next = next.getParent();
        }

        return path;
    }

//---------------------------------------------------------------------------
}
