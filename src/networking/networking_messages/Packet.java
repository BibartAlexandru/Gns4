package networking.networking_messages;

import java.util.List;

public class Packet extends FramePayload {
	private PacketHeader header;
	private PacketPayload payload;
	public PacketHeader getHeader() {
		return header;
	}
	public void setHeader(PacketHeader header) {
		this.header = header;
	}
	public PacketPayload getPayload() {
		return payload;
	}
	public void setPayload(PacketPayload payload) {
		this.payload = payload;
	}
	
	public static FramePayload decode(List<Byte> bytes) {
		System.out.println("child called");
		return new FramePayload();
	}
}
