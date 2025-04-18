package networking.networking_messages.decoders;

import java.util.List;

import networking.networking_messages.ByteArrayDecoder;
import networking.networking_messages.DHCPAckgnowledge;

public class DHCPAckgnowledgeDecoder extends DHCPPacketDecoder {

	@Override
	public DHCPAckgnowledge decode(List<Byte> bytes) throws Exception {
		if(bytes.size() != DHCPAckgnowledge.NR_BYTES)
			throw new Exception("Failed to decode DHCPACK " + bytes + "\n Size " + bytes.size() + " is invalid");
		byte code = bytes.get(0);
		if(code != DHCPAckgnowledge.DHCPACK_CODE)
			throw new Exception("DHCPACK code " + code + " is invalid");
		var ipDec = new IPv4NetworkAddressDecoder();
		var ackIp = ipDec.decode(bytes.subList(1,9));
		return new DHCPAckgnowledge(ackIp);
	}

}
