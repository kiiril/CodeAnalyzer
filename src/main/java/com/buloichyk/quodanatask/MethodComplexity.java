package com.buloichyk.quodanatask;

public class MethodComplexity {
    private String methodName;
//    private int totalConditionalStatements;
    private int ifStatements;
    private int switchStatements;
    private int forStatements;
    private int whileStatements;

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

    @Override
    public String toString() {
        return "MethodComplexity{" +
                "methodName='" + methodName + '\'' +
                ", ifStatements=" + ifStatements +
                ", switchStatements=" + switchStatements +
                ", forStatements=" + forStatements +
                ", whileStatements=" + whileStatements +
                '}';
    }
}
