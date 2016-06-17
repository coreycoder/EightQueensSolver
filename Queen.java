package eightQueens;

/**
 * 
 * This is the state class that holds the current state of one queen. 
 * The state holds the row and column position of the individual queen
 * and the node class holds an array of these queens.
 * This class also contains a method to see if this queen attacks another.
 *
 */
public class Queen {

	private int row;
	private int col;
	private int board_size;
	
	public Queen(int row, int col, int size) {
		this.row = row;
		this.col = col;
		this.board_size = size;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	/**
	 * 
	 * This method compares location of the current queen with that of another.
	 * If they are on the same row or column, then they are an attacking pair.
	 * If the absolute value of the differences of their respective rows and 
	 * columns are equal, then they are attacking each other diagonally.
	 * This method is called upon by the Node class.
	 * 
	 */
	public boolean checkAttack(Queen other) {

		if (this.row == other.row || this.col == other.col)
			return true;
		else if (Math.abs(this.row - other.row) == Math.abs(this.col - other.col))
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 * This method is used by the hill climbing algorithm to generate successor states.
	 * It takes the current position of the queen and only moves it vertically in its own column.
	 * It moves the queen down one position for each subsequent new successor until it reaches
	 * the edge of the board, then starts back from the top until it reaches the initial row.
	 * The Node class also calls upon this method.
	 */
	public void moveQueen(int move) {
		int last_row = this.board_size - 1;
		int new_row = this.row + move;
		if (new_row > last_row && new_row % last_row != 0)  
			this.row = (new_row % last_row) - 1;
		else if (new_row > last_row && new_row % last_row == 0) 
			this.row = last_row;
		else
			row += move;
	}
}
