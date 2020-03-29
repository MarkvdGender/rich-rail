package presentation.antlr.parser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import parser.RichRailLexer;
import parser.RichRailListener;
import parser.RichRailParser;

public class Command {

	public void command(String command) {
		CharStream lineStream = CharStreams.fromString(command);

		Lexer lexer = new RichRailLexer(lineStream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);

		RichRailParser parser = new RichRailParser(tokens);
		ParseTree tree = parser.command();

		ParseTreeWalker walker = new ParseTreeWalker();
		RichRailListener listener = new RichRailCli();

		walker.walk(listener, tree);
	}

}
