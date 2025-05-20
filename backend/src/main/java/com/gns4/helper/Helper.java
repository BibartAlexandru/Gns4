package com.gns4.helper;

import java.util.ArrayList;
import java.util.List;

public class Helper {
	public static byte[] toByteArr(List<Byte> bytes) {
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

  public static int listToInt(List<Byte> bytes) throws Exception{
    if(bytes.size() != 4)
      throw new Exception("Attempting to call arrayListToInt on an arraylist of size: " + bytes + "Should be 4 bytes.");
    int res =bytes.get(0) << 24 |bytes.get(1) << 16 |bytes.get(2) << 8 |bytes.get(3) ;
    return res ;
  }

  // TODO: Move function in other class
  public static ArrayList<Byte> intToByteArrayList(int n){
    var res = new ArrayList<Byte>();
    res.add((byte)(n >> 24));
    res.add((byte)(n >> 16));
    res.add((byte)(n >> 8));
    res.add((byte)n );
    return res ;
  }
}
