package main.java.networking_messages;

import java.util.ArrayList;

import main.java.networking_messages.decoders.DHCPOfferDecoder;
import main.java.other.IPv4NetworkAddress;


public class DHCPOffer extends DHCPPacket{
	public static final byte OFFER_CODE = 1;
	public static final int MIN_SIZE = 1 + 1 + 4;
	
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
		
		res.add((byte)Layer3PayloadTypes.DHCP.ordinal());
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
		return res;
	}

	public DHCPOffer(ArrayList<IPv4NetworkAddress> offeredIps) {
		super();
		this.offeredIps = offeredIps;
	}

	public boolean equals(DHCPOffer other) {
		if(offeredIps.size() != other.getOfferedIps().size())
			return false;
		for(int i = 0 ; i < offeredIps.size() ; i++)
			if(!offeredIps.get(i).equals(other.getOfferedIps().get(i)))
				return false;
		return true ;
	}
}
