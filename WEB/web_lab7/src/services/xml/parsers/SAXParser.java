package services.xml.parsers;

import exceptions.ParserException;
import model.staff.Staff;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import services.company.builder.StaffBuilder;
import services.company.manager.StaffManager;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SAX parser of XML
 *
 * @author Vera Shavela
 * @version 1.0.0
 */

public class SAXParser implements StaffsParser {
    /**
     * Logger for parser
     */
    private static final Logger logger = LogManager.getLogger("Parser");

    /**
     * Parse XML file to collective using DOM parser
     *
     * @param fileName name of the file that contains staffs stored in XML format
     * @return parsed staffs list
     * @throws ParserException if some error occurred while parsing XML file
     */
    @Override
    public List<Staff> parse(String fileName) throws ParserException {
        logger.info("SAX parsing started");
        List<Staff> staffs;

        try {
            File inputFile = new File(fileName);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();
            Handler handler = new Handler();
            saxParser.parse(inputFile, handler);
            staffs = handler.getStaffs();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            throw new ParserException("Configuration SAX parser error", e);
        }

        logger.info("Finish SAX parsing");
        return staffs;
    }

    /**
     * Tags handler
     */
    private class Handler extends DefaultHandler {

        private StaffManager manager = new StaffManager();

        private StaffBuilder builder = new StaffBuilder();

        private List<Staff> staffs = new ArrayList<>();

        private boolean parsed = true;
        private String currentElement;

        private String name;
        private double salary;
        private double experience;
        private String department;
        private int areas;
        private int calls;
        private int reports;
        private int shifts;
        private int subordinated;
        private int maxKilos;
        private boolean isShipbuilder;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currentElement = qName;
            if (qName.equals("name") ||
                    qName.equals("salary") ||
                    qName.equals("experience") ||
                    qName.equals("department") ||
                    qName.equals("areas") ||
                    qName.equals("calls") ||
                    qName.equals("reports") ||
                    qName.equals("shifts") ||
                    qName.equals("subordinated") ||
                    qName.equals("maxKilos") ||
                    qName.equals("isShipbuilder")) {
                parsed = false;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            switch (qName) {
                case "accountant":
                    staffs.add(manager.provideAccountant(builder, name, salary, experience,
                            department, reports, areas));
                    break;
                case "receptionist":
                    staffs.add(manager.provideReceptionist(builder, name, salary, experience,
                            department, reports, calls));
                    break;
                case "welder":
                    staffs.add(manager.provideWelder(builder, name, salary, experience,
                            department, shifts, isShipbuilder));
                    break;
                case "loader":
                    staffs.add(manager.provideLoader(builder, name, salary, experience,
                            department, shifts, maxKilos));
                    break;
                case "foreman":
                    staffs.add(manager.provideForeman(builder, name, salary, experience,
                            department, shifts, subordinated));
                    break;
            }
            builder.reset();
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String value = new String(ch, start, length);
            if (!parsed) {
                switch (currentElement) {
                    case "name":
                        name = value;
                        break;
                    case "salary":
                        salary = Double.parseDouble(value);
                        break;
                    case "experience":
                        experience = Double.parseDouble(value);
                        break;
                    case "department":
                        department = value;
                        break;
                    case "areas":
                        areas = Integer.parseInt(value);
                        break;
                    case "calls":
                        calls = Integer.parseInt(value);
                        break;
                    case "reports":
                        reports = Integer.parseInt(value);
                        break;
                    case "shifts":
                        shifts = Integer.parseInt(value);
                        break;
                    case "subordinates":
                        shifts = Integer.parseInt(value);
                        break;
                    case "maxKilos":
                        maxKilos = Integer.parseInt(value);
                        break;
                    case "isShipbuilder":
                        isShipbuilder = Boolean.parseBoolean(value);
                        break;
                }

                parsed = true;
            }


        }


        public List<Staff> getStaffs() {
            return staffs;
        }
    }
}
