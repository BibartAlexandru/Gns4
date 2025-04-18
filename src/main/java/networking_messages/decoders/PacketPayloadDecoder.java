package networking.networking_messages.decoders;

import java.util.List;

import networking.networking_messages.ByteArrayDecoder;
import networking.networking_messages.PacketPayload;

public class PacketPayloadDecoder extends ByteArrayDecoder<PacketPayload>{

	@Override
	public PacketPayload decode(List<Byte> bytes) throws Exception {
		return null;
	}

}
