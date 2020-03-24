package minesweeper;

import java.text.DecimalFormat;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MineSweeperController {

	protected MineSweeperModel model;
	protected StackButton stackButton;
	protected MineSweeperView view;
	protected Stage stage;

	protected MineSweeperController(MineSweeperModel model, MineSweeperView view, Stage stage) {
		this.model = model;
		this.view = view;
		this.stage = stage;

		// MenuBar ActionEvents
		view.aboutItem.setOnAction(e -> {
			Alert aboutAlert = new Alert(AlertType.INFORMATION,
					"Created by Robin Roth, Robin Weis \nand Luca Sch�dler\n\n" + "version 0.1", ButtonType.CLOSE);
			aboutAlert.setTitle("About");
			aboutAlert.setHeaderText("MineSweeper");
			aboutAlert.showAndWait();
		});

		view.helpItem.setOnAction(e -> {
			Alert helpAlert = new Alert(AlertType.INFORMATION,
					"The aim of minesweeper is to identify all the sqaures which contain mines.\n\n"
							+ "Left click on a square to reveal a number. This number indicates how many of the adjacent squares contain mines. By using these numbers you can deduce which sqaures contain mines. \n\n"
							+ "Right click on a square to mark it as containing a mine. You can right click the sqaure again to unmark it if you made a mistake.\n\n"
							+ "After all mines have successfully been marked the game is over and you win! Be careful though. Left clicking a square with a mine will result in a game over.",
					ButtonType.CLOSE);
			helpAlert.setTitle("Help");
			helpAlert.setHeaderText("How to play");
			helpAlert.showAndWait();
		});

		view.quitItem.setOnAction(e -> {
			Platform.exit();
			System.exit(0);
		});

		view.smallSizeItem.setOnAction(e -> {
			MineSweeperView.gridSize = 10;
			MineSweeperModel.reload();
		});

		view.mediumSizeItem.setOnAction(e -> {
			MineSweeperView.gridSize = 15;
			MineSweeperModel.reload();
		});

		view.largeSizeItem.setOnAction(e -> {
			MineSweeperView.gridSize = 20;
			MineSweeperModel.reload();
		});

		view.easyItem.setOnAction(e -> {
			MineSweeperView.bombPercent = 10;
			MineSweeperModel.reload();
		});

		view.normalItem.setOnAction(e -> {
			MineSweeperView.bombPercent = 15;
			MineSweeperModel.reload();
		});

		view.hardItem.setOnAction(e -> {
			MineSweeperView.bombPercent = 20;
			MineSweeperModel.reload();
		});

		view.soundOnItem.setOnAction(e -> {
			MineSweeperView.sound = true;
		});

		view.soundOffItem.setOnAction(e -> {
			MineSweeperView.sound = false;
		});

		// setOnCloseRequest
		this.view.getStage().setOnCloseRequest(e -> {

			// new stage
			Stage newStage = new Stage();
			VBox root = new VBox();

			Label lbl = new Label("Sure you want to quit?");
			Button yes = new Button("Yes");
			Button no = new Button("No");

			HBox hbox = new HBox();

			// fill stage
			hbox.getChildren().addAll(yes, no);
			root.getChildren().addAll(lbl, hbox);

			// layout
			hbox.setSpacing(10);
			hbox.setAlignment(Pos.CENTER);

			root.setAlignment(Pos.CENTER);
			root.setSpacing(10);

			// stage to scene
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/resources/CloseRequest.css").toExternalForm());
			newStage.setScene(scene);
			newStage.getIcons().add(MineSweeperView.mine);
			newStage.show();

			// Button action
			yes.setOnAction(event -> {
				Platform.exit();
				System.exit(0);
			});
			no.setOnAction(evente -> {
				stage.show();
				newStage.close();
			});

		});

		
		view.getSecondsPassedProperty().addListener((observable, oldValue, newValue) -> {
			DecimalFormat fmtt = new DecimalFormat("#0");
			DecimalFormat fmt = new DecimalFormat("00");
			String newText = (fmtt.format(MineSweeperView.minutesPassedObj.get()) + ":" + fmt.format(MineSweeperView.secondsPassedObj.get()));
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					view.timeField.setText(newText);
				}
			});
		});
	}
}
