package com.buloichyk;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public class FileVisitor extends VoidVisitorAdapter<List<MethodComplexity>> {
    @Override
    public void visit(MethodDeclaration md, List<MethodComplexity> methodComplexityList) {
        super.visit(md, methodComplexityList);
        System.out.println("Analyzing: " + md.getName());
        MethodVisitor visitor = new MethodVisitor();
        MethodComplexity methodComplexity = new MethodComplexity(md.getClass().getSimpleName() + "." + md.getName().asString() + "()");
        String camelCasePattern = "^[a-z][a-z0-9]*(([A-Z][a-z0-9]+)*[A-Z]?|([a-z0-9]+[A-Z])*|[A-Z])$";
        if (md.getName().asString().matches(camelCasePattern)) {
            methodComplexity.setCamelCase(true);
        }
        md.accept(visitor, methodComplexity);
        methodComplexityList.add(methodComplexity);
    }
}
