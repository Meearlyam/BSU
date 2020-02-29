package services.xml.parsers;

import exceptions.ParserException;
import model.staff.Staff;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import services.company.builder.StaffBuilder;
import services.company.manager.StaffManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * DOM parser of xml files
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class DOMParser implements StaffsParser {
    /**
     * Logger for parser
     */
    private static final Logger logger = LogManager.getLogger("Parser");

    /**
     * Parse XML file to staff list using DOM parser
     *
     * @param file name of the file that contains staffs stored in XML format
     * @return parsed staff list
     * @throws ParserException if some error occurred while parsing XML file
     */
    @Override
    public List<Staff> parse(String file) throws ParserException {
        logger.info("DOM parsing started");
        List<Staff> staffs = new ArrayList<>();
        StaffBuilder builder = new StaffBuilder();
        StaffManager manager = new StaffManager();

        File inputFile = new File(file);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        Document doc = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new ParserException("Configuration DOM parser error", e);
        }
        doc.getDocumentElement().normalize();


        NodeList accountantNodes = doc.getElementsByTagName("accountant");
        NodeList receptionistNodes = doc.getElementsByTagName("receptionist");
        NodeList foremanNodes = doc.getElementsByTagName("foreman");
        NodeList loaderNodes = doc.getElementsByTagName("loader");
        NodeList welderNodes = doc.getElementsByTagName("welder");


        for (int i = 0; i < accountantNodes.getLength(); ++i) {
            Node node = accountantNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String name = element.getElementsByTagName("name").item(0).getTextContent();
                double salary = Double.parseDouble(element.getElementsByTagName("salary").item(0).getTextContent());
                double experience = Double.parseDouble(element.getElementsByTagName("experience").item(0).getTextContent());
                String department = element.getElementsByTagName("department").item(0).getTextContent();
                int reports = Integer.parseInt(element.getElementsByTagName("reports").item(0).getTextContent());
                int areas = Integer.parseInt(element.getElementsByTagName("areas").item(0).getTextContent());

                staffs.add(manager.provideAccountant(builder, name, salary, experience, department, reports, areas));
                builder.reset();
            }
        }

        for (int i = 0; i < receptionistNodes.getLength(); ++i) {
            Node node = receptionistNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String name = element.getElementsByTagName("name").item(0).getTextContent();
                double salary = Double.parseDouble(element.getElementsByTagName("salary").item(0).getTextContent());
                double experience = Double.parseDouble(element.getElementsByTagName("experience").item(0).getTextContent());
                String department = element.getElementsByTagName("department").item(0).getTextContent();
                int reports = Integer.parseInt(element.getElementsByTagName("reports").item(0).getTextContent());
                int calls = Integer.parseInt(element.getElementsByTagName("calls").item(0).getTextContent());

                staffs.add(manager.provideReceptionist(builder, name, salary, experience, department, reports, calls));
                builder.reset();
            }
        }

        for (int i = 0; i < foremanNodes.getLength(); ++i) {
            Node node = foremanNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String name = element.getElementsByTagName("name").item(0).getTextContent();
                double salary = Double.parseDouble(element.getElementsByTagName("salary").item(0).getTextContent());
                double experience = Double.parseDouble(element.getElementsByTagName("experience").item(0).getTextContent());
                String department = element.getElementsByTagName("department").item(0).getTextContent();
                int shifts = Integer.parseInt(element.getElementsByTagName("shifts").item(0).getTextContent());
                int subordinated = Integer.parseInt(element.getElementsByTagName("subordinated").item(0).getTextContent());

                staffs.add(manager.provideForeman(builder, name, salary, experience, department, shifts, subordinated));
                builder.reset();
            }
        }

        for (int i = 0; i < loaderNodes.getLength(); ++i) {
            Node node = loaderNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String name = element.getElementsByTagName("name").item(0).getTextContent();
                double salary = Double.parseDouble(element.getElementsByTagName("salary").item(0).getTextContent());
                double experience = Double.parseDouble(element.getElementsByTagName("experience").item(0).getTextContent());
                String department = element.getElementsByTagName("department").item(0).getTextContent();
                int shifts = Integer.parseInt(element.getElementsByTagName("shifts").item(0).getTextContent());
                int maxKilos = Integer.parseInt(element.getElementsByTagName("maxKilos").item(0).getTextContent());

                staffs.add(manager.provideLoader(builder, name, salary, experience, department, shifts, maxKilos));
                builder.reset();
            }
        }

        for (int i = 0; i < welderNodes.getLength(); ++i) {
            Node node = welderNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String name = element.getElementsByTagName("name").item(0).getTextContent();
                double salary = Double.parseDouble(element.getElementsByTagName("salary").item(0).getTextContent());
                double experience = Double.parseDouble(element.getElementsByTagName("experience").item(0).getTextContent());
                String department = element.getElementsByTagName("department").item(0).getTextContent();
                int shifts = Integer.parseInt(element.getElementsByTagName("shifts").item(0).getTextContent());
                boolean isShipbuilder = Boolean.parseBoolean(element.getElementsByTagName("isShipbuilder").item(0).getTextContent());

                staffs.add(manager.provideWelder(builder, name, salary, experience, department, shifts, isShipbuilder));
                builder.reset();
            }
        }

        logger.info("Finish DOM parsing");
        return staffs;
    }
}
