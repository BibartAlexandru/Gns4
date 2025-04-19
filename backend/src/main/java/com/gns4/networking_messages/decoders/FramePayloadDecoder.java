package com.gns4.networking_messages.decoders;

import java.util.List;

import com.gns4.networking_messages.Layer2Payload;


public class FramePayloadDecoder extends ByteArrayDecoder<Layer2Payload>{

	/**
	 * Calls PacketDecoder & FramePayloadDecoder to try to decode
	 */
	@Override
	public Layer2Payload decode(List<Byte> bytes) throws Exception {
		try {
			var res = new PacketDecoder().decode(bytes);
			return res;
		}
		catch(Exception e) {}
		try {
			var res = new ARPPacketDecoder().decode(bytes);
			return res;
		}
		catch(Exception e) {}
		
		throw new Exception("Failed to parse frame payload: " + bytes);
	}

}
