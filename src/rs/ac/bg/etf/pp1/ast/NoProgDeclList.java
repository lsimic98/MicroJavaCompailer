// generated with ast extension for cup
// version 0.8
// 28/7/2021 13:55:4


package rs.ac.bg.etf.pp1.ast;

public class NoProgDeclList extends ProgDeclList {

    public NoProgDeclList () {
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
        buffer.append("NoProgDeclList(\n");

        buffer.append(tab);
        buffer.append(") [NoProgDeclList]");
        return buffer.toString();
    }
}
