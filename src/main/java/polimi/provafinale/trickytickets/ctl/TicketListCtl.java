package polimi.provafinale.trickytickets.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import polimi.provafinale.trickytickets.bean.BaseBean;
import polimi.provafinale.trickytickets.bean.TicketBean;
import polimi.provafinale.trickytickets.bean.UserBean;
import polimi.provafinale.trickytickets.exception.ApplicationException;
import polimi.provafinale.trickytickets.model.TicketModel;
import polimi.provafinale.trickytickets.util.DataUtility;
import polimi.provafinale.trickytickets.util.PropertyReader;
import polimi.provafinale.trickytickets.util.ServletUtility;

/*Questa servlet mostra l'elenco dei ticket; gli utenti vedono soltanto
 * i propri ticket e possono vedere o aggiungere commenti;
 * se si ? fornitori viene mostrato ogni ticket presente con possibili? anche di andare 
 * a modificare lo stato del ticket, ? inoltre possibile effettuare la ricerca */

@WebServlet(name = "ticketListCtl", urlPatterns = { "/ctl/ticketList" })
public class TicketListCtl extends BaseCtl {
	
	private static final long serialVersionUID = 1L;

	protected BaseBean populateBean(HttpServletRequest request) { //usato per la ricerca

		TicketBean bean = new TicketBean();
		bean.setTitle(DataUtility.getString(request.getParameter("title")));
		bean.setCategoryName(DataUtility.getString(request.getParameter("categoryName")));
		bean.setTicketNo(DataUtility.getLong(request.getParameter("ticketNo")));
		return bean;
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { //crea la lista dei ticket, eventualmente impaginata, e effettua il controllo dei ruoli utente

		List<?> list = null;

		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getPageSize());

		TicketBean bean = (TicketBean) populateBean(request);

		TicketModel model = new TicketModel();
		
		try {
			
			UserBean uBean=(UserBean)request.getSession().getAttribute("user");
			if(uBean.getRoleId()==2) {
				bean.setUserId(uBean.getId());
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

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {// gestisce l'esecuzione (o il reset) della ricerca oppure lo spostamento fra le pagine

		List<?> list = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getPageSize()) : pageSize;
		
		TicketBean bean = (TicketBean) populateBean(request);
		
		String op = DataUtility.getString(request.getParameter("operation"));
			
		TicketModel model = new TicketModel();
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
				ServletUtility.redirect(ViewsCtls.TICKET_LIST_CTL, request, response);
				return;
			}
			
			UserBean uBean=(UserBean)request.getSession().getAttribute("user");
			if(uBean.getRoleId()==2) {
				bean.setUserId(uBean.getId());
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
		return ViewsCtls.TICKET_LIST_VIEW;
	}
}
