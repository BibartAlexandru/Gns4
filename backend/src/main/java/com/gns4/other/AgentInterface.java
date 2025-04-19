package com.gns4.other;

import FIPA.AgentID;
import jade.core.AID;

public class AgentInterface {
	private String interfaceName;
	private AID agentID;
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public AID getAgentID() {
		return agentID;
	}
	public void setAgentID(AID agentID) {
		this.agentID = agentID;
	}
	
}
