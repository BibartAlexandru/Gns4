package com.gns4.networking_messages.decoders;

import java.util.List;

import com.gns4.networking_messages.FrameHeader;
import com.gns4.other.MAC;
import com.gns4.other.MACDecoder;


public class FrameHeaderDecoder extends ByteArrayDecoder<FrameHeader> {
	public FrameHeader decode(List<Byte> bytes) throws Exception {
		if(bytes.size() != FrameHeader.NR_BYTES)
			throw new Exception(bytes + " has invalid size of " + bytes.size());
		var macDec = new MACDecoder();
		MAC dstMac = macDec.decode(bytes.subList(0, MAC.NR_BYTES));
		MAC srcMac = macDec.decode(bytes.subList(MAC.NR_BYTES, 2*MAC.NR_BYTES));
    int type = bytes.get(12) << 24 | bytes.get(13) << 16 | bytes.get(14) << 8 | bytes.get(15);
		return new FrameHeader(dstMac, srcMac, type);
	}
}
