package com.gns4.agents;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import com.gns4.helper.ByteSerializable;
import com.gns4.helper.Helper;
import com.gns4.networking_messages.decoders.FrameDecoder;
import com.gns4.other.Interface;
import com.gns4.other.MAC;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;


public class DeviceAgent extends Agent {
	 protected ArrayList<Interface> interfaces;
	 // destIp -> int name
	 protected HashMap<InetAddress, String> routingTable;
	 protected HashMap<InetAddress, MAC> arpTable;
	 
	 /**
	  * 
	  * @param f
	  * @param dstMac
	  * @param to
	  * 
	  * Sends encoded as byte[]
	  */
	 private<T> void sendByteSerializable(ByteSerializable<T> f, Interface to) {
		 ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		 byte[] msgContent = Helper.toByteArr(f.encode());
		 msg.setByteSequenceContent(msgContent);
		 msg.addReceiver(to.getAgent().getAID());
		 send(msg);
	 }
	 
	 private void handleMessage(byte[] msg) {
		 var frameDec = new FrameDecoder();
		 try {
			 var frame = frameDec.decode(Helper.toByteArrList(msg));
		 }
		 catch(Exception e) {
			 System.out.println("Agent: " + getLocalName() + " failed to parse frame: " + msg);
		 }
	 }
	 
	 protected void setup() {
		 
		 onLoaded();
	 }
	 
	 protected void onLoaded() {
		 
	 }
	 
	 protected void onInterfaceDisconnect(Interface interf) {
		 interf.setConnectedTo(null);
		 interf.setIpv4(null);
		 interf.setStatusLogical(false);
	 }
	 
	public ArrayList<Interface> getInterfaces() {
		return interfaces;
	}
	
	public void setInterfaces(ArrayList<Interface> interfaces) {
		this.interfaces = interfaces;
	}
	public HashMap<InetAddress, String> getRoutingTable() {
		return routingTable;
	}
	public void setRoutingTable(HashMap<InetAddress, String> routingTable) {
		this.routingTable = routingTable;
	}
	public HashMap<InetAddress, MAC> getArpTable() {
		return arpTable;
	}
	public void setArpTable(HashMap<InetAddress, MAC> arpTable) {
		this.arpTable = arpTable;
	}
	
	/**
	 * 
	 * @param interf
	 * @return whether the toggle succeeded
	 */
	public boolean toggleInterfacePhysicalStatus(Interface interf) {
		if(!interfaces.contains(interf)) {
			System.out.println("Agent " + getLocalName() + " does not have interface " + interf);
			return false;
		}
		
		interf.togglePhysicalStatus();
		return true;
	}
	
	public DeviceAgent() {
		super();
	}
}
