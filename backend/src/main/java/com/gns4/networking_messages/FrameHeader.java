package com.gns4.networking_messages;

import java.lang.reflect.Array;
import java.util.ArrayList;
import com.gns4.helper.ByteSerializable;
import com.gns4.networking_messages.decoders.FrameHeaderDecoder;
import com.gns4.other.MAC;



/**
 * @encoded 16 bytes
 * 
 * */
public class FrameHeader implements ByteSerializable<FrameHeader>{
	public static final int NR_BYTES = 2 * MAC.NR_BYTES + 4;
  public static final int TYPE_FIELD_IPv4 = 0x0800;
  public static final int TYPE_FIELD_ARP = 0x0806;
	private MAC dstMac;
	private MAC sourceMac;
  // Protocol encapsulated
  private int type;
	
	
	/**
	 * Returns the concatenated encodings of DSTMAC SRCMAC
	*/
	public ArrayList<Byte> encode(){
		ArrayList<Byte> dstEnc = this.dstMac.encode();
	  ArrayList<Byte> sourceEnc = this.sourceMac.encode();
    ArrayList<Byte> typeArr = new ArrayList<Byte>();
    typeArr.add((byte)( type >> 24));
    typeArr.add((byte)( type >> 16));
    typeArr.add((byte)( type >> 8));
    typeArr.add((byte) type );
    ArrayList<Byte> res = new ArrayList<>();
   
    res.addAll(dstEnc); 
    res.addAll(sourceEnc);
    res.addAll(typeArr);
    return res;
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

	public FrameHeader(MAC dstMac, MAC sourceMac, int type) {
		super();
		this.dstMac = dstMac;
		this.sourceMac = sourceMac;
    this.type = type;
	}

	public FrameHeader(MAC dstMac, MAC sourceMac) {
		super();
		this.dstMac = dstMac;
		this.sourceMac = sourceMac;
    this.type = TYPE_FIELD_IPv4;
	}

	
	public boolean equals(FrameHeader other) {
		return this.dstMac.equals(other.getDstMac()) && this.sourceMac.equals(other.getSourceMac()) && this.type == other.type;
	}
	
	public static void main(String[] args) throws Exception {
		FrameHeader h = new FrameHeader(new MAC("12:12:12:12:12:12"), new MAC("0001.abde.02cf"), TYPE_FIELD_IPv4);
		FrameHeaderDecoder decoder = new FrameHeaderDecoder();
		FrameHeader h2 = decoder.decode(h.encode());
		if(h.equals(h2))
			throw new Exception("Decoder failed");
		System.out.println("All good");
	}
	
}
