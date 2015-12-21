import iguana.parsetrees.sppf.SPPFVisualization
import iguana.parsetrees.term.TermVisualization
import org.iguana.grammar.symbol.{Start, Nonterminal}
import iguana.utils.input.Input
import org.iguana.grammar.iggy.IggyParser
import org.iguana.parser.Iguana

object IguanaExamples extends App {

  parseSimple
  parseXML
  parseExpression
  parseIndentation

//  val indentationGrammar = IggyParser.getGrammar(Input.fromPath("src/resources/grammars/IndentationSensitive.iggy"))

//  val expressionGrammar = IggyParser.getGrammar(Input.fromPath("src/resources/grammars/Expression.iggy"))

  def parseSimple(): Unit = {
    val grammar = IggyParser.getGrammar(Input.fromPath("src/resources/grammars/Simple.iggy"))
    val input = Input.fromString("aaa")
    val start = Nonterminal.withName("A")

    val result = Iguana.parse(input, grammar, start)

    if (result.isParseSuccess) {
      SPPFVisualization.generate(result.asParseSuccess().getSPPFNode, "graphs", "simple_sppf")
      TermVisualization.generate(result.asParseSuccess().getTree(), "graphs", "simple_terms")
    }
  }

  def parseXML {
    val grammar = IggyParser.getGrammar(Input.fromPath("src/resources/grammars/XML.iggy"))
    val input = """|<note>
                   |    <from>Bob</from>
                   |    <to>Alice</to>
                   |</note>""".stripMargin

    val start = grammar.getStartSymbol(Nonterminal.withName("Start"))

    val result = Iguana.parse(input, grammar, start)

    if (result.isParseSuccess) {
      SPPFVisualization.generate(result.asParseSuccess().getSPPFNode, "graphs", "xml_sppf")
      TermVisualization.generate(result.asParseSuccess().getTree(), "graphs", "xml_terms")
      TermVisualization.generateWithoutLayout(result.asParseSuccess().getTree(), "graphs", "xml_terms_no_layout")
    }
  }

  def parseExpression {
    val grammar = IggyParser.getGrammar(Input.fromPath("src/resources/grammars/Expression.iggy"))
    val input = "1 + - if 0 then 2 else 3 + 4"
    val start = grammar.getStartSymbol("start")

    val result = Iguana.parse(input, grammar, start)

    if (result.isParseSuccess) {
      SPPFVisualization.generate(result.asParseSuccess().getSPPFNode, "graphs", "expr_sppf")
      TermVisualization.generate(result.asParseSuccess().getTree(), "graphs", "expr_terms")
      TermVisualization.generateWithoutLayout(result.asParseSuccess().getTree(), "graphs", "expr_terms_no_layout")
    }
  }


  def parseIndentation {
    val grammar = IggyParser.getGrammar(Input.fromPath("src/resources/grammars/IndentationSensitive.iggy"))
    val input = """|let x = 1
                   |    y = let z = 2
                   |            w = 3
                   |      in 2 + 3
                   |in 1 + 2 + 3
                 """.stripMargin

    val start = grammar.getStartSymbol("Start")

    val result = Iguana.parse(input, grammar, start)

    if (result.isParseSuccess) {
      SPPFVisualization.generate(result.asParseSuccess().getSPPFNode, "graphs", "indentation_sppf")
      TermVisualization.generate(result.asParseSuccess().getTree(), "graphs", "indentation_terms")
      TermVisualization.generateWithoutLayout(result.asParseSuccess().getTree(), "graphs", "indentation_terms_no_layout")
    }
  }

    implicit def toInput(s: String): Input = Input.fromString(s)
}