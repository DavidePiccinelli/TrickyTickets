package polimi.provafinale.trickytickets.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import polimi.provafinale.trickytickets.bean.BaseBean;
import polimi.provafinale.trickytickets.bean.UserBean;
import polimi.provafinale.trickytickets.util.DataUtility;
import polimi.provafinale.trickytickets.util.DataValidator;
import polimi.provafinale.trickytickets.util.ServletUtility;

@WebServlet("/BaseCtl")
public abstract class BaseCtl extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public static final String OP_SAVE = "Salva";
	public static final String OP_SIGN_IN = "Accedi";
	public static final String OP_SIGN_UP = "Registrati";
	public static final String OP_SEARCH = "Cerca";
	public static final String OP_NEXT = "Successiva";
	public static final String OP_PREVIOUS = "Precedente";
	public static final String OP_GO = "Invia";
	public static final String OP_LOG_OUT = "Esci";
	public static final String OP_RESET = "Ripristina";

	public static final String MSG_SUCCESS = "successo";
	public static final String MSG_ERROR = "errore";

	public BaseCtl() {
	}

	protected boolean validate(HttpServletRequest request) {
		return true;
	}

	protected void preload(HttpServletRequest request) {
	}

	protected BaseBean populateBean(HttpServletRequest request) {
		return null;
	}

	protected BaseBean populateDTO(BaseBean dto, HttpServletRequest request) {

		String createdBy = request.getParameter("createdBy");
		String modifiedBy = null;
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		if (user == null) {
			createdBy = "SysAdmin";
			modifiedBy = "SysAdmin";
		} else {
			modifiedBy = user.getUserName();
			if ("null".equalsIgnoreCase(createdBy) || DataValidator.isNull(createdBy)) {
				createdBy = modifiedBy;
			}
		}
	
		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);

		long cdt = DataUtility.getLong(request.getParameter("createdDatetime"));

		if (cdt > 0) {
			dto.setCreatedDatetime(DataUtility.getTimestamp(cdt));
		} else {
			dto.setCreatedDatetime(DataUtility.getCurrentTimestamp());
		}
		dto.setModifiedDatetime(DataUtility.getCurrentTimestamp());
		return dto;
	} 

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		preload(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		if (DataValidator.isNotNull(op) && !OP_RESET.equalsIgnoreCase(op)) {
			if (!validate(request)) {
				BaseBean bean = (BaseBean) populateBean(request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
				return;
			}
		}
		super.service(request, response);
	}
	
	protected abstract String getView();
}
