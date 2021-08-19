package com.simple.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在@interface Route上添加了两个元注解，@Target和@Retention：
 * @Target：用于描述注解的使用范围，并且内部声明了一个ElementerType[]数组，而值有多种ElementType，分别如下：
 *
 * TYPE：用于类、接口（包括注解类型）或枚举声明；
 * FIELD：用于字段声明（包括枚举常量）；
 * METHOD：用于方法声明；
 * PARAMETER：用于方法参数声明；
 * CONSTRUCTOR：用于构造函数声明；
 * LOCAL_VARIABLE：用于局部变量声明；
 * ANNOTATION_TYPE：用于注解类型声明；
 * PACKAGE：用于包声明；
 * TYPE_PARAMETER：Java 1.8引入，用于类型参数声明；
 * TYPE_USE：Java 1.8引入，用于各种类型。
 *
 *
 * @Retention：用于描述注解的保留时间，内部存在一个枚举RetentionPolicy，分别如下：
 *
 * SOURCE：注解只在源文件中存在，编译时即不存在；
 * CLASS：注解保留到 class文件中，运行时便不存在；
 * RUNTIME：注解将会一直保留到运行时。
 * 注解保留时间SOURCE << CLASS << RUNTIME
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Route {

    String path() default "";
}
