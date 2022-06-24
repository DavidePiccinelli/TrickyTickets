package polimi.provafinale.trickytickets.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import polimi.provafinale.trickytickets.bean.BaseBean;
import polimi.provafinale.trickytickets.bean.UserBean;
import polimi.provafinale.trickytickets.exception.ApplicationException;
import polimi.provafinale.trickytickets.model.UserModel;
import polimi.provafinale.trickytickets.util.DataUtility;
import polimi.provafinale.trickytickets.util.PropertyReader;
import polimi.provafinale.trickytickets.util.ServletUtility;

/*Questa servlet gestisce la visualizzazione dell'elenco di utenti ed � visualizzabile
 * soltanto dai fornitori (il controllo avviene nella get)
 * crea una lista di utenti con paginazione e implementa inoltre la ricerca per nome o per email 
 * nella tabella degli utenti*/

@WebServlet(name = "UserListCtl", urlPatterns = { "/ctl/userList" })
public class UserListCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	protected BaseBean populateBean(HttpServletRequest request) { //usato per la ricerca

		UserBean bean = new UserBean();		
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		return bean;
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		UserBean user = (UserBean) session.getAttribute("user");	
		
		if (user.getRoleId() != 1)
			
		{	ServletUtility.forward(ViewsCtls.ERROR_VIEW, request, response);
			return;
		}

		List<?> list = null;

		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getPageSize());

		UserBean bean = (UserBean) populateBean(request);

		UserModel model = new UserModel();
		try {
		
			list = model.search(bean, pageNo, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("Nessun dato presente", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setSize(model.search(bean).size(), request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
		
			ServletUtility.handleException(e, request, response);
			return;
		}

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<?> list = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getPageSize()) : pageSize;		
		UserBean bean = (UserBean) populateBean(request);		
		String op = DataUtility.getString(request.getParameter("operation"));			
		UserModel model = new UserModel();
		
		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			}  else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ViewsCtls.USER_LIST_CTL, request, response);
				return;
			}
		
			list = model.search(bean, pageNo, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("Nessun dato presente", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setSize(model.search(bean).size(), request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
	
			ServletUtility.handleException(e, request, response);
			return;
		}
	}
	
	protected String getView() {
		return ViewsCtls.USER_LIST_VIEW;
	}
}
