package main.java.helper;

import java.util.ArrayList;

/**
 * {@summary} SHOULD ALSO IMPLEMENT A FUNCTION public boolean equal(T other);
 * @param <T>
 */
public interface ByteSerializable<T> {
	public ArrayList<Byte> encode();
	
}
