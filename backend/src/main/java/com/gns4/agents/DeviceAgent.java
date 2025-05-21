package com.gns4.agents;

import com.gns4.helper.ByteSerializable;
import com.gns4.helper.Helper;
import com.gns4.networking_messages.decoders.FrameDecoder;
import com.gns4.other.ARPTable;
import com.gns4.other.AgentInterface;
import com.gns4.other.Interface;
import com.gns4.other.RoutingTable;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;

public class DeviceAgent extends Agent {
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

    onLoaded();
  }

  protected void onLoaded() {}

  protected void onInterfaceDisconnect(Interface interf) {
    interf.setConnectedTo(null);
    interf.setIpv4(null);
    interf.setStatusLogical(false);
  }

  /*
   * Assumes myInterf exists
   **/
  protected void connectTo(String myInterf, AgentInterface to)
  {
    Interface mine = interfaces.stream().filter(interface_ -> interface_.getName() == myInterf).findFirst().get();
    mine.setConnectedTo(to);
    
  }

  /**
   * @param f
   * @param to
   * Sends encoded as byte[]
   */
  protected <T> void sendByteSerializable(ByteSerializable<T> f, Interface to) {
    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
    byte[] msgContent = Helper.toByteArr(f.encode());
    msg.setByteSequenceContent(msgContent);
    msg.addReceiver(to.getAgent().getAID());
    send(msg);
  }

  public boolean hasInterface(String interf){
    return interfaces.stream()
      .filter(interf_ -> interf_.getName() == interf )
      .findAny()
      .isPresent();
  }

  public boolean hasEmptyInterface(String interf){
    return interfaces.stream()
      .filter(interf_ -> interf_.getName() == interf && interf_.getConnectedTo() == null )
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
