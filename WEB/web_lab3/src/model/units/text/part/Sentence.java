package model.units.text.part;

import model.units.text.TextUnitTypeEnum;
import model.units.text.TextUnit;
import java.util.ArrayList;

/**
 * Sentence
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Sentence extends TextUnit {

    public static final String SENTENCE_NAME = "SENTENCE";
    public static final String DIVIDER = "\\.!\\?";
    public static final String DELIM_FOR_COMBINING = " ";
    public static final String SPLITTING_REGEX = "[^" + DIVIDER + "]+([" + DIVIDER + "]+|\\z)";
    private ArrayList<TextUnit> sentence;


    /**
     * constructor
     * @param text text sentence value
     *
     */
    public Sentence(String text) {
        super(text, TextUnitTypeEnum.SENTENCE);
        sentence = new ArrayList<>();
    }

    /**
     * add word to sentence
     * @param word word
     *
     */
    public void addWord(Word word) {
        sentence.add(word);
    }

    public ArrayList<Word> getWords() {
        ArrayList<Word> words = new ArrayList<>();
        for (TextUnit textUnit : sentence) {
            if (textUnit.getClass() == Word.class)
                words.add((Word)textUnit);
        }
        return words;
    }

    /**
     * add  punctuation mark
     * @param punctuationMark punctuation mark
     *
     */
    public void addPunctuationMark(PunctuationMark punctuationMark) {
        sentence.add(punctuationMark);
    }


    /**
     * makes first letter of all words uppercase
     *
     * @return uppercase sentence
     */
    public Sentence uppercaseFirstLetters() {
        Sentence result = new Sentence("");
        for (TextUnit textUnit : sentence) {
            if(textUnit.getType() == TextUnitTypeEnum.WORD) {
                Word w = new Word(textUnit.getValue());
                textUnit = w.uppercaseFirstLetter();
                result.sentence.add(textUnit);
            }
            else {
                result.sentence.add(textUnit);
            }
        }
        return result;
    }

    /**
     * delete specified words from sentence
     *
     * @param length length of this words
     * @return sentence without this words
     */
    public Sentence deleteSpecifiedWords(int length) {
        Sentence result = new Sentence("");
        for (TextUnit textUnit : sentence) {
            if(textUnit.getType() == TextUnitTypeEnum.WORD) {
                Word w = new Word(textUnit.getValue());
                if(w.deleteIfNeeded(length))
                    result.sentence.add(textUnit);
            }
            else {
                result.sentence.add(textUnit);
            }
        }
        return result;
    }


    @Override
    public String toString() {
        StringBuilder textToString = new StringBuilder();
        for (TextUnit textUnit : sentence) {
            if (textUnit.getClass() != PunctuationMark.class && sentence.indexOf(textUnit) != 0)
                textToString.append(DELIM_FOR_COMBINING);
            textToString.append(textUnit.toString());

        }
        return textToString.toString();
    }
}
