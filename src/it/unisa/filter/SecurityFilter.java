package it.unisa.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String path = httpRequest.getRequestURI();
        
        // Verifica se il percorso contiene WEB-INF o META-INF
        if (path.contains("WEB-INF") || path.contains("META-INF")) {
            // Reindirizza a una pagina di errore
            httpResponse.sendRedirect("error.jsp");
            return;
        }
        
        // Procedi con la richiesta
        chain.doFilter(request, response);
    }

    public void destroy() {}
}
