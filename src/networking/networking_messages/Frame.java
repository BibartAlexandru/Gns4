package networking.networking_messages;

import java.util.ArrayList;

public class Frame {
	static final int MIN_SIZE = 64;
	public static Frame decode(ArrayList<Byte> msg) {
		if(msg.size() < 64)
			throw new Exception("Frame size is too small.");
		//TODO:
		return new Frame();
	}
	
	private FrameHeader header;
	private FramePayload payload;
	private FrameTrailer trailer;
	
	public Frame(FrameHeader header, FramePayload payload, FrameTrailer trailer) {
		this.setHeader(header);
		this.setPayload(payload);
		this.setTrailer(trailer);
	}
	
	public ArrayList<Byte> encode() {
		ArrayList<Byte> res = new ArrayList();
		
		return res;
	}
	
	public FrameHeader getHeader() {
		return header;
	}
	public void setHeader(FrameHeader header) {
		this.header = header;
	}

	public FramePayload getPayload() {
		return payload;
	}

	public void setPayload(FramePayload payload) {
		this.payload = payload;
	}

	public FrameTrailer getTrailer() {
		return trailer;
	}

	public void setTrailer(FrameTrailer trailer) {
		this.trailer = trailer;
	}
}
