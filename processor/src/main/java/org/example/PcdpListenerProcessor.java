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

        for (Element element : roundEnv.getElementsAnnotatedWith(PcdpListener.class)) {

            String packageName = ((PackageElement)element.getEnclosingElement()).getQualifiedName().toString();
            String context = element.getAnnotation(PcdpListener.class).context();
            String className = element.getSimpleName().toString() + context + "Listener";

            generateClass(className, packageName);
        }
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
}
