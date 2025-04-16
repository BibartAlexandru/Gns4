package networking.networking_messages;

import java.util.ArrayList;

public class PacketPayload implements ByteSerializable<PacketPayload> {

	@Override
	public ArrayList<Byte> encode() {
		return new ArrayList<>();
	}
	
	public boolean equals(PacketPayload other) {
		return true;
	}

}
