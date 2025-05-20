package com.gns4.agents;

import java.util.HashMap;

public class RouterAgent extends DeviceAgent{

	public static HashMap<String, RouterAgent> agents = new HashMap<String, RouterAgent>();
	public RouterAgent() {
		super();
	}

  @Override
  protected void setup() {
      super.setup();
      agents.put(getName(), this) ;
  }

}
