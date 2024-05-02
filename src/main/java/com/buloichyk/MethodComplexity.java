package com.buloichyk;

/**
 * This class represents a data object that accumulates necessary information about a method.
 **/

public class MethodComplexity {
    private final String methodName;
    private int ifStatements;
    private int switchStatements;
    private int forStatements;
    private int whileStatements;
    private boolean isCamelCase;

    public MethodComplexity(String methodName) {
        this.methodName = methodName;
    }

    public void incrementIfStatements() {
        ifStatements++;
    }
    public void incrementSwitchStatements() {
        switchStatements++;
    }
    public void incrementForStatements() {
        forStatements++;
    }
    public void incrementWhileStatements() {
        whileStatements++;
    }

    public int getIfStatements() {
        return ifStatements;
    }

    public int getSwitchStatements() {
        return switchStatements;
    }

    public int getForStatements() {
        return forStatements;
    }

    public int getWhileStatements() {
        return whileStatements;
    }

    public int getTotalConditionalStatements() {
        return ifStatements + switchStatements + forStatements + whileStatements;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setCamelCase(boolean camelCase) {
        isCamelCase = camelCase;
    }

    public boolean isCamelCase() {
        return isCamelCase;
    }
}
