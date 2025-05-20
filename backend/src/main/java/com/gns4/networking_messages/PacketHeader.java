package com.gns4.networking_messages;

import com.gns4.helper.ByteSerializable;
import com.gns4.helper.Helper;
import com.gns4.networking_messages.decoders.PacketHeaderDecoder;
import com.gns4.other.IPv4NetworkAddress;
import java.util.ArrayList;

/** 9 bytes */
public class PacketHeader implements ByteSerializable<PacketHeader> {

  public static final int SIZEOF_INT = 4;
  public static final int SIZEOF_BYTE = 1;
  public static final int HEADER_CHECKSUM_LEN = 2;
  public static final int NR_BYTES =
      2 * IPv4NetworkAddress.NR_BYTES + HEADER_CHECKSUM_LEN + 3 * SIZEOF_BYTE + SIZEOF_INT * 5;

  public PacketHeader(IPv4NetworkAddress sourceIp, IPv4NetworkAddress destIp) {
    super();
    this.version = 4;
    this.ihl = NR_BYTES;
    this.totalLength = NR_BYTES;
    this.identification = 0;
    this.flags = (byte) 0;
    this.fragmentOffset = 0;
    this.ttl = 64;
    this.protocol = (byte) 1; // ICMP
    this.headerChecksum = new byte[] {0, 0};
    this.sourceIp = sourceIp;
    this.destIp = destIp;
  }

  public PacketHeader(
      byte version,
      int identification,
      byte flags,
      int fragmentOffset,
      int ttl,
      byte protocol,
      byte[] headerChecksum,
      IPv4NetworkAddress sourceIp,
      IPv4NetworkAddress destIp)
      throws Exception {
    super();
    this.version = version;
    this.identification = identification;
    this.flags = flags;
    this.fragmentOffset = fragmentOffset;
    this.protocol = protocol;
    this.headerChecksum = headerChecksum;
    this.ttl = ttl;
    this.sourceIp = sourceIp;
    this.destIp = destIp;
    this.ihl = NR_BYTES / 4;
    this.totalLength = NR_BYTES;
  }

  public PacketHeader(
      byte version,
      int ihl,
      int totalLength,
      int identification,
      byte flags,
      int fragmentOffset,
      int ttl,
      byte protocol,
      byte[] headerChecksum,
      IPv4NetworkAddress sourceIp,
      IPv4NetworkAddress destIp)
      throws Exception {
    super();
    this.version = version;
    this.ihl = ihl;
    this.totalLength = totalLength;
    this.identification = identification;
    this.flags = flags;
    this.fragmentOffset = fragmentOffset;
    this.ttl = ttl;
    this.protocol = protocol;
    this.headerChecksum = headerChecksum;
    this.sourceIp = sourceIp;
    this.destIp = destIp;
  }

  private IPv4NetworkAddress sourceIp;
  private IPv4NetworkAddress destIp;

  /** Between 0-255 */
  private byte version;

  private int ihl;
  private int totalLength;
  private int identification;
  private byte flags;
  private int fragmentOffset;
  private int ttl;
  private byte protocol;
  private byte[] headerChecksum = new byte[HEADER_CHECKSUM_LEN];

  public byte getVersion() {
    return version;
  }

  public void setVersion(byte version) {
    this.version = version;
  }

  public int getIhl() {
    return ihl;
  }

  public void setIhl(int ihl) {
    this.ihl = ihl;
  }

  public int getTotalLength() {
    return totalLength;
  }

  public void setTotalLength(int totalLength) {
    this.totalLength = totalLength;
  }

  public int getIdentification() {
    return identification;
  }

  public void setIdentification(int identification) {
    this.identification = identification;
  }

  public byte getFlags() {
    return flags;
  }

  public void setFlags(byte flags) {
    this.flags = flags;
  }

  public int getFragmentOffset() {
    return fragmentOffset;
  }

  public void setFragmentOffset(int fragmentOffset) {
    this.fragmentOffset = fragmentOffset;
  }

  public byte getProtocol() {
    return protocol;
  }

  public void setProtocol(byte protocol) {
    this.protocol = protocol;
  }

  public byte[] getHeaderChechsum() {
    return headerChecksum;
  }

  public void setHeaderChechsum(byte[] headerChecksum) {
    this.headerChecksum = headerChecksum;
  }

  public IPv4NetworkAddress getDestIp() {
    return destIp;
  }

  public void setDestIp(IPv4NetworkAddress destIp) {
    this.destIp = destIp;
  }

  public IPv4NetworkAddress getSourceIp() {
    return sourceIp;
  }

  public void setSourceIp(IPv4NetworkAddress sourceIp) {
    this.sourceIp = sourceIp;
  }

  public int getTtl() {
    return ttl;
  }

  public void setTtl(int ttl) throws Exception {
    if (ttl >= 256 || ttl < 0) throw new Exception("TTL must be between 0 and 255");
    this.ttl = ttl;
  }

  public boolean equals(PacketHeader other) {
    return sourceIp.equals(other.getSourceIp())
        && destIp.equals(other.getDestIp())
        && ttl == other.getTtl();
  }

  @Override
  public ArrayList<Byte> encode() {
    var res = new ArrayList<Byte>();
    res.add(version);
    res.addAll(Helper.intToByteArrayList(ihl));
    res.addAll(Helper.intToByteArrayList(totalLength));
    res.addAll(Helper.intToByteArrayList(identification));
    res.add(flags);
    res.addAll(Helper.intToByteArrayList(fragmentOffset));
    res.addAll(Helper.intToByteArrayList(ttl));
    res.add(protocol);
    res.addAll(Helper.toByteArrList(headerChecksum));
    res.addAll(sourceIp.encode());
    res.addAll(destIp.encode());

    return res;
  }

  public static void main(String[] args) throws Exception {
    var ip1 = new IPv4NetworkAddress(new byte[] {0, 0, 0, 0}, new byte[] {0, 0, 0, 0});
    var ip2 = new IPv4NetworkAddress(new byte[] {0, 0, 0, 0}, new byte[] {0, 0, 0, 0});
    var p = new PacketHeader(ip1, ip2);
    var pDec = new PacketHeaderDecoder();
    var end = p.encode();
    var p2 = pDec.decode(p.encode());
    if (!p.equals(p2)) throw new Exception("FAILED");
  }
}
