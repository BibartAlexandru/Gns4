package com.gns4.networking_messages;

import java.util.ArrayList;

public class DHCPDiscover extends DHCPPacket{
	public static final int NR_BYTES = 1; // CODE FROM DHCPPacket
	
	public DHCPDiscover() {
    super() ;
    type = DHCPPacketType.DISCOVER ;
  }
	
	/**
	 * @return DHCPPacketCode(1 byte) = 1
	 */
	@Override
	public ArrayList<Byte> encode() {
		var res = new ArrayList<Byte>();
    // 0 because it's the 0th in the enum
		res.add((byte)type.ordinal());
		return res;
	}
	
	public boolean equals(DHCPDiscover other) {
		return true;
	}
}
