package main.java.networking_messages.decoders;

import java.util.List;

import main.java.networking_messages.Layer3Payload;


public class PacketPayloadDecoder extends ByteArrayDecoder<Layer3Payload>{

	@Override
	public Layer3Payload decode(List<Byte> bytes) throws Exception {
		try {
			
		}
		catch(Exception e) {}
		try {
			
		}
		catch(Exception e) {}
		throw new Exception("Failed to parse Layer3Payload: ", bytes);
	}
}
