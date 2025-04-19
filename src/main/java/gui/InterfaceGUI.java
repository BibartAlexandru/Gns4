package main.java.gui;

import main.java.agents.DeviceAgent;
import main.java.agents.PCAgent;
import main.java.other.Interface;

public class InterfaceGUI {
	private InterfacePhysicalOnLight onLight ;
	
	public InterfacePhysicalOnLight getOnLight() {
		return onLight;
	}

	public InterfaceGUI(Boolean isPyhisicalOn, DeviceAgent dev, Interface interf) {
		onLight = new InterfacePhysicalOnLight(isPyhisicalOn, dev, interf);
	}
	
	public void updateOnLight(boolean newStatus) {
		onLight.updateImage(newStatus);
	}
}
