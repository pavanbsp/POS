package com.increff.pos.controller;

import com.increff.pos.model.data.InfoData;
import com.increff.pos.util.SecurityUtil;
import com.increff.pos.util.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Stream;

@Controller
public abstract class AbstractUiController {

    @Autowired
    private InfoData info;

    @Value("${app.baseUrl}")
    private String baseUrl;

    @Value("${app.adminEmail}")
    private String adminEmail;

    protected ModelAndView mav(String page) {
        // Get current user
        UserPrincipal principal = SecurityUtil.getPrincipal();
        String email = principal == null ? "" : principal.getEmail();
        //getting all the admin emails
        String[] adminEmails = adminEmail.split(",");
        //set the role
        String role;
        if (Stream.of(adminEmails).anyMatch(email::equals)) {
            role = "supervisor";
        } else {
            role = "operator";
        }
        info.setEmail(email+"("+role+")");
        // Set info
        ModelAndView mav = new ModelAndView(page);
        mav.addObject("info", info);
        mav.addObject("baseUrl", baseUrl);
        mav.addObject("role", role);
        return mav;
    }


}
