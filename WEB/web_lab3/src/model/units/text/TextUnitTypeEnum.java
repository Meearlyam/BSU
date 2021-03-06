package model.units.text;

/**
 * text unit type enum
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public enum TextUnitTypeEnum {
    WORD {
        @Override
        public String toString() {
            return "word";
        }
    },
    PUNCTUATION_MARK {
        @Override
        public String toString() {
            return "punctuation mark";
        }
    },
    SENTENCE {
        @Override
        public String toString() {
            return "sentence";
        }
    },
    CODE_LINE {
        @Override
        public String toString() {
            return "code line";
        }
    },
    PARAGRAPH {
        @Override
        public String toString() {
            return "paragraph";
        }
    },
    CODE_BLOCK {
        @Override
        public String toString() {
            return "code block";
        }
    },
    TEXT {
        @Override
        public String toString() {
            return "text";
        }
    }

}
