package networking.networking_messages;

import java.util.ArrayList;

public class DHCPDiscover extends DHCPPacket{
	private static final byte DISCOVER_CODE = 0;
	
	public DHCPDiscover() {}
	
	/**
	 * @return DHCPPacketCode(1 byte) = 1
	 */
	@Override
	public ArrayList<Byte> encode() {
		var res = new ArrayList<Byte>();
		res.add(DISCOVER_CODE);
		return res;
	}
	
	public boolean equals(DHCPDiscover other) {
		return true;
	}
}
