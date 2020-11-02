package sample;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static sample.Main.*;

public class Controller {
	@FXML
	Text turnText, whitePlayer, blackPlayer;
	@FXML
	GridPane checkerBoard;
	private static final StackPane[][] grid = new StackPane[10][10];
	private Rectangle[][] rectangles = new Rectangle[boardSize][boardSize];


	private static void addRectangle(byte row, byte col, Color color) {
		byte strokeWidth = CELL_DIMENSION/15;
		Rectangle rectangle = new Rectangle(CELL_DIMENSION-strokeWidth-2,
				CELL_DIMENSION-strokeWidth-2,
				Color.BROWN);
		rectangle.setStroke(color);
		rectangle.setStrokeWidth(strokeWidth);
	//	Node prev = grid[row][col].getChildren().get(0);
		grid[row][col].getChildren().add(rectangle);
	//	grid[row][col].getChildren().add(prev);




	}




	private void displayState() {
		turnText.setText(stateName[turn] + "'s turn");



		for (byte row = 0; row < boardSize; row++) {
			for (byte col = 0; col < boardSize; col++) {

				ObservableList<Node> children = grid[row][col].getChildren();
				if (children.size() > 1) children.remove(1, children.size());


				if (selectedRow!=-1 && selectedCol!=-1) {
					boolean[][] validTo = Main.destinations();

					if (validTo[row][col]) {
						addRectangle(row, col, Color.YELLOW);
					}
					if (selectedRow==row && selectedCol==col && states[row][col] != NONE) {

						if(states[row][col]==WHITE)
							addRectangle(row, col, Color.BLACK);
						else
							addRectangle(row, col, Color.WHITE);
					}

				}


				if (states[row][col] != NONE) {
					children.add(new ImageView(stateImage[states[row][col]]));
				}

			}
		}

	}

	@FXML
	public void initialize() {
		for (byte col = 0; col < boardSize; col++) {
			for (byte row = 0; row < boardSize; row++) {
				final byte _col = col, _row = row;
				grid[row][col] = new StackPane(
						new Rectangle(60, 60, ((row+col)&1) != 0 ? Color.BROWN : Color.CHOCOLATE));

				grid[row][col].setOnMouseClicked(event -> Main.click(_row, _col));

			}
		}
		for (byte row = 0; row < boardSize; row++) {
			for (byte col = 0; col < boardSize; col++) {
				states[row][col] = (row==0 || row== boardSize -1) && !(col==0 || col== boardSize -1) ? BLACK :
						(col==0 || col== boardSize -1) && !(row==0 || row== boardSize -1) ? WHITE :
								NONE;
				checkerBoard.add(grid[row][col], col, row);
			}
		}
		whitePlayer.setText(stateIsHuman[WHITE] ? "Human" : "Computer");
		blackPlayer.setText(stateIsHuman[BLACK] ? "Human" : "Computer");
		new Thread(()->{
			while (true) {
				Platform.runLater(this::displayState);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}


}
