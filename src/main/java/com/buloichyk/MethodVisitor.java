package com.buloichyk;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * This class acts as a visitor for traversing methods within Java classes.
 * It analyzes conditional statements and extracts metrics necessary for visualization.
 **/
public class MethodVisitor extends VoidVisitorAdapter<MethodComplexity> {
    @Override
    public void visit(IfStmt ifStmt, MethodComplexity methodComplexity) {
        methodComplexity.incrementIfStatements();
        ifStmt.getThenStmt().accept(this, methodComplexity);
        if (ifStmt.getElseStmt().isPresent()) {
            Statement elseStmt = ifStmt.getElseStmt().get();
            // if the 'else' statement is an 'if' statement, it's an 'else if'
            if (elseStmt.isIfStmt()) {
                // process the 'else if' statement as one conditional statement
                elseStmt.accept(this, methodComplexity);
            } else {
                // otherwise, it's a regular 'else' statement
                methodComplexity.incrementIfStatements();
                elseStmt.accept(this, methodComplexity);
            }
        }
    }

    @Override
    public void visit(SwitchStmt switchStmt, MethodComplexity methodComplexity) {
        methodComplexity.incrementSwitchStatements();
        for (SwitchEntry entry : switchStmt.getEntries()) {
            for (Statement stmt : entry.getStatements()) {
                stmt.accept(this, methodComplexity); // Visit each statement inside the switch
            }
        }
    }

    @Override
    public void visit(ForStmt forStmt, MethodComplexity methodComplexity) {
        methodComplexity.incrementForStatements();
        forStmt.getBody().accept(this, methodComplexity);
    }

    @Override
    public void visit(ForEachStmt forEachStmt, MethodComplexity methodComplexity) {
        methodComplexity.incrementForStatements();
        forEachStmt.getBody().accept(this, methodComplexity);
    }

    @Override
    public void visit(WhileStmt whileStmt, MethodComplexity methodComplexity) {
        methodComplexity.incrementWhileStatements();
        whileStmt.getBody().accept(this, methodComplexity);
    }
}
