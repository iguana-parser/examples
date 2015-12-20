import org.iguana.grammar.symbol.Nonterminal

object IguanaExamples extends App {

  import org.iguana.grammar.iggy.IggyParser
  import iguana.utils.input.Input
  import org.iguana.parser.Iguana

  val simpleGrammar = IggyParser.getGrammar(Input.fromPath("src/resources/grammars/Simple.iggy"))
  val result = Iguana.parse(Input.fromString("aaa"), simpleGrammar, Nonterminal.withName("A"))
  println(result)
}