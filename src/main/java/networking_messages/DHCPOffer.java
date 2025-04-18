package main.java.networking_messages;

import java.util.ArrayList;

import main.java.networking_messages.decoders.DHCPOfferDecoder;
import main.java.other.IPv4NetworkAddress;


public class DHCPOffer extends DHCPPacket{
	public static final byte OFFER_CODE = 1;
	public static final int MIN_SIZE = 5;
	
	private ArrayList<IPv4NetworkAddress> offeredIps;

	public ArrayList<IPv4NetworkAddress> getOfferedIps() {
		return offeredIps;
	}

	public void setOfferedIps(ArrayList<IPv4NetworkAddress> offeredIps) {
		this.offeredIps = offeredIps;
	}
	
	/**
	 * 
	 * @return CODE (1byte), offeredIPsLen(4 bytes), offeredIps([0..] bytes)
	 */
	@Override
	public ArrayList<Byte> encode() {
		var res = new ArrayList<Byte>();
		res.add(OFFER_CODE);
		byte[] offeredIpsLen = new byte[] {
			(byte)(offeredIps.size() >> 24),
			(byte)(offeredIps.size() >> 16),
			(byte)(offeredIps.size() >> 8),
			(byte)offeredIps.size()
		};
		for(byte b : offeredIpsLen)
			res.add(b);
		for(var ip : offeredIps)
			res.addAll(ip.encode());
		return super.encode();
	}

	public DHCPOffer(ArrayList<IPv4NetworkAddress> offeredIps) {
		super();
		this.offeredIps = offeredIps;
	}

	public boolean equals(DHCPOffer other) {
		return offeredIps.equals(other.getOfferedIps());
	}
	
	public static void main(String[] args) throws Exception {
		ArrayList<IPv4NetworkAddress> offers = new ArrayList<>();
		offers.add(IPv4NetworkAddress.ZERO);
		var o1 = new DHCPOffer(offers);
		var enc = o1.encode();
		var dec = new DHCPOfferDecoder();
		var o2 = dec.decode(enc);
		assert o1.equals(o2);
	}
}
