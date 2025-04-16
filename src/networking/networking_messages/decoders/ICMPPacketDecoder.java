package networking.networking_messages.decoders;

import java.util.List;
import java.util.stream.Collectors;

import networking.networking_messages.ByteArrayDecoder;
import networking.networking_messages.ICMPMessageType;
import networking.networking_messages.ICMPPacket;

public class ICMPPacketDecoder extends ByteArrayDecoder<ICMPPacket> {

	@Override
	public ICMPPacket decode(List<Byte> bytes) throws Exception {
		if(bytes.size() < ICMPPacket.MIN_SIZE)
			throw new Exception("ICMP Packet " + bytes + " is under min size" );
		ICMPMessageType type = ICMPMessageType.values()[(int)bytes.get(0)];
		int length = (bytes.get(0) << 24) ^ (bytes.get(1) << 16) ^ (bytes.get(2) << 8) ^ bytes.get(3);
		String content = bytes.subList(5, 5 + length)
				.stream()
				.map(b -> String.valueOf((char)Byte.toUnsignedInt(b)))
				.collect(Collectors.joining());
		return new ICMPPacket(type, content);
	}

}
