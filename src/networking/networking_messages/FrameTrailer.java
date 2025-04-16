package networking.networking_messages;

public class FrameTrailer {
	private byte[] FCS;

	public byte[] getFCS() {
		return FCS;
	}

	public void setFCS(byte[] fCS) {
		FCS = fCS;
	}
}
