package polimi.provafinale.trickytickets.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import polimi.provafinale.trickytickets.bean.BaseBean;
import polimi.provafinale.trickytickets.bean.CommentBean;
import polimi.provafinale.trickytickets.bean.UserBean;
import polimi.provafinale.trickytickets.email.EmailUtility;
import polimi.provafinale.trickytickets.bean.TicketBean;
import polimi.provafinale.trickytickets.exception.ApplicationException;
import polimi.provafinale.trickytickets.exception.DuplicateRecordException;
import polimi.provafinale.trickytickets.model.CommentModel;
import polimi.provafinale.trickytickets.model.TicketModel;
import polimi.provafinale.trickytickets.util.DataUtility;
import polimi.provafinale.trickytickets.util.DataValidator;
import polimi.provafinale.trickytickets.util.PropertyReader;
import polimi.provafinale.trickytickets.util.ServletUtility;

/*Servlet che gestisce l'aggiunta di un commento ad un ticket: nella Get sono inclusi 
 * i controlli di sicurezza, ossia la verifica dell'esistenza e del possesso del ticket qualora
 *ad accedere sia un utente e non un fornitore*/

@WebServlet(name = "CommentCtl", urlPatterns = { "/ctl/comment" })
public class CommentCtl extends BaseCtl {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("comment"))) {
			request.setAttribute("comment", PropertyReader.getValue("error.require", "Commento"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		CommentBean bean = new CommentBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setComment(DataUtility.getString(request.getParameter("comment")));
		populateDTO(bean, request);
		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		
		TicketBean ticket = new TicketBean();
		TicketModel ticketModel = new TicketModel();
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");
		int tId;
		boolean exists = false;
		
		try {
			tId = Integer.parseInt(request.getParameter("tId"));
			request.getSession().setAttribute("tId",tId);
		} catch (NumberFormatException | NullPointerException e) {
			ServletUtility.forward(ViewsCtls.ERROR_VIEW, request, response);
			return;
		}	
		
		try {
			exists = ticketModel.checkIfExist(tId);
		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.forward(ViewsCtls.ERROR_VIEW, request, response);
			return;
		}
		
		try {
			ticket = ticketModel.findByPK(tId);
		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.forward(ViewsCtls.ERROR_VIEW, request, response);
			return;
		}
		
		if(!exists) {
			ServletUtility.forward(ViewsCtls.ERROR_VIEW, request, response);
			return;			
		}		
		
		if (user.getRoleId() != 1) {
			if (ticket.getUserId() != user.getId()) {
				ServletUtility.forward(ViewsCtls.ERROR_VIEW, request, response);
				return;
			}
		}
						
		ServletUtility.setBean(new CommentBean(), request);
		
		ServletUtility.forward(getView(), request, response);	}	
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		CommentModel model = new CommentModel();
		
		TicketModel ticketModel = new TicketModel();

		HttpSession session = request.getSession();
		
		if (OP_SAVE.equalsIgnoreCase(op)) {

			CommentBean bean = (CommentBean) populateBean(request);
			

			try {
					long tId=DataUtility.getLong(String.valueOf(session.getAttribute("tId")));
					bean.setTicketId(tId);
					TicketBean tBean = ticketModel.findByPK(tId);
					UserBean uBean=(UserBean)session.getAttribute("user");
					bean.setUserName(uBean.getUserName());
					model.add(bean);	
			
					String host = PropertyReader.getSmtp();
					String port = PropertyReader.getPort();
					String user = PropertyReader.getLogin();
					String pass = PropertyReader.getPwd();
					String dest = uBean.getEmail();
					String cont = "Il tuo ticket " + tBean.getTicketNo() + " ha ricevuto un nuovo commento";
					String subj = "Comunicazione da Tricky Tickets";

					try {
						System.out.println(
								host + " " + port + " " + user + " " + pass + " " + dest + " " + subj + " " + cont);
						EmailUtility.sendEmail(host, port, user, pass, dest, subj, cont);

					} catch (Exception ex) {
						ex.printStackTrace();}
					
					ServletUtility.setSuccessMessage("Commento salvato con successo", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri non validi");
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(e.getMessage(), request);
			}

		} 			
		
	else if (OP_RESET.equalsIgnoreCase(op)) {
		ServletUtility.forward(getView(), request, response);
				return;
			}			
			ServletUtility.forward(getView(), request, response);			

	}

	@Override
	protected String getView() {
		return ViewsCtls.COMMENT_VIEW;
	}

}