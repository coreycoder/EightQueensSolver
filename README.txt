This is Corey Thomason's N-Queen Program to solve the 18-Queen Problem using HillClimbing and Genetic Algorithm.

To Run:
1) go to diretory that contains .java files
2) javac EightQueens.java
3) java EightQueens


To use Program:

You have the choice to either solve multiple cases of N-Queen problem using a HillClimbing algorithm, 
or to solve a single case of the N-Queen problem using a Genetic algorithm.

When it prompts you, simply enter the integer assigned to the choice on the menu.

If you choose 1) HillClimbing cases, it asks you for the board size, and how many cases you want to generate. 
When the algorithm runs through all the cases, it will tell you how many were successful out of the total, print 
out the successful cases, and the average time in milliseconds per case.

If you choose 2) Genetic Algorithm, it asks you for the board size, and how big you want the population to be (divisible by 2).
The algorithm will then run and output the best heuristic of each generation. If it takes many generations, it will 
continue to print each generation until it finishes. When it finally reaches a goal state, it will print out that
answer and give the total time in milliseconds to find it.

You can either run a new case, since it returns you to the main menu, or exit the program.

Have fun!