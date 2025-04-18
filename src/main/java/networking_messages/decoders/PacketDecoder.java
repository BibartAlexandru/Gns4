package main.java.networking_messages.decoders;

import java.util.List;

import main.java.networking_messages.Packet;
import main.java.networking_messages.PacketHeader;
import main.java.networking_messages.PacketPayload;


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
