package com.gns4.networking_messages;

public abstract class DHCPPacket extends Layer3Payload{
  protected DHCPPacketType type ;

public DHCPPacketType getType() {
	return type;
}

public void setType(DHCPPacketType type) {
	this.type = type;
}
	
}
