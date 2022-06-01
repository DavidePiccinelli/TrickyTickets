package polimi.provafinale.trickytickets.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import polimi.provafinale.trickytickets.bean.BaseBean;
import polimi.provafinale.trickytickets.bean.TicketBean;
import polimi.provafinale.trickytickets.bean.UserBean;
import polimi.provafinale.trickytickets.exception.ApplicationException;
import polimi.provafinale.trickytickets.exception.DuplicateRecordException;
import polimi.provafinale.trickytickets.model.CategoryModel;
import polimi.provafinale.trickytickets.model.TicketModel;
import polimi.provafinale.trickytickets.util.DataUtility;
import polimi.provafinale.trickytickets.util.DataValidator;
import polimi.provafinale.trickytickets.util.PropertyReader;
import polimi.provafinale.trickytickets.util.ServletUtility;

@WebServlet(name = "TicketCtl", urlPatterns = { "/ctl/ticket" })
public class TicketCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	@Override
	protected void preload(HttpServletRequest request) {
			
		try {
			request.setAttribute("categoryList", new CategoryModel().list());
		} catch (ApplicationException e) {
					e.printStackTrace();
		}
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("categoryId"))) {
			request.setAttribute("categoryId", PropertyReader.getValue("error.require", "Category Name"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("title"))) {
			request.setAttribute("title", PropertyReader.getValue("error.require", "Title"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Decription"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		TicketBean bean = new TicketBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCategoryId(DataUtility.getLong(request.getParameter("categoryId")));
		bean.setTitle(DataUtility.getString(request.getParameter("title")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		populateDTO(bean, request);
		return bean;
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");	
		
		if (user.getRoleId() != 2)
		{	ServletUtility.forward(HTSView.ERROR_VIEW, request, response);
			return;
		}			
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		String op = DataUtility.getString(request.getParameter("operation"));
		TicketModel model = new TicketModel();
		HttpSession session=request.getSession();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			TicketBean bean = (TicketBean) populateBean(request);
			try {
				
				UserBean uBean=(UserBean)session.getAttribute("user");
				bean.setUserId(uBean.getId());
				bean.setUserName(uBean.getUserName());
					bean.setStatus("In lavorazione");
					model.add(bean);
					ServletUtility.setSuccessMessage("Ticket inserito con successo", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.forward(HTSView.ERROR_VIEW, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(e.getMessage(), request);
			}

		}   else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(HTSView.TICKET_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return HTSView.TICKET_VIEW;
	}

}
