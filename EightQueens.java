package eightQueens;

import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class EightQueens {

	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		System.out.println("Welcome to Corey's N-Queen Solver\n----------------------------------------");
		int input = 0;
		while (input != 3) {
			System.out.println("How would you like to solve this?");
			System.out.println("1) Hill Climbing cases");
			System.out.println("2) Genetic Algorithm");
			System.out.println("3) Exit");
			System.out.print("=> ");
			
			input = kb.nextInt();
			
			switch(input) {
			
			case 1: { 
				System.out.println("How big is the chess board?");
				System.out.print("=> ");
				int size = kb.nextInt();
				System.out.println("How many test cases would you like to run?");
				System.out.print("=> ");
				int cases = kb.nextInt();
				int num_success = 0;
				long average_time = 0;
				for (int i = 0; i < cases; i++) {
					int[] state = getRandomState(size);
					HillClimbing hill_climb = new HillClimbing(state);
					long start = System.currentTimeMillis();
					boolean success = hill_climb.HillClimbingAlgorithm();
					long end = System.currentTimeMillis() - start;
					average_time += end;
					if (success)
						num_success++;
				}
				average_time /= cases;
				System.out.println("Number of successes out of " + cases + ": " + num_success);
				System.out.println("Average time per Hill Climb: " + average_time + " milliseconds.\n\n");
				break;
			}
			
			case 2: {
				System.out.println("How big is the chess board?");
				System.out.print("=> ");
				int size = kb.nextInt();
				System.out.println("How big is the initial population?");
				System.out.print("=> ");
				int population = kb.nextInt();
				ArrayList<int[]> list = new ArrayList<int[]>();
				for (int i = 0; i < population; i++) {
					int[] state = getRandomState(size);
					list.add(state);
				}
				Genetic genetic = new Genetic(list, population, size);
				long start = System.currentTimeMillis();
				genetic.geneticAlgorithm();
				long end = System.currentTimeMillis() - start;
				System.out.println(size + "-Queen Problem with initial population of " + population);
				System.out.println("This algorithm took " + end + " milliseconds to complete.\n\n");
				break;
			}
			
			case 3: {
				System.out.println("\nThank you for using the program!");
				System.exit(0);
				break;
			}
			}
		}
	}
	
	/**
	 * 
	 * This function simply returns an array of random positions for initial Queen States
	 * using the Fisher-Yates shuffle
	 */
	
	public static int[] getRandomState(int size) {
		int[] array = new int[size];
		for (int i = 0; i < array.length; i++) {
			array[i] = i;
		}
		
		Random rd = new Random();
		for (int i = array.length - 1; i > 0; i--) {
			int index = rd.nextInt(i + 1);
			int temp = array[index];
			array[index] = array[i];
			array[i] = temp;
		}
		return array;
	}
}
