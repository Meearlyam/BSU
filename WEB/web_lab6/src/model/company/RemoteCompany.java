package model.company;

import model.staff.Staff;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


/**
 * interface that give RMI methods from company service to clients
 * @author Vera Shavela
 * @version 1.0.0
 */
public interface RemoteCompany extends Remote {

    /**
     * come to company
     *
     */
    void comeIn() throws RemoteException;

    /**
     * go out from company
     *
     */
    void goOut() throws RemoteException;

    /**
     * return staff of the company
     *
     * @return staff list
     */
    List<Staff> getStaff() throws RemoteException;

    /**
     * return company director
     *
     * @return director
     */
    CompanyDirector getDirector() throws RemoteException;

    /**
     * count total amount of staff in company
     *
     * @return totalStaffAmount total amount of staff in company
     */
    int countTotalStaffAmount() throws RemoteException;

    /**
     * consult with the employee
     *
     * @param staff staff that client need to consult with
     * @return true if successfully consulted with the staff
     */
    boolean consultWithStaff(Staff staff) throws RemoteException;

    /**
     * sort staff by work experience in company
     *
     * @param isReversed is list should be sorted in reversed order
     * @return sorted staff list
     */
    List<Staff> sortStaffByExperience(boolean isReversed) throws RemoteException;

    /**
     * select staff by both parameters(department and below certain work experience)
     *
     * @param experience maximum work experience
     * @param department name of the department staff works in
     * @return list of staff with required department with experience below parameter
     */
    List<Staff> selectSpecificStaff(double experience, String department) throws RemoteException;

    /**
     * select staff of certain department
     *
     * @param department name of the department whose staff is required
     * @return list of staff from required department
     */
    List<Staff> selectStaffByDepartment(String department) throws RemoteException;

    /**
     * select staff with certain experience
     *
     * @param minExp minimal years of work
     * @param maxExp maximum years of work
     * @return list of staff with defined experience
     */
    List<Staff> selectStaffByExperience(Double minExp, Double maxExp) throws RemoteException;
}
