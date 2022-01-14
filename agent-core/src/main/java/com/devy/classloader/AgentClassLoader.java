package com.devy.classloader;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.Enumeration;

/**
 * @Description TODO
 * @createTime 2022年01月12日 17:24:00
 */
public class AgentClassLoader extends URLClassLoader {
  private final static URL[] EMPTY = new URL[0];

  public AgentClassLoader(URL[] urls, ClassLoader parent) {
    super(urls, parent);
  }

  public AgentClassLoader(URL[] urls) {
    super(urls);
  }

  public AgentClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
    super(urls, parent, factory);
  }

  @Override
  public URL getResource(String name) {
    URL url = findResource(name);
    if (null != url) {
      return url;
    }
    return super.getResource(name);
  }

  @Override
  public Enumeration<URL> getResources(String name) throws IOException {
    Enumeration<URL> urls = findResources(name);
    if (null != urls) {
      return urls;
    }
    return super.getResources(name);
  }

  @Override
  protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
    final Class<?> loadedClass = findLoadedClass(name);
    if (loadedClass != null) {
      return loadedClass;
    }

    try {
      Class<?> aClass = findClass(name);
      if (resolve) {
        resolveClass(aClass);
      }
      return aClass;
    } catch (Throwable e) {
      return super.loadClass(name, resolve);
    }
  }

}
