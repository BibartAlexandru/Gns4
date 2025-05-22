package com.gns4.other;

import java.awt.Frame;
import java.time.Duration;
import java.time.Instant;

import com.gns4.agents.DeviceAgent;
import com.gns4.networking_messages.FrameHeader;
import com.gns4.networking_messages.FrameTrailer;
import com.gns4.networking_messages.ICMPMessageType;
import com.gns4.networking_messages.ICMPPacket;
import com.gns4.networking_messages.Layer2Payload;
import com.gns4.networking_messages.Packet;
import com.gns4.networking_messages.PacketHeader;

public class PingingThread extends Thread{

  private byte[] ip ;
  private static final int timeout = 1 ;
  private Instant lastPingTime;
  private int  timesPinged ;
  private DeviceAgent device ;
  private Interface from ;
  private MAC toMAC ; 
  // NEED TO CALL THIS FUNCTION WHEN DEVICE AGENT RECEIVES A ECHO REPLY
  public int repliesReceived ;
  public byte[] getIp() {
	return ip;
}

public void setIp(byte[] ip) {
	this.ip = ip;
}

// TODO: 
private void sendICMPEcho(){
  ICMPPacket echo = new ICMPPacket(ICMPMessageType.ECHO, "Hello") ;
  PacketHeader pHeader = new PacketHeader(this.from.getIpv4(), new IPv4NetworkAddress(ip, new byte[]{0,0,0,0}));
  pHeader.setProtocol((byte)1);
  FrameHeader fHeader = new FrameHeader(toMAC, from.getMac());
  try{
    FrameTrailer fTrailer = new FrameTrailer(FrameTrailer.FCS_0) ;
    Packet p = new Packet(pHeader, echo ) ;
    // Frame f = new Frame(fHeader,p, fTrailer) ;
  }
  catch(Exception e){
    // Never reaches
  }
}

public PingingThread(DeviceAgent device, Interface from,  MAC toMac, byte[] ip){
    super(); 
    this.ip = ip ;
    this.lastPingTime = null ;
    this.timesPinged = 0 ;
    this.repliesReceived = 0 ;
    this.device = device ;
    this.from = from ;
    this.toMAC = toMAC ;
  }

  public void run(){
    while (this.timesPinged < 4) {
      if(Duration.between(lastPingTime, Instant.now()).getSeconds() > timeout) 
      {
          sendICMPEcho();
          lastPingTime = Instant.now() ;
      }
    } 
  }
}
