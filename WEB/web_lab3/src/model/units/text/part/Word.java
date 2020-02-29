package model.units.text.part;

import model.units.text.TextUnit;
import model.units.text.TextUnitTypeEnum;

/**
 * Word
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Word extends TextUnit {

    public static final String WORD_NAME = "WORD";
    public static final String DIVIDER = ",:;'\"";
    public static final String PATTERN = "[^ " + Paragraph.DIVIDER + Sentence.DIVIDER + DIVIDER +  "]+";

    /**
     * word value getter
     * @return word
     */
    public String getText() {
        return text;
    }

    /**
     * word value
     */
    private String text;

    /**
     * constructor
     * @param text word value
     *
     */
    public Word(String text) {
        super(text, TextUnitTypeEnum.WORD);
        this.text = text;
    }

    /**
     * makes first letter of word uppercase
     *
     */
    public Word uppercaseFirstLetter() {
        this.text = this.value.substring(0,1).toUpperCase() + this.value.substring(1);
        this.value = this.text;
        return this;
    }

    /**
     * delete the word if it is corresponding
     *
     * @param length length of the specified words
     * @return is it need to delete the word
     */
    public boolean deleteIfNeeded(int length) {
        int index = "AEYUIOaeuioyАЕЁЮЯИУЫОЭаеёюяиыоуэ".indexOf(text.charAt(0));
        if((text.length() == length) && (index > -1)){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return text;
    }

}