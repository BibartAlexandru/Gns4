package main.java.networking_messages;

import java.util.ArrayList;

public abstract class ByteDeserializable {
	public abstract ByteDeserializable decode(ArrayList<Byte> bytes) ;
}
