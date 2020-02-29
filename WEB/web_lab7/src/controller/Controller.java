package controller;

import exceptions.ParserException;
import exceptions.XMLException;
import model.ParsingModeEnum;
import services.xml.XMLValidator;
import services.xml.parsers.DOMParser;
import services.xml.parsers.SAXParser;
import services.xml.parsers.StAXParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import model.company.Company;
import model.staff.Staff;
import java.util.List;

/**
 * Basic class demonstrates functions of classes
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Controller {

    /**
     * Logger for creating logfile via log4j2
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * company
     */
    private Company company;

    /**
     * getter of company
     *
     * @return company
     */
    public Company getCompany() {
        return company;
    }


    /**
     * Constructor that create controller to work with company
     *
     * @param company company
     */
    public Controller(Company company) {
        this.company = company;
    }

    /**
     * Method that parses the file into staff list object
     * @param file name of the file that contains staffs stored in XML format
     * @param mode flag that defines which parser to use while parsing file
     * @return staff list object
     * @throws ParserException
     */
    public List<Staff> parseMedicinesList(String file, ParsingModeEnum mode) throws ParserException {
        switch (mode) {
            case DOM:
                return (new DOMParser()).parse(file);
            case SAX:
                return (new SAXParser()).parse(file);
            case StAX:
                return (new StAXParser()).parse(file);
            default:
                return null;
        }
    }

    /**
     * Validates XML file with given schema
     * @param file XML file
     * @param schema XSD file
     * @return true if validation succeed, otherwise false
     */
    public boolean validate(String file, String schema) throws XMLException {
        boolean result = XMLValidator.validate(file, schema);
        if (result) {
            logger.info("Validation succeed");
        } else {
            logger.info("Validation failed");
        }
        return result;
    }

    /**
     * Method to add new staff into company
     */
    public void addStaff(Staff staff) {
        company.addStaff(staff);
    }

    /**
     * Method that returns staff list of company
     *
     * @return staff list
     */
    public List<Staff> getCompanyStaff() {
        return company.getStaff();
    }

    /**
     * Method to get amount of staff in company
     *
     * @return staff amount in company
     */
    public int getCompanyStaffAmount() {
        return company.countTotalStaffAmount();
    }

    /**
     * Method to demonstrate sorting (by experience)
     *
     * @param isReversed determine the order of list
     * @return results of work to show
     */
    public List<Staff> getSortedCompanyStaff(boolean isReversed) {
        return company.sortStaffByExperience(isReversed);
    }

    /**
     * Method to demonstrate selecting staff by experience
     *
     * @param minExp minimal experience of work
     * @param maxExp maximum experience of work
     * @return results of work to show
     */
    public List<Staff> getCompanyStaffSelectByExperience(double minExp, double maxExp) {
        return company.selectStaffByExperience(minExp, maxExp);
    }

    /**
     * Method to demonstrate selecting staff by department
     *
     * @param department department the staff works
     * @return results of work to show
     */
    public List<Staff> getCompanyStaffSelectByDepartment(String department) {
        return company.selectStaffByDepartment(department);
    }

    /**
     * Method to demonstrate specified selecting staff
     *
     * @param department name of department staff works in
     * @param experience maximum work experience of staff
     * @return staff list that meets the specified criteria
     */
    public List<Staff> getCompanyStaffByBothCriteria(String department, double experience) {
        return company.selectSpecificStaff(experience, department);
    }
}