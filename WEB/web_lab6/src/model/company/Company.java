package model.company;

import model.staff.Staff;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * this is company that consists of several departments and staff
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Company implements RemoteCompany {

    public static int TOTAL_VISITORS_ON_RECEPTION = 3;

    /**
     * lock to synchronize staff
     */
    private Lock staffLock;

    /**
     * semaphore to synchronize reception
     */
    private Semaphore receptionSemaphore;

    /**
     * list of working staff
     */
    private List<Staff> staffs;

    public void comeIn() throws RemoteException {
        try {
            Thread.sleep(1000);
            receptionSemaphore.acquire();
        } catch(InterruptedException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    public void goOut() {
        receptionSemaphore.release();
    }

    /**
     * getter of staff
     *
     * @return staff list
     */
    public List<Staff> getStaff() {
        return staffs;
    }

    /**
     * director of company
     */
    private CompanyDirector director;

    /**
     * getter of director
     *
     * @return director
     */
    public CompanyDirector getDirector() {
        return director;
    }

    /**
     * Constructor that create staff with staff builder
     * @param director company director
     */
    public Company(CompanyDirector director) {
        this.director = director;
        staffs = new ArrayList<>();
        staffLock = new ReentrantLock();
        receptionSemaphore = new Semaphore(TOTAL_VISITORS_ON_RECEPTION);
    }


    /**
     * add staff to staff list
     *
     * @param staff staff to add to list
     */
    public void addStaff(Staff staff) {
        staffLock.lock();
        staffs.add(staff);
        staffLock.unlock();
    }

    /**
     * consult with the employee
     *
     * @param staff staff that client need to consult with
     * @return true if successfully consulted with the staff
     */
    public boolean consultWithStaff(Staff staff) throws RemoteException {
        staffLock.lock();
        boolean result = staffs.remove(staff);
        staffLock.unlock();
        return result;
    }

    /**
     * count total amount of staff in company
     *
     * @return totalStaffAmount
     */
    public int countTotalStaffAmount() {
        staffLock.lock();
        List<Staff> curStaffs = getStaff();
        staffLock.unlock();
        return director.countTotalStaffAmount(curStaffs);
    }

    /**
     * select staff with certain experience
     *
     * @param minExp minimal years of work
     * @param maxExp maximum years of work
     * @return list of staff with defined experience
     */
    public List<Staff> selectStaffByExperience(Double minExp, Double maxExp) {
        staffLock.lock();
        List<Staff> curStaffs = getStaff();
        staffLock.unlock();
        return director.selectStaffByExperience(curStaffs, minExp, maxExp);
    }

    /**
     * select staff of certain department
     *
     * @param department name of the department whose staff is required
     * @return list of staff from required department
     */
    public List<Staff> selectStaffByDepartment(String department) {
        staffLock.lock();
        List<Staff> curStaffs = getStaff();
        staffLock.unlock();
        return director.selectStaffByDepartment(curStaffs, department);
    }

    /**
     * select staff by both parameters(department and below certain work experience)
     *
     * @param experience maximum work experience
     * @param department name of the department staff works in
     * @return list of staff with required department with experience below parameter
     */
    public List<Staff> selectSpecificStaff(double experience, String department) {
        staffLock.lock();
        List<Staff> curStaffs = getStaff();
        staffLock.unlock();
        return director.selectSpecificStaff(curStaffs, experience, department);
    }


    /**
     * sort staff by work experience in company
     *
     * @param isReversed is list should be sorted in reversed order
     * @return sorted staff list
     */
    public List<Staff> sortStaffByExperience(boolean isReversed) {
        staffLock.lock();
        List<Staff> curStaffs = getStaff();
        staffLock.unlock();
        return director.sortStaffByExperience(curStaffs, isReversed);
    }

}
