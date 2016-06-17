package eightQueens;

import java.util.PriorityQueue;

/**
 * 
 * This is the Node class used both by the HillClimbing and
 * Genetic algorithms. It holds an array of Queens that make up the current state,
 * the size of the chess board, the heuristic, and the fitness of that state.
 *
 */
public class Node implements Comparable<Node>{

	private Queen[] state;
	private int heuristic;
	private int board_size;
	private int fitness;
	
	public Node(int size) {
		this.board_size = size;
		this.state = new Queen[this.board_size];
		this.heuristic = 0;
	}
	
	public Node(Node current) {
		this.board_size = current.board_size;
		this.state = new Queen[this.board_size];
		for (int i = 0; i < this.board_size; i++) {
			this.state[i] = new Queen(current.getState()[i].getRow(), current.getState()[i].getCol(), this.board_size);
		}
		this.heuristic = 0;
	}
	
	/**
	 * 
	 * This function takes in a single Node and generates all the successors of that node.
	 * It does so by calling upon the moveQueen function in the State class. First, a new Node
	 * is created, the state is altered by moving the queen to its new position, then the resulting
	 * heuristic is found, and finally the Node is added to a priority queue to be returned to
	 * the HillClimbing function. It is stored in a priority queue because the lowest heuristic Node
	 * will be chosen first
	 * 
	 */
	public PriorityQueue<Node> generateNeighbors(Node current_node) {
		PriorityQueue<Node> neighbors = new PriorityQueue<Node>();
		for (int i = 0; i < this.board_size; i++) {
			for (int j = 1; j < this.board_size; j++) {
				Node temp_node = new Node(current_node);
				temp_node.getState()[i].moveQueen(j);
				temp_node.setHeuristic();
				neighbors.add(temp_node);
			}
		}
		return neighbors;
	}
	
	/**
	 * This method finds the heuristic given the current state of queens by calling upon
	 * the checkAttack method in the Queen class. For each pair of queens attacking each
	 * other directly or indirectly, the heuristic is increased.
	 */
	public void setHeuristic() {
		for (int i = 0; i < this.state.length - 1; i++) {
			for (int j = i + 1; j < state.length; j++) {
				if (this.state[i].checkAttack(state[j]))
					this.heuristic++;
			}
		}
	}
	
	public int getHeuristic() {
		return this.heuristic;
	}
	
	/**
	 * This method is used for the genetic algorithm. Since the genetic algorithm tests 
	 * probability of breeding based on fitness, and fitness is the total number of 
	 * non-attacking pairs of queens, I simply find the total possible number of 
	 * non-attacking queens for this board size and subtract the current heuristic 
	 * from that sum, resulting in the actual number of non-attacking queens.
	 */
	public void setFitness() {
		for (int i = this.board_size - 1; i > 0; i--) {
			this.fitness += i;
		}
		this.fitness -= this.heuristic;
	}
	
	public int getFitness() {
		return this.fitness;
	}
	
	public Queen[] getState() {
		return this.state;
	}
	
	public void setState(Queen[] state) {
		for (int i = 0; i < this.state.length; i++) {
			this.state[i] = new Queen(state[i].getRow(), state[i].getCol(), this.board_size);
		}
	}
	
	public void setState(int[] state) {
		for (int i = 0; i < this.board_size; i++) {
			this.state[i] = new Queen(state[i], i, this.board_size);
		}
	}
	
	public void printState() {
		boolean exists = false;
		for (int i = 0; i < this.board_size; i++) {
			for (int j = 0; j < this.board_size; j++) {
				exists = false;
				for (int k = 0; k < this.board_size; k++) {
					if (i == this.state[k].getRow() && j == this.state[k].getCol()) {
						System.out.print("[X]");
						exists = true;
						break;
					}
				}
				if (!exists)
					System.out.print("[O]");
			}
			System.out.println();
		}
	}
	
	/**
	 * 
	 * These two methods are helper methods for the Comparable interface to 
	 * use when choosing a Node from a priority queue with the lowest
	 * heuristic.
	 * 
	 */
	public boolean equals(Node other) {
		return this.getHeuristic() == other.getHeuristic();
	}
	
	public int compareTo(Node other) {
		if (this.equals(other))
			return 0;
		else if (this.getHeuristic() > other.getHeuristic())
			return 1;
		else
			return -1;
	}
}
