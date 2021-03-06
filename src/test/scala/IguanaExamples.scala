import iguana.parsetrees.sppf.SPPFVisualization
import iguana.parsetrees.term.TermVisualization
import org.iguana.grammar.symbol.Nonterminal
import iguana.utils.input.Input
import org.iguana.grammar.iggy.IggyParser
import org.iguana.parser.{ParseError, ParseSuccess, Iguana}
import org.scalatest.FunSuite

class IguanaExamples extends FunSuite {

  test("SimpleGrammar") {

    val grammar = IggyParser.getGrammar(Input.fromPath("src/resources/grammars/Simple.iggy"))
    val input = Input.fromString("aaa")
    val start = Nonterminal.withName("A")

    val result = Iguana.parse(input, grammar, start)

    assert(result.isParseSuccess)

    result match {
      case s:ParseSuccess => SPPFVisualization.generate(s.sppfNode, "graphs", "simple_sppf")
                             TermVisualization.generate(s.getTree, "graphs", "simple_terms")
      case _              => _
    }
  }

  test("XML Parser") {
    val grammar = IggyParser.getGrammar(Input.fromPath("src/resources/grammars/XML.iggy"))
    val input = """|<note>
                  |    <from>Bob</from>
                  |    <to>Alice</to>
                  |</note>""".stripMargin

    val start = grammar.getStartSymbol(Nonterminal.withName("Start"))

    val result = Iguana.parse(input, grammar, start)

    assert(result.isParseSuccess)

    result match {
      case s:ParseSuccess => SPPFVisualization.generate(s.sppfNode, "graphs", "xml_sppf")
                             TermVisualization.generate(s.getTree, "graphs", "xml_terms")
                             TermVisualization.generateWithoutLayout(s.getTree, "graphs", "xml_terms_no_layout")
      case _              =>
    }

    val input2 = """|<note>
                    |    <from>Bob</to>
                    |    <to>Alice</from>
                    |</note>""".stripMargin

    assert(Iguana.parse(input2, grammar, start).isParseError)
  }

  test("Expressions") {
    val grammar = IggyParser.getGrammar(Input.fromPath("src/resources/grammars/Expression.iggy"))
    val input = "1 + - if 0 then 2 else 3 + 4"
    val start = grammar.getStartSymbol("start")

    val result = Iguana.parse(input, grammar, start)

    assert(result.isParseSuccess)

    result match {
      case s:ParseSuccess => SPPFVisualization.generate(s.sppfNode, "graphs", "expr_sppf")
                             TermVisualization.generate(s.getTree, "graphs", "expr_terms")
                             TermVisualization.generateWithoutLayout(s.getTree, "graphs", "expr_terms_no_layout")
      case _              =>
    }
  }


  test("Indentation") {
    val grammar = IggyParser.getGrammar(Input.fromPath("src/resources/grammars/IndentationSensitive.iggy"))
    val input = """|let x = 1
                   |    y = let z = 2
                   |            w = 3
                   |      in 2 + 3
                   |in 1 + 2 + 3
                 """.stripMargin

    val start = grammar.getStartSymbol("Start")

    val result = Iguana.parse(input, grammar, start)

    assert(result.isParseSuccess)

    result match {
      case s:ParseSuccess => SPPFVisualization.generate(s.sppfNode, "graphs", "indentation_sppf")
                             TermVisualization.generate(s.getTree, "graphs", "indentation_terms")
                             TermVisualization.generateWithoutLayout(s.getTree, "graphs", "indentation_terms_no_layout")
      case _              =>
    }

    val input2 = """let x = 1
                   |    y = let z = 2
                   |            w = 3
                   |      in 2
                   |   + 3
                   |in 1 + 2 + 3"""

    assert(Iguana.parse(input2, grammar, start).isParseError)
  }

  implicit def toInput(s: String): Input = Input.fromString(s)
}