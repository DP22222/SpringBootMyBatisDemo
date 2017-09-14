package com.springboot.demo.controller;

import com.springboot.demo.ConstantsCodeEnum;
import com.springboot.demo.bean.ResultMessage;
import com.springboot.demo.bean.UserBean;
import com.springboot.demo.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网上案例链接  http://www.jianshu.com/p/e40b329f5544
 * <p>
 * 关键字@RestController代表这个类是用Restful风格来访问的，
 * 如果是普通的WEB页面访问跳转时，我们通常会使用@Controller
 *
 * @RestController 可以将结果直接表示成json数据
 */
@RestController
public class MyController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String helloWorld() {
        return "hello world";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String postHttp() {
        return "postHttp";
    }

    private boolean isNotNull(@RequestParam(value = "account", required = true) String account, @RequestParam(value = "password", required = true) String password, ResultMessage resultMessage) {
        if (account == null || account.equals("")){
            resultMessage.raise(ConstantsCodeEnum.ACCOUNT_NULL.getCode(),ConstantsCodeEnum.ACCOUNT_NULL.getName());
            return true;
        }else if(password == null || password.equals("")){
            resultMessage.raise(ConstantsCodeEnum.PASSWORD_NULL.getCode(),ConstantsCodeEnum.PASSWORD_NULL.getName());
            return true;
        }
        return false;
    }

    @Autowired
    private LoginMapper loginMapper;
    /**
     * http://blog.csdn.net/u010399316/article/details/52913299
     */
    @RequestMapping(value = "/loginbypost", method = RequestMethod.POST)
    public Object loginByPost(@RequestParam(value = "account", required = true) String account,
                              @RequestParam(value = "password", required = true) String password) {
        System.out.println("hello post"+":name:"+account);
        System.out.println("hello post"+":pwd:"+password);

        ResultMessage resultMessage = ResultMessage.create();


        /*判断账号密码是否输入空值*/
        if (isNotNull(account, password, resultMessage)) return resultMessage;

        UserBean login = this.loginMapper.getByName(account);
        if (login == null){
            resultMessage.raise(ConstantsCodeEnum.INEXISTENCE.getCode(),ConstantsCodeEnum.INEXISTENCE.getName());
            return resultMessage;
        }else{
            System.out.println(login.toString());
            if (login.getPassword().equals(password) ){
                resultMessage.put("name",account);
                resultMessage.put("pwd",password);
                resultMessage.raise(ConstantsCodeEnum.SUCCESS.getCode(),ConstantsCodeEnum.SUCCESS.getName());
            }else{
                resultMessage.raise(ConstantsCodeEnum.PASSWORD_INCORRECT.getCode(),ConstantsCodeEnum.PASSWORD_INCORRECT.getName());
            }

            return resultMessage;
        }
    }

    @RequestMapping(value = "/registerAccountByPost", method = RequestMethod.POST)
    public Object registerAccountByPost(@RequestParam(value = "account", required = true) String account,
                              @RequestParam(value = "password", required = true) String password) {
        System.out.println("注册name:"+account+"，pwd:"+password);

        ResultMessage resultMessage = ResultMessage.create();

        /*判断账号密码是否输入空值*/
        if (isNotNull(account, password, resultMessage)) return resultMessage;

        UserBean userBean = this.loginMapper.getByName(account);
        if (userBean != null){
            resultMessage.raise(ConstantsCodeEnum.ACCOUNT_ALREADY_EXIST.getCode(), ConstantsCodeEnum.ACCOUNT_ALREADY_EXIST.getName());
            return resultMessage;
        }else{
            resultMessage.put("name",account);
            resultMessage.put("pwd",password);
            resultMessage.raise(ConstantsCodeEnum.SUCCESS.getCode(),ConstantsCodeEnum.SUCCESS.getName());

            userBean = new UserBean();
            userBean.setAccount(account);
            userBean.setPassword(password);
            int registerUser = this.loginMapper.register(userBean);
            System.out.println("注册成功："+registerUser);
            return resultMessage;
        }
    }

}