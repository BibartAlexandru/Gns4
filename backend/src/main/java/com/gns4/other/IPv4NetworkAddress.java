package com.gns4.other;

import com.gns4.helper.ByteSerializable;
import com.gns4.helper.Helper;
import com.gns4.networking_messages.decoders.IPv4NetworkAddressDecoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPv4NetworkAddress implements ByteSerializable<IPv4NetworkAddress> {
  public static final IPv4NetworkAddress ZERO =
      new IPv4NetworkAddress(new byte[] {0, 0, 0, 0}, new byte[] {0, 0, 0, 0});
  public static final IPv4NetworkAddress IP_BROADCAST =
      new IPv4NetworkAddress(
          new byte[] {(byte) 255, (byte) 255, (byte) 255, (byte) 255},
          new byte[] {(byte) 255, (byte) 255, (byte) 255, (byte) 255});

  public static final int NR_BYTES = 8;

  // byte is signed =(
  private byte[] ip;
  private byte[] subnetMask;

  public static byte[] intToSubnetByteArray(int subnetMask){
    int mask = Integer.MAX_VALUE << (32 - subnetMask) ;
    return Helper.intToByteArray(mask) ;
  }

  public IPv4NetworkAddress(byte[] ip, byte[] subnetMask) {
    super();
    this.setIp(ip);
    this.setSubnetMask(subnetMask);
  }

  public static byte[] ipFromString(String addr) throws Exception{
   Pattern p = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$") ; 
    Matcher m = p.matcher(addr);
    if(!m.matches())
      throw new Exception("Invalid IP address: " + addr + " passed to ipFromString.");
    String n0 = m.group(1) ;
    String n1 = m.group(2) ;
    String n2 = m.group(3) ;
    String n3 = m.group(4) ;
    byte[] res = new byte[4] ;
    res[0] = (byte)Integer.parseInt(n0) ;
    res[1] = (byte)Integer.parseInt(n1) ;
    res[2] = (byte)Integer.parseInt(n2) ;
    res[3] = (byte)Integer.parseInt(n3) ;
    return res;
  }

  public byte[] getIp() {
    return ip;
  }

  public int compareSubnet(IPv4NetworkAddress other) {
    try {
      int thisMask = Helper.toInt(this.subnetMask);
      int otherMask = Helper.toInt(other.getSubnetMask());
      if (thisMask == otherMask) return 0;
      if (thisMask > otherMask) return 1;
      return -1;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  @Override
  public String toString(){
   return String.valueOf((int)(ip[0] & 0xff)) + "." + 
          String.valueOf((int)(ip[1] & 0xff)) + "." +
          String.valueOf((int)(ip[2] & 0xff)) + "." +
          String.valueOf((int)(ip[3] & 0xff)) + "/" + 
          String.valueOf((int)(subnetMask[0] & 0xff)) + "." +
          String.valueOf((int)(subnetMask[1] & 0xff)) + "." +
          String.valueOf((int)(subnetMask[2] & 0xff)) + "." +
          String.valueOf((int)(subnetMask[3] & 0xff)); 
  }

  public void setIp(byte[] ip) {
    this.ip = ip;
  }

  public byte[] getSubnetMask() {
    return subnetMask;
  }

  public void setSubnetMask(byte[] subnetMask) {
    this.subnetMask = subnetMask;
  }

  /**
   * @return ip(4 bytes) smask(4 bytes)
   */
  @Override
  public ArrayList<Byte> encode() {
    var res = new ArrayList<Byte>();
    for (byte b : ip) res.add(b);
    for (byte b : subnetMask) res.add(b);
    return res;
  }

  public boolean equals(IPv4NetworkAddress other) {
    for (int i = 0; i < 4; i++)
      if (ip[i] != other.getIp()[i] || subnetMask[i] != other.getSubnetMask()[i]) return false;
    return true;
  }

  public static void main(String[] args) throws Exception {
    IPv4NetworkAddress i1 =
        new IPv4NetworkAddress(new byte[] {0, (byte) 255, 0, 0}, new byte[] {0, (byte) 255, 0, 0});
    var dec = new IPv4NetworkAddressDecoder();
    IPv4NetworkAddress i2 = dec.decode(i1.encode());
    if (!i1.equals(i2)) throw new Exception("FAILED");
    //		System.out.println(Byte.toUnsignedInt(i1.ip[1]));
  }
}
