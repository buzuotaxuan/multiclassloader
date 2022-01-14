package com.po;

/**
 * @Description TODO
 * @createTime 2022年01月04日 16:17:00
 */
public class User {
  public String name;

  public User(String name) {
    this.name = name;
  }

  public String operateName(String name) {
    return name;
  }

  @Override
  public String toString() {
    return "User{" +
        "name='" + name + '\'' +
        '}';
  }

}
