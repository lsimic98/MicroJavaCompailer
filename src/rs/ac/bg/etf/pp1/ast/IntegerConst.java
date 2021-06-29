// generated with ast extension for cup
// version 0.8
// 29/5/2021 0:41:41


package rs.ac.bg.etf.pp1.ast;

public class IntegerConst extends ConstIdent {

    private String integerConstName;
    private Integer integerConstValue;

    public IntegerConst (String integerConstName, Integer integerConstValue) {
        this.integerConstName=integerConstName;
        this.integerConstValue=integerConstValue;
    }

    public String getIntegerConstName() {
        return integerConstName;
    }

    public void setIntegerConstName(String integerConstName) {
        this.integerConstName=integerConstName;
    }

    public Integer getIntegerConstValue() {
        return integerConstValue;
    }

    public void setIntegerConstValue(Integer integerConstValue) {
        this.integerConstValue=integerConstValue;
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
        buffer.append("IntegerConst(\n");

        buffer.append(" "+tab+integerConstName);
        buffer.append("\n");

        buffer.append(" "+tab+integerConstValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IntegerConst]");
        return buffer.toString();
    }
}
