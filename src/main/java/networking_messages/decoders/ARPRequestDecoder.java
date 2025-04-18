package main.java.networking_messages.decoders;

import java.util.List;

import main.java.networking_messages.ARPRequest;



public class ARPRequestDecoder extends ARPPacketDecoder{

	@Override
	public ARPRequest decode(List<Byte> bytes) throws Exception {
		if(bytes.size() != ARPRequest.NR_BYTES)
			throw new Exception("ARPRequest: " + bytes + " has invalid size : " + bytes.size());
		byte code = bytes.get(0);
		if(code != ARPRequest.ARPREQUEST_CODE)
			throw new Exception("ARPRequest code: " + code + " is invalid.");
		var ipDec = new IPv4NetworkAddressDecoder();
		var reqIp = ipDec.decode(bytes.subList(1, 9));
		return new ARPRequest(reqIp);
	}

}
