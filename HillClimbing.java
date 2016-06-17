package eightQueens;

import java.util.PriorityQueue;

/**
 * 
 * This is the class that holds the Hill Climbing algorithm. It must maintain a current node, 
 * the size of the chess board, and a priority queue of all neighboring/successor states.
 *
 */
public class HillClimbing {

	private Node current_node;
	private int board_size;
	private PriorityQueue<Node> neighbors;
	
	/**
	 * 
	 * Since there is no initial Node to start with, the constructor takes an array with 
	 * random positions and creates a new Node to initialize a new array of Queens.
	 */
	public HillClimbing(int[] state) {
		this.board_size = state.length;
		this.current_node = new Node(this.board_size);
		this.current_node.setState(state);
		this.neighbors = new PriorityQueue<Node>();
	}
	
	/**
	 * 
	 * Starting with the initial Node that holds the first random state of the queens,
	 * you generate all the successor states of that state and put them into a priority queue.
	 * Then it takes the Node with the lowest heuristic. If that heuristic is zero, it prints 
	 * out that answer state and exits the loop. 
	 * If It doesn't equal zero, it continues the loop, given that the next lowest successor 
	 * has a lower heuristic than the previous Node. If it doesn't, then it has reached a local
	 * minimum and the algorithm is finished, resulting in a failure.
	 * 
	 */
	public boolean HillClimbingAlgorithm() {	
		this.current_node.setHeuristic();
		int best_heuristic = this.current_node.getHeuristic();
		int current_heuristic = 0;
		
		while (current_heuristic <= best_heuristic) {
			this.neighbors = this.current_node.generateNeighbors(this.current_node);
			this.current_node = this.neighbors.poll();
			current_heuristic = this.current_node.getHeuristic();
			if (current_heuristic == best_heuristic)
				break;
			best_heuristic = current_heuristic;
			if (best_heuristic == 0) {
				this.current_node.printState();
				System.out.println("-----------------------------------------------------");
				break;
			}
		}
		if (best_heuristic == 0) {
			return true;
		}
		else
			return false;
		
	}
		
	public Node getCurrentNode() {
		return this.current_node;
	}
}
