package parsers;

import model.exceptions.InvalidParsingException;
import model.units.code.CodeBlock;
import model.units.code.CodeLine;
import model.units.text.TextUnit;
import model.units.text.part.Paragraph;
import model.units.text.part.Sentence;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * sentence text parser
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class SentenceParser {

    /**
     * next splitter
     */
    private WordParser nextSplitter;

    public SentenceParser() {
        nextSplitter = new WordParser();
    }

    public ArrayList<TextUnit> split(ArrayList<TextUnit> textUnits) throws InvalidParsingException {
        ArrayList<TextUnit> result = new ArrayList<>();
        for (TextUnit textUnit : textUnits) {
            if (textUnit.getClass() == CodeBlock.class) {
                String[] lines = textUnit.getValue().split(CodeLine.DIVIDER);
                CodeLine currentLine = null;
                CodeBlock codeBlock = new CodeBlock(textUnit.getValue());
                for (String line : lines) {
                    line = trim(line);
                    if (line.length() > 0) {
                        currentLine = new CodeLine((line));
                        codeBlock.addCodeLine(currentLine);
                    }
                }
                result.add(codeBlock);
            } else if (textUnit.getClass() == Paragraph.class) {
                Sentence sentence = null;
                Matcher matcher = Pattern.compile(Sentence.SPLITTING_REGEX).matcher(textUnit.getValue());
                result.add(textUnit);
                while (matcher.find()) {
                    sentence = new Sentence(trim(matcher.group()));
                    result.add(sentence);
                }
            }
        }

        if (result.isEmpty())
            throw new InvalidParsingException("There is no sentence or code lines");
        ArrayList<TextUnit> splited;

        try {
            splited = nextSplitter.split(result);
        }
        catch (Exception e) {
            throw e;
        }

        return splited;
    }

    /**
     * replace tabs
     * @param text text
     * @return replaced text
     */
    protected String trim(String text) {
        text = text.trim();
        text = text.replaceAll("[\t ]+", " ");
        return text;
    }

    @Override
    public String toString() {
        return "Splitting into sentences and code lines";
    }
}
