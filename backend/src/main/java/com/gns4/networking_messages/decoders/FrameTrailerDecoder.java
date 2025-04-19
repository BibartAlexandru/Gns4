package com.gns4.networking_messages.decoders;

import java.util.List;

import com.gns4.networking_messages.FrameTrailer;


public class FrameTrailerDecoder extends ByteArrayDecoder<FrameTrailer> {

	@Override
	public FrameTrailer decode(List<Byte> bytes) throws Exception {
		if(bytes.size() != FrameTrailer.NR_BYTES)
			throw new Exception("frame trailer" + bytes + " has an invalid length of" + bytes.size());
		return new FrameTrailer(bytes);
	}
}
