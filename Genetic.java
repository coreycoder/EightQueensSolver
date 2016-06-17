package eightQueens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * 
 * This is the genetic algorithm class. It holds an ArrayList of Nodes that represents
 * the entire population. This population size remains the same for the duration of the 
 * algorithm. It holds the board size, and also the current generation that the algorithm is in.
 *
 */

public class Genetic {
	private int population_size;
	private ArrayList<Node> population;
	private int board_size;
	private int generation;
	
	/**
	 * 
	 * The constructor takes an ArrayList of integer arrays for the initial population.
	 * Each array is then passed through to the Node constructor and is used to 
	 * initialize an array of Queen objects. The index position of the index array is the 
	 * column position of the queen. And the value of the array is the row position for the queen.
	 * The heuristic and fitness of each state is found as well. 
	 */
	public Genetic(ArrayList<int[]> random_population, int population_size, int board_size) {
		this.board_size = board_size;
		this.population_size = population_size;
		this.population = new ArrayList<Node>();
		for (int i = 0; i < this.population_size; i++) {
			Node temp = new Node(this.board_size);
			temp.setState(random_population.get(i));
			temp.setHeuristic();
			temp.setFitness();
			this.population.add(temp);
		}
		this.generation = 0;
	}
	
	/**
	 * This is the Genetic Algorithm. It does its calculations of fitness and probability using
	 * the number of non-attacking queens, but when trying to reach the goal state, it checks using 
	 * the heuristic since it is a smaller value to deal with.
	 * The algorithm starts with the initial population and does the usual genetic sequence:
	 * choose parent states by probability -> crossover -> possible mutation. 
	 */
	public void geneticAlgorithm() {
		Random rd = new Random();
		
		int current_heuristic;
		int best_heuristic = 1000;
		
		//this loop runs until the goal state is reached
		while (best_heuristic != 0) {
		
			//This algorithm moves in groups of 2, since it requires 2 parents to create child state
			for (int i = 0; i < this.population_size; i += 2) {
				
				//2 parent states are returned based on their probability
				ArrayList<Node> list = this.chooseByProbability();
				
				//2 parent states from above
				Node parent_1 = list.get(0);
				Node parent_2 = list.get(1);
				
				//Create 2 new empty Queen arrays
				Queen[] state_1 = new Queen[this.board_size];
				Queen[] state_2 = new Queen[this.board_size];
				
				//This random value is the point of crossover for the new child states
				int random = rd.nextInt(this.board_size);
				
				//Crossover the two states of the parents and put them in the new child states.
				for (int j = 0; j < random; j++) {
					state_1[j] = parent_1.getState()[j];
					state_2[j] = parent_2.getState()[j];
				}
				for (int k = random; k < this.board_size; k++) {
					state_1[k] = parent_2.getState()[k];
					state_2[k] = parent_1.getState()[k];
				}
				
				//These random values are to see if whether or not the child states should be mutated
				int mutate_1 = rd.nextInt(101);
				int mutate_2 = rd.nextInt(101);
				
				//If the child states do mutate, they mutate the queen at this random index
				int random_1 = rd.nextInt(this.board_size);
				int random_2 = rd.nextInt(this.board_size);
				
				//This mutates the child states at a 0.02 mutation rate
				if (mutate_1 > 98)
					state_1[random_1].setRow(random_2);
				if (mutate_2 > 98)
					state_2[random_2].setRow(random_1);	
				
				
				//Create new nodes, initialize the Queen arrays given the new child states, and find
				//the resulting heuristic and fitness values
				Node child_1 = new Node(this.board_size);
				Node child_2 = new Node(this.board_size);
				child_1.setState(state_1);
				child_2.setState(state_2);
				child_1.setHeuristic();
				child_2.setHeuristic();
				child_1.setFitness();
				child_2.setFitness();
				
				//Replace the parent states with the child states
				this.population.set(i, child_1);
				this.population.set(i + 1, child_2);
			}
			
			//Increment and print out the generation number
			this.generation++;
			if (this.generation % 1 == 0) {
				System.out.println("Generation: " + this.generation);
				
			}
			
			//This little function sorts the ArrayList population based on 
			//their fitness values. This makes it easy to find the lowest heuristic
			//in the ArrayList instead of having to search through the entire list.
			Collections.sort(this.population, new Comparator<Node>() {
				@Override
				public int compare(Node first, Node second) {
					if (first.getFitness() == second.getFitness())
						return 0;
					else if (first.getFitness() > second.getFitness())
						return 1;
					else
						return -1;
				}
			});

			//Gets the heuristic of the Node with the lowest heuristic to check with goal state
			current_heuristic = this.population.get(this.population_size - 1).getHeuristic();
			if (current_heuristic < best_heuristic)
				best_heuristic = current_heuristic;
			if (best_heuristic == 0) 
				this.population.get(this.population_size - 1).printState();
		
			//Prints the current value of the best heuristic out of the entire population
			System.out.println("best heuristic: " + best_heuristic);
			
		}
		
	}
	
	/**
	 * 
	 * This function uses the Roulette Wheel style of choosing the most eligible state
	 * based on its fitness. First, the sum of all the fitness values are found in the entire
	 * population. A random number from 0-sum is chosen and a loop adds up the fitness values
	 * until the random number is in the range of that fitness value. The larger the fitness value, 
	 * the larger the range of possible choices and more likely to be chosen.
	 * This function returns an ArrayList of 2 Nodes so both parents returned by this function 
	 * are not the same parent. If the function happens to choose the same parent as the first, 
	 * it runs the loop again until it chooses another parent with a high probability.  
	 */
	public ArrayList<Node> chooseByProbability() {
		Random rd = new Random();
		int count = 0;
		int index = -5;
		ArrayList<Node> list = new ArrayList<Node>();
		
		while(count < 2) {
			int total_probability = 0;
			for (int i = 0; i < this.population.size(); i++) {
				total_probability += this.population.get(i).getFitness();
			}
			int random = rd.nextInt(total_probability);
			int sum = 0;
			int i = 0;
			while (sum < random) {
				sum += this.population.get(i++).getFitness();
			}
			if (index == i - 1) {
				continue;
			}
			index = Math.max(0, i - 1);
			list.add(this.population.get(Math.max(0, i - 1)));
			count++;
		}
		return list;
	}
}
