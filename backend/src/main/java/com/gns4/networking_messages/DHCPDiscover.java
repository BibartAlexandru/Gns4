package com.gns4.networking_messages;

import java.util.ArrayList;

public class DHCPDiscover extends DHCPPacket{
	private static final int DISCOVER_CODE = 0;
	public static final int NR_BYTES = 2;
	
	public DHCPDiscover() {}
	
	/**
	 * @return DHCPPacketCode(1 byte) = 1
	 */
	@Override
	public ArrayList<Byte> encode() {
		var res = new ArrayList<Byte>();
		res.add((byte)Layer3PayloadTypes.DHCP.ordinal());
		res.add((byte)DISCOVER_CODE);
		return res;
	}
	
	public boolean equals(DHCPDiscover other) {
		return true;
	}
}
