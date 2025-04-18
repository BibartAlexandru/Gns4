package networking.networking_messages;

import java.util.ArrayList;

public abstract class PacketPayload implements ByteSerializable<PacketPayload> {

	@Override
	public ArrayList<Byte> encode() {
		return new ArrayList<>();
	}
	
	public boolean equals(PacketPayload other) {
		return true;
	}

}
