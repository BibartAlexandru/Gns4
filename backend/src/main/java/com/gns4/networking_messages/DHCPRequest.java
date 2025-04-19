package com.gns4.networking_messages;

import java.util.ArrayList;

import com.gns4.networking_messages.decoders.DHCPRequestDecoder;
import com.gns4.other.IPv4NetworkAddress;




public class DHCPRequest extends DHCPPacket {
	public static final int DHCPREQUEST_CODE = 2;
	public static final int NR_BYTES = 1 + 1 + IPv4NetworkAddress.NR_BYTES;
	private IPv4NetworkAddress requestedIp;
	
	public DHCPRequest(IPv4NetworkAddress requestedIp) {
		this.requestedIp = requestedIp;
	}

	public IPv4NetworkAddress getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(IPv4NetworkAddress requestedIp) {
		this.requestedIp = requestedIp;
	}
	
	public boolean equals(DHCPRequest other) {
		return requestedIp.equals(other.getRequestedIp());
	}
	
	/**
	 * @return CODE (1byte) IPAddr(8bytes)
	 */
	@Override
	public ArrayList<Byte> encode() {
		var res = new ArrayList<Byte>();
		res.add((byte)Layer3PayloadTypes.DHCP.ordinal());
		res.add((byte) DHCPREQUEST_CODE);
		res.addAll(requestedIp.encode());
		return res;
	}
	
	public static void main(String[] args) throws Exception {
		var req1 = new DHCPRequest(IPv4NetworkAddress.IP_BROADCAST);
		var enc = req1.encode();
		var dec = new DHCPRequestDecoder();
		var req2 = dec.decode(enc);
		assert req1.equals(req2);
	}
}
