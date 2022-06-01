package polimi.provafinale.trickytickets.ctl;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import polimi.provafinale.trickytickets.bean.BaseBean;
import polimi.provafinale.trickytickets.bean.TicketBean;
import polimi.provafinale.trickytickets.bean.UserBean;
import polimi.provafinale.trickytickets.email.EmailUtility;
import polimi.provafinale.trickytickets.exception.ApplicationException;
import polimi.provafinale.trickytickets.exception.DuplicateRecordException;
import polimi.provafinale.trickytickets.model.TicketModel;
import polimi.provafinale.trickytickets.model.UserModel;
import polimi.provafinale.trickytickets.util.DataUtility;
import polimi.provafinale.trickytickets.util.PropertyReader;
import polimi.provafinale.trickytickets.util.ServletUtility;

@WebServlet(name = "StatusCtl", urlPatterns = { "/ctl/status" })

public class StatusCtl extends BaseCtl {
	
	private static final long serialVersionUID = 1L;
	
	public static HashMap<String,String> statusMap(){
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("In lavorazione","In lavorazione");
		map.put("Chiuso", "Chiuso");
		map.put("Annullato", "Annullato");
		return map;
	}
	
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if ("-----Seleziona-----".equalsIgnoreCase(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		TicketBean bean = new TicketBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));
		populateDTO(bean, request);
		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");
		TicketModel ticketModel = new TicketModel();
		
		long tId;
		
		try {
			tId = Integer.parseInt(request.getParameter("tId"));
		} catch (NumberFormatException | NullPointerException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri non validi");
			return;
		}
		
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
		
		if (user.getRoleId() != 1)
		{	ServletUtility.forward(HTSView.ERROR_VIEW, request, response);
			return;
		}

		request.getSession().setAttribute("tId",tId);
		
		ServletUtility.setBean(new TicketBean(), request);

		ServletUtility.forward(getView(), request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		
		TicketModel model = new TicketModel();

		UserModel userModel = new UserModel();
		
		HttpSession session=request.getSession();
		
		if (OP_SAVE.equalsIgnoreCase(op)) {

			TicketBean bean = (TicketBean) populateBean(request);
				
			try {
				long tId = DataUtility.getLong(String.valueOf(session.getAttribute("tId")));
				TicketBean tBean = model.findByPK(tId);
				UserBean utente = userModel.findByUserName(tBean.getCreatedBy());
				tBean.setStatus(bean.getStatus());
				model.update(tBean);
	
				String host = PropertyReader.getSmtp();
				String port = PropertyReader.getPort();
				String user = PropertyReader.getLogin();
				String pass = PropertyReader.getPwd();
				String dest = utente.getEmail();
				String cont = "Il tuo ticket " + tBean.getTicketNo() + " ha cambiato stato";
				String subj = "Comunicazione da Tricky Tickets";

				try {
					System.out.println(
							host + " " + port + " " + user + " " + pass + " " + dest + " " + subj + " " + cont);
					EmailUtility.sendEmail(host, port, user, pass, dest, subj, cont);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
				ServletUtility.setSuccessMessage("Stato aggiornato con successo", request);
				ServletUtility.forward(getView(), request, response);				

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.forward(HTSView.ERROR_VIEW, request, response);
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(e.getMessage(), request);
			}

		}  else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect("/trickytickets/jsp/status.jsp", request, response);
			return;
		}
	}

	@Override
	protected String getView() {
		return HTSView.STATUS_VIEW;
	}


	
	
}
