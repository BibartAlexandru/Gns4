package networking.networking_messages.decoders;

import java.util.List;

import networking.networking_messages.ByteArrayDecoder;
import networking.networking_messages.DHCPRequest;

public class DHCPRequestDecoder extends DHCPPacketDecoder {

	@Override
	public DHCPRequest decode(List<Byte> bytes) throws Exception {
		if(bytes.size() != DHCPRequest.NR_BYTES)
			throw new Exception("Failed to decode DHCPRequest " + bytes);
		int code = bytes.get(0);
		if(code != DHCPRequest.DHCPREQUEST_CODE)
			throw new Exception("Failed to decode DHCPRequest " + bytes + "\nCode" + code + " is invalid");
		var ipDec = new IPv4NetworkAddressDecoder();
		var ip = ipDec.decode(bytes.subList(1, 9));
		return new DHCPRequest(ip);
	}

}
