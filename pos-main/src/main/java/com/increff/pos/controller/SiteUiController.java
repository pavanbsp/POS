package com.increff.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SiteUiController extends AbstractUiController {

    // WEBSITE PAGES
    @RequestMapping(value = "")
    public ModelAndView index() {
        return mav("login.html");
    }

    @RequestMapping(value = "/site/signup")
    public ModelAndView signup() {
        return mav("signup.html");
    }

    @RequestMapping(value = "/site/login")
    public ModelAndView login() {
        return mav("login.html");
    }

    @RequestMapping(value = "/site/logout")
    public ModelAndView logout() {
        return mav("logout.html");
    }

    @RequestMapping(value = "/ui/home")
    public ModelAndView home() {
        return mav("home.html");
    }

    @RequestMapping(value = "/ui/brand")
    public ModelAndView brand() {
        return mav("brand.html");
    }

    @RequestMapping(value = "/ui/product")
    public ModelAndView product() {
        return mav("product.html");
    }

    @RequestMapping(value = "/ui/inventory")
    public ModelAndView inventory() {
        return mav("inventory.html");
    }

    @RequestMapping(value = "/ui/orders")
    public ModelAndView orders() {
        return mav("orders.html");
    }

    @RequestMapping(value = "/ui/dailyReport")
    public ModelAndView dailyReport() {
        return mav("dailyReport.html");
    }

    @RequestMapping(value = "/ui/salesReport")
    public ModelAndView salesReport() {
        return mav("salesReport.html");
    }

    @RequestMapping(value = "/ui/inventoryReport")
    public ModelAndView inventoryReport() {
        return mav("inventoryReport.html");
    }

    @RequestMapping(value = "/ui/brandReport")
    public ModelAndView brandReport() {
        return mav("brandReport.html");
    }

}
