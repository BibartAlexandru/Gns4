package main.java.networking_messages.decoders;

import java.util.List;

import main.java.networking_messages.Layer3Payload;


public class Layer3PayloadDecoder extends ByteArrayDecoder<Layer3Payload>{

	@Override
	public Layer3Payload decode(List<Byte> bytes) throws Exception {
		try {
			var dec = new DHCPPacketDecoder();
			var dhcp = dec.decode(bytes);
			return dhcp;
		}
		catch(Exception e) {}
		try {
			var dec = new ICMPPacketDecoder();
			var icmp = dec.decode(bytes);
			return icmp;
		}
		catch(Exception e) {}
		throw new Exception("Failed to parse Layer3Payload: " +  bytes);
	}
}
