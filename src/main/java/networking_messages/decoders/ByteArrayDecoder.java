package main.java.networking_messages.decoders;

import java.util.List;

public abstract class ByteArrayDecoder<T> {
	public abstract T decode(List<Byte> bytes) throws Exception;
}
