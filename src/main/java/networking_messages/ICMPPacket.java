package main.java.networking_messages;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import main.java.networking_messages.decoders.ICMPPacketDecoder;


public class ICMPPacket extends PacketPayload{
	public static final int MIN_SIZE = 5;
	public ICMPPacket(ICMPMessageType type, String content) {
		super();
		this.type = type;
		this.content = content;
	}

	private ICMPMessageType type;
	private String content;
	public ICMPMessageType getType() {
		return type;
	}
	public void setType(ICMPMessageType type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * @return type(1byte) content_len(4bytes) content([0..] by)
	 */
	@Override
	public ArrayList<Byte> encode() {
		byte _type = (byte)type.ordinal();
		byte[] contentLength = new byte[] {
			(byte) (content.length() >> 24),
			(byte) (content.length() >> 16),
			(byte) (content.length() >> 8),
			(byte) content.length()
 		};
		var res = new ArrayList<Byte>();
		res.add(_type);
		for(byte b : contentLength)
			res.add(b);
		for(byte b : content.getBytes(StandardCharsets.US_ASCII))
			res.add(b);
		return res;
	}
	
	public static void main(String[] args) throws Exception {
		var p1 = new ICMPPacket(ICMPMessageType.ECHO, "This is an example hehe");
		var dec = new ICMPPacketDecoder();
		var enc = p1.encode();
		var p2 = dec.decode(enc);
		assert p1.equals(p2);
	}
}
