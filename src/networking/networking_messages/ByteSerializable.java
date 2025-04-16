package networking.networking_messages;

import java.util.ArrayList;

public interface ByteSerializable<T> {
	public ArrayList<Byte> encode();
}
