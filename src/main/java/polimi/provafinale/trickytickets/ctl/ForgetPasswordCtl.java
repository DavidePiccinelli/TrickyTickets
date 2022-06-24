package polimi.provafinale.trickytickets.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import polimi.provafinale.trickytickets.bean.BaseBean;
import polimi.provafinale.trickytickets.bean.UserBean;
import polimi.provafinale.trickytickets.exception.ApplicationException;
import polimi.provafinale.trickytickets.exception.RecordNotFoundException;
import polimi.provafinale.trickytickets.model.UserModel;
import polimi.provafinale.trickytickets.util.DataUtility;
import polimi.provafinale.trickytickets.util.DataValidator;
import polimi.provafinale.trickytickets.util.PropertyReader;
import polimi.provafinale.trickytickets.util.ServletUtility;

/*Questa Servlet cerca il nome utente e nel caso attua il comando che invia la password
 * alla mail presente nel database; l'invio in questo caso viene però fatto direttamente dalla 
 * classe model*/

@WebServlet(name = "ForgetPasswordCtl", urlPatterns = { "/forgetPassword" })
public class ForgetPasswordCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		String login = request.getParameter("userName");

		if (DataValidator.isNull(login)) {
			request.setAttribute("userName", PropertyReader.getValue("error.require", "User Name"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		UserBean bean = new UserBean();

		bean.setUserName(DataUtility.getString(request.getParameter("userName")));

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		UserBean bean = (UserBean) populateBean(request);

		UserModel model = new UserModel();

		if (OP_GO.equalsIgnoreCase(op)) {

			try {
				String msg = model.forgetPassword(bean.getUserName());

				ServletUtility.setSuccessMessage(msg, request);
			} catch (RecordNotFoundException e) {
				ServletUtility.setErrorMessage(e.getMessage(), request);
				;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;

			}
			ServletUtility.forward(getView(), request, response);
		}

	}

	@Override
	protected String getView() {
		return ViewsCtls.FORGET_PASSWORD_VIEW;
	}

}
