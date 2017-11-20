/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import businesslogic.UserService;
import domainmodel.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 698437
 */
public class AdminFilter implements Filter {

    private FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
       HttpSession session = ((HttpServletRequest) req).getSession();
        String username = (String) session.getAttribute("username");
        UserService us = new UserService();
        User user = null;
        try {
            user = us.get(username);
        } catch (Exception ex) {
            Logger.getLogger(AdminFilter.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (user != null && user.getRole().getRoleID() == 1) {
            // yes, go onwards to the servlet or next filter
            chain.doFilter(req, res);
        } else {
            // get out of here!
            ((HttpServletResponse) res).sendRedirect("home");
}
    }

    @Override
    public void destroy() {
    }
}
