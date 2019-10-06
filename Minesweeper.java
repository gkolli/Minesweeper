//Naren Kolli - Summer 2019

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Minesweeper {
	public static JPanel panel;
	public static JPanel northPanel;
	public static MinesweeperWorker worker;
	public static Cell[][] cells;
	public static JFrame frame;
	public static JLabel counter;
	public static int numRemainingBombs;
	private static JFrame openingScreen;
	private static JPanel openingPanel;
	private static JFrame gameLostFrame;
	private static JPanel gameLostPanel;
	
	public static void main(String[] args){
		startGame();
	}
	public static void startGame(){
		class EasyListener implements ActionListener{

			public void actionPerformed(ActionEvent arg0) {
				worker = new MinesweeperWorker(0);
				Minesweeper.doEverything();
				openingScreen.dispose();
			}
			
		}
		class MediumListener implements ActionListener{

			public void actionPerformed(ActionEvent arg0) {
				worker = new MinesweeperWorker(1);
				Minesweeper.doEverything();
				openingScreen.dispose();
			}
			
		}
		class HardListener implements ActionListener{

			public void actionPerformed(ActionEvent arg0) {
				worker = new MinesweeperWorker(2);
				Minesweeper.doEverything();
				openingScreen.dispose();
			}
			
		}
		openingScreen = new JFrame();
		openingScreen.pack();
		openingScreen.setVisible(true);
		openingScreen.setSize(800, 300);
		openingScreen.setLocationRelativeTo(null);
		openingScreen.setResizable(false);
		openingScreen.setTitle("Choose your difficulty!");
		openingScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		openingPanel = new JPanel();
		openingPanel.setLayout(new GridLayout(0,3));
		JButton easyButton = new JButton("Easy");
		easyButton.addActionListener(new EasyListener());
		JButton mediumButton = new JButton("Medium");
		mediumButton.addActionListener(new MediumListener());
		JButton hardButton = new JButton("Hard");
		hardButton.addActionListener(new HardListener());
		openingPanel.add(easyButton, BorderLayout.LINE_START);
		openingPanel.add(mediumButton, BorderLayout.CENTER);
		openingPanel.add(hardButton, BorderLayout.LINE_END);
		openingScreen.add(openingPanel);
		openingScreen.setVisible(true);
	}
	
		public static void  doEverything(){
		int numRows = worker.getRowNum();
		int numCols = worker.getColNum();
		numRemainingBombs = worker.getBombNum();
		cells = new Cell[numRows][numCols];
		frame = new JFrame();
		if(numRows == 8){
			frame.setSize(600, 600);
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);
		}
		else if(numRows == 16 && numCols != 32){
			 frame.setSize(1000,1000);
			 frame.setLocationRelativeTo(null);
			 frame.setResizable(false);
		}
		else{
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		counter = new JLabel(Integer.toString(numRemainingBombs) + " bombs remaining");
		
		JButton resetButton = new JButton("Reset");
		class Resetter implements ActionListener{

			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				startGame();
			}
			
		}
		resetButton.addActionListener(new Resetter());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		panel = new JPanel();
		northPanel = new JPanel();
		northPanel.add(resetButton);
		northPanel.add(counter);
		addCells();
		worker.giveValues(cells);
		panel.setLayout(new GridLayout(numRows,numCols));
		frame.add(northPanel, BorderLayout.PAGE_START);
		frame.add(panel, BorderLayout.CENTER);
		if(worker.getBombNum() == 10)
			frame.setTitle("Minesweeper: Easy");
		else if(numRows == 16 && numCols != 32)
			frame.setTitle("Minesweeper: Medium");
		else
			frame.setTitle("Minesweeper: Insane in the Membrane");
		
		frame.setVisible(true);
		}
	
	public static void addCells(){
		for(int r = 0; r<worker.getRowNum(); r++)
			for(int c = 0; c< worker.getColNum(); c++){
				Cell temp = new Cell();
				cells[r][c] = temp;
				panel.add(cells[r][c].getButton());		
			}
		
	}
	
	public static void showAllBombs(){
		for(int r = 0; r<worker.getRowNum();r++)
			for(int c = 0;c<worker.getColNum();c++)
				if(cells[r][c].getValue() == -1 && cells[r][c].getText() != "!"){
					cells[r][c].failGame();
				}
		System.out.println("Kachow! You Lost!");
	}
	public static void showAllValues(){
		for(int r = 0; r<worker.getRowNum();r++)
			for(int c = 0;c<worker.getColNum();c++){
				cells[r][c].getButton().setEnabled(false);
				if(cells[r][c].getValue() != -1)
					cells[r][c].showValue();
			}
	}
	
	public static ArrayList<Cell> surroundingCells(int row, int col){
		ArrayList<Cell> results = new ArrayList<Cell>();
		for(int r = row-1; r<=row+1;r++)
			for(int c = col-1; c<=col+1; c++)
				if(r>=0 && r<worker.getRowNum() && c>=0 && c<worker.getColNum())
					results.add(cells[r][c]);
		return results;
	}
	
	
	
	public static void displaySurroundingZeroes(Cell cellIn){
		for(int r = 0; r<worker.getRowNum();r++)
			for(int c= 0; c<worker.getColNum(); c++)
				if(cells[r][c].equals(cellIn)){
					ArrayList<Cell> cellArray = surroundingCells(r,c);
					for(Cell temp: cellArray){
						if(!temp.isAMine() && !temp.getText().equals("!"))
							temp.display();
					}
				}
		}
	
	public static void incrementCounter(){
		numRemainingBombs++;
		counter.setText(Integer.toString(numRemainingBombs) + " bombs remaining");
	}
	public static void decrementCounter(){
		numRemainingBombs--;
		counter.setText(Integer.toString(numRemainingBombs) + " bombs remaining");
	}
	
	public static void lostGame(){
		
		gameLostFrame = new JFrame();
		gameLostFrame.setSize(400, 70);
		gameLostFrame.setTitle("End of Game Screen");
		gameLostFrame.setResizable(false);
		gameLostFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameLostPanel = new JPanel();
		
		class GameReset implements ActionListener{

			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				gameLostFrame.dispose();
				startGame();
				
			}
			
		}
		
		JButton resetButton = new JButton("Reset the Game?");
		resetButton.addActionListener(new GameReset());
		gameLostPanel.add(resetButton);
		
		class CloseGame implements ActionListener{

			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				gameLostFrame.dispose();
				
			}
			
		}
		
		JButton closeGameButton = new JButton("Exit the Game.");
		closeGameButton.addActionListener(new CloseGame());
		gameLostPanel.add(closeGameButton);
		gameLostFrame.add(gameLostPanel);
		gameLostFrame.setLocationRelativeTo(null);
		gameLostFrame.setVisible(true);
	}
}

	

	


