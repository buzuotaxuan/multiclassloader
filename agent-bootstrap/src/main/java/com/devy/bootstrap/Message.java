package com.devy.bootstrap;


/**
 * @Description TODO
 * @createTime 2022年01月11日 16:55:00
 */
public class Message {
  private static MessageHandler messageHandler;
  public static void init(final MessageHandler handler) {
    messageHandler = handler;
  }
    public static void  invokeBefore(final Object[] argumentArray, final String listenerClass//只是为了排查时更加方便,所以在字节码增强时将注入的 listener 类名也写到字节码中
       ){
      try {
        messageHandler.handleOnBefore(argumentArray,listenerClass);
      } catch (Throwable throwable) {
        throwable.printStackTrace();
      }
    }
}
