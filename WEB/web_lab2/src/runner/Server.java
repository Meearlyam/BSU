package runner;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import util.manager.StaffManager;
import util.builder.StaffBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import model.company.*;


/**
 * Server class that contains company
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Server {

    /**
     * log4j logger
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * Server main method
     *
     * @param args command line params
     */
    public static void main(String[] args) {
        Company company = new Company(new CompanyDirector());
        StaffManager manager = new StaffManager();
        StaffBuilder builder = new StaffBuilder();

        company.addStaff(manager.provideAccountant(builder, "Mark Austin (accountant 1)", "Finance department"));
        builder.reset();

        company.addStaff(manager.provideAccountant(builder, "Kate Sawyer (accountant 2)", "Finance department"));
        builder.reset();

        company.addStaff(manager.provideReceptionist(builder, "Mary Katy (receptionist)", "Finance department"));
        builder.reset();

        company.addStaff(manager.provideForeman(builder, "Dave Winning (foreman)", "Workman department"));
        builder.reset();

        company.addStaff(manager.provideLoader(builder, "Mitchel Knight (loader 1)", "Workman department"));
        builder.reset();

        company.addStaff(manager.provideLoader(builder, "Terry Krowly (loader 2)", "Workman department"));
        builder.reset();

        company.addStaff(manager.provideWelder(builder, "Paul Smith (welder 1)", "Workman department"));
        builder.reset();

        company.addStaff(manager.provideWelder(builder, "Stacy Connor", "Workman department"));
        builder.reset();

        logger.info("Create company");

        try {
            RemoteCompany stub = (RemoteCompany) UnicastRemoteObject.exportObject(company, 0);
            Registry reg = LocateRegistry.createRegistry(9001);
            reg.bind("Company", stub);
            logger.info("Register company");
        } catch(RemoteException | AlreadyBoundException e) {
            logger.warn(e);
        }
    }
}
