all:
	@java -classpath .:antlr/antlr-3.5.2-complete.jar org.antlr.Tool src/TFunctions.g
	@javac -classpath .:antlr/antlr-3.5.2-complete.jar src/*.java
	@java -classpath .:antlr/antlr-3.5.2-complete.jar:src/ TestT

clean:
	@rm -rf *.tokens src/*.class src/TFunctionsLexer.java src/TFunctionsParser.java .antlr