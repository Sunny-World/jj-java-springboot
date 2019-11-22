package cn.jiweiqing.base.config.param;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Order(0)
@Component
@WebFilter(filterName = "ParamsValueTrimFilter",urlPatterns = "/*")
public class ParamsValueTrimFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ParamsValueTrimRequestWrapper requestWrapper = new ParamsValueTrimRequestWrapper((HttpServletRequest) servletRequest);
        filterChain.doFilter(requestWrapper,servletResponse);
    }

    @Override
    public void destroy() {}

}
