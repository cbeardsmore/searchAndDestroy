/***************************************************************************
*	FILE: Search.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: AMI300
*	PURPOSE: Implementations of various search algorithms
*   LAST MOD: 13/04/07
*   REQUIRES: java.util
***************************************************************************/

import java.util.*;

public class Search
{
    //CONSTANTS
    public static final int MIN_BEAM = 1;
    public static final int MAX_BEAM = 10000;
    public static final int INITIAL_DEPTH = 1;
    public static final int GOAL_HEURISTIC = 0;
    public static final int INIT_HEURISTIC = Integer.MAX_VALUE;

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
        Queue<Node> beamCopy;
        boolean done = false;

        //ensure all fields are valid before continuing
        validateFields( graph, initial, goal, k );

        //only need these lines if the heuristic file is missing information
        graph.getNode(initial).setHeuristic( INIT_HEURISTIC );
        graph.getNode(goal).setHeuristic( GOAL_HEURISTIC );

        //add initial node to the beam
        beam.add( graph.getNode(initial) );

        //loop until goal is found or beam is empty
        while ( !done )
        {
            printCollection( "BEAM", beam );
            //used to help do partial paths in memory
            beamCopy = new PriorityQueue<>( beam );
            //loop while beam still has nodes within it
            while ( !beam.isEmpty() )
            {
                Node nextNode = beam.remove();

                //add all children for consideration, set parent field
                for ( Node child : nextNode.getNodes() )
                {
                    double cost = nextNode.getCost() + nextNode.getEdgeCost( child );
                    child.setCost( cost );

                    //goal test
                    if ( goal.equals( child.getName() ) )
                    {
                        child.setParent( nextNode );
                        //goal path + all paths currently in memory
                        paths.add( createPath( child ) );
                        for ( Node nextBeam : beamCopy )
                            paths.add( createPath( nextBeam ) );
                        //if no mueseum we can stop now, if not we continue
                        if ( !museum )
                            return paths;
                    }
                    //not a goal node, add a copy into the frontier
                    else
                    {
                        //check we are not looping
                        if ( !nextNode.inPath( child ) )
                        {
                            child.setParent( nextNode );
                            frontier.add( (Node)child.clone() );
                        }
                    }
                }
            }

            printCollection( "FRONTIER", frontier );

            //add the best k items into the new beam
            int curr = 0;
            while ( ( !frontier.isEmpty() ) && ( curr < k ) )
            {
                Node nextNode = frontier.remove();
                curr++;

                //only add if its not already in the beam
                if ( !beam.contains( nextNode ) )
                    beam.add( (Node)nextNode.clone() );
            }

            //throw away any other elements, they are outside of the beam now
            frontier.clear();

            //if the beam is empty, no more solutions are possible
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
        LinkedList<Node> leafNodes = new LinkedList<>();
        boolean done = false;
        int count = 0;
        double bound = -1.0;

        //ensure all fields are valid
        validateFields( graph, initial, goal, numNodes );

        //only needed if information missing from heuristic file
        graph.getNode(goal).setHeuristic( GOAL_HEURISTIC );

        Node goalNode = graph.getNode(goal);
        Node initNode = graph.getNode(initial);
        //add first node into queue and set initial parameters
        initNode.setDepth( INITIAL_DEPTH );
        initNode.setFN( initNode.getHeuristic() );
        frontier.addFirst( initNode );
        leafNodes.addFirst( initNode );

        while ( !done )
        {
            //simple count of how many iterations we perform
            count++;

            //if frontier is empty, no solutions are possible
            if ( frontier.isEmpty() )
            {
                System.out.println("FAILURE: NO SOLUTION, ALL PATHS EXPLORED");
                System.out.println( "\tITERATIONS: " + count );
                return null;
            }
            //if root is infinity, no solutions are possible
            if ( initNode.getFN() == ( Double.POSITIVE_INFINITY ) )
            {
                //solution was found earlier
                if ( bound > 0 )
                    return paths;
                System.out.println("FAILURE: NO SOLUTION, ROOT IS INFINITY");
                System.out.println( "\tITERATIONS: " + count );
                return null;
            }

            printSMAStar( frontier, leafNodes );

            //get the best node to open - lowest f(n) cost, highest depth
            Collections.sort( frontier, Node.NodeComparatorAStar );
            Node front = frontier.peekFirst();
            front.setVisited();

            if ( ( bound > 0 ) && ( front.getFN() > bound ) )
            {
                System.out.println( "\tITERATIONS: " + count );
                return paths;
            }

            //memory is full, rollback the worst leaf node
            if ( frontier.size() >= numNodes )
            {
                Collections.sort( leafNodes, Node.NodeComparatorAStar );
                Node worst = leafNodes.removeLast();
                backup( worst, frontier );
                isParentLeaf( worst.getParent(), leafNodes );
            }

            //get the next child to consider from the current best node
            Node succ = front.getNextChild();
            //skip until we find a child we want essentially
            while ( ( succ != null ) && ( ( front.inPath( succ ) ) || ( frontier.contains( succ )
                            || ( succ == goalNode ) ) ) )
            {
                //another goal test here before we explore from the frontier
                if ( succ == goalNode )
                {
                    goalNode.setParent( front );
                    double cost = front.getCost() + front.getEdgeCost( goalNode );
                    goalNode.setCost( cost );

                    //set the initial solution bound
                    if ( bound < 0.0 )
                        bound = goalNode.getCost();
                    //the first solution found is NOT always the most optimal
                    else if ( goalNode.getCost() < bound )
                    {
                        bound = goalNode.getCost();
                        paths.clear();
                    }
                    front.removeNode( goalNode );
                    goalReached( succ, leafNodes, paths );
                }

                //if ( frontier.contains( succ ) )
                //    if ( succ.getParent() != null )
                //        succ.getParent().setBestChild();
                succ = front.getNextChild();
            }

            //a valid child exists
            if ( succ != null )
            {
                //parent is no longer a leafNode
                leafNodes.remove(front);
                leafNodes.add(succ);

                //set the successor values
                System.out.println("VISIT SUCCESSOR: " + succ.getName() );
                setSuccessor( succ, front );

                //if depth too great, we rollback and set f(n) to infinity
                if ( succ.getDepth() >= numNodes )
                {
                    //set to infinity to ensure its always culled first
                    succ.setFN(Double.POSITIVE_INFINITY);
                    leafNodes.remove(succ);
                    backup(succ, frontier);
                    isParentLeaf( front, leafNodes );
                }
                //add the new child into the open list (the frontier)
                else
                    frontier.addLast(succ);
            }
            //if no children, we reset f(n) to the best of all the children
            if ( !front.hasNextChild() )
                front.reset();
        }

        System.out.println( "\tITERATIONS: " + count );
        return paths;
    }

//---------------------------------------------------------------------------
    //NAME: goalReached()
    //IMPORT: goalNode (Node), leafNodes (LinkedList<Node>), paths (List<List<String>>)
    //PURPOSE: Set up paths upon reaching the goal and restore state

    public static void goalReached( Node goalNode, LinkedList<Node> leafNodes,
                                                   List<List<String>> paths)
    {
        System.out.println("SUCCESS: SOLUTION FOUND");
        paths.add( createPath( goalNode ) );
        for ( Node next : leafNodes )
            paths.add( createPath( next ) );
    }

//---------------------------------------------------------------------------
    //NAME: backup()
    //IMPORT backupNode (Node), frontier (LinkedList<Node>)
    //PURPOSE: Rollback a node, removing it from the frontier

    private static void backup( Node backupNode, LinkedList<Node> frontier )
    {
        System.out.println("BACKUP NODE: " + backupNode.getName() );
        // BACK UP ITS fn COST TO ITS PARENTS BEST COST
        if ( backupNode.getParent() != null )
            backupNode.getParent().setBestChild();
        else
            throw new IllegalArgumentException("CAN'T BACKUP INIT NODE");
        // REMOVE IT FROM THE FRONTIER
        frontier.remove( backupNode );
    }

//---------------------------------------------------------------------------
    //NAME: setSuccessor()
    //IMPORT: succ (node), parent (Node)
    //PURPOSE: Set a node to be a successor of its parent

    private static void setSuccessor( Node succ, Node parent )
    {
        succ.setParent( parent );
        succ.setDepth( parent.getDepth() + 1 );
        double cost = parent.getCost() + parent.getEdgeCost( succ );
        succ.setCost( cost );
        if ( !succ.isVisited() )
            succ.setFN( cost + succ.getHeuristic() );
        parent.setBestChild();
    }

//---------------------------------------------------------------------------
    //NAME: isParentLeaf()
    //IMPORT: parent (Node), leafNodes (LinkedList<Node>)
    //PURPOSE: Check if a parent node is a leaf, set it if it is

    private static void isParentLeaf( Node parent, LinkedList<Node> leafNodes )
    {
        //if there are none of my children in leafNodes, i am now a leaf
        for ( Node next : leafNodes )
            if ( next.inPath( parent ) )
                return;

        leafNodes.add(parent);
    }

//---------------------------------------------------------------------------
    //NAME: printSMAStar()
    //IMPORT: frontier (LinkedList<Node>), leafNodes (LinkedList<Node>)
    //PURPOSE: Print out the current contents of both the frontier and leafNodes

    private static void printSMAStar( LinkedList<Node> frontier, LinkedList<Node> leafNodes )
    {
        System.out.println("-------------------------------");
        printCollection( "FRONTIER: ", frontier );
        printCollection( "LEAF NODES: ", leafNodes );
        System.out.println("-------------------------------");
        for ( Node next : frontier )
            if ( next != null )
                System.out.println( next.toString() );
        System.out.println("-------------------------------");
    }

//---------------------------------------------------------------------------
    //NAME: printCollection()
    //IMPORT: label (String), collect (Collection<Node>)
    //PURPOSE: Print a collection of nodes prefixed by the given label

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

        path.add( 0, "COST=" + goal.getCost() );
        //loop until initial goal found
        while ( next != null )
        {
            //add to the path and go to the next parent
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
                System.out.print("\n-------------------------------\nSOLUTION PATH: ");
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
