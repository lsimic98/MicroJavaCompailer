// generated with ast extension for cup
// version 0.8
// 28/7/2021 13:55:4


package rs.ac.bg.etf.pp1.ast;

public class IfElseStatement extends Statement {

    private BeginIf BeginIf;
    private Condition Condition;
    private RParenResolution RParenResolution;
    private Statement Statement;
    private BeginElse BeginElse;
    private Statement Statement1;

    public IfElseStatement (BeginIf BeginIf, Condition Condition, RParenResolution RParenResolution, Statement Statement, BeginElse BeginElse, Statement Statement1) {
        this.BeginIf=BeginIf;
        if(BeginIf!=null) BeginIf.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.RParenResolution=RParenResolution;
        if(RParenResolution!=null) RParenResolution.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.BeginElse=BeginElse;
        if(BeginElse!=null) BeginElse.setParent(this);
        this.Statement1=Statement1;
        if(Statement1!=null) Statement1.setParent(this);
    }

    public BeginIf getBeginIf() {
        return BeginIf;
    }

    public void setBeginIf(BeginIf BeginIf) {
        this.BeginIf=BeginIf;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public RParenResolution getRParenResolution() {
        return RParenResolution;
    }

    public void setRParenResolution(RParenResolution RParenResolution) {
        this.RParenResolution=RParenResolution;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public BeginElse getBeginElse() {
        return BeginElse;
    }

    public void setBeginElse(BeginElse BeginElse) {
        this.BeginElse=BeginElse;
    }

    public Statement getStatement1() {
        return Statement1;
    }

    public void setStatement1(Statement Statement1) {
        this.Statement1=Statement1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(BeginIf!=null) BeginIf.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(RParenResolution!=null) RParenResolution.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(BeginElse!=null) BeginElse.accept(visitor);
        if(Statement1!=null) Statement1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(BeginIf!=null) BeginIf.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(RParenResolution!=null) RParenResolution.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(BeginElse!=null) BeginElse.traverseTopDown(visitor);
        if(Statement1!=null) Statement1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(BeginIf!=null) BeginIf.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(RParenResolution!=null) RParenResolution.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(BeginElse!=null) BeginElse.traverseBottomUp(visitor);
        if(Statement1!=null) Statement1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfElseStatement(\n");

        if(BeginIf!=null)
            buffer.append(BeginIf.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(RParenResolution!=null)
            buffer.append(RParenResolution.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(BeginElse!=null)
            buffer.append(BeginElse.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement1!=null)
            buffer.append(Statement1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfElseStatement]");
        return buffer.toString();
    }
}
