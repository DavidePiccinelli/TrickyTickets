package polimi.provafinale.trickytickets.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import polimi.provafinale.trickytickets.bean.BaseBean;
import polimi.provafinale.trickytickets.bean.UserBean;
import polimi.provafinale.trickytickets.exception.ApplicationException;
import polimi.provafinale.trickytickets.exception.DuplicateRecordException;
import polimi.provafinale.trickytickets.model.UserModel;
import polimi.provafinale.trickytickets.util.DataUtility;
import polimi.provafinale.trickytickets.util.DataValidator;
import polimi.provafinale.trickytickets.util.PropertyReader;
import polimi.provafinale.trickytickets.util.ServletUtility;

/*Questa Servlet consente di visualizzare e modificare i dati del profilo utente 
 * inoltre dalla view corrispondente è possibile 
 * raggiungere la pagina per il cambio password. 
 * Anche qui vengono verificati i campi nella validate 
 * per poi andare ad aggiornare il database*/

@WebServlet(name = "MyProfileCtl", urlPatterns = { "/ctl/myProfile" })
public class MyProfileCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	public static final String OP_UPDATE = "Aggiorna";
	public static final String OP_CHANGE_MY_PASSWORD = "Cambia Password";

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		String op = DataUtility.getString(request.getParameter("operation"));

		if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op) || op == null ) {
			return pass;
		}

		String login = request.getParameter("userName");

		if (DataValidator.isNull(request.getParameter("Nome"))) {
			request.setAttribute("Nome", PropertyReader.getValue("error.require", "Nome"));
			pass = false;
		}
		
		if (DataValidator.isNull(login)) {
			request.setAttribute("userName", PropertyReader.getValue("error.require", "userName"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("Telefono"))) {
			request.setAttribute("Telefono", PropertyReader.getValue("error.require", "Telefono"));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("Telefono"))) {
			request.setAttribute("Telefono", PropertyReader.getValue("error.invalid", "Telefono"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("Email"))) {
			request.setAttribute("Email", PropertyReader.getValue("error.require", "Email"));
			pass = false;
		} 
		
		if ("-----Seleziona-----".equalsIgnoreCase(request.getParameter("Area"))) {
			request.setAttribute("Area", PropertyReader.getValue("error.require", "Area"));
			pass = false;
		} 
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		UserBean bean = new UserBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("Nome")));
		bean.setUserName(DataUtility.getString(request.getParameter("userName")));
		bean.setContactNo(DataUtility.getString(request.getParameter("Telefono")));
		bean.setEmail(DataUtility.getString(request.getParameter("Email")));
		bean.setBusinessArea(DataUtility.getString(request.getParameter("Area")));
		populateDTO(bean, request);
		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		UserBean userBean = (UserBean) session.getAttribute("user");

		long id = userBean.getId();

		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();

		if (id > 0 || op != null) {
			UserBean bean;
			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {			
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		UserBean userBean = (UserBean) session.getAttribute("user");

		long id = userBean.getId();

		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();

		if (OP_UPDATE.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);
			try {
				if (id > 0) {
					userBean.setName(bean.getName());
					userBean.setEmail(bean.getEmail());
					userBean.setContactNo(bean.getContactNo());
					userBean.setUserName(bean.getUserName());
					userBean.setBusinessArea(bean.getBusinessArea());
					model.update(userBean);					
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Profilo aggiornato ", request);
				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Username già in uso", request);
			}
		} else if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ViewsCtls.CHANGE_PASSWORD_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		
		return ViewsCtls.MY_PROFILE_VIEW;
	}

}
