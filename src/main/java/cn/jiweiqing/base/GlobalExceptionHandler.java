package cn.jiweiqing.base;

import cn.jiweiqing.base.bean.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * 全局异常处理
 * @author liugan
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResultBean<Void> exceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
        handleException(request,exception);
        String validMessage = getValidMessage(exception);
        if(validMessage!=null){
            return ResultBean.error(400,validMessage);
        }
        return ResultBean.error(400,"请求发生错误");
    }

    private void handleException(HttpServletRequest request, Exception exception) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("请求地址：").append(request.getRequestURI().toString()).append("\r\n");
        sb.append("请求参数FORM：").append(requestParam2String(request.getParameterMap())).append("\r\n");
        sb.append("请求参数JSON：").append(request.getAttribute("body"));
        log.error(sb.toString(),exception);
    }

    private String requestParam2String(Map<String,String[]> params){
        StringBuilder sb = new StringBuilder();
        if(params!=null){
            for (Map.Entry<String,String[]> entry : params.entrySet()){
                sb.append(entry.getKey()).append("=").append(Arrays.toString(entry.getValue())).append("&");
            }
            if(sb.length()>0){
                sb.deleteCharAt(sb.length()-1);
            }
        }
        return sb.toString();
    }

    private String getValidMessage(Exception e){
        BindingResult bindingResult = null;
        String message = null;
        if(e instanceof MethodArgumentNotValidException){
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        }
        if(e instanceof BindException){
            bindingResult = ((BindException) e).getBindingResult();
        }
        if(bindingResult!=null){
            message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        }
        if(e instanceof ConstraintViolationException){
            ConstraintViolation<?> violation = ((ConstraintViolationException) e).getConstraintViolations().iterator().next();
            message = violation.getMessage();
        }
        return message;
    }

}
