package com.gns4.agents;

import java.util.ArrayList;
import java.util.HashMap;

import com.gns4.helper.Helper;
import com.gns4.networking_messages.Frame;
import com.gns4.networking_messages.decoders.FrameDecoder;
import com.gns4.other.IPv4NetworkAddress;
import com.gns4.other.Interface;
import com.gns4.other.MAC;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class RouterAgent extends DeviceAgent{

	public static HashMap<String, RouterAgent> agents = new HashMap<String, RouterAgent>();
	public RouterAgent() {
		super();
	}

  @Override
  protected void setup() {
      super.setup();
      agents.put(getName(), this) ;
    var interfaces = new ArrayList<Interface>();
    for(int i = 0 ; i < 4 ; i++){
      interfaces.add(new Interface(
          true,
          true,
          "g0/" + String.valueOf(i) , 
          MAC.getRandom(),
          IPv4NetworkAddress.ZERO,
          null,
          this
          ));
    }
		setInterfaces(interfaces);
    addBehaviour(new CyclicBehaviour(this) {
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                System.out.println("Router " + getLocalName() + " received a message");
                System.out.println(msg.getContent());
                try{
                  FrameDecoder fDec = new FrameDecoder() ;
                  Frame f = fDec.decode(Helper.toByteArrList(msg.getByteSequenceContent())); 
                  String receivedInterfaceName = msg.getContent();
                  f = parseFrame(f, receivedInterfaceName);
                  // Frame dropped
                  // if(f == null)
                    //return ;
                  System.out.println("Router " + getLocalName() + " received a frame:"); 
                  System.out.println(f.prettyPrint()) ;
                }    
                catch(Exception e){
                  e.printStackTrace();
                }
            } else {
                block();
            }
        }
    });
  }



}
