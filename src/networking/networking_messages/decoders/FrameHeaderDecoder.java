package networking.networking_messages.decoders;

import java.util.List;

import networking.networking_messages.ByteArrayDecoder;
import networking.networking_messages.FrameHeader;
import networking.other.MAC;

public class FrameHeaderDecoder extends ByteArrayDecoder<FrameHeader> {
	public FrameHeader decode(List<Byte> bytes) throws Exception {
		if(bytes.size() != 28)
			throw new Exception(bytes + " has invalid size of " + bytes.size());
		MAC dstMac = MAC.decode(bytes.subList(0, 14));
		MAC srcMac = MAC.decode(bytes.subList(14, 28));
		return new FrameHeader(dstMac, srcMac);
	}
}
