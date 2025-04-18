package networking.networking_messages;

import java.util.List;

public abstract class ByteArrayDecoder<T> {
	public abstract T decode(List<Byte> bytes) throws Exception;
}
