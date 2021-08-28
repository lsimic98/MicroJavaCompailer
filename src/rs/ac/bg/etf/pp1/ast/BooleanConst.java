// generated with ast extension for cup
// version 0.8
// 28/7/2021 13:55:4


package rs.ac.bg.etf.pp1.ast;

public class BooleanConst extends ConstIdent {

    private String booleanConstName;
    private Boolean booleanConstValue;

    public BooleanConst (String booleanConstName, Boolean booleanConstValue) {
        this.booleanConstName=booleanConstName;
        this.booleanConstValue=booleanConstValue;
    }

    public String getBooleanConstName() {
        return booleanConstName;
    }

    public void setBooleanConstName(String booleanConstName) {
        this.booleanConstName=booleanConstName;
    }

    public Boolean getBooleanConstValue() {
        return booleanConstValue;
    }

    public void setBooleanConstValue(Boolean booleanConstValue) {
        this.booleanConstValue=booleanConstValue;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("BooleanConst(\n");

        buffer.append(" "+tab+booleanConstName);
        buffer.append("\n");

        buffer.append(" "+tab+booleanConstValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BooleanConst]");
        return buffer.toString();
    }
}
