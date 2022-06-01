package polimi.provafinale.trickytickets.ctl;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import polimi.provafinale.trickytickets.bean.BaseBean;
import polimi.provafinale.trickytickets.bean.UserBean;
import polimi.provafinale.trickytickets.exception.ApplicationException;
import polimi.provafinale.trickytickets.exception.DuplicateRecordException;
import polimi.provafinale.trickytickets.model.UserModel;
import polimi.provafinale.trickytickets.util.DataUtility;
import polimi.provafinale.trickytickets.util.DataValidator;
import polimi.provafinale.trickytickets.util.PropertyReader;
import polimi.provafinale.trickytickets.util.ServletUtility;

@WebServlet(name = "UserRegistrationCtl", urlPatterns = { "/registration" })
public class UserRegistrationCtl extends BaseCtl {
	
	public static HashMap<String,String> businessAreaMap(){
		HashMap<String,String> map= new HashMap<String,String>();
		map.put("Commerciale","Commerciale");
		map.put("Amministrativo","Amministrativo");
		map.put("Logistico","Logistico");
		map.put("IT","IT");
		return map;
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		String login = request.getParameter("Username");

		if (DataValidator.isNull(request.getParameter("Nome"))) {
			request.setAttribute("Nome", PropertyReader.getValue("error.require", "Nome"));
			pass = false;
		}

		if (DataValidator.isNull(login)) {
			request.setAttribute("Username", PropertyReader.getValue("error.require", "Username"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("Password"))) {
			request.setAttribute("Password", PropertyReader.getValue("error.require", "Password"));
			pass = false;

		} else if (!DataValidator.isPassword(request.getParameter("Password"))) {
			request.setAttribute("Password", PropertyReader.getValue("error.password", "Password"));
			return false;
		} else if (!DataValidator.isPassword(request.getParameter("Password"))) {
			request.setAttribute("Password", PropertyReader.getValue("error.password", "Password"));
			return false;
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
		bean.setRoleId(2);
		bean.setRoleName("Utente");
		bean.setName(DataUtility.getString(request.getParameter("Nome")));
		bean.setUserName(DataUtility.getString(request.getParameter("Username")));
		bean.setPassword(DataUtility.getString(request.getParameter("Password")));
		bean.setContactNo(DataUtility.getString(request.getParameter("Telefono")));
		bean.setEmail(DataUtility.getString(request.getParameter("Email")));
		bean.setBusinessArea(DataUtility.getString(request.getParameter("Area")));

		populateDTO(bean, request);

		return bean;
	}

	private static final long serialVersionUID = 1L;

	public UserRegistrationCtl() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		
		UserModel model = new UserModel();

		if (OP_SIGN_UP.equalsIgnoreCase(op)) {

			UserBean bean = (UserBean) populateBean(request);
			try {
				long pk = model.registerUser(bean);
				bean.setId(pk);
				request.getSession().setAttribute("UserBean", bean);
				ServletUtility.setSuccessMessage("Registrazione completata con successo", request);
				ServletUtility.forward(HTSView.USER_REGISTRATION_VIEW, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Username già in uso", request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				e.printStackTrace();
				return;
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(HTSView.USER_REGISTRATION_CTL, request, response);
			return;
		}
	}

	@Override
	protected String getView() {
		return HTSView.USER_REGISTRATION_VIEW;
	}

}
