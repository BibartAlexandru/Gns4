package com.gns4.agents;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import com.gns4.networking_messages.ARPRequest;
import com.gns4.networking_messages.DHCPDiscover;
import com.gns4.other.IPv4NetworkAddress;
import com.gns4.other.Interface;
import com.gns4.other.MAC;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;


public class PCAgent extends DeviceAgent {
	private static HashMap<String, PCAgent> agents = new HashMap<String, PCAgent>();
	private PCAgentStates state ;
  private Instant lastDHCPAquireAttempt ;
  private final int DHCPAquireAttemptCooldown = 10 ;// in seconds
	
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
      null,
				null,
				this
				));
		setInterfaces(interfaces);
		System.out.println("PC interfaces size: " + this.interfaces.size());
		
		Behaviour checkConn = new TickerBehaviour(this, 500) {
			protected void onTick() {
				if(isInterfaceConnected()) {
					switch(state) {
            // We were disconnected, but now connected
						case INTERFACE_DISCONNECTED:{
              if(interfaces.get(0).getIpv4() == null)
							  state = PCAgentStates.REQUESTING_DHCP_IP;
              else 
                state = PCAgentStates.RECEIVED_IP;
						}
						case RECEIVED_IP:{
							//
						}
						case REQUESTING_DHCP_IP:{
              if(Duration.between(lastDHCPAquireAttempt, Instant.now()).getSeconds() >= DHCPAquireAttemptCooldown)
                requestAddressDHCP() ;
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
	
  public void requestAddressDHCP(){
    if(interfaces.size() == 0){
      //TODO: Log this
    }
    if(interfaces.get(0).getStatusPhysical() == false)
  {
      //TODO: Log this
    }
    if(interfaces.get(0).getStatusLogical() == false)
{
      //TODO: Log this
    }
    var dhcpDiscover = new DHCPDiscover() ;
    sendByteSerializable(dhcpDiscover, interfaces.get(0));
    lastDHCPAquireAttempt = Instant.now() ;
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
