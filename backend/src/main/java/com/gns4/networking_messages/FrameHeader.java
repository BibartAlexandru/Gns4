package com.gns4.networking_messages;

import java.util.ArrayList;
import java.util.List;

import com.gns4.helper.ByteSerializable;
import com.gns4.networking_messages.decoders.FrameHeaderDecoder;
import com.gns4.other.MAC;



/**
 * @encrypted 28 bytes
 * 
 * */
public class FrameHeader implements ByteSerializable<FrameHeader>{
	public static final int NR_BYTES = 2 * MAC.NR_BYTES;
	private MAC dstMac;
	private MAC sourceMac;
	
	
	/**
	 * Returns the concatenated encodings of DSTMAC SRCMAC
	*/
	public ArrayList<Byte> encode(){
		ArrayList<Byte> dstEnc = this.dstMac.encode();
	    ArrayList<Byte> sourceEnc = this.sourceMac.encode();
	    
	    dstEnc.addAll(sourceEnc);
	    return dstEnc;
	}

	public MAC getDstMac() {
		return dstMac;
	}

	public void setDstMac(MAC dstMac) {
		this.dstMac = dstMac;
	}

	public MAC getSourceMac() {
		return sourceMac;
	}

	public void setSourceMac(MAC sourceMac) {
		this.sourceMac = sourceMac;
	}

	public FrameHeader(MAC dstMac, MAC sourceMac) {
		super();
		this.dstMac = dstMac;
		this.sourceMac = sourceMac;
	}
	
	public boolean equals(FrameHeader other) {
		return this.dstMac.equals(other.getDstMac()) && this.sourceMac.equals(other.getSourceMac());
	}
	
	public static void main(String[] args) throws Exception {
		FrameHeader h = new FrameHeader(new MAC("12:12:12:12:12:12"), new MAC("0001.abde.02cf"));
		FrameHeaderDecoder decoder = new FrameHeaderDecoder();
		FrameHeader h2 = decoder.decode(h.encode());
		if(h.equals(h2))
			throw new Exception("Decoder failed");
		System.out.println("All good");
	}
	
}
