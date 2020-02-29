package services.xml.parsers;

import exceptions.ParserException;
import model.staff.Staff;

import java.util.List;

/**
 * Parsing interface for all DOM, SAX, StAX parsers
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public interface StaffsParser {
    List<Staff> parse(String file) throws ParserException;
}
