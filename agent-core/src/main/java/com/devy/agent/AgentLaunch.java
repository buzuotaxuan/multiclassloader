package com.devy.agent;

import com.devy.classloader.AgentClassLoader;
import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.jar.JarFile;
import sun.misc.Launcher;

/**
 * @Description TODO
 * @createTime 2022年01月04日 15:51:00
 */
public class AgentLaunch {

  public static void premain(final String agentArgs, final Instrumentation instrumentation) {
    try {
      start(agentArgs, instrumentation);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }


  public static void agentmain(String agentArgs, Instrumentation inst) {
    try {
      start(agentArgs, inst);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public static void start(String agentArgs,Instrumentation inst) throws Exception{

    try {
      File file = new File(
          "/Users/devy/javaworkspace/multiclassloader/deploy/boot/agent-bootstrap-1.0-SNAPSHOT.jar");
      inst.appendToBootstrapClassLoaderSearch(new JarFile(file));
      Launcher.getBootstrapClassPath().addURL(file.toURI().toURL());

    } catch (IOException e) {
      e.printStackTrace();
    }

    File file = new File(
        "/Users/devy/javaworkspace/multiclassloader/deploy/module/agent-module-1.0-SNAPSHOT-jar-with-dependencies.jar");

    AgentClassLoader agentClassLoader = new AgentClassLoader(new URL[]{file.toURI().toURL()},Instrumentation.class.getClassLoader());
    Class coreLauncherOfClass = agentClassLoader.loadClass("com.devy.Launch");
    Constructor constructor = coreLauncherOfClass.getConstructor(Instrumentation.class,ClassLoader.class);

    Object coreLauncherOfInstance = constructor.newInstance(inst,Instrumentation.class.getClassLoader());

    Method startMethod = coreLauncherOfClass.getDeclaredMethod("start");
    startMethod.invoke(coreLauncherOfInstance);



  }
}
