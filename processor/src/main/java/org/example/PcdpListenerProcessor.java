package org.example;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PcdpListenerProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        processPcdpListener(roundEnv.getElementsAnnotatedWith(PcdpListener.class));
        processPcdpListeners(roundEnv.getElementsAnnotatedWith(PcdpListeners.class));
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(List.of("org.example.PcdpListener","org.example.PcdpListeners"));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_10;
    }

    private void processPcdpListeners(Set<? extends Element> pcdpListenersElements){
        for (Element element : pcdpListenersElements) {
            String packageName = ((PackageElement)element.getEnclosingElement()).getQualifiedName().toString();
            for (PcdpListeners pcdpListeners : element.getAnnotationsByType(PcdpListeners.class)) {
                generatePcdpListener(pcdpListeners.value(), packageName);
            }
        }
    }

    private void processPcdpListener(Set<? extends Element> pcdpListenerElements){
        for (Element element : pcdpListenerElements) {
            String packageName = ((PackageElement)element.getEnclosingElement()).getQualifiedName().toString();
            generatePcdpListener(element.getAnnotationsByType(PcdpListener.class), packageName);
        }
    }

    private void generatePcdpListener(PcdpListener[] pcdpListeners, String packageName){
        for (PcdpListener pcdpListener : pcdpListeners) {
            String className = pcdpListener.context() + "Listener";
            try {
                JavaFileObject generatedClass = processingEnv.getFiler().createSourceFile(className);
                BufferedWriter bufferedWriter = new BufferedWriter(generatedClass.openWriter());
                bufferedWriter.append("package ").append(packageName).append(";\n");
                bufferedWriter.append("public class ").append(className).append("{\n");
                bufferedWriter.append("////TODO Flesh out listener\n");
                bufferedWriter.append("}");
                bufferedWriter.close();
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.toString());
            }
        }
    }
}