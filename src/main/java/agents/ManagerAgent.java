package main.java.agents;

import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import main.java.gui.MainGUI;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import main.java.other.Interface;

public class ManagerAgent extends Agent {

	private AgentContainer thisContainer ;
	private ArrayList<String> agents;
	
    @Override
    protected void setup() {
    	MainGUI.setManager(this);
    	agents = new ArrayList<>();
    	
    	// Attempting to fetch the agent container
    	ProfileImpl p = new ProfileImpl();
    	p.setParameter(Profile.MAIN_HOST, "localhost");
    	p.setParameter(Profile.MAIN_PORT, "1099");
    	p.setParameter(Profile.MAIN, "false");
    	thisContainer = Runtime.instance().createAgentContainer(p);
    	
    	// Starting the GUI
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
    
    /**
     * @return name of the createdAgent
     */
    public String createPcAgent() {
    	try {
    		String name = "PC" + agents.size();
    		AgentController newGuy = thisContainer.createNewAgent(name, "main.java.agents.PCAgent", new String[] {"0000.0000.0001"});
    		newGuy.start();
    		agents.add(newGuy.getName());
    		return newGuy.getName();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		agents.remove(agents.size()-1);
    	}
    	return null;
	}
    
    public ArrayList<Interface> getDeviceAgentInterfaces(String name){
    	if(agents.indexOf(name) == -1) {
    		System.out.println("Agent " + name + " was not found by ManagerAgent");
    		return null;
    	}
    	try {
    		var pc = PCAgent.getPCByAgentName(name);
    		if(pc == null) {
    			System.out.println("PCAgent.getPCByAgent(" + name + ") returned null");
    			return null;
    		}
    		var interfaces = pc.getInterfaces();
    		if(interfaces == null)
    			System.out.println("pc.getInterfaces() returnes null");
    		return interfaces;
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		return null;
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
