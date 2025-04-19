package com.gns4.networking_messages.decoders;

import java.util.ArrayList;
import java.util.List;

import com.gns4.networking_messages.DHCPOffer;
import com.gns4.other.IPv4NetworkAddress;


public class DHCPOfferDecoder extends DHCPPacketDecoder{

	@Override
	public DHCPOffer decode(List<Byte> bytes) throws Exception {
		if(bytes.size() < DHCPOffer.MIN_SIZE)
			throw new Exception("Failed to parse DHCPOFffer " + bytes);
		// 0th byte is DHCP
		byte code = bytes.get(1);
		if(code != DHCPOffer.OFFER_CODE)
			throw new Exception("Failed to parse DHCPOFffer " + bytes);
		int offeredIpsLen = (bytes.get(2) >> 24) ^ (bytes.get(3) >> 16) ^ (bytes.get(4) >> 8) ^ (bytes.get(5));
		var offeredIps = new ArrayList<IPv4NetworkAddress>();
		var dec = new IPv4NetworkAddressDecoder();
		
		int startByte = 6 ;
		for(int i = 0 ; i < offeredIpsLen ; i += IPv4NetworkAddress.NR_BYTES)
		{
			var ip = dec.decode(bytes.subList(startByte + i, startByte + i + IPv4NetworkAddress.NR_BYTES));
			offeredIps.add(ip);
		}
		
		return new DHCPOffer(offeredIps);
	}

}
