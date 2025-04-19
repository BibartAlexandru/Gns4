package main.java.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.agents.DeviceAgent;
import main.java.agents.PCAgent;
import main.java.other.Interface;

public class InterfacePhysicalOnLight extends ImageView{
	
	private final Image on = new Image(getClass().getResource("/on.png").toExternalForm());
	private final Image off = new Image(getClass().getResource("/off.png").toExternalForm());

	public InterfacePhysicalOnLight(boolean isOn, DeviceAgent dev, Interface interf) {
		setFitHeight(30);
		setFitWidth(30);
		updateImage(isOn);
		
		setOnMouseClicked(e -> {
			dev.toggleInterfacePhysicalStatus(interf);
		});
	}
	
	public void updateImage(boolean newStatus) {
		if(newStatus)
			setImage(on);
		else
			setImage(off);
	}
}
