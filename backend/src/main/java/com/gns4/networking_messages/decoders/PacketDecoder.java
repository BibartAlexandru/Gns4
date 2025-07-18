package com.gns4.networking_messages.decoders;

import java.util.List;

import com.gns4.networking_messages.Layer3Payload;
import com.gns4.networking_messages.Packet;
import com.gns4.networking_messages.PacketHeader;


public class PacketDecoder extends ByteArrayDecoder<Packet>{

	@Override
	public Packet decode(List<Byte> bytes) throws Exception {
		if(bytes.size() < Packet.MIN_BYTES)
			throw new Exception("Packet: " + bytes + " has invalid size: " + bytes.size());
		var hDec = new PacketHeaderDecoder();
		var plDec = new Layer3PayloadDecoder();
		// 0th byte is Layer2Payload: PACKET
		PacketHeader h = hDec.decode(bytes.subList(1, 18));
		Layer3Payload pl = plDec.decode(bytes.subList(10, bytes.size()));
		return new Packet(h, pl);
	}

}
