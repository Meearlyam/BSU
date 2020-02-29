package parsers;

import model.Text;
import model.exceptions.InvalidParsingException;
import model.units.code.CodeBlock;
import model.units.text.TextUnit;
import model.units.text.part.Paragraph;
import model.units.text.part.Sentence;

import java.util.ArrayList;

/**
 * paragraph text splitter parser
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class TextParser {

    /**
     * next splitter
     */
    private ParagraphParser nextSplitter;


    public TextParser() {
        nextSplitter = new ParagraphParser();
    }

    /**
     * split text codeBlock and paragraph
     * @param textString text
     * @return return Text object
     */
    public Text splitText(String textString) throws InvalidParsingException {
        Text text = new Text();
        ArrayList<TextUnit> result;
        try {
            result = nextSplitter.split(trim(textString));
        } catch (Exception e) {
            throw e;
        }

        text.setText(result);
        return text;
    }

    /**
     * replace tabs
     * @param text text
     * @return replaced text
     */
    protected String trim(String text){
        text = text.trim();
        text = text.replaceAll( "[\t ]+", " ");
        return text;
    }

}