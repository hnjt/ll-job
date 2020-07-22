package com.ll.config.exception;

import com.ll.commons.ResultVo;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


/**
 * @ModelAttribute：在Model上设置的值，对于所有被 @RequestMapping 注解的方法中，
 * 都可以通过 ModelMap获取，或者@ModelAttribute("KEY")
 * @ExceptionHandler 拦截异常，我们可以通过该注解实现自定义异常处理。其中，
 * 配置的 value 指定需要拦截的异常类型，下面拦截了 Exception.class 这种异常。
 * by ChenYb date 2019/4/29
 */
@ControllerAdvice
public class MyControllerAdvice {


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
    public String errorHandler(Exception ex) {
        ex.printStackTrace();
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess(false);
        resultVo.setErrorCode("1000");
        resultVo.setMsg(ex.getMessage());
        return  resultVo.toJSONString();
    }

}