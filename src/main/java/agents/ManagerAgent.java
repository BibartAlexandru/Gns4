package main.java.agents;

import jade.core.Agent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import main.java.gui.MainGUI;

public class ManagerAgent extends Agent {

    @Override
    protected void setup() {
        if (Platform.isFxApplicationThread()) {
            setupGUI();
        } else {
            Thread javaFxThread = new Thread(() -> {
                Application.launch(MainGUI.class);
            });
            javaFxThread.setDaemon(true); 
            javaFxThread.start();
        }
    }

    private void setupGUI() {
    	try {
    		new MainGUI().start(new Stage());
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
