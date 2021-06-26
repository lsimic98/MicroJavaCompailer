// generated with ast extension for cup
// version 0.8
// 21/5/2021 16:49:47


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Assignop Assignop);
    public void visit(Factor Factor);
    public void visit(Mulop Mulop);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(Expr1 Expr1);
    public void visit(ConstDecl ConstDecl);
    public void visit(ExprList ExprList);
    public void visit(Expr Expr);
    public void visit(ConstIdent ConstIdent);
    public void visit(FormalParamList FormalParamList);
    public void visit(ClassDecl ClassDecl);
    public void visit(ProgDeclList ProgDeclList);
    public void visit(MethodVarDeclList MethodVarDeclList);
    public void visit(FormPars FormPars);
    public void visit(VarIdent VarIdent);
    public void visit(DesignatorList DesignatorList);
    public void visit(VarDeclList VarDeclList);
    public void visit(Addop Addop);
    public void visit(FormalParamDecl FormalParamDecl);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(Statement Statement);
    public void visit(ConstList ConstList);
    public void visit(Term Term);
    public void visit(MethodTypeName MethodTypeName);
    public void visit(ClassVarDeclList ClassVarDeclList);
    public void visit(StatementList StatementList);
    public void visit(Moduo Moduo);
    public void visit(Division Division);
    public void visit(Multiplication Multiplication);
    public void visit(Minus Minus);
    public void visit(Plus Plus);
    public void visit(EqualsOperation EqualsOperation);
    public void visit(FactorDesignator FactorDesignator);
    public void visit(FactorExpr FactorExpr);
    public void visit(FactorNewArray FactorNewArray);
    public void visit(FactorBooleanConst FactorBooleanConst);
    public void visit(FactorCharConst FactorCharConst);
    public void visit(FactorNumConst FactorNumConst);
    public void visit(SingleFactor SingleFactor);
    public void visit(FactorList FactorList);
    public void visit(SingelExprList SingelExprList);
    public void visit(MulitExprList MulitExprList);
    public void visit(NormalExpr NormalExpr);
    public void visit(ConditionalExpr ConditionalExpr);
    public void visit(PositiveExpr PositiveExpr);
    public void visit(NegativeExpr NegativeExpr);
    public void visit(NoDesignatorList NoDesignatorList);
    public void visit(DesignatorListArray DesignatorListArray);
    public void visit(Designator Designator);
    public void visit(DecDesignatorStatement DecDesignatorStatement);
    public void visit(IncDesignatorStatement IncDesignatorStatement);
    public void visit(AssingDesignatorStatement AssingDesignatorStatement);
    public void visit(NoStatements NoStatements);
    public void visit(Statements Statements);
    public void visit(PrintWithoutArgs PrintWithoutArgs);
    public void visit(PrintWithArgs PrintWithArgs);
    public void visit(Read Read);
    public void visit(DesignatorStatementInStatement DesignatorStatementInStatement);
    public void visit(Type Type);
    public void visit(ArrayFormalParam ArrayFormalParam);
    public void visit(OneFormalParam OneFormalParam);
    public void visit(SingleFormalParamDecl SingleFormalParamDecl);
    public void visit(FormalParamDecls FormalParamDecls);
    public void visit(NoFormParam NoFormParam);
    public void visit(FormParams FormParams);
    public void visit(NoMethodVariables NoMethodVariables);
    public void visit(MethodVariables MethodVariables);
    public void visit(VoidMethodType VoidMethodType);
    public void visit(NonVoidMethodType NonVoidMethodType);
    public void visit(MethodDecl MethodDecl);
    public void visit(NoMethodDecl NoMethodDecl);
    public void visit(MethodDeclarations MethodDeclarations);
    public void visit(ArrayVar ArrayVar);
    public void visit(OneVar OneVar);
    public void visit(MultipleVarDecl MultipleVarDecl);
    public void visit(SingleVarDecl SingleVarDecl);
    public void visit(VarDecl VarDecl);
    public void visit(CharacterConst CharacterConst);
    public void visit(IntegerConst IntegerConst);
    public void visit(BooleanConst BooleanConst);
    public void visit(MultipleConstDecl MultipleConstDecl);
    public void visit(SingleConstDecl SingleConstDecl);
    public void visit(ConstDeclDerived1 ConstDeclDerived1);
    public void visit(NoProgDeclList NoProgDeclList);
    public void visit(ProgVarDeclList ProgVarDeclList);
    public void visit(ProgConstDeclList ProgConstDeclList);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
