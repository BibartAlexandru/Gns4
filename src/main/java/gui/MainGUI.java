package main.java.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;

public class MainGUI extends Application{

	float x = 0;
	float y = 0;
	float sceneX = 500;
	float sceneY = 500;
	Pane p = new Pane();
	
	public EventHandler<ActionEvent> onAddPc = event -> {
		Image pcImg = new Image(getClass().getResource("/PC.png").toExternalForm());
		ImageView view = new ImageView(pcImg);
		view.setFitWidth(100);
		view.setFitHeight(100);
		view.setPreserveRatio(true);
		
		
		view.setOnMousePressed(e -> {
			x = (float) (e.getSceneX() - view.getLayoutX());
			y = (float) (e.getSceneY() - view.getLayoutY());
		});
		
		view.setOnMouseDragged(e -> {
			view.setLayoutX(e.getSceneX() - x);
			view.setLayoutY(e.getSceneY() - y);
		});
		
		p.getChildren().add(view);
	};
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Button addPcBtn = new Button("Add PC");
		Button addRouterBtn = new Button("Add Router");
		
		BorderPane root = new BorderPane();
		
		addPcBtn.setOnAction(onAddPc);
		
		HBox row = new HBox(10);
		row.getChildren().addAll(addPcBtn, addRouterBtn);
		root.setTop(row);
		root.setCenter(p);
		
		Scene scene = new Scene(root, sceneX, sceneY);
		
		primaryStage.setTitle("GNS4");
		primaryStage.setScene(scene);
		primaryStage.show();
	
	}


}
