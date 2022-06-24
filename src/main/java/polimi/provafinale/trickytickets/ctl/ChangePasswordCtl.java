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
import polimi.provafinale.trickytickets.exception.RecordNotFoundException;
import polimi.provafinale.trickytickets.model.UserModel;
import polimi.provafinale.trickytickets.util.DataUtility;
import polimi.provafinale.trickytickets.util.DataValidator;
import polimi.provafinale.trickytickets.util.PropertyReader;
import polimi.provafinale.trickytickets.util.ServletUtility;

/*Servlet per la modifica della password: si occupa di fare tutti
 * i controlli sulla consistenza dei dati con DataValidator dopodichè 
 * effettua l'update del database*/

@WebServlet(name = "ChangePasswordCtl", urlPatterns = { "/ctl/changePassword" })
public class ChangePasswordCtl extends BaseCtl {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;
		
		if (DataValidator.isNull(request.getParameter("oldPassword"))) {
			request.setAttribute("oldPassword", PropertyReader.getValue("error.require", "Vecchia Password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", PropertyReader.getValue("error.require", "Nuova Password"));
			pass = false;
		} 
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Conferma Password"));
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", PropertyReader.getValue("error.password", "newPassword"));
			return false;}
		else if (!request.getParameter("newPassword").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			ServletUtility.setErrorMessage("Password non corrispondenti", request);

			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
	
		UserBean bean = new UserBean();
		bean.setPassword(DataUtility.getString(request.getParameter("oldPassword")));
		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		populateDTO(bean, request);
		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		HttpSession session = request.getSession(true);

		String op = DataUtility.getString(request.getParameter("operation"));
	
		UserModel model = new UserModel();
		
		UserBean bean = (UserBean) populateBean(request);

		UserBean UserBean = (UserBean) session.getAttribute("user");

		String newPassword = request.getParameter("newPassword");
		
		long id = UserBean.getId();
		
		if (OP_SAVE.equalsIgnoreCase(op)) {
			try {
				boolean flag = model.changePassword(id, bean.getPassword(), newPassword);
		
				if (flag == true) {
				
					bean = model.findByUserName(UserBean.getUserName());
					
					session.setAttribute("user", bean);
					
					ServletUtility.setBean(bean, request);
					
					ServletUtility.setSuccessMessage("Password cambiata con successo", request);
				}
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;

			} catch (RecordNotFoundException e) {
				ServletUtility.setErrorMessage("Vecchia password non valida", request);
			}
		} 

		ServletUtility.forward(ViewsCtls.CHANGE_PASSWORD_VIEW, request, response);
	}

	@Override
	protected String getView() {
		
		return ViewsCtls.CHANGE_PASSWORD_VIEW;
	}

}
