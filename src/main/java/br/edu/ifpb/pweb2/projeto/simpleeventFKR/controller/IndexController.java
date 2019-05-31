package br.edu.ifpb.pweb2.projeto.simpleeventFKR.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public String showIndex (Model model) {
//        model.addAttribute("login", "s");
        return "index.html";
    }

}