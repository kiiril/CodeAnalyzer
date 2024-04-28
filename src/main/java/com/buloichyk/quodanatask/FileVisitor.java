package com.buloichyk.quodanatask;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public class FileVisitor extends VoidVisitorAdapter<List<MethodComplexity>> {
    @Override
    public void visit(MethodDeclaration md, List<MethodComplexity> methodComplexityList) {
        super.visit(md, methodComplexityList);
        System.out.println("Analyzing: " + md.getName());
        MethodVisitor visitor = new MethodVisitor();
        MethodComplexity methodComplexity = new MethodComplexity(md.hashCode() + md.getName().asString());
        md.accept(visitor, methodComplexity);
        methodComplexityList.add(methodComplexity);
    }
}
