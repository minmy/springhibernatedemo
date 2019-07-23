package com.xinmy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lijianxin
 */
@Slf4j
@Controller
@RequestMapping
public class HelloWordController {

    @RequestMapping("index")
    public String helloWord() {
        return "index";
    }

    @RequestMapping("login")
    public String login() {
        return "login";
    }
}
