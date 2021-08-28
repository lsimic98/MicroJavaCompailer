package rs.ac.bg.etf.pp1;

import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

public class CodeGenerator extends VisitorAdaptor{
	
	private int varCount;
	
	private int paramCnt;
	
	private int mainPc;
	
	/* Variable for optimal IF */
	
	public int getMainPc() {
		return mainPc;
	}
	
	public CodeGenerator()
	{
		//ord
        Obj ordMethod = Tab.find("ord");
        ordMethod.setAdr(Code.pc);
        
        //chr
        Obj chrMethod = Tab.find("chr");
        chrMethod.setAdr(Code.pc);
        
        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.exit);
        Code.put(Code.return_);
        
        //len
        Obj lenMethod = Tab.find("len");
        lenMethod.setAdr(Code.pc);
        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.arraylength);
        Code.put(Code.exit);
        Code.put(Code.return_);	
	}
	
	
	public void visit(AssingDesignatorStatement assignDesignator)
	{
        Designator designator = assignDesignator.getDesignator();
        Obj obj = null;
        
        if(designator.getDesignatorList() instanceof DesignatorListArray)
        	obj = new Obj(Obj.Elem, "elem", designator.obj.getType().getElemType());
        else
        	obj = designator.obj;
        
        Code.store(obj);
	}
	
	
	public void visit(IncDesignatorStatement incDesignator)
	{
        Designator designator = incDesignator.getDesignator();       
        Obj obj = null;
        
        if(designator.getDesignatorList() instanceof DesignatorListArray)
        	obj = new Obj(Obj.Elem, "elem", designator.obj.getType().getElemType());
        else
        	obj = designator.obj;
        
        Code.load(obj);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(obj);
	}
	
	public void visit(DecDesignatorStatement decDesignator)
	{
        Designator designator = decDesignator.getDesignator();       
        Obj obj = null;
        
        if(designator.getDesignatorList() instanceof DesignatorListArray)
        	obj = new Obj(Obj.Elem, "elem", designator.obj.getType().getElemType());
        else
        	obj = designator.obj;
        
        Code.load(obj);
        Code.loadConst(1);
        Code.put(Code.sub);
        Code.store(obj);
	}
	
	//Adresa pocetka niza :(
	public void visit(LSquareBrace lSquareBrace)
	{
        Designator designator = ((Designator)lSquareBrace.getParent().getParent());
        Code.load(designator.obj);
	}

	
	public void visit(Designator designator)
	{
		if(
			(
				designator.getParent() instanceof IncDesignatorStatement || 
				designator.getParent() instanceof DecDesignatorStatement
			)
			&&
			designator.getDesignatorList() instanceof DesignatorListArray
				
		)
		{
			Code.put(Code.dup2);			
		}
	}
	
	
	public void visit(FactorDesignator factorDesignator)
	{
		if(factorDesignator.getParent() instanceof FactorList)
		{
			Designator designator = factorDesignator.getDesignator();
			Obj obj = null;
			
	        if(designator.getDesignatorList() instanceof DesignatorListArray)
	        	obj = new Obj(Obj.Elem, "elem", designator.obj.getType().getElemType());
	        else
	        	obj = designator.obj;
	        
	        Code.load(obj);
		}
	}
	
	public void visit(SingleFactor singleFactor)
	{
		if(singleFactor.getFactor() instanceof FactorDesignator)
		{
			FactorDesignator factorDesignator = (FactorDesignator) singleFactor.getFactor();
			Designator designator = factorDesignator.getDesignator();
			Obj obj = null;
			
			if(designator.getDesignatorList() instanceof DesignatorListArray)
	        	obj = new Obj(Obj.Elem, "elem", designator.obj.getType().getElemType());
			else
				obj = designator.obj;
			
			if(
				designator.getDesignatorList() instanceof DesignatorListArray &&
				(
					singleFactor.getParent() instanceof MulitExprList ||
					(
							singleFactor.getParent() instanceof SingelExprList &&
							singleFactor.getParent().getParent() instanceof MulitExprList
					)		
				)
			)
			{
				Code.put(Code.dup2);
			}
			Code.load(obj);
		}
	}
	
	public void visit(FactorList factorList)
	{
		Mulop mulop = factorList.getMulop();
		
		if(mulop instanceof Multiplication)
			Code.put(Code.mul);
		else if(mulop instanceof Division)
			Code.put(Code.div);
		else if(mulop instanceof Moduo)
			Code.put(Code.rem);
		else
			System.err.println("What the hell did just happened?");
	}
	
	public void visit(FactorNumConst factorNumConst)
	{
		Obj con = Tab.insert(Obj.Con, "$", factorNumConst.struct);
		con.setLevel(0);
		con.setAdr(factorNumConst.getN1());
		Code.load(con);
		
		if(
			factorNumConst.getParent() instanceof SingleFactor &&
			factorNumConst.getParent().getParent() instanceof SingelExprList &&
			factorNumConst.getParent().getParent().getParent() instanceof MulitExprList &&
			factorNumConst.getParent().getParent().getParent().getParent() instanceof NegativeExpr
		)
		{
			Code.put(Code.neg);
		}		
	}
	
	public void visit(FactorCharConst factorCharConst)
	{
		Obj con = Tab.insert(Obj.Con, "$", factorCharConst.struct);
		con.setLevel(0);
		con.setAdr(factorCharConst.getC1());
		Code.load(con);
	}
	
	public void visit(FactorBooleanConst factorBooleanConst)
	{
		Obj con = Tab.insert(Obj.Con, "$", factorBooleanConst.struct);
		con.setLevel(0);
		
		if(factorBooleanConst.getB1())
			con.setAdr(1);
		else
			con.setAdr(0);
		
		Code.load(con);
	}
	
	public void visit(FactorNewArray factorNewArray)
	{
		Code.put(Code.newarray);
		if(factorNewArray.struct.getElemType().equals(Tab.charType))
			Code.put(0);
		else
			Code.put(1);
	}
	
	
	public void visit(VoidMethodType voidMethodType)
	{
		String methodName = voidMethodType.getMethodName();
		if(methodName.equals("main"))
			mainPc = Code.pc;
		
		Obj obj = voidMethodType.obj;
		obj.setAdr(Code.pc);
		
		Code.put(Code.enter);
        Code.put(obj.getLevel());
        Code.put(obj.getLocalSymbols().size());
	}
	
	public void visit(MethodDecl methodDecl)
	{
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(MulitExprList multiExprList)
	{
		if(multiExprList.getAddop() instanceof Plus)
			Code.put(Code.add);
		else if(multiExprList.getAddop() instanceof Minus)
			Code.put(Code.sub);
		else if(multiExprList.getAddop() instanceof Modifoperator)
			addModifOperator();
	}
	
	public void visit(NegativeExpr negativeExpr)
	{
        if(!(negativeExpr.getExprList() instanceof MulitExprList))
            Code.put(Code.neg);
	}
	
	
	/* Optimising IF */
    Stack<Integer> ifFixupStack = new Stack<>();
	Stack<Stack<Integer>> andFixupStack = new Stack<>();
	Stack<Stack<Integer>> orFixupStack = new Stack<>();
	
	public void visit(BeginIf beginIf)
	{
		andFixupStack.push(new Stack<>());
		orFixupStack.push(new Stack<>());
	}

	public void visit(RelopCondFactExpr relopCondFactExpr)
	{
		int opCode = 0;
		
		Relop relop = relopCondFactExpr.getRelop();
		if(relop instanceof BoolEqual)
			opCode = Code.eq;
		else if(relop instanceof BoolNotEqual)
			opCode = Code.ne;
		else if(relop instanceof Greater)
			opCode = Code.gt;
		else if(relop instanceof GreaterOrEqual)
			opCode = Code.ge;
		else if(relop instanceof Less)
			opCode = Code.lt;
		else if(relop instanceof LessOrEqual)
			opCode = Code.le;
		
		Code.putFalseJump(opCode, 0);
		andFixupStack.peek().push(Code.pc - 2);	
	}
	
	//Za bool promenljive - primer a==2 && bt
	public void visit(SingleCondFactExpr singleCondFactExpr)
	{
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0);
        andFixupStack.peek().push(Code.pc - 2);
	}
	
	public void visit(OrCondition orCondition)
	{
		
        while (!andFixupStack.peek().empty())
        {
        	int fixupAdr = andFixupStack.peek().pop();
            Code.fixup(fixupAdr);
        }
        

        Code.putJump(0);
        orFixupStack.peek().push(Code.pc - 2);
	}
	
	//Detection of if(...){..}
	public void visit(IfStatement ifStatement)
	{
        while (!andFixupStack.peek().empty())
        {
        	int fixupAdr = andFixupStack.peek().pop();
            Code.fixup(fixupAdr);
        }
        if(!andFixupStack.empty())
        	andFixupStack.pop();
	}
	
	//Detection of if(... ')' or else if(... ')'
	public void visit(RParenResolution rParenResolution)
	{
        while (!orFixupStack.peek().empty())
        {
            int fixupAddr = orFixupStack.peek().pop();
            Code.fixup(fixupAddr);
        }
        
        if (!orFixupStack.empty())
            orFixupStack.pop();	
	}
	
	//Detection of else or else if(...)  
	public void visit(BeginElse beginElse)
	{
		//jump else branch
		Code.putJump(0);
		ifFixupStack.push(Code.pc - 2);
		
        while (!andFixupStack.peek().empty())
        {
            int fixupAddr = andFixupStack.peek().pop();
            Code.fixup(fixupAddr);
        }
        if (!andFixupStack.empty()) 
            andFixupStack.pop();
	}
	
	public void visit(IfElseStatement ifElseStatement)
	{
		int fixupAddr = ifFixupStack.pop();
		Code.fixup(fixupAddr);
	}
	
	/* END_Optimising IF*/
	
	/* Prints and Reads */
		
	public void visit(Read read)
	{
		Obj obj = read.getDesignator().obj;
		
		if(obj.getType().equals(Tab.charType))
			Code.put(Code.bread);
		else
			Code.put(Code.read);
		
		if(obj.getType().getKind() == Struct.Array)
			obj = new Obj(Obj.Elem, "elem", obj.getType().getElemType());
		
		Code.store(obj);
	}
	
	
	public void visit(PrintWithoutArgs printWithoutArgs)
	{
		if(printWithoutArgs.getExpr().struct.equals(Tab.intType))
		{
			Code.loadConst(5);
			Code.put(Code.print);
		}
		else
		{
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}
	
	public void visit(PrintWithArgs printWthArgs)
	{
		int numConst = printWthArgs.getN2();
		if(printWthArgs.getExpr().struct.equals(Tab.intType))
		{
			Code.loadConst(numConst);
			Code.put(Code.print);
		}
		else
		{
			Code.loadConst(numConst);
			Code.put(Code.bprint);
		}
		
	}
	
	
	

	
	/* END Print and Reads*/
	
	public void visit(StatementStartLabel statementStartLabel)
	{
		System.out.println(Code.pc);
	}
	
	
	private void addModifOperator()
	{
		Code.put(Code.sub);
		Code.put(Code.dup);

		Code.loadConst(0);
		
		Code.putFalseJump(Code.ne, 0); //  var == 0  pop
		int fixuAdr1 = Code.pc - 2;
		
		Code.put(Code.dup);
		Code.loadConst(0);
		Code.putFalseJump(Code.le, 0); //var > 0
		int fixuAdr2 = Code.pc - 2;

		Code.put(Code.pop);
		Code.loadConst(-1);
		Code.putJump(0);
		int fixuAdr3 = Code.pc - 2;

		
		
		Code.fixup(fixuAdr2);
		Code.put(Code.pop);
		Code.loadConst(1);
		Code.fixup(fixuAdr1);
		Code.fixup(fixuAdr3);


		
		
		
		


		

		


		
		
		
		
		
	}

	
	
	
	
}
