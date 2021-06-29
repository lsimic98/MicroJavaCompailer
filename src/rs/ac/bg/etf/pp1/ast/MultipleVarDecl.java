// generated with ast extension for cup
// version 0.8
// 29/5/2021 0:41:41


package rs.ac.bg.etf.pp1.ast;

public class MultipleVarDecl extends VarDeclList {

    private VarDeclList VarDeclList;
    private VarIdent VarIdent;

    public MultipleVarDecl (VarDeclList VarDeclList, VarIdent VarIdent) {
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
        this.VarIdent=VarIdent;
        if(VarIdent!=null) VarIdent.setParent(this);
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public VarIdent getVarIdent() {
        return VarIdent;
    }

    public void setVarIdent(VarIdent VarIdent) {
        this.VarIdent=VarIdent;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclList!=null) VarDeclList.accept(visitor);
        if(VarIdent!=null) VarIdent.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
        if(VarIdent!=null) VarIdent.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        if(VarIdent!=null) VarIdent.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleVarDecl(\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarIdent!=null)
            buffer.append(VarIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleVarDecl]");
        return buffer.toString();
    }
}
