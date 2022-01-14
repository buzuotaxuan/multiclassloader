package com.devy.test;

import com.devy.intercepter.ExtensionModule;
import com.devy.intercepter.ModuleLifeAdapter;
import com.devy.listener.PersionListener;
import java.util.ArrayList;
import java.util.List;
import org.kohsuke.MetaInfServices;

/**
 * @Description TODO
 * @createTime 2022年01月12日 19:09:00
 */
@MetaInfServices(ExtensionModule.class)
public class Persion extends ModuleLifeAdapter implements ExtensionModule {

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  private String address;

  @Override
  public void active() {

    List list=new ArrayList();
    list.add(new PersionListener("operateName","before","com.po.User"));
      eventListener.put("com.po.User",list);
  }
}
