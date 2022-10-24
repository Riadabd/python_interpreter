grammar TFunctions;

@members {
    TFunctions t = new TFunctions();
}

prog 	: (stmt)+ 
		;
	
stmt 	: assignment | element_assignment | list_addition | slicing | list_comprehension |
		print
	 	;

assignment 	: ID { t.initList($ID.text); } '=' '[' value_list[$ID.text] ']'
			;

element_assignment  : ID '[' INT ']' '=' value { if (t.lists.containsKey($ID.text)) { int i = Integer.valueOf($INT.text).intValue(); t.update($ID.text, i, $value.val); } }
					;

list_addition   : originalList = ID '=' firstList = ID { ArrayList<Object> l = new ArrayList<>(); for(Object o: t.lists.get($firstList.text)) { l.add(o); } } ('+' secondList = ID { for(Object o: t.lists.get($secondList.text)) { l.add(o); } })* { t.lists.put($originalList.text, l); }
				;

slicing : firstList = ID '=' secondList = ID '[' (firstIndex = INT)? ':' (secondIndex = INT)? ']' { int startIndex = 0; int lastIndex = t.lists.get($secondList.text).size(); if ($firstIndex.text != null) { startIndex = Integer.parseInt($firstIndex.text); } if ($secondIndex.text != null) { lastIndex = Integer.parseInt($secondIndex.text); } ArrayList<Object> l = new ArrayList<>(); for(int i = startIndex; i < lastIndex; i++) { l.add(t.lists.get($secondList.text).get(i)); } t.lists.put($firstList.text, l); }
		;

list_comprehension	: firstList = ID '=' '[' 'for' variable = ID 'in' secondList = ID 'if' result = logical_statements[$variable.text, $secondList.text] ']' { t.populateMap($firstList.text, $secondList.text, $result.o); }
					;

value_list [String listID]  : v1=value { if (t.lists.containsKey($v1.val)) { for (Object o: t.lists.get($v1.val)) { t.addToList($listID, o); } } else { t.addToList($listID, $v1.val); } } (',' v2=value { if (t.lists.containsKey($v2.val)) { for (Object o: t.lists.get($v2.val)) { t.addToList($listID, o); } } else { t.addToList($listID, $v2.val); } })*
							|
							;

value returns [Object val]		: INT 		{ $val = Integer.valueOf($INT.text); }
								| STRING 	{ $val = String.valueOf($STRING.text); }
								| ID 		{ $val = String.valueOf($ID.text); }
								;

logical_statements [String variableName, String secondList] returns [String o]	: firstAndStat = logical_term[$variableName, $secondList] { $o = $firstAndStat.o; } ('and' secondAndStat = logical_term[$variableName, $secondList] { if ($firstAndStat.o == null || $firstAndStat.o.equals("")) { $o = new String(); } else { $o = t.logicAnd($firstAndStat.o, $secondAndStat.o); } } )*
																						;

logical_term [String variableName, String secondList] returns [String o]	: firstOrStat = logical_negate[$variableName, $secondList] { $o = $firstOrStat.o; } ('or' secondOrStat = logical_negate[$variableName, $secondList] { if ($firstOrStat.o == null || $firstOrStat.o.equals("")) { $o = $secondOrStat.o; } else { $o = t.logicOr($firstOrStat.o, $secondOrStat.o); } } )*
																			;
logical_negate [String variableName, String secondList] returns [String o]	: (notOp = 'not')? cond = logical_factor[$variableName, $secondList] { $o = t.logicNot(secondList, $cond.o, $notOp.text); }
																					;

logical_factor [String variableName, String secondList] returns [String o] : '(' left = INT logical_operator right = INT ')' { int leftArg = Integer.parseInt($left.text); int rightArg = Integer.parseInt($right.text); $o = t.logicalResult(secondList, leftArg, $logical_operator.op, rightArg); }
																	
																	| '(' ID logical_operator INT ')' { if (!(variableName.equals($ID.text))) { System.out.println("Unknown variable " + $ID.text + ". Did you mean " + variableName + "?"); return new String(); } else { int arg = Integer.parseInt($INT.text); $o = t.logicalLeftIDResult(secondList, arg, $logical_operator.text); } }
																	
																	| '(' INT logical_operator ID ')' { if (!(variableName.equals($ID.text))) { System.out.println("Unknown variable " + $ID.text + ". Did you mean " + variableName + "?"); return new String(); } else { int arg = Integer.parseInt($INT.text); $o = t.logicalRightIDResult(secondList, arg, $logical_operator.text); } }

																	| '(' left = ID logical_operator right = ID ')' { if (!(variableName.equals($left.text))) { System.out.println("Unknown variable " + $left.text + ". Did you mean " + variableName +"?"); return new String(); } else if (!(variableName.equals($right.text))) { System.out.println("Unknown variable " + $right.text + ". Did you mean " + variableName +"?"); return new String(); } else { $o = t.logicalIDResult(secondList, $logical_operator.text); }  }
																	
																	| '(' logical_statements[$variableName, $secondList] ')' { $o = $logical_statements.o; }
																	;   

logical_operator returns [String op]	: GT { $op = new String(">"); }
										| GTE { $op = new String(">="); }
										| LT { $op = new String("<"); }
										| LTE { $op = new String("<="); }
										| EQ { $op = new String("=="); }
										| NEQ { $op = new String("!="); }
										;

print 	: 'print' '(' ID ')' { t.printList($ID.text); }
		;


INT:('-')?('0'..'9')+;
ID:('a'..'z'|'A'..'Z'|'\_')('a'..'z'|'A'..'Z'|'0'..'9'|'\_')*;
STRING:'\''('a'..'z'|'A'..'Z'|'0'..'9')* '\'';

GT: '>';
GTE: '>=';
LT: '<';
LTE: '<=';
EQ: '==';
NEQ: '!=';

WS:(' '|'\t'|'\n'|'\r')+{skip();};