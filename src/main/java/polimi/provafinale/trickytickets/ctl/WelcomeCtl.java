package polimi.provafinale.trickytickets.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import polimi.provafinale.trickytickets.util.ServletUtility;

/*Questa servlet gestisce la view di benvenuto welcome.jsp 
 * dove viene mostrato un messaggio di benvenuto generico o personalizzato 
 * senza ulteriori funzioni*/

@WebServlet(name = "WelcomeCtl", urlPatterns = { "/welcome" })
public class WelcomeCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected String getView() {
		return ViewsCtls.WELCOME_VIEW;
	}

}
