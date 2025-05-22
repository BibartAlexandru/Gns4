package com.gns4.agents;

import com.gns4.helper.ByteSerializable;
import com.gns4.helper.Helper;
import com.gns4.networking_messages.ARPPacket;
import com.gns4.networking_messages.DHCPDiscover;
import com.gns4.networking_messages.Frame;
import com.gns4.networking_messages.FrameHeader;
import com.gns4.networking_messages.FrameTrailer;
import com.gns4.networking_messages.Packet;
import com.gns4.networking_messages.PacketHeader;
import com.gns4.networking_messages.decoders.FrameDecoder;
import com.gns4.networking_messages.decoders.PacketDecoder;
import com.gns4.other.ARPTable;
import com.gns4.other.AgentInterface;
import com.gns4.other.IPv4NetworkAddress;
import com.gns4.other.Interface;
import com.gns4.other.MAC;
import com.gns4.other.RoutingTable;

import de.vandermeer.asciitable.AsciiTable;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeviceAgent extends Agent {
  public static final Logger logger = Logger.getMyLogger(PCAgent.class.getName());
  protected ArrayList<Interface> interfaces;
  // destIp -> int name
  protected RoutingTable routingTable;
  protected ARPTable arpTable;

  public ARPTable getArpTable() {
    return arpTable;
  }

  public void setArpTable(ARPTable arpTable) {
    this.arpTable = arpTable;
  }

  public DeviceAgent() {
    super();
  }

  public RoutingTable getRoutingTable() {
    return routingTable;
  }

  public void setRoutingTable(RoutingTable routingTable) {
    this.routingTable = routingTable;
  }

  public ArrayList<Interface> getInterfaces() {
    return interfaces;
  }

  public void setInterfaces(ArrayList<Interface> interfaces) {
    this.interfaces = interfaces;
  }

  /**
   * @param interf
   * @return whether the toggle succeeded
   */
  public boolean toggleInterfacePhysicalStatus(Interface interf) {
    if (!interfaces.contains(interf)) {
      System.out.println("Agent " + getLocalName() + " does not have interface " + interf);
      return false;
    }

    interf.togglePhysicalStatus();
    return true;
  }

  protected void setup() {
    arpTable = new ARPTable();
    routingTable = new RoutingTable() ;
    onLoaded();
  }

  protected void onLoaded() {}

  protected void onInterfaceDisconnect(Interface interf) {
    logger.info("Disconnected interface: " + interf.getName());
    interf.setConnectedTo(null);
    interf.setIpv4(IPv4NetworkAddress.ZERO);
  }

  protected Frame buildDHCPDiscoverFrame(Interface src){
    var fHeader = new FrameHeader(src.getMac(), MAC.MAC_BROADCAST) ;
    try{
      var fTrailer = new FrameTrailer(new ArrayList<>(List.of((byte)0, (byte)0, (byte)0, (byte)0)));
      var dhcpDiscover = new DHCPDiscover() ;
      var pHeader = new PacketHeader(IPv4NetworkAddress.ZERO, IPv4NetworkAddress.IP_BROADCAST);
      var packet = new Packet(pHeader, dhcpDiscover) ;
      var frame = new Frame(fHeader, packet, fTrailer) ;
      return frame ;
    }
    catch(Exception e){
      // Frame Trailer can be of size != 4 
      e.printStackTrace();
    }
    return null ;
  }

  /*
   * Assumes myInterf exists
   **/
  protected void connectTo(String myInterf, AgentInterface to)
  {
    Interface mine = interfaces.stream().filter(interface_ -> interface_.getName().equals(myInterf)).findFirst().get();
    mine.setConnectedTo(to);
    
  }

  /**
   * @param f
   * @param to
   * Sends f as byte[]
   * Appends the interface it sends to in the Content of the ACL message  ;
   */
  protected <T> void sendByteSerializable(ByteSerializable<T> f, Interface to) {
    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
    byte[] msgContent = Helper.toByteArr(f.encode());
    msg.setByteSequenceContent(msgContent);
    msg.setContent(to.getName());
    msg.addReceiver(to.getAgent().getAID());
    send(msg);
  }

  public void ping(byte[] address){
    // logger.warning("Pinging ");
  }

  public void addIp(String interfaceName, IPv4NetworkAddress ip){
    System.out.println("Adding ip: " + ip.toString() + " to interface: " + interfaceName);
    getInterfaceByName(interfaceName).setIpv4(ip);
    System.out.println("Address after add: " + getInterfaceByName(interfaceName).getIpv4().toString());
  }

  public Interface getInterfaceByName(String interfaceName){
    Optional<Interface> possibleInterf = interfaces.stream().filter(interface_ -> interface_.getName().equals(interfaceName)).findFirst();
    if(possibleInterf.isEmpty())
      return null ;
    return possibleInterf.get();
  }


  // Will return the Frame if it is not dropped.
  // Will return null if it is dropped.
  public Frame parseFrame(Frame f, String receivedInterfaceName){
    Interface receivedInterface = interfaces.stream().
filter(interface_ -> interface_.getName() == receivedInterfaceName).findFirst()
.get() ;
      if(!f.getHeader().getDstMac().equals(receivedInterface.getMac()))
        return null;
    if(f.getHeader().getType() == FrameHeader.TYPE_FIELD_IPv4){
      Packet p = (Packet) f.getPayload() ;
      if(!p.getHeader().getDestIp().equals(receivedInterface.getIpv4()))
        return null ;
      return f ;
    }  
    else{
      ARPPacket aP = (ARPPacket) f.getPayload() ;
      //TODO:
    }
    return null ;
  }

  public boolean hasInterface(String interf){
    return interfaces.stream()
      .filter(interf_ -> interf_.getName().equals(interf))
      .findAny()
      .isPresent();
  }

  public void printInterfaces(){
    AsciiTable output = new AsciiTable() ;
    output.addRule();
    output.addRow("Interface Name", "Status", "Address", "Connected To");
    output.addRule();
    for(Interface i: interfaces) 
        output.addRow(i.prettyPrint());
    output.addRule();
    System.out.println(output.render());
  }

  public void printInterface(String name){
    AsciiTable output = new AsciiTable() ;
    output.addRule();
    output.addRow("Interface Name", "Status", "Address", "Connected To");
    output.addRule();
    Interface i = interfaces.stream().filter(interface_ -> interface_.getName().equals(name)).findFirst().get();
    output.addRow(i.prettyPrint());
    output.addRule();
    System.out.println(output.render());
  }

  public boolean hasEmptyInterface(String interf){
    return interfaces.stream()
      .filter(interf_ -> interf_.getName().equals(interf) && interf_.getConnectedTo() == null )
      .findAny()
      .isPresent();
  }


  private void handleMessage(byte[] msg) {
    var frameDec = new FrameDecoder();
    try {
      var frame = frameDec.decode(Helper.toByteArrList(msg));
    } catch (Exception e) {
      System.out.println("Agent: " + getLocalName() + " failed to parse frame: " + msg);
    }
  }
}
