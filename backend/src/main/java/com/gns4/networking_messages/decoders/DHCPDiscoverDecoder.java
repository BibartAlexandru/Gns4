package com.gns4.networking_messages.decoders;

import java.util.List;

import com.gns4.networking_messages.DHCPDiscover;



public class DHCPDiscoverDecoder extends DHCPPacketDecoder{

	@Override
	public DHCPDiscover decode(List<Byte> bytes) throws Exception {
		if(bytes.size() != DHCPDiscover.NR_BYTES)
			throw new Exception("Failed to parse DHCPDiscover " + bytes);
		return new DHCPDiscover();
	}

}
