package com.test;

import com.po.User;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import com.sun.tools.javac.util.Assert;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @createTime 2022年01月04日 16:17:00
 */
public class Main {




  public  Map<String, String> pids(){
    List<VirtualMachineDescriptor> list = VirtualMachine.list();
    Map<String, String> map = list.stream().collect(
        Collectors.toMap(VirtualMachineDescriptor::id, VirtualMachineDescriptor::displayName));
    return map;
  }

  public void attach(String pid) {
    Assert.checkNonNull(pid);
    VirtualMachine virtualMachine =null;
    try {
      File agentFile = new File("/Users/devy/javaworkspace/multiclassloader/deploy/agent-core-1.0-SNAPSHOT-jar-with-dependencies.jar");
      virtualMachine= VirtualMachine.attach(pid);
      virtualMachine.loadAgent(agentFile.getAbsolutePath());
     //
    } catch (AttachNotSupportedException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (AgentLoadException e) {
      e.printStackTrace();
    } catch (AgentInitializationException e) {
      e.printStackTrace();
    }finally {
      try {
        virtualMachine.detach();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {

    Main pidService = new Main();
    Map<String, String> pids = pidService.pids();
    pids.forEach((k,v)->{
      if (v.endsWith("Main")){
        pidService.attach(k);
        return;
      }
    });

    User user = new User("haha");
    user.operateName("你好");
    System.out.println( "hahahah"+Thread.currentThread().getContextClassLoader());

    User use2 = new User("haha");



    User use3 = new User("haha");

  }
}
