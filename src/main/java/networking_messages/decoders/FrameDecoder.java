package main.java.networking_messages.decoders;

import java.util.ArrayList;
import java.util.List;

import main.java.networking_messages.Frame;


public class FrameDecoder extends ByteArrayDecoder<Frame>{

	@Override
	public Frame decode(List<Byte> bytes) throws Exception {
		if(bytes.size() < Frame.MIN_SIZE)
			throw new Exception("Frame " + bytes + "has invalid size of "+ bytes.size());
		
		var hDec = new FrameHeaderDecoder();
		var pDec = new FramePayloadDecoder();
		var tDec = new FrameTrailerDecoder();
		
		var header = hDec.decode(bytes.subList(0, 28));
		var payload = pDec.decode(bytes.subList(28, bytes.size()-4));
		var trailer = tDec.decode(bytes.subList(bytes.size()-4, bytes.size()));
		return new Frame(header, payload, trailer);
	}
	
}