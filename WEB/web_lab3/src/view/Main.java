package view;

import controller.Controller;
import model.Text;
import model.exceptions.FileException;
import model.exceptions.InvalidParsingException;
import localization.LocaleHelper;
import model.exceptions.UppercaseFirstLettersException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class which runs controller's demonstrate method
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Main {

    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final Logger logger = LogManager.getLogger();

    /**
     * main method
     * @param args argument lines
     */
    public static void main(String[] args) {
        int length = 10;
        logger.info(LocaleHelper.getLocalizedString(LocaleHelper.CONTROLLER_INIT));
        Controller controller = new Controller();
        try {
            String result = controller.loadText(args[0]);
            System.out.println(ANSI_GREEN + "------" + LocaleHelper.getLocalizedString(LocaleHelper.FILE_LOADED) + " ------" + ANSI_RESET);
            System.out.println(result);
            System.out.println();
            System.out.println();

            System.out.print(ANSI_GREEN + "------" + LocaleHelper.getLocalizedString(LocaleHelper.START_COMBINE) + "------" + ANSI_RESET);
            Text text = controller.parseTextStringToText();
            System.out.println(text.toString());
            System.out.println();

            System.out.println(ANSI_GREEN + "------" + (LocaleHelper.getLocalizedString(LocaleHelper.UPPERCASE_FIRST_LETTERS)) + "------" + ANSI_RESET);
            System.out.println(controller.uppercaseFirstLetters(text).toString());
            System.out.println();

            System.out.println(ANSI_GREEN + "------" + (LocaleHelper.getLocalizedString(LocaleHelper.FIRST_MAX_NOT_LETTERS)) + "------" + ANSI_RESET);
            System.out.println(controller.maxNotLetterSubstring(text));
            System.out.println();

            System.out.println(ANSI_GREEN + "------" + (LocaleHelper.getLocalizedString(LocaleHelper.DELETE_SPECIFIED_WORDS)) + "------" + ANSI_RESET);
            System.out.println(LocaleHelper.getLocalizedString(LocaleHelper.SPECIFIED_LENGTH) + " : " + length);
            System.out.println(controller.deleteSpecifiedWords(length, text));
            System.out.println();

        } catch (FileException | InvalidParsingException | ArrayIndexOutOfBoundsException | UppercaseFirstLettersException ex) {
            logger.error(ex.getMessage(), ex);
            System.out.println();
            ex.printStackTrace();
        }
        logger.info(LocaleHelper.getLocalizedString(LocaleHelper.SHUTDOWN));
    }
}
