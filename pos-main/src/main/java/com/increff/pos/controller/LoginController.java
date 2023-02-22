package com.increff.pos.controller;

import com.increff.pos.dto.UserDto;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.InfoData;
import com.increff.pos.model.form.UserForm;
import com.increff.pos.pojo.UserPojo;
import com.increff.pos.util.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.increff.pos.util.NormalizeFormUtil.normalizeUserForm;
import static com.increff.pos.util.ValidateFormUtil.validateLoginForm;

@Controller
public class LoginController {

    @Autowired
    private UserDto userDto;
    @Autowired
    private InfoData info;

    @ApiOperation(value = "Logs in a user")
    @RequestMapping(path = "/session/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView login(HttpServletRequest req, UserForm userForm) throws ApiException {
        try {
            validateLoginForm(userForm);
        } catch (ApiException e) {
            info.setType("Login");
            info.setMessage(e.getMessage());
            return new ModelAndView("redirect:/site/login");
        }
        normalizeUserForm(userForm);
        UserPojo userPojo = userDto.getUser(userForm.getEmail());
        if (userPojo == null) {
            info.setType("Login");
            info.setMessage("User do not exists, click sign up to register");
            return new ModelAndView("redirect:/site/login");
        }
        boolean authenticated = (userPojo != null && Objects.equals(userPojo.getPassword(), userForm.getPassword()));
        if (!authenticated) {
            info.setType("Login");
            info.setMessage("Invalid password");
            return new ModelAndView("redirect:/site/login");
        }

        // Create authentication object
        Authentication authentication = SecurityUtil.convert(userPojo);
        // Create new session
        HttpSession session = req.getSession(true);
        // Attach Spring SecurityContext to this new session
        SecurityUtil.createContext(session);
        // Attach Authentication object to the Security Context
        SecurityUtil.setAuthentication(authentication);

        return new ModelAndView("redirect:/ui/home");

    }

    @RequestMapping(path = "/session/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return new ModelAndView("redirect:/site/login");
    }

}
