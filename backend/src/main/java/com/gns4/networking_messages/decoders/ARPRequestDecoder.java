package com.gns4.networking_messages.decoders;

import java.util.List;

import com.gns4.networking_messages.ARPRequest;
import com.gns4.other.IPv4NetworkAddress;




public class ARPRequestDecoder extends ARPPacketDecoder{

	@Override
	public ARPRequest decode(List<Byte> bytes) throws Exception {
		if(bytes.size() != ARPRequest.NR_BYTES)
			throw new Exception("ARPRequest: " + bytes + " has invalid size : " + bytes.size());
		// 0th byte is ARP
		byte code = bytes.get(1);
		if(code != ARPRequest.ARPREQUEST_CODE)
			throw new Exception("ARPRequest code: " + code + " is invalid.");
		var ipDec = new IPv4NetworkAddressDecoder();
		var reqIp = ipDec.decode(bytes.subList(2, 2 + IPv4NetworkAddress.NR_BYTES));
		return new ARPRequest(reqIp);
	}

}
