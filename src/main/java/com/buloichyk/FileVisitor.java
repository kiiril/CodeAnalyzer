package com.buloichyk;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class serves as a visitor for traversing Java files.
 * It specifically targets MethodDeclaration nodes within the Abstract Syntax Tree (AST),
 * extracting relevant information from each method and passing it for further processing
 * by the MethodVisitor.
 **/

public class FileVisitor extends VoidVisitorAdapter<List<MethodComplexity>> {
    private final Map<String, Integer> methodCounts = new HashMap<>();
    @Override
    public void visit(ClassOrInterfaceDeclaration classOrInterface, List<MethodComplexity> ignore) {
        super.visit(classOrInterface, ignore);
        // clear the method counts for a new entered class
        methodCounts.clear();
    }
    @Override
    public void visit(MethodDeclaration md, List<MethodComplexity> methodComplexityList) {
        super.visit(md, methodComplexityList);
        System.out.println("Analyzing: " + md.getName());

        // get the class of the method
        ClassOrInterfaceDeclaration containingClass = md.findAncestor(ClassOrInterfaceDeclaration.class).orElse(null);
        if (containingClass != null) {
            // if class has multiple methods with the same name, then indicate difference adding number in braces
            String basicMethodName = md.getNameAsString();
            int count = methodCounts.getOrDefault(basicMethodName, 0);
            methodCounts.put(basicMethodName, count + 1);
            String methodName = containingClass.getNameAsString() + ": " + md.getName().asString() + "()";
            if (count > 0) {
                methodName += " (" + (count + 1) + ")";
            }
            MethodComplexity methodComplexity = new MethodComplexity(methodName);
            String camelCasePattern = "^[a-z][a-z0-9]*(([A-Z][a-z0-9]+)*[A-Z]?|([a-z0-9]+[A-Z])*|[A-Z])$";
            if (basicMethodName.matches(camelCasePattern)) {
                methodComplexity.setCamelCase(true);
            }

            MethodVisitor visitor = new MethodVisitor();
            md.accept(visitor, methodComplexity);
            methodComplexityList.add(methodComplexity);
        }
    }
}
