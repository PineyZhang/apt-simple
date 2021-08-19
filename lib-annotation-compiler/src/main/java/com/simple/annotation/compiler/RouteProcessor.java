package com.simple.annotation.compiler;

import com.google.auto.service.AutoService;
import com.simple.annotation.Route;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

/**
 * AbstractProcessor 注释处理器，主要作用于在编译时期，根据相应的注解生成 java 代码文件。
 */
@AutoService(Processor.class)
public class RouteProcessor extends AbstractProcessor {

    Filer filer;

    /**
     * 处理注解，生成代码文件
     * @param annotations
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 获取所有使用 @Route 注解的对象
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(Route.class);
        Map<String, String> map = new HashMap<>();
        // 遍历所有使用过 Route 注解的节点
        for (Element e: elementsAnnotatedWith){
            TypeElement typeElement =  (TypeElement) e;
            // 获取每个 Activity 的 Route 注解
            Route annotation = typeElement.getAnnotation(Route.class);
            // 获取注解的值 path，如：one/OneActivity
            String path = annotation.path();
            // 获取全路径类名
            Name qualifiedName = typeElement.getQualifiedName();
            map.put(path, qualifiedName + ".class");
        }
        System.out.println("map size:" + map.size());
        if (map.size() > 0){
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()){
                String activityKey = iterator.next();
                String cls = map.get(activityKey);
                // 使用 javapoet 创建 java 代码
                MethodSpec methodSpec = MethodSpec.methodBuilder("putActivity")
                        .addModifiers(Modifier.PUBLIC)
                        .returns(void.class)
                        .addStatement("ARouter.getInstance().putActivity("+"\""+activityKey+"\","+cls+")")
                        .build();
                final ClassName interfaceName = ClassName.get("com.simple.arouter.api","IRouter");
                TypeSpec typeSpec = TypeSpec.classBuilder("ActivityUtil"+System.currentTimeMillis())
                        .addSuperinterface(interfaceName)
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(methodSpec)
                        .build();

                JavaFile javaFile = JavaFile.builder("com.simple.arouter.api", typeSpec).build();

                try {
                    javaFile.writeTo(filer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    /**
     * 指定使用的java版本
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    /**
     * 声明处理的注解类型，如：@Route
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> types = new HashSet<>();
        types.add(Route.class.getCanonicalName());
        return types;
    }
}
