package main.java.networking_messages.decoders;

import java.util.List;
import java.util.stream.Collectors;

import main.java.networking_messages.ICMPMessageType;
import main.java.networking_messages.ICMPPacket;
import main.java.networking_messages.Layer3Payload;


public class ICMPPacketDecoder extends Layer3PayloadDecoder {

	@Override
	public Layer3Payload decode(List<Byte> bytes) throws Exception {
		if(bytes.size() < ICMPPacket.MIN_SIZE)
			throw new Exception("ICMP Packet " + bytes + " is under min size" );
		// 0th byte is Layer2Payload: ICMP
		ICMPMessageType type = ICMPMessageType.values()[(int)bytes.get(1)];
		int length = (bytes.get(2) << 24) ^ (bytes.get(3) << 16) ^ (bytes.get(4) << 8) ^ bytes.get(5);
		String content = bytes.subList(6, 6 + length)
				.stream()
				.map(b -> String.valueOf((char)Byte.toUnsignedInt(b)))
				.collect(Collectors.joining());
		return new ICMPPacket(type, content);
	}

}
