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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PcdpListenerProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        var tmp = roundEnv.getElementsAnnotatedWith(PcdpListener.class);
        print(tmp);

        /*for (Element element : roundEnv.getElementsAnnotatedWith(PcdpListener.class)) {
            print(element);
            var tmp2 = element.getAnnotationsByType(PcdpListener.class).length;
            print(tmp2);
        }*/

        //processPcdpListeners(roundEnv.getElementsAnnotatedWith(PcdpListeners.class));


        print("single");
        processPcdpListener(roundEnv.getElementsAnnotatedWith(PcdpListener.class));
        print("Multiple");
        processPcdpListeners(roundEnv.getElementsAnnotatedWith(PcdpListeners.class));

        //processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Jose");


        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Arrays.asList("org.example.PcdpListener"));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_10;
    }

    private void processPcdpListeners(Set<? extends Element> pcdpListenersElements){
        for (Element element : pcdpListenersElements) {

            for (PcdpListeners pcdpListeners : element.getAnnotationsByType(PcdpListeners.class)) {
                for (PcdpListener pcdpListener :pcdpListeners.value()) {

                    String packageName = ((PackageElement)element.getEnclosingElement()).getQualifiedName().toString();
                    String context = pcdpListener.context();
                    String className = element.getSimpleName().toString() + context + "Listener";
                    print(className);

                    generateClass(className, packageName);
                }
            }
        }
    }

    private void processPcdpListener(Set<? extends Element> pcdpListenerElements){
        for (Element element : pcdpListenerElements) {

            String packageName = ((PackageElement)element.getEnclosingElement()).getQualifiedName().toString();
            String context = element.getAnnotation(PcdpListener.class).context();
            String className = element.getSimpleName().toString() + context + "Listener";

            generateClass(className, packageName);
        }
    }

    private void generateClass(String className, String packageName){

        try {
            JavaFileObject generatedClass = processingEnv.getFiler().createSourceFile(className);
            BufferedWriter bufferedWriter = new BufferedWriter(generatedClass.openWriter());
            bufferedWriter.append("package " + packageName + ";");
            bufferedWriter.newLine();
            bufferedWriter.append("public class ");
            bufferedWriter.append(className);
            bufferedWriter.append("{");
            bufferedWriter.newLine();
            bufferedWriter.append("////TODO Flesh out litener");
            bufferedWriter.newLine();
            bufferedWriter.append("}");
            bufferedWriter.close();
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.toString());
        }
    }

    private void print(Object object){
        processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, String.valueOf(object));
    }
}
