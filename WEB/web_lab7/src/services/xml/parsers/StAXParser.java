package services.xml.parsers;

import exceptions.ParserException;
import model.staff.Staff;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.company.builder.StaffBuilder;
import services.company.manager.StaffManager;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * StAX parser of XML
 *
 * @author Vera Shavela
 * @version 1.0.0
 */

public class StAXParser implements StaffsParser {
    /**
     * Logger for parser
     */
    private static final Logger logger = LogManager.getLogger("Parser");


    /**
     * Parse XML file to collective using DOM parser
     *
     * @param file name of the file that contains staffs stored in XML format
     * @return parsed staff list
     * @throws ParserException if some error occurred while parsing XML file
     */
    @Override
    public List<Staff> parse(String file) throws ParserException {
        logger.info("StAX parsing started");
        List<Staff> staffs = new ArrayList<>();
        StaffManager manager = new StaffManager();
        StaffBuilder builder = new StaffBuilder();

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader;
        try {
            eventReader = factory.createXMLEventReader(new FileReader(file));
        } catch (XMLStreamException | FileNotFoundException e) {
            throw new ParserException("Configuration StAX parser error", e);
        }
        String currentElement = "";
        boolean parsed;
        parsed = true;
        String name = "";
        double salary = 0;
        double experience = 0;
        String department = "";
        int areas = 0;
        int calls = 0;
        int reports = 0;
        int shifts = 0;
        int subordinated = 0;
        int maxKilos = 0;
        boolean isShipbuilder = false;


        while (eventReader.hasNext()) {
            XMLEvent event = null;
            try {
                event = eventReader.nextEvent();
            } catch (XMLStreamException e) {
                throw new ParserException("Fail to get eventReader", e);
            }
            String qName = "";

            switch (event.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    StartElement startElement = event.asStartElement();
                    qName = startElement.getName().getLocalPart();
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
                    break;
                case XMLStreamConstants.CHARACTERS:
                    String value = event.asCharacters().getData();
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
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    EndElement endElement = event.asEndElement();
                    qName = endElement.getName().getLocalPart();
                    switch (qName) {
                        case "accountant":
                            staffs.add(manager.
                                    provideAccountant(
                                            builder,
                                            name,
                                            salary,
                                            experience,
                                            department,
                                            reports,
                                            areas));
                            break;
                        case "receptionist":
                            staffs.add(manager.provideReceptionist(builder, name, salary, experience, department, reports, calls));
                            break;
                        case "welder":
                            staffs.add(manager.provideWelder(builder, name, salary, experience, department, shifts, isShipbuilder));
                            break;
                        case "loader":
                            staffs.add(manager.provideLoader(builder, name, salary, experience, department, shifts, maxKilos));
                            break;
                        case "foreman":
                            staffs.add(manager.provideForeman(builder, name, salary, experience, department, shifts, subordinated));
                            break;
                    }
                    builder.reset();
                    break;
            }
        }

        return staffs;
    }
}