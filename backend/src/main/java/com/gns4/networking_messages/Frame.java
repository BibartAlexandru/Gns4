package com.gns4.networking_messages;

import java.util.ArrayList;
import java.util.Arrays;

import com.gns4.helper.ByteSerializable;
import com.gns4.networking_messages.decoders.FrameDecoder;
import com.gns4.other.IPv4NetworkAddress;
import com.gns4.other.MAC;




/**
 *
 * @param header
 * @param payload
 * @param trailer
 */
public class Frame implements ByteSerializable<Frame>{
	public static final int MIN_SIZE = 64;
	
	
	private FrameHeader header;
	private Layer2Payload payload;
	private FrameTrailer trailer;
	
	public Frame(FrameHeader header, Layer2Payload payload, FrameTrailer trailer) {
		this.setHeader(header);
		this.setPayload(payload);
		this.setTrailer(trailer);
	}
	
	/**
	 * @return concatenated encodings of header, payload, trailer
	 * Pads the payload with 0s until the frame reaches a min size of 64 bytes
	  */
	public ArrayList<Byte> encode() {
		ArrayList<Byte> res = new ArrayList();
		var hEnc = header.encode();
		var pEnc = payload.encode();
		var tEnc = trailer.encode();
		int frameLen = hEnc.size() + pEnc.size() + tEnc.size();
		if(frameLen < Frame.MIN_SIZE)
			payload.padPayload(pEnc, Frame.MIN_SIZE - frameLen);
		res.addAll(hEnc);
		res.addAll(pEnc);
		res.addAll(tEnc);
		return res;
	}
	
	
	public FrameHeader getHeader() {
		return header;
	}
	public void setHeader(FrameHeader header) {
		this.header = header;
	}

	public Layer2Payload getPayload() {
		return payload;
	}

	public void setPayload(Layer2Payload payload) {
		this.payload = payload;
	}

	public FrameTrailer getTrailer() {
		return trailer;
	}

	public void setTrailer(FrameTrailer trailer) {
		this.trailer = trailer;
	}
	
	public boolean equals(Frame other) {
		return header.equals(other.getHeader()) && payload.equals(other.getPayload()) && trailer.equals(other.getTrailer());
	}
	
	public static void main(String[] args) throws Exception {
		var fDec = new FrameDecoder();
		FrameHeader h = new FrameHeader(new MAC("0000.0000.0000"), new MAC("1111.1111.1111"));
		Layer2Payload p = new ARPRequest(IPv4NetworkAddress.IP_BROADCAST, IPv4NetworkAddress.ZERO);
		FrameTrailer t = new FrameTrailer(new ArrayList<Byte>(Arrays.asList((byte)0,(byte)0,(byte)0, (byte)0)));
		Frame f = new Frame(h, p, t);
		Frame f2 = fDec.decode(f.encode());
		if(!f.equals(f2))
			throw new Exception("Failed frame decoding ");
		System.out.println("All good");
	}
}
