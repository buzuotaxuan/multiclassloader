package com.devy;

import static org.apache.commons.lang.reflect.FieldUtils.writeField;

import com.devy.bootstrap.DefaultMessageHandler;
import com.devy.classloader.MyClassloader;
import com.devy.intercepter.ExtensionModule;
import com.devy.intercepter.ModuleLifeAdapter;
import com.devy.load.ModulLoad;
import com.devy.load.ModuleLoadCallback;
import com.devy.transformer.FirstTransformer;
import com.devy.load.MessageUtils;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

/**
 * @Description TODO
 * @createTime 2022年01月12日 17:25:00
 */
public class Launch {

    private final Instrumentation instrumentation;

    private final ClassLoader classLoader;

    public Launch(Instrumentation instrumentation,ClassLoader classLoader){
      this.instrumentation=instrumentation;
      this.classLoader=classLoader;
    }

    public void start(){

          ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();

          File file = new File("/Users/devy/javaworkspace/multiclassloader/deploy/usermodule/agent-user-module-1.0-SNAPSHOT-jar-with-dependencies.jar");

          ClassLoader myClassLoader = null;
          try {
            myClassLoader = new MyClassloader(new URL[]{file.toURI().toURL()});

            Thread.currentThread().setContextClassLoader(myClassLoader);
            DefaultMessageHandler eventListener= new DefaultMessageHandler();
            MessageUtils.init(eventListener);
            new ModulLoad().load(new ModuleLoadCallback(){
              @Override
              public void onLoad(ExtensionModule moduleClass, DefaultMessageHandler eventListener) throws Throwable {

                if(moduleClass instanceof ModuleLifeAdapter){
                  ModuleLifeAdapter target =   (ModuleLifeAdapter) moduleClass;
                  for (final Field resourceField : getFieldsListWithAnnotation(target.getClass(), Resource.class)) {
                    final Class<?> fieldType = resourceField.getType();

                    if (DefaultMessageHandler.class.isAssignableFrom(fieldType)) {
                      writeField(
                          resourceField,
                          target,
                          eventListener,
                          true
                      );
                    }
                    target.active();
                  }


                }

              }
            },eventListener);

            instrumentation.addTransformer(new FirstTransformer(eventListener));
          }catch (Exception e){
            e.printStackTrace();
          }finally {
            Thread.currentThread().setContextClassLoader(currentClassLoader);
          }

    }


  private static List<Field> getFieldsListWithAnnotation(final Class<?> cls, final Class<? extends Annotation> annotationCls) {
    final List<Field> allFields = new ArrayList<Field>();
    Class<?> currentClass = cls;
    while (currentClass != null) {
      final Field[] declaredFields = currentClass.getDeclaredFields();
      for (final Field field : declaredFields) {
        if (field.getAnnotation(annotationCls) != null) {
          allFields.add(field);
        }
      }
      currentClass = currentClass.getSuperclass();
    }
    return allFields;
  }
}
