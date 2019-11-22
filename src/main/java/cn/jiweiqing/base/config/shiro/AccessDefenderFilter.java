package cn.jiweiqing.base.config.shiro;

import org.apache.shiro.SecurityUtils;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AccessDefenderFilter implements Filter {

    private ShiroResources shiroResources;

    public AccessDefenderFilter(ShiroResources shiroResources){
       this.shiroResources = shiroResources;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if((!SecurityUtils.getSubject().isAuthenticated()) && (!shiroResources.isAnonResource(request.getRequestURI()))){
            servletRequest.getRequestDispatcher(shiroResources.getUnlogin()).forward(request,servletResponse);
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {}

}
