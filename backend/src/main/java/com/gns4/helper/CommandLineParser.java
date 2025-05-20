package com.gns4.helper;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gns4.agents.PCAgent;

import jade.util.Logger;

public class CommandLineParser extends Thread{
  public static final Logger logger = Logger.getMyLogger(Thread.class.getName());
  public static final String[] classesWithLogger = {
    "com.gns4.agents.PCAgent",
    "com.gns4.agents.RouterAgent"
  } ;

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

      Pattern pattern = Pattern.compile("\\s*(\\w+)\\s+ping\\s+(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\s*)");
      Matcher m = pattern.matcher(command);
      if(m.matches() == false)
      {
        System.out.println("Invalid ping. Should be of the form <PC|R1|R2|R3|R4> ping <IP>");
        continue ;
      }
      String from = m.group(1) ;
      String ip = m.group(2) ;
      switch (from) {
        case "PC":
          PCAgent pc = null ;
          for(var entry : PCAgent.agents.entrySet()){
            // System.out.println(entry.getKey());
            pc = entry.getValue() ;
        }
          if(pc != null)
            pc.ping();
          else 
            logger.warning("Attempting to ping from PC1, but it is null");
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
  }
  
}
