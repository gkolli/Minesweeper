//Naren Kolli - Summer 2019

import javax.swing.JButton;

public class MinesweeperWorker {
	
	private final int NUM_ROWS;
	private final int NUM_COLS;
	private final int NUM_BOMBS;
	private boolean[][] bombArray;
	private int[][] numArray;
	
	
	
	public MinesweeperWorker(int difficulty){
		
		
		if(difficulty == 0){
			NUM_ROWS = 8;
			NUM_COLS = 8;
			NUM_BOMBS = 10;
		}
		else if(difficulty == 1){
			NUM_ROWS = 16;
			NUM_COLS = 16;
			NUM_BOMBS = 40;
		}
		else{
			NUM_ROWS = 16;
			NUM_COLS = 32;
			NUM_BOMBS = 100;
		}
		bombArray = new boolean[NUM_ROWS][NUM_COLS];
		numArray = new int[bombArray.length][bombArray[0].length];
		this.fillBoolArray();
		this.printBoolArray();
		this.fillNumArray();
		this.printNumArray();
		
	}
	private void fillNumArray(){
		for(int r = 0; r<NUM_ROWS; r++){
			for(int c = 0; c<NUM_COLS; c++){
				int bombCount = 0;
				if(bombArray[r][c])
					numArray[r][c] = -1;
				else if((r!=0 && r!= NUM_ROWS-1) && (c!=0 && c!= NUM_COLS-1)){ //Checks for all non-edge piece, and give it the number of adj. bombs
					for(int row1 = r-1; row1<=r+1; row1++){
						for(int col1 = c-1; col1<=c+1; col1++)
							if(bombArray[row1][col1])
								bombCount++;
						
					}
					numArray[r][c] = bombCount;
					bombCount = 0;	
				}
				else if(r == 0 && c == 0){ //top left corner
					if(bombArray[r][c+1]) //to the right of the corner
						bombCount++;
					if(bombArray[r+1][c]) //below the corner
						bombCount++;
					if(bombArray[r+1][c+1]) //down and one over from corner
						bombCount++;
					numArray[r][c] = bombCount;
					bombCount = 0;
				}
				else if(r == NUM_ROWS -1 && c == 0){ //bottom left corner
					if(bombArray[r][c+1]) //to the right of the corner
						bombCount++;
					if(bombArray[r-1][c]) //above the corner
						bombCount++;
					if(bombArray[r-1][c+1]) //up and one over from corner
						bombCount++;
					numArray[r][c] = bombCount;
					bombCount = 0;
				}
				else if(r == NUM_ROWS -1 && c == NUM_COLS -1){ //bottom right corner
					if(bombArray[r][c-1]) //to the left of the corner
						bombCount++;
					if(bombArray[r-1][c]) //above the corner
						bombCount++;
					if(bombArray[r-1][c-1]) //up and one over left from corner
						bombCount++;
					numArray[r][c] = bombCount;
					bombCount = 0;
				}
				else if(r == 0 && c == NUM_COLS -1){ //bottom right corner
					if(bombArray[r][c-1]) //to the left of the corner
						bombCount++;
					if(bombArray[r+1][c]) //below the corner
						bombCount++;
					if(bombArray[r+1][c-1]) //down and one over left from corner
						bombCount++;
					numArray[r][c] = bombCount;
					bombCount = 0;
					
				}
				else if(r==0){
					for(int row5 = r; row5<=r+1; row5++){
						for(int col5 = c-1; col5<=c+1; col5++)
							if(bombArray[row5][col5])
								bombCount++;
					}
					numArray[r][c] = bombCount;
					bombCount = 0;
				}
				else if(r== NUM_ROWS - 1){
					for(int row3 = r-1; row3<=r; row3++){
						for(int col3 = c-1; col3<=c+1; col3++)
							if(bombArray[row3][col3])
								bombCount++;
					}	
					numArray[r][c] = bombCount;
					bombCount = 0;
				}
				else if(c==0){
					for(int row4 = r-1; row4<=r+1; row4++){
						for(int col4 = c; col4<=c+1; col4++)
							if(bombArray[row4][col4])
								bombCount++;
					}
					numArray[r][c] = bombCount;
					bombCount = 0;
				}
				else if(c==NUM_COLS -1){
					for(int row5 = r-1; row5<=r+1; row5++){
						for(int col5 = c-1; col5<=c; col5++)
							if(bombArray[row5][col5])
								bombCount++;
					}
					numArray[r][c] = bombCount;
					bombCount = 0;
				}
			}
		}
	}
	private void fillBoolArray(){
		
		for(int i = 0; i<bombArray.length; i++)
			for(int j = 0; j<bombArray[i].length;j++)
				bombArray[i][j] = false;
		for(int b = 0; b<NUM_BOMBS; b++){ //randomly filling array with bombs
			int row = (int)(Math.random()*NUM_ROWS);
			int col = (int)(Math.random()*NUM_COLS);
			if(!bombArray[row][col])
				bombArray[row][col] = true;
			else 
				b--;
		}
	}
	public void printBoolArray(){
		for(int r = 0; r<bombArray.length;r++){
			for(int c = 0; c<bombArray[r].length;c++)
				if(bombArray[r][c])
					System.out.print(bombArray[r][c] + "  ");
				else
					System.out.print(bombArray[r][c] + " ");
			System.out.println();
		}
	}
	public void printNumArray(){
		for(int r = 0; r<numArray.length;r++){
			for(int c = 0; c<numArray[r].length;c++)
				if(numArray[r][c] == -1)
					System.out.print(numArray[r][c] + " ");
				else
					System.out.print(numArray[r][c] + "  ");
			System.out.println();
		}
	}
	public int getRowNum(){
		return NUM_ROWS;
	}
	public int getColNum(){
		return NUM_COLS;
	}
	public int getBombNum(){
		return NUM_BOMBS;
	}
	public boolean[][] getBombArray(){
		return bombArray;
	}
	public int[][] getNumArray(){
		return numArray;
	}
	
	public void giveValues(Cell[][] cells){
		for(int r = 0; r<NUM_ROWS;r++)
			for(int c = 0; c<NUM_COLS;c++)
				if(bombArray[r][c] == true)
					cells[r][c].makeABomb();
				else
					cells[r][c].setValue(numArray[r][c]);
	}
	
	
}
