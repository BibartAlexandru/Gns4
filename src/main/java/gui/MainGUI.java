package main.java.gui;

import java.util.ArrayList;
import main.java.other.Interface;
import jade.core.Agent;
import jade.wrapper.AgentContainer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.agents.ManagerAgent;
import main.java.agents.PCAgent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;

public class MainGUI extends Application{
	
	private static ManagerAgent manager;
	private static String upInterfaceImgSrc;
	private static String downInterfaceImgSrc; 
	private static String pcImgSrc;
	private static String cssFile;
	
	public MainGUI() {
		super();
		downInterfaceImgSrc = getClass().getResource("/off.png").toExternalForm();
		upInterfaceImgSrc = getClass().getResource("/on.png").toExternalForm();
		pcImgSrc = getClass().getResource("/PC.png").toExternalForm();
		cssFile = getClass().getResource("/index.css").toExternalForm();
	}
	
	public static void setManager(ManagerAgent manager) {
		MainGUI.manager = manager;
	}
	
	static float x = 0;
	static float y = 0;
	float sceneX = 500;
	float sceneY = 500;
	Pane p = new Pane();
	
	public EventHandler<ActionEvent> onAddPc = event -> {
		String thisPc = manager.createPcAgent();
		if(thisPc == null)
			return;
		
		Image pcImg = new Image(pcImgSrc);
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
		
		// Show interfaces
		view.setOnMouseEntered(e -> {
			Node interfacesModal = createInterfacesModal(manager.getDeviceAgentInterfaces(thisPc));
			// If modal already exists don't add it anymore. Was flickering that's why I added this
			if(p.getChildren().stream().anyMatch(child -> child.getId() != null && child.getId().equals("InterfacesModal")))
				return;
			p.getChildren().add(interfacesModal);
		});
		
		// Delete the Node that was the shown Interface modal
		view.setOnMouseExited(e -> {
			p.getChildren().removeIf(child -> child.getId() != null && child.getId().equals("InterfacesModal"));
		});
		
		p.getChildren().add(view);
	};
	
	public static Node createInterfacesModal(ArrayList<Interface> interfaces) {
		VBox col = new VBox();
		col.setMinWidth(300);
		col.setBackground(new Background(
			    new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)
				));
		
		col.getStyleClass().add("interfaces-modal");
		col.getChildren().add(new Text("Interfaces"));
		for(var interf : interfaces) {
			HBox row = new HBox();
			row.getStyleClass().add("interface-row");
			row.getChildren().add(new Text(interf.getName()));
			Image intStatus = new Image(downInterfaceImgSrc);
			var view = new ImageView(intStatus);
			view.getStyleClass().add("interface-status-image");
			view.setFitWidth(10);
			view.setFitHeight(10);
			row.getChildren().add(view);
			col.getChildren().add(row);
		}
		col.setId("InterfacesModal");
		col.setLayoutX(x);
		col.setLayoutY(y);
		return col;
	}
	
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
		scene.getStylesheets().add(cssFile);
		primaryStage.setTitle("GNS4");
		primaryStage.setScene(scene);
		primaryStage.show();
	
	}


}
