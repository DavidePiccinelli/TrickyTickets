package polimi.provafinale.trickytickets.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import polimi.provafinale.trickytickets.bean.BaseBean;
import polimi.provafinale.trickytickets.bean.CommentBean;
import polimi.provafinale.trickytickets.bean.TicketBean;
import polimi.provafinale.trickytickets.bean.UserBean;
import polimi.provafinale.trickytickets.exception.ApplicationException;
import polimi.provafinale.trickytickets.model.CommentModel;
import polimi.provafinale.trickytickets.model.TicketModel;
import polimi.provafinale.trickytickets.util.DataUtility;
import polimi.provafinale.trickytickets.util.ServletUtility;

@WebServlet(name = "CommentListCtl", urlPatterns = { "/ctl/commentList" })
public class CommentListCtl extends BaseCtl {
	
	private static final long serialVersionUID = 1L;
	
	protected BaseBean populateBean(HttpServletRequest request) {

		CommentBean bean = new CommentBean();

		bean.setTicketNo(DataUtility.getLong(request.getParameter("ticketNo")));
		
		bean.setTicketTitle(DataUtility.getString(request.getParameter("ticketTitle")));

		return bean;
	}	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int tId;
		
		try {
			tId = Integer.parseInt(request.getParameter("tId"));
		} catch (NumberFormatException | NullPointerException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri non validi");
			return;
		}
		
		TicketBean ticket = new TicketBean();
		TicketModel ticketModel = new TicketModel();
				
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");
		
	    boolean exists = false;
		
		try {
			exists = ticketModel.checkIfExist(tId);
		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.forward(HTSView.ERROR_VIEW, request, response);
			return;

		}
		
		if(!exists) {
			ServletUtility.forward(HTSView.ERROR_VIEW, request, response);
			return;			
		}
		
		if (user.getRoleId() != 1) {
			try {
				ticket = ticketModel.findByPK(tId);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.forward(HTSView.ERROR_VIEW, request, response);
				return;
			}

			if (ticket.getUserId() != user.getId()) {
				ServletUtility.forward(HTSView.ERROR_VIEW, request, response);
				return;
			}

		}		

		List<?> list = null;

		int pageNo = 1;

		int pageSize = 100;

		CommentBean bean = (CommentBean) populateBean(request);					 
		
		bean.setTicketId(tId);
		
		CommentModel model = new CommentModel();
		
		try {
			list = model.search(bean);
			
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
		ServletUtility.forward(getView(), request, response);
	}
	
	protected String getView() {
		return HTSView.COMMENT_LIST_VIEW;
	}
}
