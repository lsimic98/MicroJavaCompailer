package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor {
		
	boolean mainMethodDetected = false;
	boolean errorDetected = false;
	int printCallCount = 0;
	Obj currentMethod = null;
	boolean returnFound = false;
	int nVars;
	int cnt = 0;
	
	Struct currentSelectedType = null;
	List<Obj> currentMethodFormParsList;

	
	

	/*----------------------------------------------------------*/
	
	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	public void visit(ProgName progName)
	{
		progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		Tab.openScope();
	}
	
	
	public void visit(Program program)
	{
		nVars = Tab.currentScope().getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();	
	}
	
	public void visit(ProgConstDeclList progConstDeclList)
	{
//		currentSelectedType = null;

	}
	
	//Proveravamo da li tip sa zadatim imenom postoji u nasoj Tabeli Simbola
	public void visit(Type type)
	{
		Obj typeNode = Tab.find(type.getTypeName());
		if (typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola", null);
			type.struct = Tab.noType;
			currentSelectedType = Tab.noType;
		} 
		else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
				currentSelectedType = typeNode.getType();
			} 
			else {
				report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip ", type);
				type.struct = Tab.noType;
				currentSelectedType = Tab.noType;
			}
		}  	
	}
	
	//Proveravamo da li NUM_CONST, CHAR_CONST i BOOL_CONST odgovara deklarisanom tipu; primer: int x = 3;
	public void visit(IntegerConst intConst)
	{
		String intConstName = intConst.getIntegerConstName();
		Integer intConstValue = intConst.getIntegerConstValue();
		
		if(!Tab.intType.equals(currentSelectedType) || !Tab.find(intConstName).equals(Tab.noObj))
		{
			report_error("Greska: Tip se ne slaze ili konstnta sa deklarisanim imenom vec psotoji." , intConst);
		}
				
		Obj obj = Tab.insert(Obj.Con, intConstName, currentSelectedType);
		obj.setAdr(intConstValue); //Set Value of Const
		report_info("Deklarisana konstanta " + intConstName, intConst);
	}
	
	public void visit(CharacterConst charConst)
	{
		String charConstName = charConst.getCharacterConstName();
		Character charConstValue = charConst.getCharacterConstValue();
		
		if(!Tab.charType.equals(currentSelectedType) || !Tab.find(charConstName).equals(Tab.noObj))
		{
			report_error("Greska: Tip se ne slaze ili konstnta sa deklarisanim imenom vec psotoji." , charConst);
		}
				
		Obj obj = Tab.insert(Obj.Con, charConstName, currentSelectedType);
		obj.setAdr(charConstValue); //Set Value of Const
		report_info("Deklarisana konstanta " + charConstName, charConst);
	}
	
	public void visit(BooleanConst boolConst)
	{
		String boolConstName = boolConst.getBooleanConstName();
		Boolean boolConstValue = boolConst.getBooleanConstValue();
		
		if(!SymbolTable.boolType.equals(currentSelectedType) || !Tab.find(boolConstName).equals(Tab.noObj))
		{
			report_error("Greska: Tip se ne slaze ili konstnta sa deklarisanim imenom vec psotoji." , boolConst);
		}
				
		Obj obj = Tab.insert(Obj.Con, boolConstName, currentSelectedType);
		if(boolConstValue)
			obj.setAdr(1);
		else
			obj.setAdr(0);
		report_info("Deklarisana konstanta " + boolConstName, boolConst);
	}
	
	
	//Ubacivanje deklarisane promenljive primitivnog tipa u tablu simobla. Primer: int x;
	public void visit(OneVar oneVarDecl)
	{
		String varName = oneVarDecl.getVarName();
		
		if(!Tab.find(varName).equals(Tab.noObj))
		{
			report_error("Greska: Promenljiva sa imenom " + varName + " vec postoji!", oneVarDecl);
		}
		
		Obj obj = Tab.insert(Obj.Var, varName, currentSelectedType);
		oneVarDecl.obj = obj;
		report_info("Deklarisana promenljiva " + varName, oneVarDecl);

			
	}
	
	public void visit(ArrayVar arrayVarDecl)
	{
		String arrayName = arrayVarDecl.getVarName();
		
		if(!Tab.find(arrayName).equals(Tab.noObj))
		{
			report_error("Greska: Promenljiva sa imenom " + arrayName + " vec postoji!", arrayVarDecl);
		}
		
		Obj obj = Tab.insert(Obj.Var, arrayName, new Struct(Struct.Array, currentSelectedType));
		arrayVarDecl.obj = obj;
		
		report_info("Deklarisana promenljiva " + arrayName, arrayVarDecl);
	}
	
	
    public void visit(VarDecl varDecl) {
        currentSelectedType = null;
    }
    
    public void visit(ConstDecl constDecl) {
        currentSelectedType = null;
    }
    
    
    
    public void visit(VoidMethodType voidMethodType)
    {
    	String methodName = voidMethodType.getMethodName();
    	Obj symbol = Tab.currentScope().findSymbol(methodName); 
    	
    	if(symbol != null)
    	{
    		report_error("Greska: Definisana metoda sa imenom " + methodName + " vec postoji !", voidMethodType);
    	}
    	
    	if(methodName.equals("main"))
    	{
    		mainMethodDetected = true;
//    		if(currentMethodFormPars.size() > 0)
//    		{
//    			report_error("Main metoda ne moze da ima parametre! ", voidMethodType);
//    		}
    	}
    	
    	currentMethod = Tab.insert(Obj.Meth, methodName, Tab.noType);
    	Tab.openScope(); //Ubacen Obj tipa metode bez pobratnog tip
    	currentMethodFormParsList = new ArrayList<Obj>(); //Nova lista parametara
    	
    }
	
    public void visit(OneFormalParam oneFormParam)
    {
    	String formParamName = oneFormParam.getVarName();
    	if(contains(formParamName, currentMethodFormParsList).equals(Tab.noObj))
    	{
    		Obj formParamObj = new Obj(Obj.Var, formParamName, oneFormParam.getType().struct);
    		currentMethodFormParsList.add(formParamObj);
    		report_info("Formani parametar uspesno detektovan! " + formParamName, oneFormParam);	
    	}
    	else
    	{
    		report_error("Formalni parametar vec postoji! " + formParamName, oneFormParam);
    	}  	
    }
    
    public void visit(ArrayFormalParam arrayFormParam) 
    {
    	String formParamName = arrayFormParam.getVarName();
    	if(contains(formParamName, currentMethodFormParsList).equals(Tab.noObj))
    	{
    		Struct arrayType = new Struct(Struct.Array, arrayFormParam.getType().struct);
    		Obj formParamObj = new Obj(Obj.Var, formParamName, arrayType);
    		
    		currentMethodFormParsList.add(formParamObj);
    		report_info("Formani parametar uspesno detektovan! " + formParamName, arrayFormParam);	
    	}
    	else
    	{
    		report_error("Formalni parametar vec postoji! " + formParamName, arrayFormParam);
    	}  	
    	
    }
    
    public void visit(FormParams formParams)
    {
    	int fpCnt = 1;
    	if(currentMethodFormParsList != null)
    	{
	    	for(Obj formPar : currentMethodFormParsList)
	    	{
	    		Obj formParObj = Tab.insert(formPar.getKind(), formPar.getName(), formPar.getType());
	    		formParObj.setFpPos(fpCnt++);
	    	}
	    	currentMethod.setLevel(currentMethodFormParsList.size());
    	}
    	
    	currentMethodFormParsList = null;
    	

    }
	
    public void visit(MethodDecl methodDecl)
    {
    	Tab.chainLocalSymbols(currentMethod);
    	Tab.closeScope();
    	
    	currentMethod = null;
    }
    
    public void visit(NormalExpr normalExpr)
    {
    	normalExpr.struct = normalExpr.getExpr1().struct;
    }
    
    
    /* Designators */
    public void visit(Designator designator)
    {
    	String designatorName = designator.getName();
    	Obj obj = Tab.find(designatorName);
    	designator.obj = obj;
    	
    	if(obj.equals(Tab.noObj))
    	{
    		report_error("Greska: Promenljiva nije deklarisana!", designator);
    	}
    	else if( 
    			(designator.getDesignatorList() instanceof DesignatorListArray) && 
    			(obj.getType().getKind() != Struct.Array)
		)
    	{
    		report_error("Greska: Promenljiva nije nizovskog tipa! ", designator);
    	}
    	
    }
    
    public void visit(IncDesignatorStatement incDesignatorStatement)
    {
    	String designatorName = incDesignatorStatement.getDesignator().getName();
    	Obj designatorObj = Tab.find(designatorName);
    	
    	if(designatorObj.getKind() != Obj.Var)
    	{
    		report_error("Greska: Konstanta se ne moze inkrementirati!", incDesignatorStatement);	
    	}
    	else if(
    			(designatorObj.getType().getKind() == Struct.Array) && 
    			(incDesignatorStatement.getDesignator().getDesignatorList() instanceof NoDesignatorList)
		)
    	{
    		report_error("Greska: Referenca na niz se ne moze inkrementirati!", incDesignatorStatement);	
    	}
    	else if(designatorObj.getType().getKind() != Struct.Array && !designatorObj.getType().equals(Tab.intType))
    	{
    		report_error("Greska: Promenljiva mora biti Array ili int", incDesignatorStatement);	
    	}
    }
    
    public void visit(DecDesignatorStatement decDesignatorStatement)
    {
    	String designatorName = decDesignatorStatement.getDesignator().getName();
    	Obj designatorObj = Tab.find(designatorName);
    	
    	if(designatorObj.getKind() != Obj.Var)
    	{
    		report_error("Greska: Konstanta se ne moze dekrementirati!", decDesignatorStatement);	
    	}
    	else if(
    			(designatorObj.getType().getKind() == Struct.Array) && 
    			(decDesignatorStatement.getDesignator().getDesignatorList() instanceof NoDesignatorList)
		)
    	{
    		report_error("Greska: Referenca na niz se ne moze dekrementirati!", decDesignatorStatement);	
    	}
    	else if(designatorObj.getType().getKind() != Struct.Array && !designatorObj.getType().equals(Tab.intType))
    	{
    		report_error("Greska: Promenljiva mora biti Array ili int", decDesignatorStatement);	
    	}
    }
    
    
    public void visit(AssingDesignatorStatement assignDS)
    {
    	String designatorName = assignDS.getDesignator().getName();
    	Obj designatorObj = Tab.find(designatorName);
    	
    	if(designatorObj.getKind() != Obj.Var)
    	{
    		report_error("Greska: Ne mozemo dodeliti vrednost konstanti!", assignDS);
    	}
    	else if(
    			designatorObj.getType().getKind() != Struct.Array && 
    			!designatorObj.getType().assignableTo(assignDS.getExpr().struct)
		)
    	{
    		report_error("Greska: Nekompatibilni tipovi u izrazu!", assignDS);
    	}
    	
    }
    
    public void visit(NegativeExpr negativeExpr)
    {
    	negativeExpr.struct = negativeExpr.getExprList().struct;
    	if(!negativeExpr.struct.equals(Tab.intType))
    		report_error("Greska! Minus ne sme da stoji ispred izraza koji nije tipa Int", negativeExpr);
    }
    
    public void visit(PositiveExpr positiveExpr)
    {
    	positiveExpr.struct = positiveExpr.getExprList().struct;
    }
    
    public void visit(SingelExprList singleExprList)
    {
    	singleExprList.struct = singleExprList.getTerm().struct;
    }
    
    public void visit(MulitExprList multiExprList)
    {
    	if(!multiExprList.getTerm().struct.equals(Tab.intType))
    	{
    		report_error("Greska: Pogresan tip u izrazu za sabiranje!", multiExprList);
    	}
    	if(!multiExprList.getExprList().struct.equals(Tab.intType))
    	{
    		report_error("Greska: Pogresan tip u izrazu za sabiranje!", multiExprList);
    	}
    	
    	multiExprList.struct = multiExprList.getTerm().struct;

    }
    
    /* END_Designators*/

    
    /* Term */
    public void visit(SingleFactor singleFactor)
    {
    	singleFactor.struct = singleFactor.getFactor().struct;
    }
    
    
    //Proveravamo da li su oba Term-a tipa Int  x = x + 1 * 2 * 3;
    public void visit(FactorList factorList)
    {
    	Struct factorStruct = factorList.getFactor().struct;
    	Struct termStruct = factorList.getTerm().struct;
    	
    	
    	//Provera da li su oba cinioca (Term i Factor) tipa Int
    	if((termStruct.getKind() != Tab.intType.getKind()) || (factorStruct.getKind() != Tab.intType.getKind()))
    	{
    		report_error("Greska: Pogresan tip u izrazu za mnozenje!", factorList);
    	}
    	
    	factorList.struct = factorList.getFactor().struct;

    }
    /* END_Term */
    
    /* Factor */
    public void visit(FactorDesignator factorDesignator)
    {
    	Obj factor = Tab.find(factorDesignator.getDesignator().getName());
    	if(factorDesignator.getDesignator().getDesignatorList() instanceof DesignatorListArray)
    	{
    		//Ako se radi o nizu npr: arr[5]
    		factorDesignator.struct = factor.getType().getElemType();
    	}
    	else
    	{
    		factorDesignator.struct = factor.getType();
    	}
    }
    
    public void visit(FactorNumConst factorNumConst)
    {
    	factorNumConst.struct = Tab.intType;
    }
    public void visit(FactorBooleanConst factorBooleanConst)
    {
    	factorBooleanConst.struct = SymbolTable.boolType;
    }
    public void visit(FactorCharConst factorCharConst)
    {
    	factorCharConst.struct = Tab.charType;
    }
    public void visit(FactorNewArray factorNewArray)
    {
    	factorNewArray.struct = new Struct(Struct.Array, factorNewArray.getType().struct);
    }
    public void visit(FactorExpr factorExpr)
    {
        factorExpr.struct = factorExpr.getExpr().struct;
    }
    /* END_Factor */
    
    /* Read&Print */
    public void visit(Read readExpr)
    {
    	String designatorName = readExpr.getDesignator().getName();
    	Obj designatorObj = Tab.find(designatorName);

    	
    	if(designatorObj.getKind() != Obj.Var)
    	{
    		report_error("Greska: Konstanta se ne moze dodeliti vrednost sa standardnog ulaza!", readExpr);	
    	}
    	else
    	{
    		Struct designatorStruct = designatorObj.getType();
    		if(
    				designatorStruct.getKind() != Struct.Array && 
    				!designatorStruct.equals(Tab.intType) &&
    				!designatorStruct.equals(Tab.charType) &&
    				!designatorStruct.equals(SymbolTable.boolType) 
			)
    		{
    			report_error("Greska: Pogresan tip destinacione promenljive!", readExpr);		
    		}
    		if(
				designatorStruct.getKind() == Struct.Array &&
				readExpr.getDesignator().getDesignatorList() instanceof NoDesignatorList
			)
    		{
    			report_error("Greska: Ne mozemo procitati niz sa standardnog ulaza!", readExpr);		
    		}
    		
    	}
    	
    }
    
    public void visit(PrintWithoutArgs printWithoutArgs)
    {
    	Expr printExpr = printWithoutArgs.getExpr();
    	Struct printExprStruct = printExpr.struct;
    	
    	if(
    			printExprStruct.getKind() == Struct.Array &&
    			!printExprStruct.getElemType().equals(Tab.intType) &&
    			!printExprStruct.getElemType().equals(Tab.charType) &&
    			!printExprStruct.getElemType().equals(SymbolTable.boolType)
		)
    	{
    		report_error("Greska: Element niza mora biti primitivnog tipa za ispis!", printWithoutArgs);		
    	}
    	else if(
				printExprStruct.getKind() != Struct.Array &&
				!printExprStruct.equals(Tab.intType) &&
				!printExprStruct.equals(Tab.charType) &&
				!printExprStruct.equals(SymbolTable.boolType) 
		)
    	{
    		report_error("Greska: Slozeni tip podataka ne moze da se ispise!", printWithoutArgs);		

    	}	
    }
    
    public void visit(PrintWithArgs printWithArgs)
    {
    	Expr printExpr = printWithArgs.getExpr();
    	Struct printExprStruct = printExpr.struct;
    	
    	if(
    			printExprStruct.getKind() == Struct.Array &&
    			!printExprStruct.getElemType().equals(Tab.intType) &&
    			!printExprStruct.getElemType().equals(Tab.charType) &&
    			!printExprStruct.getElemType().equals(SymbolTable.boolType)
		)
    	{
    		report_error("Greska: Element niza mora biti primitivnog tipa za ispis!", printWithArgs);		
    	}
    	else if(
				printExprStruct.getKind() != Struct.Array &&
				!printExprStruct.equals(Tab.intType) &&
				!printExprStruct.equals(Tab.charType) &&
				!printExprStruct.equals(SymbolTable.boolType) 
		)
    	{
    		report_error("Greska: Slozeni tip podataka ne moze da se ispise!", printWithArgs);		

    	}
    	
    }
    /* END_Read&Print */
    
    
    /* Conditions */
    public void visit(SingleCondFactExpr singleCondFactExpr)
    {
    	Struct exprStruct = singleCondFactExpr.getExpr().struct;
    	singleCondFactExpr.struct = exprStruct;
    	
    	if(!singleCondFactExpr.equals(SymbolTable.boolType))
    	{
    		report_error("Greska: Izraz mora biti logickog tipa Bool!", singleCondFactExpr);
    	}
    	
    }
    
    public void visit(RelopCondFactExpr relopCondFactExpr)
    {
    	Struct leftBoolExprStruct = relopCondFactExpr.getExpr().struct;
    	Struct rightBoolExprStruct = relopCondFactExpr.getExpr1().struct;
    	
    	if(leftBoolExprStruct.getKind() == Struct.Array || rightBoolExprStruct.getKind() == Struct.Array)
    	{
    		if(relopCondFactExpr.getRelop() instanceof BoolEqual || relopCondFactExpr.getRelop() instanceof BoolNotEqual)
    		{
    			if(
    					(leftBoolExprStruct.getKind() == Struct.Array && rightBoolExprStruct == Tab.nullType) ||
    					(rightBoolExprStruct.getKind() == Struct.Array && leftBoolExprStruct == Tab.nullType) ||
    					(leftBoolExprStruct.equals(rightBoolExprStruct))
				)
    			{
    				report_info("Logicki izrazi su kompatibilni!", relopCondFactExpr);
    				
    			}
    			else
    			{
    	    		report_error("Greska: Nekompatiblini tipovi u logickom izrazu", relopCondFactExpr);

    			}
    		}
    		else
    		{
        		report_error("Greska: Uz promenljive tipa niza mogu se koristiti samo != i == operatori!", relopCondFactExpr);

    		}
    		
    	}
    	else if(!leftBoolExprStruct.compatibleWith(rightBoolExprStruct))
    	{
    		report_error("Greska: Nekompatiblini tipovi u logickom izrazu", relopCondFactExpr);
    		
    	}
    	

    	
    }
    
    /* END_Conditions*/
    
    
    
    
    
	
	
	
	

    private Obj contains(String name, List<Obj> list)
    {
    	for(Obj obj : list)
    	{
    		if(obj.getName().equals(obj))
    			return obj;
    	}
    	return Tab.noObj;
    }
	public boolean passed() {
		return !errorDetected;
	}
	

}
