package com.gns4.agents;

import java.util.ArrayList;
import java.util.HashMap;

import com.gns4.other.IPv4NetworkAddress;
import com.gns4.other.Interface;
import com.gns4.other.MAC;

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

  }

}
