package main.java.networking_messages.decoders;

import java.util.List;

import main.java.networking_messages.FrameHeader;
import main.java.other.MAC;
import main.java.other.MACDecoder;


public class FrameHeaderDecoder extends ByteArrayDecoder<FrameHeader> {
	public FrameHeader decode(List<Byte> bytes) throws Exception {
		if(bytes.size() != 28)
			throw new Exception(bytes + " has invalid size of " + bytes.size());
		var macDec = new MACDecoder();
		MAC dstMac = macDec.decode(bytes.subList(0, 14));
		MAC srcMac = macDec.decode(bytes.subList(14, 28));
		return new FrameHeader(dstMac, srcMac);
	}
}
