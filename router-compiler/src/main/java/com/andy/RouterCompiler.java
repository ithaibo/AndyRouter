package com.andy;

import com.andy.annotation.Route;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by Andy on 2017/10/10.
 */

@AutoService(Processor.class)
@SupportedAnnotationTypes("com.andy.annotation.Route")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RouterCompiler extends AbstractProcessor {
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations != null && annotations.size() > 0) {
            //add a method named gatherActivityClass, used to put Class[Activity] into map.
            MethodSpec.Builder loadIntoMethodBuilder = MethodSpec.methodBuilder(Constants.NAME_METHOD_COLLECT_ACTIVITY_CLASS)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(
                            ParameterSpec.builder(
                                    ParameterizedTypeName.get(
                                            ClassName.get(Map.class),
                                            ClassName.get(String.class),
                                            ClassName.get(Object.class)),
                                    Constants.NAME_PARAM_MAP_ACTIVITY_NODE)
                                    .build());

            //add activity class into actNode
            Set<? extends Element> routeElements = roundEnv.getElementsAnnotatedWith(Route.class);
            for (Element element : routeElements) {
                loadIntoMethodBuilder.addStatement(
                        Constants.NAME_PARAM_MAP_ACTIVITY_NODE +".put($S, $T.class)",
                        element.getAnnotation(Route.class).path(),
                        ClassName.get((TypeElement) element)
                );
            }

            //produce the source java file
            try {
                JavaFile.builder(
                        "com.andy.router",
                        TypeSpec.classBuilder(Constants.NAME_CLASS_ROUTER_WAREHOUSE)
                                .addModifiers(Modifier.PUBLIC)
                                .addMethod(loadIntoMethodBuilder.build())
                                .build()
                ).build().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
