package main.java.other;


import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import main.java.agents.DeviceAgent;

public class Interface {
	
	public Interface(boolean statusPhysical, boolean statusLogical, String name, MAC mac, IPv4NetworkAddress ipv4,
			AgentInterface connectedTo, DeviceAgent agent) {
		super();
		this.statusPhysical = statusPhysical;
		this.statusLogical = statusLogical;
		this.name = name;
		this.mac = mac;
		this.ipv4 = ipv4;
		this.connectedTo = connectedTo;
		this.agent = agent;
	}

	private boolean statusPhysical;
	private boolean statusLogical;
	private String name;
	private MAC mac;
	private IPv4NetworkAddress ipv4;
	private AgentInterface connectedTo;
	private DeviceAgent agent;
	
	public boolean isStatusPhysical() {
		return statusPhysical;
	}



	public void setStatusPhysical(boolean statusPhysical) {
		this.statusPhysical = statusPhysical;
	}



	public boolean isStatusLogical() {
		return statusLogical;
	}



	public void setStatusLogical(boolean statusLogical) {
		this.statusLogical = statusLogical;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public MAC getMac() {
		return mac;
	}



	public void setMac(MAC mac) {
		this.mac = mac;
	}



	public IPv4NetworkAddress getIpv4() {
		return ipv4;
	}



	public void setIpv4(IPv4NetworkAddress ipv4) {
		this.ipv4 = ipv4;
	}



	public AgentInterface getConnectedTo() {
		return connectedTo;
	}



	public void setConnectedTo(AgentInterface connectedTo) {
		this.connectedTo = connectedTo;
	}



	public DeviceAgent getAgent() {
		return agent;
	}



	public void setAgent(DeviceAgent agent) {
		this.agent = agent;
	}
	
	/**
	 * Makes the agent send an ACL Inform message with the byte array.
	 * Should not be used directly. Call the sendSomething functions in the DeviceAgent instead.
	 * @throws Exception 
	 */
	private void send(byte[] msg) throws Exception {
		if(connectedTo == null)
			throw new Exception("Attempting to send frame on Interface " +  name  +  "which is not connected to anything.");
		ACLMessage agentMsg = new ACLMessage(ACLMessage.INFORM);
		agentMsg.setContent(msg.toString());
		agentMsg.addReceiver(connectedTo.getAgentID());
		agent.send(agentMsg);
	}
}
