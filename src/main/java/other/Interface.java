package networking.other;

import java.net.InetAddress;

import agents.DeviceAgent;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class Interface {
	private boolean statusPhysical;
	private boolean statusLogical;
	private String name;
	private String mac;
	private InetAddress ipv4;
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



	public String getMac() {
		return mac;
	}



	public void setMac(String mac) {
		this.mac = mac;
	}



	public InetAddress getIpv4() {
		return ipv4;
	}



	public void setIpv4(InetAddress ipv4) {
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
	
	public Interface(boolean statusPhysical, boolean statusLogical, String name, String mac, InetAddress ipv4,
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
	
	/**
	 * Makes the agent send an ACL Inform message with the byte array.
	 * Should not be used directly. Call the sendSomething functions in the DeviceAgent instead.
	 */
	private void send(byte[] msg) {
		if(connectedTo == null)
			throw new Exception("Attempting to send frame on Interface " +  name  +  "which is not connected to anything.");
		ACLMessage agentMsg = new ACLMessage(ACLMessage.INFORM);
		agentMsg.setContent(msg.toString());
		agentMsg.addReceiver(connectedTo.getAgentID());
		agent.send(agentMsg);
	}
}
