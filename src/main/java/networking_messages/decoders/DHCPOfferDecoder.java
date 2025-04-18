package networking.networking_messages.decoders;

import java.util.ArrayList;
import java.util.List;

import networking.networking_messages.ByteArrayDecoder;
import networking.networking_messages.DHCPOffer;
import networking.other.IPv4NetworkAddress;

public class DHCPOfferDecoder extends DHCPPacketDecoder{

	@Override
	public DHCPOffer decode(List<Byte> bytes) throws Exception {
		if(bytes.size() < DHCPOffer.MIN_SIZE)
			throw new Exception("Failed to parse DHCPOFffer " + bytes);
		byte code = bytes.get(0);
		if(code != DHCPOffer.OFFER_CODE)
			throw new Exception("Failed to parse DHCPOFffer " + bytes);
		int offeredIpsLen = (bytes.get(1) >> 24) ^ (bytes.get(2) >> 16) ^ (bytes.get(3) >> 8) ^ (bytes.get(4));
		var offeredIps = new ArrayList<IPv4NetworkAddress>();
		var dec = new IPv4NetworkAddressDecoder();
		for(int i = 0 ; i < offeredIpsLen ; i += IPv4NetworkAddress.NR_BYTES)
		{
			var ip = dec.decode(bytes.subList(i, i + IPv4NetworkAddress.NR_BYTES));
			offeredIps.add(ip);
		}
		
		return new DHCPOffer(offeredIps);
	}

}
