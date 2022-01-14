package com.devy.load;

import com.devy.bootstrap.DefaultMessageHandler;
import com.devy.intercepter.ExtensionModule;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @Description TODO
 * @createTime 2022年01月14日 11:19:00
 */
public class ModulLoad {


  public void load(ModuleLoadCallback moduleLoadCallback, DefaultMessageHandler eventListener){
    final ServiceLoader<ExtensionModule> moduleServiceLoader = ServiceLoader.load(ExtensionModule.class, Thread.currentThread().getContextClassLoader());
    final Iterator<ExtensionModule> it = moduleServiceLoader.iterator();
    while (it.hasNext()) {
      final ExtensionModule module;
      try {
        module = it.next();
        final Class<?> classOfModule = module.getClass();
        moduleLoadCallback.onLoad(module,eventListener);

      } catch (Throwable cause) {
        continue;
      }


    }

  }

}
