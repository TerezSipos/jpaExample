package sipost.user.management.web.servlet;

import java.io.IOException;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

@WebServlet("/JmsSendNewUserServlet")
public class JmsSendNewUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Logger oLogger = Logger.getLogger(JmsSendNewUserServlet.class);

	@Resource(lookup = "java:/ConnectionFactory")
    ConnectionFactory connectionFactory;

	@Resource(lookup = "java:/jms/queue/UserManagementIn")
    Destination destination;

	public JmsSendNewUserServlet() {
    	oLogger.info("--JmsSendNewUserServlet()--");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String p_name = request.getParameter("name");
		if (p_name != null) {
			oLogger.info(String.format("--JmsSendNewUserServlet.doGet(\"%s\")--", p_name));
			try {
				QueueConnection connection = (QueueConnection)connectionFactory.createConnection();
				QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
				MessageProducer producer = session.createProducer(destination);
				TextMessage message = session.createTextMessage("Hello," + p_name + "! ^__^");
                producer.send(message);
			} catch (JMSException e) {
				e.printStackTrace();
			}
			response.getWriter()
				.append("JMS sent. ");
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		oLogger.info("--JmsSendNewUserServlet.doPost--");
		doGet(request, response);
	}

}
