package sipost.user.management.ejb;

import java.sql.SQLException;

import javax.ejb.EJBException;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

public class EjbExeption extends EJBException {
	private final static String DELETE_ERROR_MESSAGE = "Cannot delete role (have user with coresponding role)";

	private static final long serialVersionUID = 5633917663462543264L;
	private static Logger oLogger = Logger.getLogger(EjbExeption.class);

	public static void getCause(PersistenceException e) {
		Throwable throwable = e;
		while (throwable != null && !(throwable instanceof SQLException)) {
			throwable = throwable.getCause();
		}
		if (throwable instanceof SQLException) {
			SQLException sqlex = (SQLException) throwable;
			String mess = sqlex.getMessage();
			mess = mess.substring(0, sqlex.getMessage().indexOf("for"));
			oLogger.error("------------------" + throwable.getClass() + "------------");

			if (sqlex.getMessage().indexOf("Cannot delete") >= 0) {
				mess = DELETE_ERROR_MESSAGE;
			}
			throw new EjbExeption(mess, sqlex);
		}
	}

	public EjbExeption() {
		super();
	}

	public EjbExeption(String message) {
		super(message);
	}

	public EjbExeption(String message, Throwable cause) {
		super(message);
	}

}
