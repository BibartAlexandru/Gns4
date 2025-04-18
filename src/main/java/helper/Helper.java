package main.java.helper;

import java.util.ArrayList;

public class Helper {
	public static byte[] toByteArr(ArrayList<Byte> bytes) {
		byte[] res = new byte[bytes.size()];
		for(int i = 0 ; i < bytes.size(); i++)
			res[i] = bytes.get(i);
		return res;
	}
	
	public static ArrayList<Byte> toByteArrList(byte[] bytes){
		var res = new ArrayList<Byte>();
		for(byte b : bytes)
			res.add(b);
		return res;
	}
}
