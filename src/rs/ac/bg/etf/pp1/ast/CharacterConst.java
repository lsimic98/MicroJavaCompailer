// generated with ast extension for cup
// version 0.8
// 28/7/2021 13:55:4


package rs.ac.bg.etf.pp1.ast;

public class CharacterConst extends ConstIdent {

    private String characterConstName;
    private Character characterConstValue;

    public CharacterConst (String characterConstName, Character characterConstValue) {
        this.characterConstName=characterConstName;
        this.characterConstValue=characterConstValue;
    }

    public String getCharacterConstName() {
        return characterConstName;
    }

    public void setCharacterConstName(String characterConstName) {
        this.characterConstName=characterConstName;
    }

    public Character getCharacterConstValue() {
        return characterConstValue;
    }

    public void setCharacterConstValue(Character characterConstValue) {
        this.characterConstValue=characterConstValue;
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
        buffer.append("CharacterConst(\n");

        buffer.append(" "+tab+characterConstName);
        buffer.append("\n");

        buffer.append(" "+tab+characterConstValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CharacterConst]");
        return buffer.toString();
    }
}
