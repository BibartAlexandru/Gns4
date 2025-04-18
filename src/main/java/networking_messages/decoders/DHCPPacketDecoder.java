package main.java.networking_messages.decoders;

import java.util.List;

import main.java.networking_messages.DHCPPacket;


public class DHCPPacketDecoder extends ByteArrayDecoder<DHCPPacket>{

	@Override
	public DHCPPacket decode(List<Byte> bytes) throws Exception {
		try {
			var dec = new DHCPDiscoverDecoder();
			var disc = dec.decode(bytes);
			return disc;
		}
		catch(Exception e) {}
		try {
			var dec = new DHCPOfferDecoder();
			var offer = dec.decode(bytes);
			return offer;
		}
		catch(Exception e) {}
		try {
			var dec = new DHCPRequestDecoder();
			var req = dec.decode(bytes);
			return req;
		}
		catch(Exception e) {}
		try {
			var dec = new DHCPAckgnowledgeDecoder();
			var ack = dec.decode(bytes);
			return ack;
		}
		catch(Exception e) {}
		throw new Exception("Failed to parse DHCPPacket: " +  bytes);
	}

}
