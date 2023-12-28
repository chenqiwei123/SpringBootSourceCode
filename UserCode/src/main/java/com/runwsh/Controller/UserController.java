package com.runwsh.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @RequestMapping("cqw")
    public String cqw(){
        return "cqw";
    }
}
