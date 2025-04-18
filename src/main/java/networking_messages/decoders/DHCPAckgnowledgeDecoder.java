package main.java.networking_messages.decoders;

import java.util.List;

import main.java.networking_messages.DHCPAckgnowledge;
import main.java.other.IPv4NetworkAddress;



public class DHCPAckgnowledgeDecoder extends DHCPPacketDecoder {

	@Override
	public DHCPAckgnowledge decode(List<Byte> bytes) throws Exception {
		if(bytes.size() != DHCPAckgnowledge.NR_BYTES)
			throw new Exception("Failed to decode DHCPACK " + bytes + "\n Size " + bytes.size() + " is invalid");
		// 0th byte is DHCP
		byte code = bytes.get(1);
		if(code != DHCPAckgnowledge.DHCPACK_CODE)
			throw new Exception("DHCPACK code " + code + " is invalid");
		var ipDec = new IPv4NetworkAddressDecoder();
		var ackIp = ipDec.decode(bytes.subList(2,2 + IPv4NetworkAddress.NR_BYTES));
		return new DHCPAckgnowledge(ackIp);
	}

}
