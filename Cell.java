//Naren Kolli - Summer 2019
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;


public class Cell {
	private JButton button;
	private int value;
	private boolean beenChecked;
	
	class Mouser implements MouseListener{

		
		public void mouseClicked(MouseEvent arg0) {
			if (!beenChecked) {
				if (arg0.getButton() == 1) {
					if (value == -1) {
						causeFailure();

					} else {
						display();
						checkGameEnd();
					}
				}
				else if (button.getText().equals("!")) {
					if (value == 0 || !beenChecked) {
						button.setText("");
						Minesweeper.incrementCounter();
					}

					else {
						button.setText(Integer.toString(value));
						Minesweeper.incrementCounter();
					}
					button.setBackground(null);
				}
				else {
					button.setText("!");
					button.setBackground(Color.ORANGE);
					Minesweeper.decrementCounter();
				}
			}
		}
		
		
		public void mouseEntered(MouseEvent arg0) {
	
		}

		
		public void mouseExited(MouseEvent arg0) {
			
			
		}

		
		public void mousePressed(MouseEvent arg0) {
			
			
		}

		
		public void mouseReleased(MouseEvent arg0) {
			
			
		}
	}
	
	public Cell(){
		button = new JButton();
		button.addMouseListener(new Mouser());
		beenChecked = false;
		
	}
	public JButton getButton(){
		return button;
	}
	
	public void makeABomb(){
		value = -1;
	}
	public void setValue(int valIn){
		value = valIn;
	}
	public void showValue(){
		if(value!=0){
			String num = Integer.toString(value);
			button.setText(num);
		}
	}
	public void causeFailure(){
		Minesweeper.showAllBombs();
		Minesweeper.showAllValues();
		Minesweeper.lostGame();
	}
	public void failGame(){
		if(this.getButton().getText().equals("!")){
			button.setText("\u2600");
			button.setBackground(new Color(25, 143, 71));
		}
		else{
			button.setText("\u2600");
			button.setBackground(Color.RED);
		}
	}
	public int getValue(){
		return value;
	}
	public String getText(){
		return button.getText();
	}
	public void checkButton(){
		beenChecked = true;
		button.setEnabled(false);
	}
	public boolean checked(){
		return beenChecked;
	}
	public void display(){
		if(!this.beenChecked){
			button.setBackground(null);
			showValue();
			checkButton();
			if(value == 0)
				Minesweeper.displaySurroundingZeroes(this);
			}
	}
	public boolean isAMine(){
		if(value == -1)
			return true;
		else
			return false;
	}
	
	public void checkGameEnd(){ //sees if all non-bombs have been revealed
		int[] gameBoard = new int[1];
		for(int r = 0; r<Minesweeper.worker.getRowNum(); r++)
			for(int c = 0; c< Minesweeper.worker.getColNum();c++){
				if(Minesweeper.cells[r][c].checked())
					gameBoard[0]++;
			}
		if(gameBoard[0] == (Minesweeper.worker.getRowNum() * Minesweeper.worker.getColNum())-Minesweeper.worker.getBombNum())
			winGame();
	}
	
	public void winGame(){
		Minesweeper.counter.setText("Congrats, you Won!");
		Minesweeper.lostGame();
		
	}
	
}
