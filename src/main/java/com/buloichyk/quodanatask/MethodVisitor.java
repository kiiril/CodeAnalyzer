package com.buloichyk.quodanatask;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodVisitor extends VoidVisitorAdapter<MethodComplexity> {
    @Override
    public void visit(IfStmt ifStmt, MethodComplexity methodComplexity) {
//        super.visit(ifStmt, methodComplexity);
        System.out.println("I was in if: ");
        methodComplexity.incrementIfStatements();
        ifStmt.getThenStmt().accept(this, methodComplexity);
        if (ifStmt.getElseStmt().isPresent()) {
            methodComplexity.incrementIfStatements();
            ifStmt.getElseStmt().get().accept(this, methodComplexity);
        }
    }

    @Override
    public void visit(SwitchStmt switchStmt, MethodComplexity methodComplexity) {
//        super.visit(switchStmt, methodComplexity);
        System.out.println("I was in sw");
        methodComplexity.incrementSwitchStatements();
        for (SwitchEntry entry : switchStmt.getEntries()) {
            for (Statement stmt : entry.getStatements()) {
                stmt.accept(this, methodComplexity); // Visit each statement inside the switch entry
            }
        }
    }

    @Override
    public void visit(ForStmt forStmt, MethodComplexity methodComplexity) {
//        super.visit(forStmt, methodComplexity);
        System.out.println("I was in for");
        methodComplexity.incrementForStatements();
        forStmt.getBody().accept(this, methodComplexity);
    }

    @Override
    public void visit(ForEachStmt forEachStmt, MethodComplexity methodComplexity) {
        System.out.println("I was in foreach");
        methodComplexity.incrementForStatements();
        forEachStmt.getBody().accept(this, methodComplexity);
    }

    @Override
    public void visit(WhileStmt whileStmt, MethodComplexity methodComplexity) {
//        super.visit(whileStmt, methodComplexity);
        System.out.println("I was in while");
        methodComplexity.incrementWhileStatements();
        whileStmt.getBody().accept(this, methodComplexity);
    }
}