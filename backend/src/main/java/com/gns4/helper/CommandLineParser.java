package com.gns4.helper;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.runtime.debug.Profiler.DecisionEvent;

import com.gns4.agents.DeviceAgent;
import com.gns4.agents.PCAgent;
import com.gns4.agents.RouterAgent;
import com.gns4.other.IPv4NetworkAddress;
import com.gns4.other.Interface;
import com.gns4.other.MAC;

import de.vandermeer.asciitable.AsciiTable;
import jade.util.Logger;

public class CommandLineParser extends Thread{
  public static final Logger logger = Logger.getMyLogger(Thread.class.getName());
  public static final String[] classesWithLogger = {
    "com.gns4.agents.PCAgent",
    "com.gns4.agents.RouterAgent"
  } ;

  public static PCAgent findPc(){
    PCAgent pc = null ;
    for(var entry : PCAgent.agents.entrySet())
      // System.out.println(entry.getKey());
      pc = entry.getValue() ;
      return pc ;
  }

  public static RouterAgent findRouter(String name){
    RouterAgent r = null ;
    for(var entry : RouterAgent.agents.entrySet())
      if(entry.getKey().contains(name))
      {
        r = entry.getValue() ;
        break ;
      }
      return r ;
  }

  public static DeviceAgent findDevice(String name){
    if(!name.contains("R1")
    && !name.contains("R2")
    && !name.contains("R3")
    && !name.contains("R4")
    && !name.contains("PC"))
      return null;
    if(name.contains("PC"))
      return findPc();
    return findRouter(name);
  }
   
  public static void addARPEntryForAllDevices(MAC mac, IPv4NetworkAddress ip){
    PCAgent pc = findPc() ;
    for(var entry : RouterAgent.agents.entrySet()){
      RouterAgent r = entry.getValue(); 
      r.getArpTable().add(ip, mac);
    }
    pc.getArpTable().add(ip, mac);
  }
    
  /**
   * Checks the command & pings if it is a ping
   */
  public void checkPing(String command){
      Pattern pattern = Pattern.compile("\\s*(\\w+)\\s+ping\\s+(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\s*)");
      Matcher m = pattern.matcher(command);
      if(m.matches() == false)
      {
        // System.out.println("Invalid ping. Should be of the form: <PC|R1|R2|R3|R4> ping <IP>");
        return ;
      }
      String from = m.group(1) ;
      String ip = m.group(2) ;
      DeviceAgent device = findDevice(from) ;
      try{
        byte[] ipAdd = IPv4NetworkAddress.ipFromString(ip);  
        device.ping(ipAdd) ;
      }
      catch (Exception e){
        e.printStackTrace();
      }
  }

  /**
   * 
   * @syntax connect <from> <to_device> <interface>
   * @example connect PC R2 g0/3
   */ 
  public void checkConnectPc(String command){
    // logger.warning("Checking for connect command");
    // logger.warning("Command is: " + command);
    Pattern p = Pattern.compile("connect\\s+(\\w+)\\s+(\\w+)\\s+(\\w+(?:\\/\\d{1,2})?)\\s*"); 
    Matcher m = p.matcher(command);
    if(!m.matches())
      return ;
    // logger.warning("Command is a connect command");
    String from = m.group(1) ;
    String toDevice = m.group(2) ;
    String toInterface = m.group(3) ;
    switch (from) {
      case "PC":
        PCAgent pc = findPc() ; 
        if(pc != null)
          pc.connectToRouter(toDevice, toInterface);
        else 
          logger.warning("pc is null");
        break;
      case "R1":
        break ;
      case "R2":
        break ;
      case "R3":
        break ;
      case "R4":
        break ;
      default:
        break;
    }
  }

  /*
    @syntax ip address add <ip>/<subnet> <Device> <interface>
  */
  public void checkAddAddress(String command){
    Pattern p = Pattern
    .compile("ip\\s+address\\s+add\\s+(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})\\/(\\d{1,2})\\s+(\\w+)\\s+(\\w+(?:\\/\\w+)?)") ;
    Matcher m = p.matcher(command) ;
    if(!m.matches())
      return ;
    String ipString = m.group(1) ;
    String subnetMaskString = m.group(2) ;
    String deviceName = m.group(3) ;
    String interfaceName = m.group(4) ;
    System.out.println("Device is: " + deviceName);
    DeviceAgent device = CommandLineParser.findDevice(deviceName) ;
    if(device == null){
      logger.warning("Device: " + deviceName + " does not exist!");
      return ;
    }
    try{
      byte[] ipAdd = IPv4NetworkAddress.ipFromString(ipString) ;
      int subnetMask = Integer.parseInt(subnetMaskString);
      IPv4NetworkAddress ip = new IPv4NetworkAddress(ipAdd, IPv4NetworkAddress.intToSubnetByteArray(subnetMask));
      device.addIp(interfaceName, ip);
      addARPEntryForAllDevices(device.getInterfaceByName(interfaceName).getMac(), ip);
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  /**
   * @syntax show interfaces <device> <interface>
   * Or show interfaces <device>
   */
  public void checkShowInterfaces(String command){
    Pattern p = Pattern.compile("\\s*show\\s+interfaces\\s+(\\w+)(\\s+(\\w+(?:\\/\\d{1,2})?)\\s*)?") ;
    Matcher m = p.matcher(command);
    if(!m.matches())
      return;
    String deviceName = m.group(1);
    DeviceAgent dev = findDevice(deviceName) ;
    if(dev == null){
      logger.warning("Device: " + deviceName + " does not exist!");
      return ;
    }
    if(m.group(3) == null){
      dev.printInterfaces();
    }
    else{
      String interf = m.group(3);
      dev.printInterface(interf);
    }
  }

  public void run(){
    Scanner scanner = new Scanner(System.in);
    boolean skipThisIteration = false ;
    while(true){
      skipThisIteration = false ;
      String command = scanner.nextLine().trim();
      switch (command) {
        case "q":
          for(var c : classesWithLogger)
            Logger.getMyLogger(c).setLevel(Logger.WARNING);
          skipThisIteration = true ;
          break;
        case "resume logs":
          for(var c : classesWithLogger)
            Logger.getMyLogger(c).setLevel(Logger.INFO);
          skipThisIteration = true ;
        default:
          break;
      }

      if(skipThisIteration)
        continue ;
      checkPing(command);
      checkConnectPc(command);
      checkShowInterfaces(command);
      checkAddAddress(command) ;
    }
  }
  
}
