// generated with ast extension for cup
// version 0.8
// 29/5/2021 0:41:41


package rs.ac.bg.etf.pp1.ast;

public class BoolEqual extends Relop {

    public BoolEqual () {
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
        buffer.append("BoolEqual(\n");

        buffer.append(tab);
        buffer.append(") [BoolEqual]");
        return buffer.toString();
    }
}
