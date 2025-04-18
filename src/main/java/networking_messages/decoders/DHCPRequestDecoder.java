package main.java.networking_messages.decoders;

import java.util.List;

import main.java.networking_messages.DHCPRequest;
import main.java.other.IPv4NetworkAddress;


public class DHCPRequestDecoder extends DHCPPacketDecoder {

	@Override
	public DHCPRequest decode(List<Byte> bytes) throws Exception {
		if(bytes.size() != DHCPRequest.NR_BYTES)
			throw new Exception("Failed to decode DHCPRequest " + bytes);
		// 0th byte is DHCP
		int code = bytes.get(1);
		if(code != DHCPRequest.DHCPREQUEST_CODE)
			throw new Exception("Failed to decode DHCPRequest " + bytes + "\nCode" + code + " is invalid");
		var ipDec = new IPv4NetworkAddressDecoder();
		var ip = ipDec.decode(bytes.subList(2, 2 + IPv4NetworkAddress.NR_BYTES));
		return new DHCPRequest(ip);
	}

}
