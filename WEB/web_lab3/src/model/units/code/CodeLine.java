package model.units.code;

import model.units.text.TextUnit;
import model.units.text.TextUnitTypeEnum;

/**
 * CodeLine class
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class CodeLine extends TextUnit {

    public static final String DIVIDER = "\n";
    public static final String DIVIDERS_PATTERN = DIVIDER;

    /**
     * code line
     *
     */
    private String codeLine;

    /**
     * code line getter
     * @return code line
     */
    public String getCodeLine() {
        return codeLine;
    }

    /**
     * cosntuctor
     * @param value vlaue of code line
     *
     */
    public CodeLine(String value ){
        super(value, TextUnitTypeEnum.CODE_LINE );
        codeLine = value;
    }

    public String toString() {
        return codeLine;
    }

}
