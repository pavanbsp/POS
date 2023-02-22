package com.increff.pos.controller;

import com.increff.pos.dto.UserDto;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.InfoData;
import com.increff.pos.model.form.UserForm;
import com.increff.pos.pojo.UserPojo;
import com.increff.pos.util.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.stream.Stream;

import static com.increff.pos.util.NormalizeFormUtil.normalizeUserForm;
import static com.increff.pos.util.ValidateFormUtil.validateUserForm;

@Controller
public class SignupController {
    @Autowired
    private UserDto userDto;
    @Autowired
    private InfoData info;

    @Value("${app.adminEmail}")
    private String adminEmail;

    @ApiOperation(value = "Sign up a user")
    @RequestMapping(path = "/session/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView signup(HttpServletRequest req, UserForm userForm) throws ApiException {
        try {
            validateUserForm(userForm);
        } catch (ApiException e) {
            info.setType("Signup");
            info.setMessage(e.getMessage());
            return new ModelAndView("redirect:/site/signup");
        }
        normalizeUserForm(userForm);
        String[] adminEmails = adminEmail.split(",");
        //set the role
        String role;
        if (Stream.of(adminEmails).anyMatch(userForm.getEmail()::equals)) {
            role = "supervisor";
        } else {
            role = "operator";
        }
        userForm.setRole(role);
        try {
            userDto.addUser(userForm);
        } catch (ApiException e) {
            info.setType("Signup");
            info.setMessage(e.getMessage());
            return new ModelAndView("redirect:/site/signup");
        }
        UserPojo p = userDto.getUser(userForm.getEmail());
        // Create authentication object
        Authentication authentication = SecurityUtil.convert(p);
        // Create new session
        HttpSession session = req.getSession(true);
        // Attach Spring SecurityContext to this new session
        SecurityUtil.createContext(session);
        // Attach Authentication object to the Security Context
        SecurityUtil.setAuthentication(authentication);
        return new ModelAndView("redirect:/ui/home");
    }
}
