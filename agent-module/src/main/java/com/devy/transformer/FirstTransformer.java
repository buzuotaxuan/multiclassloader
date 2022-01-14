package com.devy.transformer;

import com.devy.bootstrap.DefaultMessageHandler;
import com.devy.intercepter.AdviceListener;
import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.List;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * @Description TODO
 * @createTime 2022年01月04日 16:19:00
 */
public class FirstTransformer implements ClassFileTransformer {

  private DefaultMessageHandler eventListener;
  public FirstTransformer(DefaultMessageHandler eventListener){
    this.eventListener=eventListener;
  }


  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
      ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

    List<AdviceListener> adviceListeners =eventListener.getAdviceListener(className.replace("/","."));
    if(adviceListeners!=null){
      try {
        ClassPool classPool = ClassPool.getDefault();
        CtClass clazz = classPool.makeClass(new ByteArrayInputStream(classfileBuffer), false);
        for (AdviceListener adviceListener:adviceListeners){
          CtMethod method=clazz.getDeclaredMethod(adviceListener.getMethod());
          if (adviceListener.getIntercepter().equals("before")){
            method.insertBefore("com.devy.bootstrap.Message.invokeBefore($args,this.getClass().getName());");
          }
        }
        return clazz.toBytecode();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
