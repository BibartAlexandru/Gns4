package main.java.networking_messages.decoders;

import java.util.List;

import main.java.networking_messages.DHCPDiscover;



public class DHCPDiscoverDecoder extends DHCPPacketDecoder{

	@Override
	public DHCPDiscover decode(List<Byte> bytes) throws Exception {
		if(bytes.size() != DHCPDiscover.NR_BYTES && bytes.get(0) != (byte)0 )
			throw new Exception("Failed to parse DHCPDiscover " + bytes);
		return new DHCPDiscover();
	}

}
