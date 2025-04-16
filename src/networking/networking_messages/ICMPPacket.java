package networking.networking_messages;

public class ICMPPacket extends PacketPayload{
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
}
