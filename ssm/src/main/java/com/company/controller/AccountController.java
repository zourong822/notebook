package com.company.controller;

import com.company.entity.Account;
import com.company.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @RequestMapping("/findAll")
    public String findAll(Model model){
        System.out.println("Controller.findAll(),,,");
        List<Account> list = accountService.findAll();
        model.addAttribute("list",list);
        return "list";
    }

    @RequestMapping("/save")
    public void save(HttpServletRequest request, HttpServletResponse response, Account account) throws IOException {
        System.out.println("Controller.save(),,,");
        System.out.println(account);
        accountService.save(account);
        response.sendRedirect(request.getContextPath()+"/account/findAll");
        return;
    }
}
