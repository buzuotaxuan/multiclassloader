package com.devy.intercepter;

import com.devy.bootstrap.DefaultMessageHandler;
import javax.annotation.Resource;

/**
 * @Description TODO
 * @createTime 2022年01月14日 10:02:00
 */
public class ModuleLifeAdapter implements ModuleLife{

  @Resource
  public DefaultMessageHandler eventListener;

  @Override
  public void active() {

  }
}
