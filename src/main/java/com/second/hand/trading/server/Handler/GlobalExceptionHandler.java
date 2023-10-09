package com.second.hand.trading.server.Handler;

import com.second.hand.trading.server.Exception.ParamException;
import com.second.hand.trading.server.enums.ErrorMsg;
import com.second.hand.trading.server.vo.ResultVo;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * controller的方法参数错误
     * @param e      过滤器
     * @return
     */

    /**
     * 如果一个请求在经过Spring框架处理之前就要处理（或者说能够/可以被处理），
     * 那么这种情况一般选择用过滤器，例如：对请求URL做限制，限制某些URL的请求不被接受，
     * 这个动作是没有必要经过Spring的，直接过滤器初始化规则过滤即可；
     * 再比如：对请求数据做字符转换，请求的是密文，转换成明文，顺便再校验一下数据格式，
     * 这个也不需要经过Spring；总之，与详细业务不相关的请求处理都可以用过滤器来做；
     * 而与业务相关的自然就用拦截器来做，比如：对业务处理前后的数据做日志的记录；
     *
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVo MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> collect = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return ResultVo.fail(ErrorMsg.PARAM_ERROR,collect);
    }

    /**
     * 缺少request body错误
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultVo HttpMessageNotReadableExceptionHandler() {
        return ResultVo.fail(ErrorMsg.MISSING_PARAMETER, "requestBody错误!");
    }

    /**
     * url中缺少Query Params
     * @param e e.getMessage()返回首个缺少的参数名
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultVo MissingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        return ResultVo.fail(ErrorMsg.MISSING_PARAMETER, "缺少参数"+e.getParameterName());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResultVo ConstraintViolationExceptionHandler(ConstraintViolationException e) {

        Set<ConstraintViolation<?>> set = e.getConstraintViolations();
        Map<String, String> map = new HashMap<>();
        if (set.size() > 0) {
            for (ConstraintViolation<?> cv : set) {
                String[] param = cv.getPropertyPath().toString().split("\\.");
                String message = cv.getMessage();
                map.put(param[param.length - 1], message);
            }
        }

        return ResultVo.fail(ErrorMsg.PARAM_ERROR, map);
    }

    @ExceptionHandler(ParamException.class)
    public ResultVo ParamExceptionHandler(ParamException e) {
        return ResultVo.fail(ErrorMsg.PARAM_ERROR, e.getMap());
    }

    /*@ExceptionHandler(Exception.class)
    public Object CommonExceptionHandler(Exception e){
        return "服务器错误";
    }*/


    /**
     * 拦截cookie缺失异常
     * @return
     */
    @ExceptionHandler(MissingRequestCookieException.class)
    public ResultVo MissingRequestCookieExceptionHandler(){
        return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
    }
}
/**
 * DefaultHandlerExceptionResolver
 *
 * HttpRequestMethodNotSupportedException
 * 405 (SC_METHOD_NOT_ALLOWED)
 * HttpMediaTypeNotSupportedException
 * 415 (SC_UNSUPPORTED_MEDIA_TYPE)
 * HttpMediaTypeNotAcceptableException
 * 406 (SC_NOT_ACCEPTABLE)
 * MissingPathVariableException
 * 500 (SC_INTERNAL_SERVER_ERROR)
 * MissingServletRequestParameterException
 * 400 (SC_BAD_REQUEST)
 * ServletRequestBindingException
 * 400 (SC_BAD_REQUEST)
 * ConversionNotSupportedException
 * 500 (SC_INTERNAL_SERVER_ERROR)
 * TypeMismatchException
 * 400 (SC_BAD_REQUEST)
 * HttpMessageNotReadableException
 * 400 (SC_BAD_REQUEST)
 * HttpMessageNotWritableException
 * 500 (SC_INTERNAL_SERVER_ERROR)
 * MethodArgumentNotValidException
 * 400 (SC_BAD_REQUEST)
 * MissingServletRequestPartException
 * 400 (SC_BAD_REQUEST)
 * BindException
 * 400 (SC_BAD_REQUEST)
 * NoHandlerFoundException
 * 404 (SC_NOT_FOUND)
 * AsyncRequestTimeoutException
 * 503 (SC_SERVICE_UNAVAILABLE)
 */