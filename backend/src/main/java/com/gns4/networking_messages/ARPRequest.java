package com.gns4.networking_messages;

import java.net.InetAddress;
import java.util.ArrayList;

import com.gns4.helper.PrettyPrintable;
import com.gns4.networking_messages.decoders.ARPRequestDecoder;
import com.gns4.other.IPv4NetworkAddress;



public class ARPRequest extends ARPPacket implements PrettyPrintable{
	static public final byte ARPREQUEST_CODE = 0;
	static public final int NR_BYTES = 1 + 1 + 2* IPv4NetworkAddress.NR_BYTES;
	private IPv4NetworkAddress requestedIp;
  private IPv4NetworkAddress senderIp;
	
	public IPv4NetworkAddress getSenderIp() {
	return senderIp;
}

public void setSenderIp(IPv4NetworkAddress senderIp) {
	this.senderIp = senderIp;
}

	public ARPRequest(IPv4NetworkAddress requestedIp, IPv4NetworkAddress senderIp) {
		this.requestedIp = requestedIp;
    this.senderIp = senderIp ;
	}

	public IPv4NetworkAddress getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(IPv4NetworkAddress requestedIp) {
		this.requestedIp = requestedIp;
	}
	
	@Override
	public ArrayList<Byte> encode() {
		var res = new ArrayList<Byte>();
		res.add((byte) Layer2PayloadTypes.ARP.ordinal());
		res.add(ARPREQUEST_CODE);
		res.addAll(requestedIp.encode());
    res.addAll(senderIp.encode());
		return res;
	}
	
	public boolean equals(ARPRequest other) {
		return requestedIp.equals(other.getRequestedIp());
	}
	
	public static void main(String[] args) throws Exception {
		var a1 = new ARPRequest(IPv4NetworkAddress.IP_BROADCAST, IPv4NetworkAddress.ZERO);
		var enc = a1.encode();
		var dec = new ARPRequestDecoder();
		var a2 = dec.decode(enc);
		assert a1.equals(a2);
	}

	@Override
	public String prettyPrint() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'prettyPrint'");
	}
}
