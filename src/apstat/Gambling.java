package apstat;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gambling extends JPanel {
	private static final int SIZE = 10;
	private JButton[][] board = new JButton[SIZE][SIZE];
	private JLabel label;
	private JButton play;

	private int[][] matrix = new int[SIZE][SIZE];
	// initialize in play:
	private int turns, winnings;

	public Gambling() {
		setLayout(new BorderLayout());
		JPanel north = new JPanel();
		add(north, BorderLayout.NORTH);
		label = new JLabel("Pay 4 then click \"play\" to generate treasure.");
		north.add(label);

		JPanel center = new JPanel();
		center.setLayout(new GridLayout(SIZE, SIZE));
		add(center, BorderLayout.CENTER);
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				matrix[r][c] = 0;
				board[r][c] = new JButton();
				board[r][c].setBackground(Color.GRAY);
				int row = r, col = c; // capture
				board[r][c].addActionListener(e -> clicked(row, col));
				board[r][c].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
				board[r][c].setBorderPainted(false);
				board[r][c].setEnabled(false);
				center.add(board[r][c]);
			}
		}

		play = new JButton("Play");
		play.addActionListener(this::play);
		add(play, BorderLayout.SOUTH);
	}

	private void clicked(int r, int c) {
		board[r][c].setEnabled(false);
		winnings += matrix[r][c];
		board[r][c].setBackground(colorFor[matrix[r][c]]);
		board[r][c].paintImmediately(0, 0, 1000, 1000);
		label.setText("You have " + --turns + " turn" + (turns == 1 ? "" : "s") + " to uncover treasure.");
		board[r][c].setBorderPainted(true);
		if (turns == 0)
			endGame();
	}

	private void endGame() {
		label.setText("You won " + winnings + " dollars! To generate more treasure, pay 4 and click \"play\"");
		play.setEnabled(true);
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				board[r][c].setEnabled(false);
				board[r][c].setBackground(colorFor[matrix[r][c]]);
				board[r][c].paintImmediately(0, 0, 1000, 1000);
				sleep();
			}
		}
	}

	private void play(ActionEvent e) {
		play.setEnabled(false);
		winnings = 0;
		label.setText("You have " + (turns = 3) + " turns to uncover treasure.");
		for (int c = 0; c < SIZE; c++) {
			for (int r = 0; r < SIZE; r++) {
				matrix[r][c] = 0;
				board[r][c].setBorderPainted(false);
				board[r][c].setEnabled(true);
				board[r][c].setBackground(UNREVEALED);
				board[r][c].paintImmediately(0, 0, 1000, 1000);
				sleep();
				double n = Math.random() * 28;
				if (n < 1)
					matrix[r][c] = 4;
				else if (n < 7)
					matrix[r][c] = 3;
				else if (n < 14)
					matrix[r][c] = 2;
				else
					matrix[r][c] = 0;
			}
		}
	}

	private void sleep() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Colors

	private static final Color UNREVEALED = new Color(255, 251, 150);

	private static final Color BROWN = new Color(150, 104, 66);
	private static final Color GREEN = new Color(92, 255, 86);
	private static final Color RED = new Color(255, 8, 0);
	private static final Color BLUE = new Color(0, 127, 255);

	private static final Color[] colorFor = { BROWN, null, GREEN, RED, BLUE };
	
	public static void main(String[] args) throws Throwable {
		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		JFrame frame = new JFrame("Treasure hunt - Triple your money!");
		frame.setSize(800, 800);
		frame.setLocation(200, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Gambling());
		frame.setVisible(true);
	}
}
