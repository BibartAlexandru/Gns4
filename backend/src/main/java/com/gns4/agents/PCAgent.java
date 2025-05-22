package com.gns4.agents;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import com.gns4.helper.CommandLineParser;
import com.gns4.networking_messages.ARPRequest;
import com.gns4.networking_messages.DHCPDiscover;
import com.gns4.networking_messages.Frame;
import com.gns4.other.AgentInterface;
import com.gns4.other.IPv4NetworkAddress;
import com.gns4.other.Interface;
import com.gns4.other.MAC;

import de.vandermeer.asciitable.AsciiTable;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.util.Logger;


public class PCAgent extends DeviceAgent {
  public static final Logger logger = Logger.getMyLogger(PCAgent.class.getName());
	public static HashMap<String, PCAgent> agents = new HashMap<String, PCAgent>();
	private PCAgentStates state ;
  private Instant lastDHCPAquireAttempt ;
  private final int DHCPAquireAttemptCooldown = 10 ;// in seconds
	
	@Override
	protected void setup() {
		super.setup();
    CommandLineParser cmdP = new CommandLineParser();
    cmdP.start();
		agents.put(getName(), this);
		
		Object[]args = getArguments();
		String macStr = args[0].toString();	

		MAC interfMAC = null;
		try {
			interfMAC = new MAC(macStr);
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.info("Using mac 0000.0000.0000 for PC: " + getLocalName());
			try {
				interfMAC = new MAC("0000.0000.0000");
				//never fails here
			}catch(Exception en) {}
		}
		state = PCAgentStates.INTERFACE_DISCONNECTED;
		var interfaces = new ArrayList<Interface>();
		interfaces.add(new Interface(
        true,
				true,
				"eth0", 
				interfMAC,
        IPv4NetworkAddress.ZERO,
				null,
				this
				));
		setInterfaces(interfaces);
		logger.info("PC interfaces size: " + this.interfaces.size());
		
		Behaviour checkConn = new TickerBehaviour(this, 500) {
			protected void onTick() {
         logger.info("Checking connection. Status: [" + isInterfaceConnected() + "]");
        logger.info("PC state is: " + state);
				if(isInterfaceConnected() == true) {
					switch(state) {
            // We were disconnected, but now connected
						case INTERFACE_DISCONNECTED:{
              AID other = interfaces.get(0).getConnectedTo().getAgentID() ;
              String otherIname = interfaces.get(0).getConnectedTo().getInterfaceName() ;
              logger.info("Device: " + getLocalName() + " has been connected to: " + other + "/" + otherIname ) ;
              if(interfaces.get(0).getIpv4().equals(IPv4NetworkAddress.ZERO))
							  state = PCAgentStates.REQUESTING_DHCP_IP;
              else 
                state = PCAgentStates.RECEIVED_IP;
              break ;
						}
						case RECEIVED_IP:{
							//
              break ;
						}
						case REQUESTING_DHCP_IP:{
              if(
              lastDHCPAquireAttempt == null || 
              Duration.between(lastDHCPAquireAttempt, Instant.now()).getSeconds() >= DHCPAquireAttemptCooldown)
                requestAddressDHCP() ;
              break ;
						}
					}
				}
				else {
					switch(state) {
						case INTERFACE_DISCONNECTED:{
							//
              break;
						}
						case RECEIVED_IP:{
							onInterfaceDisconnect(interfaces.get(0));
							state = PCAgentStates.INTERFACE_DISCONNECTED;
              break ;
						}
						case REQUESTING_DHCP_IP:{
							onInterfaceDisconnect(interfaces.get(0));
							state = PCAgentStates.INTERFACE_DISCONNECTED;
              break ;
						}
					}
				}
			}
		};
		checkConn.setBehaviourName("checkConn");
		
		addBehaviour(checkConn);
	}

  public void connectToRouter(String router, String interf){
    logger.warning("PC attempting to connect an interface to a rounter");
    if(!router.contains("R1")
    && !router.contains("R2")
    && !router.contains("R3")
    && !router.contains("R4")){
      logger.warning("Attempting to connect to nonexistent router: " + router) ;
      return ;
    }
    RouterAgent selected = CommandLineParser.findRouter(router) ;
    if(selected == null)
    {
      logger.warning("Could not find agent: " + router+ " when attempting to connect PC to it.") ;
      return ;
    }
    if(!selected.hasEmptyInterface(interf))
    {
      // printInterface(interf);
      logger.warning("Router: " + router+ 
        " does not have an empty interface with the name: " 
        + interf + " to which the PC is trying to connect to.") ;
      return ;
    }
    AgentInterface toInterface = new AgentInterface(
      interf,
      selected.getAID() 
    ) ;
    AgentInterface pcInterface = new AgentInterface("eth0", getAID());
    interfaces.get(0).setConnectedTo(toInterface);
    selected.connectTo(interf,pcInterface);
  }

  public void disconnectInterface(){
    interfaces.clear();
  }



  public void requestAddressDHCP(){
    if(interfaces.size() == 0){
      logger.info("Attempting to request an IP via DHCP, but device: " + getLocalName() + " has no interfaces." );
    }
    if(interfaces.get(0).getStatusPhysical() == false)
    {
      logger.info("Attempting to request an IP via DHCP, but device: " + getLocalName() + " has a damaged interface.");
    }
    if(interfaces.get(0).getStatusLogical() == false)
    {
        System.out.
        println("Attempting to request an IP via DHCP, but device: " + getLocalName() 
          + " has an administratively shutdown interface.");
    }
    Frame f = buildDHCPDiscoverFrame(interfaces.get(0));
    sendByteSerializable(f, interfaces.get(0));
    lastDHCPAquireAttempt = Instant.now() ;
    
    // Logging
    AsciiTable table = new AsciiTable();
    table.addRule();
    table.addRow("Message", "Source");
    table.addRule();
    table.addRow("DHCPDiscover", getLocalName()) ;
    table.addRule();
    System.out.println("PC sent a frame:");
    System.out.println(table.render());
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
