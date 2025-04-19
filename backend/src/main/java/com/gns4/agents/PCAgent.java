package com.gns4.agents;

import java.util.ArrayList;
import java.util.HashMap;

import com.gns4.other.IPv4NetworkAddress;
import com.gns4.other.Interface;
import com.gns4.other.MAC;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;


public class PCAgent extends DeviceAgent {
	private static HashMap<String, PCAgent> agents = new HashMap<String, PCAgent>();
	
	private PCAgentStates state ;
	
	@Override
	protected void setup() {
		super.setup();
		agents.put(getName(), this);
		
		Object[]args = getArguments();
		String macStr = args[0].toString();	

		boolean intStatus = true ;
		MAC interfMAC = null;
		try {
			interfMAC = new MAC(macStr);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Using mac 0000.0000.0000 for PC: " + getLocalName());
			try {
				interfMAC = new MAC("0000.0000.0000");
				//never fails here
			}catch(Exception en) {}
		}
		state = PCAgentStates.INTERFACE_DISCONNECTED;
		var interfaces = new ArrayList<Interface>();
		interfaces.add(new Interface(
				intStatus,
				true,
				"eth0", 
				interfMAC,
				IPv4NetworkAddress.ZERO,
				null,
				this
				));
		setInterfaces(interfaces);
		System.out.println("PC interfaces size: " + this.interfaces.size());
		
		Behaviour checkConn = new TickerBehaviour(this, 500) {
			protected void onTick() {
				if(isInterfaceConnected()) {
					switch(state) {
						case INTERFACE_DISCONNECTED:{
							state = PCAgentStates.REQUESTING_DHCP_IP;
						}
						case RECEIVED_IP:{
							//
						}
						case REQUESTING_DHCP_IP:{
							//
						}
					}
				}
				else {
					switch(state) {
						case INTERFACE_DISCONNECTED:{
							//
						}
						case RECEIVED_IP:{
							onInterfaceDisconnect(interfaces.get(0));
							state = PCAgentStates.INTERFACE_DISCONNECTED;
						}
						case REQUESTING_DHCP_IP:{
							onInterfaceDisconnect(interfaces.get(0));
							state = PCAgentStates.INTERFACE_DISCONNECTED;
						}
					}
				}
			}
		};
		checkConn.setBehaviourName("checkConn");
		
		addBehaviour(checkConn);
	}
	
	public static PCAgent getPCByAgentName(String name) {
		if(agents.containsKey(name))
			return agents.get(name);
		return null;
	}
	
	public boolean isInterfaceConnected() {
		return interfaces.get(0).getConnectedTo() != null;
	}
	
}
