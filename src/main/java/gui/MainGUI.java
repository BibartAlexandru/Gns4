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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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
	
	private boolean isDraggingPC = false ;
	private boolean showInterfaces = false ;
	private Node interfacesModal = null;

	
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
		view.getStyleClass().add("pc-img-view");
		
		view.setOnMousePressed(e -> {
			x = (float) (e.getSceneX() - view.getLayoutX());
			y = (float) (e.getSceneY() - view.getLayoutY());
		});
		
		
		view.setOnMouseDragged(e -> {
			view.setLayoutX(e.getSceneX() - x);
			view.setLayoutY(e.getSceneY() - y);
		});
		
		view.setOnMouseClicked(e -> {
			Node interfacesModal = createInterfacesModal(manager.getDeviceAgentInterfaces(thisPc), thisPc);
			// If modal already exists don't add it anymore. Was flickering that's why I added this
			if(isDraggingPC || 
					(p.getChildren().stream().anyMatch(child -> child.getId() != null && child.getId().equals("InterfacesModal")))
					)
				return;
			interfacesModal.setLayoutX(e.getSceneX() - x);
			interfacesModal.setLayoutY(e.getSceneY() - y + 100);
			p.getChildren().add(interfacesModal);
			this.interfacesModal = interfacesModal;
		});
		p.getChildren().add(view);
	};
	
	public static Node createInterfacesModal(ArrayList<Interface> interfaces, String pcAgent) {
		VBox col = new VBox();
		col.setMinWidth(100);
		col.setBackground(new Background(
			    new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)
				));
		col.getStyleClass().add("interfaces-modal");
		col.getChildren().add(new Text("Interfaces"));
		col.setSpacing(20);
		col.setPadding(new Insets(10,10,10,10));
		col.setBorder(new Border(new BorderStroke(
				Color.BLACK,
				BorderStrokeStyle.SOLID,
				CornerRadii.EMPTY,
				new BorderWidths(1)
				)));
		
		for(var interf : interfaces) {
			HBox row = new HBox();
			row.getStyleClass().add("interface-row");
			row.setMinWidth(150);
			row.setMinHeight(20);
			row.setPadding(new Insets(10, 10, 10, 10));
			
			var intNameText = new Text(interf.getName());
			Region space = new Region();
			row.setHgrow(space, Priority.ALWAYS);
			var onLight = interf.getGui().getOnLight();
			row.getChildren().addAll(intNameText, space, onLight);
			col.getChildren().add(row);
		}
		col.setId("InterfacesModal");
		col.setLayoutX(x);
		col.setLayoutY(y);
		return col;
	}
	
	/**
	 * Tests style class with contains()
	 * @param n
	 * @param styleClass
	 * @return
	 */
	public boolean isDescendantOf(Node n, String styleClass) {
		if(n.getParent() == null)
			return false;
		if(n.getStyleClass().contains(styleClass))
			return true ;
		return isDescendantOf(n.getParent(), styleClass);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Button addPcBtn = new Button("Add PC");
		Button addRouterBtn = new Button("Add Router");
		Button connectBtn = new Button("Connect Devices");
		
		BorderPane root = new BorderPane();
		
		addPcBtn.setOnAction(onAddPc);
		
		HBox row = new HBox(10);
		row.getChildren().addAll(addPcBtn, addRouterBtn);
		root.setTop(row);
		root.setCenter(p);
		
		Scene scene = new Scene(root, sceneX, sceneY);
		scene.setOnMouseClicked(e -> {
		    Node t = (Node) e.getTarget();
			// deletes interface modal if exists in p
		    System.out.println(t);
			if(!isDescendantOf(t, "interfaces-modal")
					&& !t.getStyleClass().contains("pc-img-view")
					)
				p.getChildren().removeIf(child -> child.getId() != null && child.getId().equals("InterfacesModal"));
		});
		
		
		
		scene.getStylesheets().add(cssFile);
		primaryStage.setTitle("GNS4");
		primaryStage.setScene(scene);
		primaryStage.show();
	
	}


}
