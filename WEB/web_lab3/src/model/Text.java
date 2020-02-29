package model;

import model.exceptions.UppercaseFirstLettersException;
import model.units.code.CodeBlock;
import model.units.text.TextUnit;
import model.units.text.TextUnitTypeEnum;
import model.units.text.part.Paragraph;
import model.units.text.part.Sentence;
import model.units.text.part.Word;
import java.util.ArrayList;

/**
 * word text splitter parser
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Text extends TextUnit {

    public static final String TEXT_NAME = "TEXT";

    /**
     * text units
     */
    private ArrayList<TextUnit> text;

    public void setText(ArrayList<TextUnit> text) {
        this.text = text;
    }

    public Text() {
        super("",TextUnitTypeEnum.TEXT);
        text = new ArrayList<>();
    }

    /**
     * add sentence to array
     * @param sentence sentence
     */
    public void addSentence(Sentence sentence) {
        text.add(sentence);
    }

    /**
     * add paragraph to array
     * @param paragraph paragraph
     */
    public void addParagraph(Paragraph paragraph) {
        text.add(paragraph);
    }

    /**
     * add codeBlock to array
     * @param codeBlock code block
     */
    public void addCode(CodeBlock codeBlock) {
        text.add(codeBlock);
    }

    /**
     * get all sentences
     * @return  sentences in text
     */
    public ArrayList<Sentence> getSentences() {
        ArrayList<Sentence> sentences = new ArrayList<>();
        for (TextUnit textUnit : text) {
            if (textUnit.getClass() == Sentence.class)
                sentences.add((Sentence)textUnit);
        }
        return sentences;
    }

    /**
     * get all text words
     * @return words in text
     */
    public ArrayList<Word> getAllTextWords() {
        ArrayList<Sentence> sentences = getSentences();
        ArrayList<Word> words = new ArrayList<>();
        for (Sentence sentence : sentences) {
            words.addAll(sentence.getWords());
        }
        return words;
    }

    /**
     * get all paragraphs
     * @return  paragraphs in text
     */
    public ArrayList<Paragraph> getParagraphs() {
        ArrayList<Paragraph> paragraphs = new ArrayList<>();
        for (TextUnit textUnit : text) {
            if (textUnit.getClass() == Paragraph.class)
                paragraphs.add((Paragraph)textUnit);
        }
        return paragraphs;
    }

    /**
     * get all code blocks
     * @return code blocks in text
     */
    public ArrayList<CodeBlock> getCodeBlocks() {
        ArrayList<CodeBlock> codeBlocks = new ArrayList<>();
        for (TextUnit textUnit : text) {
            if (textUnit.getClass() == CodeBlock.class)
                codeBlocks.add((CodeBlock)textUnit);
        }
        return codeBlocks;
    }

    /**
     * uppercase first letters of all words
     * @return text with uppercase first letters
     */
    public Text uppercaseFirstLetters() throws UppercaseFirstLettersException {
        Text result = new Text();
        for (TextUnit textUnit : text) {
            if(textUnit.getClass() == Sentence.class) {
                try {
                    result.text.add(((Sentence) textUnit).uppercaseFirstLetters());
                } catch (Exception e) {
                    throw e;
                }
            }
            else {
                result.text.add(textUnit);
            }

        }

        if(result.text.isEmpty()){
            throw new UppercaseFirstLettersException("Empty result of uppercase first letters operation");
        }

        return result;
    }

    /**
     * delete specified words
     *
     * @param length length of this words
     * @return text without this words
     */
    public Text deleteSpecifiedWords(int length) {
        Text result = new Text();
        for (TextUnit textUnit : text) {
            if(textUnit.getClass() == Sentence.class) {
                try {
                    result.text.add(((Sentence) textUnit).deleteSpecifiedWords(length));
                } catch (Exception e) {
                    throw e;
                }
            }
            else {
                result.text.add(textUnit);
            }

        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder textToString = new StringBuilder();
        for (TextUnit textUnit : text) {
            textToString.append(textUnit.toString());
            if (textUnit.getClass() == Sentence.class)
                textToString.append(" ");
        }
        return textToString.toString();
    }
}