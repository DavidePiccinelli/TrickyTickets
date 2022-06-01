package polimi.provafinale.trickytickets.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import polimi.provafinale.trickytickets.bean.BaseBean;
import polimi.provafinale.trickytickets.ctl.BaseCtl;
import polimi.provafinale.trickytickets.ctl.HTSView;

/* Classe di supporto creata per facilitare e velocizzare le operazioni ridondanti 
 * utilizzate nelle altre classi,principalmente nei Controller, 
 * come ad esempio l'attribuzione di dati alle request */

public class ServletUtility {

	public static void forward(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		RequestDispatcher rd = request.getRequestDispatcher(page);
		System.out.println("Forward a "+page);
		rd.forward(request, response);
	}

	public static void redirect(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("Redirect a "+page);
		response.sendRedirect(page);
	}

	public static void handleException(Exception e, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setAttribute("exception", e);
		ServletUtility.forward(HTSView.ERROR_CTL, request, response);
		e.printStackTrace();
	}

	public static String getErrorMessage(String property, HttpServletRequest request) {
		String val = (String) request.getAttribute(property);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	public static String getMessage(String property, HttpServletRequest request) {
		String val = (String) request.getAttribute(property);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	public static void setErrorMessage(String msg, HttpServletRequest request) {
		request.setAttribute(BaseCtl.MSG_ERROR, msg);
	}

public static String getErrorMessage(HttpServletRequest request) {
		String val = (String) request.getAttribute(BaseCtl.MSG_ERROR);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	public static void setSuccessMessage(String msg, HttpServletRequest request) {
		request.setAttribute(BaseCtl.MSG_SUCCESS, msg);
	}

	public static String getSuccessMessage(HttpServletRequest request) {
		String val = (String) request.getAttribute(BaseCtl.MSG_SUCCESS);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	public static void setBean(BaseBean bean, HttpServletRequest request) {
		request.setAttribute("bean", bean);
	}

	public static BaseBean getBean(HttpServletRequest request) {
		return (BaseBean) request.getAttribute("bean");
	}

	public static String getParameter(String property, HttpServletRequest request) {
		String val = (String) request.getParameter(property);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	public static void setSize(int size, HttpServletRequest request) {
		request.setAttribute("size", size);
	}

	public static int getSize(HttpServletRequest request) {
		return (Integer) request.getAttribute("size");
	} 

	public static void setList(List<?> list, HttpServletRequest request) {
		request.setAttribute("list", list);
	}

	@SuppressWarnings("rawtypes")
	public static List getList(HttpServletRequest request) {
		return (List) request.getAttribute("list");
	}

	public static void setPageNo(int pageNo, HttpServletRequest request) {
		request.setAttribute("pageNo", pageNo);
	}

	public static int getPageNo(HttpServletRequest request) {
		return (Integer) request.getAttribute("pageNo");
	}

	public static void setPageSize(int pageSize, HttpServletRequest request) {
		request.setAttribute("pageSize", pageSize);
	}

	public static int getPageSize(HttpServletRequest request) {
		return (Integer) request.getAttribute("pageSize");
	}

	public static void setOperation(String msg, HttpServletRequest request) {
		request.setAttribute("Operation", msg);
	}

	public static String getOperation(HttpServletRequest request) {
		String val = (String) request.getAttribute("Operation");
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}
}
