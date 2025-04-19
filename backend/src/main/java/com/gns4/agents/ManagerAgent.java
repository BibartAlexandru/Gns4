package com.gns4.agents;

import java.util.ArrayList;

import com.gns4.other.Interface;

import jade.core.AID;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class ManagerAgent extends Agent {

	private AgentContainer thisContainer ;
	private ArrayList<String> agents;
	
    @Override
    protected void setup() {
    	agents = new ArrayList<>();
    	
    	// Attempting to fetch the agent container
    	ProfileImpl p = new ProfileImpl();
    	p.setParameter(Profile.MAIN_HOST, "localhost");
    	p.setParameter(Profile.MAIN_PORT, "1099");
    	p.setParameter(Profile.MAIN, "false");
    	thisContainer = Runtime.instance().createAgentContainer(p);
    	
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
}
