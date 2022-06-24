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
import polimi.provafinale.trickytickets.bean.CategoryBean;
import polimi.provafinale.trickytickets.exception.ApplicationException;
import polimi.provafinale.trickytickets.model.CategoryModel;
import polimi.provafinale.trickytickets.util.DataUtility;
import polimi.provafinale.trickytickets.util.PropertyReader;
import polimi.provafinale.trickytickets.util.ServletUtility;

/*Servlet che genera la lista delle categorie esistenti, eventualmente con paginazione, 
 * e consente la ricerca usando il nome come parametro*/

@WebServlet(name = "CategoryListCtl", urlPatterns = { "/ctl/categoryList" })

public class CategoryListCtl extends BaseCtl {
	
	private static final long serialVersionUID = 1L;

	protected BaseBean populateBean(HttpServletRequest request) { //usato per la ricerca
		
		CategoryBean bean = new CategoryBean();
		bean.setName(DataUtility.getString(request.getParameter("name")));
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
		CategoryBean bean = (CategoryBean) populateBean(request);
		CategoryModel model = new CategoryModel();
		
		try {
			list = model.search(bean, pageNo, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("Nessun risultato", request);
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
		
		CategoryBean bean = (CategoryBean) populateBean(request);
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		CategoryModel model = new CategoryModel();
		
		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) { //Selezione del comando

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ViewsCtls.CATEGORY_LIST_CTL, request, response);
				return;
			}
			list = model.search(bean, pageNo, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("Nessun risultato", request);
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
		return ViewsCtls.CATEGORY_LIST_VIEW;
	}
}
