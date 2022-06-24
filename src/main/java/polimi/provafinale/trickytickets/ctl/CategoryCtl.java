package polimi.provafinale.trickytickets.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import polimi.provafinale.trickytickets.bean.BaseBean;
import polimi.provafinale.trickytickets.bean.CategoryBean;
import polimi.provafinale.trickytickets.bean.UserBean;
import polimi.provafinale.trickytickets.exception.ApplicationException;
import polimi.provafinale.trickytickets.exception.DuplicateRecordException;
import polimi.provafinale.trickytickets.model.CategoryModel;
import polimi.provafinale.trickytickets.util.DataUtility;
import polimi.provafinale.trickytickets.util.DataValidator;
import polimi.provafinale.trickytickets.util.PropertyReader;
import polimi.provafinale.trickytickets.util.ServletUtility;

/*Servlet per l'aggiunta di una nuova categoria, 
 * nella Get viene verificato che l'utente abbia il ruolo di fornitore; vengono poi 
 * verificati i dati e infine la Post aggiunge la categoria*/

@WebServlet(name = "CategoryCtl", urlPatterns = { "/ctl/category" })
public class CategoryCtl extends BaseCtl {
	
	private static final long serialVersionUID = 1L;

	@Override 
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		CategoryBean bean = new CategoryBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		populateDTO(bean, request);
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

		ServletUtility.setOperation("Salva", request);
	
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");	
		
		if (user.getRoleId() != 1)
		{	ServletUtility.forward(ViewsCtls.ERROR_VIEW, request, response);
			return;
		}
	
		String op = DataUtility.getString(request.getParameter("operation"));
		
		CategoryModel model = new CategoryModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			CategoryBean bean = (CategoryBean) populateBean(request);

			try {				
					model.add(bean);
					ServletUtility.setSuccessMessage("Categoria creata con successo", request);
			} 
			
			catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.forward(ViewsCtls.ERROR_VIEW, request, response);
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(e.getMessage(), request);
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ViewsCtls.CATEGORY_CTL, request, response);
			return;
		}
		
		ServletUtility.forward(getView(), request, response);		
	}

	@Override
	protected String getView() {
		return ViewsCtls.CATEGORY_VIEW;
	}



}
