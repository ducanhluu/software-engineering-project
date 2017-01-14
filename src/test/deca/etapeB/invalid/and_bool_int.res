DEBUG fr.ensimag.deca.syntax.AbstractDecaParser$1.reportError(AbstractDecaParser.java:86) - Error found by ANTLR
INFO  fr.ensimag.deca.syntax.DecacErrorListner.syntaxError(DecacErrorListner.java:42) - Use token info for Location
DEBUG fr.ensimag.deca.syntax.AbstractDecaParser.parseProgramAndManageErrors(AbstractDecaParser.java:44) - ParseCancellationException raised while compiling file:
org.antlr.v4.runtime.misc.ParseCancellationException
	at fr.ensimag.deca.syntax.DecacErrorListner.syntaxError(DecacErrorListner.java:62)
	at org.antlr.v4.runtime.ProxyErrorListener.syntaxError(ProxyErrorListener.java:65)
	at org.antlr.v4.runtime.Parser.notifyErrorListeners(Parser.java:564)
	at org.antlr.v4.runtime.DefaultErrorStrategy.reportInputMismatch(DefaultErrorStrategy.java:325)
	at org.antlr.v4.runtime.DefaultErrorStrategy.reportError(DefaultErrorStrategy.java:148)
	at fr.ensimag.deca.syntax.AbstractDecaParser$1.reportError(AbstractDecaParser.java:94)
	at fr.ensimag.deca.syntax.DecaParser.select_expr(DecaParser.java:2094)
	at fr.ensimag.deca.syntax.DecaParser.unary_expr(DecaParser.java:1959)
	at fr.ensimag.deca.syntax.DecaParser.mult_expr(DecaParser.java:1789)
	at fr.ensimag.deca.syntax.DecaParser.sum_expr(DecaParser.java:1671)
	at fr.ensimag.deca.syntax.DecaParser.inequality_expr(DecaParser.java:1494)
	at fr.ensimag.deca.syntax.DecaParser.eq_neq_expr(DecaParser.java:1370)
	at fr.ensimag.deca.syntax.DecaParser.and_expr(DecaParser.java:1280)
	at fr.ensimag.deca.syntax.DecaParser.or_expr(DecaParser.java:1191)
	at fr.ensimag.deca.syntax.DecaParser.assign_expr(DecaParser.java:1105)
	at fr.ensimag.deca.syntax.DecaParser.expr(DecaParser.java:1062)
	at fr.ensimag.deca.syntax.DecaParser.if_then_else(DecaParser.java:878)
	at fr.ensimag.deca.syntax.DecaParser.inst(DecaParser.java:744)
	at fr.ensimag.deca.syntax.DecaParser.list_inst(DecaParser.java:549)
	at fr.ensimag.deca.syntax.DecaParser.block(DecaParser.java:259)
	at fr.ensimag.deca.syntax.DecaParser.main(DecaParser.java:205)
	at fr.ensimag.deca.syntax.DecaParser.prog(DecaParser.java:152)
	at fr.ensimag.deca.syntax.DecaParser.parseProgram(DecaParser.java:119)
	at fr.ensimag.deca.syntax.AbstractDecaParser.parseProgramAndManageErrors(AbstractDecaParser.java:40)
	at fr.ensimag.deca.context.ManualTestContext.main(ManualTestContext.java:29)
Caused by: fr.ensimag.deca.tree.LocationException: mismatched input 'and' expecting {'instanceof', ')', '=', '&&', '||', '==', '!=', '<=', '>=', '<', '>', '+', '-', '*', '/', '%', '.'}
	... 25 more
./src/test/deca/etapeB/invalid/and_bool_int.deca:7:10: mismatched input 'and' expecting {'instanceof', ')', '=', '&&', '||', '==', '!=', '<=', '>=', '<', '>', '+', '-', '*', '/', '%', '.'}
