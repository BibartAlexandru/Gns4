package networking.networking_messages;

import java.net.InetAddress;
import java.util.ArrayList;

import networking.networking_messages.decoders.ARPRequestDecoder;
import networking.other.IPv4NetworkAddress;

public class ARPRequest extends ARPPacket {
	static public final byte ARPREQUEST_CODE = 0;
	static public final int NR_BYTES = 9;
	private IPv4NetworkAddress requestedIp;
	
	public ARPRequest(IPv4NetworkAddress requestedIp) {
		this.requestedIp = requestedIp;
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
		res.add(ARPREQUEST_CODE);
		res.addAll(requestedIp.encode());
		return res;
	}
	
	public boolean equals(ARPRequest other) {
		return requestedIp.equals(other.getRequestedIp());
	}
	
	public static void main(String[] args) throws Exception {
		var a1 = new ARPRequest(IPv4NetworkAddress.IP_BROADCAST);
		var enc = a1.encode();
		var dec = new ARPRequestDecoder();
		var a2 = dec.decode(enc);
		assert a1.equals(a2);
	}
}
