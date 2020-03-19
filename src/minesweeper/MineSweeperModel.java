package minesweeper;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class MineSweeperModel {
	
	protected Stage primaryStage;
	protected StackButton[][] grid;
	
	
	protected MineSweeperModel(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}


	public void reload() {//methode works LS

		grid = new StackButton[MineSweeperView.gridSize][MineSweeperView.gridSize];

		MineSweeperView.secondsPassed = 0;

		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				MineSweeperView.secondsPassed++;
			};
		};
		MineSweeperView.timer.cancel();
		MineSweeperView.timer = new Timer();
		MineSweeperView.timer.schedule(task, 1000, 1000);

		MineSweeperView.root.getChildren().remove(1);
		MineSweeperView.root.getChildren().add(createContent());
		primaryStage.sizeToScene();
	}

	protected Parent createContent() { //Methode doesnt work LS da esch de fehler ergend wo
		// Reset in case of a new game
		MineSweeperView.numBombs = 0;
		MineSweeperView.foundBombs = 0;

		Pane secondroot = new Pane();
		secondroot.setPrefSize(MineSweeperView.gridSize * 40, MineSweeperView.gridSize * 40);

		// Create the StackButton and Bombs on percentage

		for (int i = 0; i < MineSweeperView.gridSize; i++) {
			for (int j = 0; j < MineSweeperView.gridSize; j++) {

				StackButton stackButton = new StackButton(j, i, Math.random() < (double) MineSweeperView.bombPercent);
				grid[j][i] = stackButton;
				secondroot.getChildren().add(stackButton);

			}
		}
		// add the value under the buttons and the color

		for (int i = 0; i < MineSweeperView.gridSize; i++) {
			for (int j = 0; j < MineSweeperView.gridSize; j++) {

				int bombNeighbours = 0;

				ArrayList<StackButton> neighbours = new ArrayList<StackButton>();

				int[] locs = new int[] { -1, -1, -1, 0, -1, 1, 0, -1, 0, 1, 1, -1, 1, 0, 1, 1 };

				for (int l = 0; l < locs.length; l++) {
					int dx = locs[l];
					int dy = locs[++l];

					int newX = dx + j;
					int newY = dy + i;

					if (newX >= 0 && newX < MineSweeperView.gridSize && newY >= 0 && newY < MineSweeperView.gridSize) {
						neighbours.add(grid[newX][newY]);
						if (grid[newX][newY].hasBomb) {
							bombNeighbours++;
						}
					}
				}

				grid[j][i].numBombs = bombNeighbours;
				grid[j][i].neighbours = neighbours;

				Color[] colors = { null, Color.BLUE, Color.GREEN, Color.RED, Color.DARKBLUE, Color.DARKRED, Color.CYAN,
						Color.BLACK, Color.DARKGRAY };

				grid[j][i].color = colors[grid[j][i].numBombs];
			}
		}
		return secondroot;

	}


	public HBox createContenttt() {//testmethod works LS
		
		HBox roott = new HBox();
		roott.getChildren().add(new Button("Hallo"));
		
		return roott;
	}

}
