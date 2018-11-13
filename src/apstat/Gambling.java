package apstat;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gambling extends JPanel {
	private JButton[][] board;
	private int[][] matrix;
	private JLabel label;
	private int turns, winnings;
	private JButton play;

	public Gambling() {
		turns = 3;
		winnings = 0;
		setLayout(new BorderLayout());
		JPanel north = new JPanel();
		north.setLayout(new FlowLayout());
		add(north, BorderLayout.NORTH);
		label = new JLabel("Pay 4 then click \"play\" to generate treasure.");
		north.add(label);

		JPanel center = new JPanel();
		center.setLayout(new GridLayout(10, 10));
		add(center, BorderLayout.CENTER);
		board = new JButton[10][10];
		matrix = new int[10][10];
		for (int r = 0; r < 10; r++)
			for (int c = 0; c < 10; c++) {
				matrix[r][c] = 0;
				board[r][c] = new JButton();
				board[r][c].setBackground(new Color(153, 153, 153));
				board[r][c].addActionListener(new Handler1(r, c));
				board[r][c].setBorderPainted(false);
				board[r][c].setEnabled(false);
				center.add(board[r][c]);
			}
		play = new JButton("Play");
		play.addActionListener(new Handler2());
		add(play, BorderLayout.SOUTH);
	}

	private class Handler1 implements ActionListener {
		private int r, c;

		public Handler1(int r, int c) {
			this.r = r;
			this.c = c;
		}

		public void actionPerformed(ActionEvent e) {
			board[r][c].setEnabled(false);
			winnings += matrix[r][c];
			if (matrix[r][c] == 0)
				board[r][c].setBackground(new Color(150, 104, 66));// brown
			else if (matrix[r][c] == 2)
				board[r][c].setBackground(new Color(92, 255, 86));// green
			else if (matrix[r][c] == 3)
				board[r][c].setBackground(new Color(255, 8, 0));// red
			else
				board[r][c].setBackground(new Color(0, 127, 255));// blue
			board[r][c].paintImmediately(0, 0, 1000, 1000);
			turns--;
			label.setText("You have " + (turns == 1 ? 1 + " turn " : turns + " turns ") + "to uncover treasure.");
			board[r][c].setBorderPainted(true);
			if (turns == 0) {
				label.setText("You won " + winnings + " dollars! To generate more treasure, pay 4 and click \"play\"");
				winnings = 0;
				turns = 3;
				play.setEnabled(true);
				for (int r = 0; r < board.length; r++)
					for (int c = 0; c < board.length; c++) {
						board[r][c].setEnabled(false);
						/*
						 * if(board[r][c].getBackground().equals(new Color(255,251,150)))
						 * board[r][c].setBackground(new Color(153,153,153));
						 */

						if (matrix[r][c] == 0)
							board[r][c].setBackground(new Color(150, 104, 66));// brown
						else if (matrix[r][c] == 2)
							board[r][c].setBackground(new Color(92, 255, 86));// green
						else if (matrix[r][c] == 3)
							board[r][c].setBackground(new Color(255, 8, 0));// red
						else if (matrix[r][c] == 4)
							board[r][c].setBackground(new Color(0, 127, 255));// blue
						else if (matrix[r][c] == 5)
							board[r][c].setBackground(new Color(150, 104, 66));// h brown
						else if (matrix[r][c] == 7)
							board[r][c].setBackground(new Color(150, 104, 66)); // h blue
						else if (matrix[r][c] == 8)
							board[r][c].setBackground(new Color(150, 104, 66)); // h blue
						else
							board[r][c].setBackground(new Color(150, 104, 66)); // h blue

						board[r][c].paintImmediately(0, 0, 1000, 1000);
						sleep(100);
					}
			}
		}
	}

	private class Handler2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			play.setEnabled(false);
			label.setText("You have " + turns + " turns to uncover treasure.");
			for (int c = 0; c < board.length; c++)
				for (int r = 0; r < board.length; r++) {
					matrix[r][c] = 0;
					board[r][c].setBorderPainted(false);
					board[r][c].setEnabled(true);
					board[r][c].setBackground(new Color(255, 251, 150));
					board[r][c].paintImmediately(0, 0, 1000, 1000);
					sleep(100);
					double n = Math.random();
					if (n < 1 / 27.0)
						matrix[r][c] = 4;// blue
					else if (n < 7 / 27.0)
						matrix[r][c] = 3;// red
					else if (n < 14 / 27.0)
						matrix[r][c] = 2;
					else
						matrix[r][c] = 0;
				}
		}

	}

	private void sleep(int millis) {
		do {
		} while (System.nanoTime() / 100000 % millis != 0);
	}

	public static void main(String[] args) throws Throwable {
		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		JFrame frame = new JFrame("Treasure hunt - Triple your money!");
		frame.setSize(800, 800);
		frame.setLocation(200, 100);
		frame.setContentPane(new Gambling());
		frame.setVisible(true);
	}
}
