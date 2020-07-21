package com.exception;

import com.alibaba.fastjson.JSONObject;
import com.utils.HttpClientUtil;
import com.utils.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @ModelAttribute：在Model上设置的值，对于所有被 @RequestMapping 注解的方法中，
 * 都可以通过 ModelMap获取，或者@ModelAttribute("KEY")
 * @ExceptionHandler 拦截异常，我们可以通过该注解实现自定义异常处理。其中，
 * 配置的 value 指定需要拦截的异常类型，下面拦截了 Exception.class 这种异常。
 * by ChenYb date 2019/4/29
 */
@ControllerAdvice
public class MyControllerAdvice {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${auth-exception-ip}")
    private String exceptionIp;
    @Value("${auth-exception-port}")
    private String exceptionPort;

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * by ChenYb date 2019/4/29
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {}

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "CHENYB");
    }

    /**
     * 全局异常捕捉处理
     * by ChenYb date 2019/4/29
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultVo errorHandler(Exception ex) {
        ex.printStackTrace();
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess(false);
        resultVo.setErrorCode("1000");
        resultVo.setMsg(ex.getMessage());
        logger.error("1000  =>{}<=",ex.getMessage());
        return  resultVo;
    }

    /**
     * 拦截捕捉自定义异常 MyException.class
     * by ChenYb date 2019/4/29
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MyException.class)
    public ResultVo myErrorHandler(MyException ex) {
        ResultVo resultVo = new ResultVo();
        try {
            HashMap paramMap = new HashMap<>();
            paramMap.put("itemNo",ex.errorCode);
            String httpStr = HttpClientUtil.doRequest("GET", exceptionIp, exceptionPort, "/v3.0.1/codeitem/getExeptionByCodeNo", paramMap);
            JSONObject map = JSONObject.parseObject(httpStr);
            resultVo.setMsg(map.get("data").toString());
        }catch (Exception e){
            throw new MyException(ExceptionEnum.EXCEPTION_UNKOWN);
        }
        if (null == resultVo.getMsg())
            resultVo.setMsg("ControllerAdvice");
        resultVo.setErrorCode(ex.errorCode);
        resultVo.setSuccess(false);
        logger.error(ex.getErrorCode()+" : =>{}!<=",resultVo.getMsg());
        return  resultVo;
    }

}