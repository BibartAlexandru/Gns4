package networking.networking_messages;

import java.util.ArrayList;

import networking.other.MAC;

public class FrameHeader {
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
	
	public static void main(String[] args) throws Exception {
		FrameHeader h = new FrameHeader(new MAC("12:12:12:12:12:12"), new MAC("0001.abde.02cf"));
		System.out.println(h.encode());
	
	}
	
}
