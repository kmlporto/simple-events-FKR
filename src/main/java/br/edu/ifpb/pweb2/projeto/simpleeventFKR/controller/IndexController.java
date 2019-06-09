package br.edu.ifpb.pweb2.projeto.simpleeventFKR.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.EspecialidadeDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.EventoDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.UserDAO;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    public EventoDAO eventoDAO;
    @Autowired
    public UserDAO userDAO;
    @Autowired
    public EspecialidadeDAO especDAO;

    @RequestMapping(method = RequestMethod.GET)
    public String showIndex(Model model) {
        model.addAttribute("eventos", eventoDAO.findAll());
        model.addAttribute("especialidades", especDAO.findAll());
        return "index.html";
    }

}
