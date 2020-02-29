package controller;


import localization.LocaleHelper;
import model.Text;
import model.exceptions.UppercaseFirstLettersException;
import model.exceptions.FileException;
import model.exceptions.InvalidParsingException;
import parsers.TextParser;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * word text splitter parser
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Controller {

    private String textString;

    private static Logger logger = LogManager.getLogger();

    public String getTextString() {
        return textString;
    }

    /**
     * loadText method
     *
     * @param path path to file
     * @return text string
     * @throws FileException if no file
     */
    public String loadText(String path) throws FileException {
        logger.info(LocaleHelper.getLocalizedString(LocaleHelper.FILE_LOADING));
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            textString = new String(encoded, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new FileException("Invalid arguments", e);
        }
        logger.info(LocaleHelper.getLocalizedString(LocaleHelper.FILE_LOADED));

        return textString;
    }

    /**
     * method which parses text string to text object
     *
     * @return text object
     */
    public Text parseTextStringToText() throws InvalidParsingException {
        logger.info(LocaleHelper.getLocalizedString(LocaleHelper.START_TEXT_PARSING));
        TextParser textParser = new TextParser();
        Text parsedText;
        try {
            parsedText = textParser.splitText(textString);
        } catch (Exception e) {
            throw e;
        }
        logger.info(LocaleHelper.getLocalizedString(LocaleHelper.FINISH_TEXT_PARSING));

        return parsedText;
    }

    /**
     * uppercase first letter of words in text
     *
     * @param text txt object
     * @return uppercase text object
     */
    public Text uppercaseFirstLetters(Text text) throws UppercaseFirstLettersException {
        logger.info(LocaleHelper.getLocalizedString(LocaleHelper.START_UPPERCASE_WORDS));
        Text uppercaseText;
        try {
            uppercaseText = text.uppercaseFirstLetters();
        }
        catch(Exception e) {
            throw new UppercaseFirstLettersException("Empty result of uppercase operation", e);
        }
        logger.info(LocaleHelper.getLocalizedString(LocaleHelper.FINISH_UPPERCASE_WORDS));

        return uppercaseText;
    }

    /**
     * return maximum length substring that doesn't contain any letters
     *
     * @param text text object
     * @return string that doesn't contain any letters and has maximum length among the same strings
     */
    public String maxNotLetterSubstring(Text text) {
        logger.info(LocaleHelper.getLocalizedString(LocaleHelper.START_NOT_LETTER_SUBSTRING_SEARCH));
        String pattern = "[<>.,/\\-_\\\\?!:;\\[\\]{}()'\"0-9\\n\\s*]+";
        String result = "";
        int max_length = 0;
        Matcher matcher = Pattern.compile(pattern).matcher(text.toString());
        while(matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if((end - start) > max_length) {
                max_length = end - start;
                result = text.toString().substring(start,end);
            }
        }
        logger.info(LocaleHelper.getLocalizedString(LocaleHelper.FINISH_NOT_LETTER_SUBSTRING_SEARCH));

        return result;
    }

    /**
     * deletes all words that starts with vowel and has specified length
     * @param length length of the words that must be deleted from text
     * @param text to delete words from
     * @return string text without specified words
     */
    public Text deleteSpecifiedWords(int length, Text text) {
        logger.info(LocaleHelper.getLocalizedString(LocaleHelper.START_SPECIFIED_DELETING));
        Text result = text.deleteSpecifiedWords(length);
        logger.info(LocaleHelper.getLocalizedString(LocaleHelper.FINISH_SPECIFIED_DELETING));

        return result;
    }
}
