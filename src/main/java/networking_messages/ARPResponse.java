package main.java.networking_messages;

import java.util.ArrayList;

import main.java.networking_messages.decoders.ARPResponseDecoder;
import main.java.other.IPv4NetworkAddress;
import main.java.other.MAC;

public class ARPResponse extends ARPPacket{
	static public final byte ARPRESPONSE_CODE = 1;
	static public final int NR_BYTES = 1 + IPv4NetworkAddress.NR_BYTES + MAC.NR_BYTES;
	
	private IPv4NetworkAddress requestedIp;
	private MAC responseMAC;
	
	public IPv4NetworkAddress getRequestedIp() {
		return requestedIp;
	}
	public void setRequestedIp(IPv4NetworkAddress requestedIp) {
		this.requestedIp = requestedIp;
	}
	public MAC getResponseMAC() {
		return responseMAC;
	}
	public ARPResponse(IPv4NetworkAddress requestedIp, MAC responseMAC) {
		super();
		this.requestedIp = requestedIp;
		this.responseMAC = responseMAC;
	}
	public void setResponseMAC(MAC responseMAC) {
		this.responseMAC = responseMAC;
	}
	
	/**
	 * @return CODE(1byte) reqIp(8bytes) respMAC(6bytes)
	 */
	@Override
	public ArrayList<Byte> encode() {
		var res = new ArrayList<Byte>();
		res.add(ARPRESPONSE_CODE);
		res.addAll(requestedIp.encode());
		res.addAll(responseMAC.encode());
		return res;
	}
	
	public boolean equals(ARPResponse other) {
		return requestedIp.equals(other.getRequestedIp()) && responseMAC.equals(other.getResponseMAC());
	}
	
	public static void main(String[] args) throws Exception {
		var r1 = new ARPResponse(IPv4NetworkAddress.IP_BROADCAST, MAC.MAC_BROADCAST);
		var enc = r1.encode();
		var dec = new ARPResponseDecoder();
		var r2 = dec.decode(enc);
		assert r1.equals(r2);
	}
}
