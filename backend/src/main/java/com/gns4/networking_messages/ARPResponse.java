package com.gns4.networking_messages;

import java.util.ArrayList;

import com.gns4.helper.PrettyPrintable;
import com.gns4.other.IPv4NetworkAddress;
import com.gns4.other.MAC;



public class ARPResponse extends ARPPacket implements PrettyPrintable{
	static public final byte ARPRESPONSE_CODE = 1;
	static public final int NR_BYTES = 1 + 1 + IPv4NetworkAddress.NR_BYTES + MAC.NR_BYTES;
	
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
		res.add((byte) Layer2PayloadTypes.ARP.ordinal());
		res.add(ARPRESPONSE_CODE);
		res.addAll(requestedIp.encode());
		res.addAll(responseMAC.encode());
		return res;
	}
	
	public boolean equals(ARPResponse other) {
		return requestedIp.equals(other.getRequestedIp()) && responseMAC.equals(other.getResponseMAC());
	}
	@Override
	public String prettyPrint() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'prettyPrint'");
	}
}
