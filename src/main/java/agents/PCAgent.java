package main.java.agents;

import java.util.ArrayList;

import main.java.other.IPv4NetworkAddress;
import main.java.other.Interface;
import main.java.other.MAC;

public class PCAgent extends DeviceAgent {

	public PCAgent(String macStr) throws Exception {
		super();
		
		boolean intStatus = true ;
		
		var interfaces = new ArrayList<Interface>();
		interfaces.add(new Interface(
				intStatus,
				intStatus,
				"eth0", 
				new MAC(macStr),
				IPv4NetworkAddress.ZERO,
				null,
				this
				));
		setInterfaces(interfaces);
		
	}

}
