package networking.networking_messages.decoders;

import java.util.List;

import networking.networking_messages.ByteArrayDecoder;
import networking.networking_messages.FramePayload;

public class FramePayloadDecoder extends ByteArrayDecoder<FramePayload>{

	@Override
	public FramePayload decode(List<Byte> bytes) throws Exception {
		
		return new FramePayload();
	}

}
