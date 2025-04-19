package com.gns4.networking_messages.decoders;

import java.util.ArrayList;
import java.util.List;

import com.gns4.networking_messages.Frame;
import com.gns4.networking_messages.FrameHeader;
import com.gns4.networking_messages.FrameTrailer;



public class FrameDecoder extends ByteArrayDecoder<Frame>{

	@Override
	public Frame decode(List<Byte> bytes) throws Exception {
		if(bytes.size() < Frame.MIN_SIZE)
			throw new Exception("Frame " + bytes + "has invalid size of "+ bytes.size());
		
		var hDec = new FrameHeaderDecoder();
		var pDec = new FramePayloadDecoder();
		var tDec = new FrameTrailerDecoder();
		
		var header = hDec.decode(bytes.subList(0, FrameHeader.NR_BYTES));
		var payload = pDec.decode(bytes.subList(FrameHeader.NR_BYTES, bytes.size()-FrameTrailer.NR_BYTES));
		var trailer = tDec.decode(bytes.subList(bytes.size()-FrameTrailer.NR_BYTES, bytes.size()));
		return new Frame(header, payload, trailer);
	}
	
}