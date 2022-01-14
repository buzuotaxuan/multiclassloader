package com.devy.load;

import com.devy.bootstrap.DefaultMessageHandler;
import com.devy.intercepter.ExtensionModule;

public interface ModuleLoadCallback {
  void onLoad(ExtensionModule moduleClass, DefaultMessageHandler eventListener) throws Throwable;
}
