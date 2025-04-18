package networking.networking_messages.decoders;

import java.util.List;

import networking.networking_messages.ByteArrayDecoder;
import networking.networking_messages.Packet;
import networking.networking_messages.PacketHeader;
import networking.networking_messages.PacketPayload;

public class PacketDecoder extends ByteArrayDecoder<Packet>{

	@Override
	public Packet decode(List<Byte> bytes) throws Exception {
		var hDec = new PacketHeaderDecoder();
		var plDec = new PacketPayloadDecoder();
		PacketHeader h = hDec.decode(bytes.subList(0, 17));
		PacketPayload pl = plDec.decode(bytes.subList(9, bytes.size()));
		return new Packet(h, pl);
	}

}
