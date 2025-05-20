package com.gns4.networking_messages;

import java.util.ArrayList;
import de.vandermeer.asciitable.AsciiTable;
import com.gns4.helper.Loggable;

public class DHCPDiscover extends DHCPPacket implements Loggable{
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

	@Override
	public String toLogs() {
    return "";
	}
}
