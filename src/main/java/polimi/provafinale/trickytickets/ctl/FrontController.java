package polimi.provafinale.trickytickets.ctl;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import polimi.provafinale.trickytickets.util.ServletUtility;

// Classe che verifica la validità delle credenziali a ogni accesso -> init

@WebFilter(filterName="FrontCtl",urlPatterns={"/ctl/*","/doc/*"})

public class FrontController implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession(true);		
		
		if (session.getAttribute("user") == null) {
			
			ServletUtility.setErrorMessage("Sessione scaduta, effettuare nuovamente l'accesso", request);
			
			String hitUri=request.getRequestURI();
		
			req.setAttribute("uri", hitUri);			
			
			ServletUtility.forward("/login", request, response);
			
		} else {
			chain.doFilter(req, resp);		
		}
	
	}
	
	public void init(FilterConfig conf) throws ServletException {

	}

}
