package localization;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Locale Wrapper
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class LocaleHelper {
    public static final String DEFAULT_LANGUAGE = "ru";
    private static Locale locale = new Locale(DEFAULT_LANGUAGE);

    private static final String BUNDLE_PATH = "localization/locales/locale";
    private static ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PATH, locale);

    public static String INVALID_ARGS = "InvalidArgs";
    public static String FILE_LOADED = "FileLoaded";
    public static String SPECIFIED_LENGTH = "SpecifiedLength";
    public static String START_COMBINE = "StartCombine";
    public static String UPPERCASE_FIRST_LETTERS = "UppercaseFirstLetters";
    public static String FIRST_MAX_NOT_LETTERS = "FirstMaxLengthNotLetter";
    public static String DELETE_SPECIFIED_WORDS = "DeleteSpecifiedWords";
    public static String FILE_LOADING = "FileLoading";
    public static String START_TEXT_PARSING = "StartTextParsing";
    public static String FINISH_TEXT_PARSING = "FinishTextParsing";
    public static String START_UPPERCASE_WORDS = "StartUppercaseWords";
    public static String FINISH_UPPERCASE_WORDS = "FinishUppercaseWords";
    public static String START_SPECIFIED_DELETING = "StartSpecifiedDeleting";
    public static String FINISH_SPECIFIED_DELETING = "FinishSpecifiedDeleting";
    public static String START_NOT_LETTER_SUBSTRING_SEARCH = "StartNotLetterSubstringSearch";
    public static String FINISH_NOT_LETTER_SUBSTRING_SEARCH = "FinishNotLetterSubstringSearch";
    public static String CONTROLLER_INIT = "ControllerInit";
    public static String UPPERCASE_ERROR = "UppercaseError";
    public static String SPECIFIED_WORDS_NOT_FOUND = "SpecifiedWordsNotFound";
    public static String SHUTDOWN = "Shutdown";

    public static Locale getDefaultLocale(){
        return new Locale(DEFAULT_LANGUAGE);
    }

    public static Locale getCurrentLocale(){
        return locale;
    }

    /**
     * set new locale
     * @param localeStr new loacle string
     */
    public static void setLocale( String localeStr ){
        locale = new Locale(localeStr);
        bundle = ResourceBundle.getBundle(BUNDLE_PATH, locale);
    }

    /**
     * get localized string
     * @param string localized string key
     * @return localized string
     */
    public static String getLocalizedString( String string ){
        return bundle.getString( string );
    }

}