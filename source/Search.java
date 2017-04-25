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
        Queue<Node> frontier = new PriorityQueue<>( 11, Node.NodeComparatorBeam);
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
        LinkedList<Node> frontier = new LinkedList<>();
        boolean done = false;
        int count = 0;

        // ENSURE ALL FIELDS ARE VALID BEFORE CONTINUING
        validateFields( graph, initial, goal, numNodes );
        // SET THE HEURISTIC OF THE GOAL AND INITIAL NODES
        graph.getNode(goal).setHeuristic(0);

        // ADD THE FIRST NODE INTO THE QUEUE + INITIAL SET ITS COST
        Node initNode = graph.getNode(initial);
        initNode.setDepth(1);
        frontier.addFirst( initNode );

        while ( !done )
        {
            // IF THE QUEUE IS EMPTY, NO SOLUTION IS POSSIBLE
            if ( frontier.isEmpty() )
            {
                System.out.println("FAILURE: NO SOLUTION");
                return null;
            }

            System.out.println("-------------------------------");
            printCollection( "FRONTIER: ", frontier );
            for ( Node next : frontier )
                if ( next != null )
                    System.out.println( next.toString() );
            System.out.println("-------------------------------");

            Scanner sc = new Scanner(System.in);
            sc.nextInt();

            // GET THE BEST NODE - LOWEST f-COST AND HIGHEST DEPTH
            Collections.sort( frontier, Node.NodeComparatorAStar );
            Node front = frontier.peekFirst();

            // PERFORM THE GOAL TEST WHEN PULLED FROM FRONTIER
            if ( graph.getNode(goal) == front )
            {
                System.out.println("SUCCESS: SOLUTION FOUND");
                return null;
            }

            // IF DEPTH IS TOO LARGE WE ROLL BACK THE CURRENT FRONT, ITS USELESS
            if ( front.getDepth() >= numNodes )
            {
                front.setCost( Double.MAX_VALUE );
                backup(front);
                frontier.remove(front);
                continue;
            }

            // GET THE NEXT POSSIBLE CHILD FROM THE FRONT NODE
            Node succ = null;
            succ = front.getNextChild();
            while ( ( succ != null ) && ( front.inPath( succ ) && ( !frontier.contains( succ ) ) ) )
                succ = front.getNextChild();

            // IF A VALID CHILD EXISTS
            if ( succ != null )
            {
                succ.setParent( front );
                succ.setDepth( front.getDepth() + 1 );
                double cost = front.getCost() + front.getEdgeCost( succ );
                succ.setCost( cost );
            }

            // MEMORY IS FULL SO WE ROLLBACK THE WORST COST IN FRONTIER
            if ( frontier.size() == numNodes )
            {
                Collections.sort( frontier, Node.NodeComparatorAStar );
                Node worst = frontier.removeLast();
                backup( worst );
                Node parent = worst.getParent();
            }


            // INSERT NEXT SUCCESSOR INTO THE FRONTIER
            if ( succ != null )
                frontier.addLast( succ );
        }

        return paths;
    }

//---------------------------------------------------------------------------

    private static void backup( Node backupNode )
    {
        System.out.println("BACKUP NODE: " + backupNode.getName() );
        // IF NODE IS COMPLETE AND HAS A PARENT
        if ( backupNode.getParent() != null )
        {
            backupNode.getParent().setBest( backupNode.aCost() );
        }
    }

//---------------------------------------------------------------------------

    private static void printCollection( String label, Collection<Node> collect )
    {
        System.out.print( label + ": ");
        for ( Node next : collect )
            if ( next != null )
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
