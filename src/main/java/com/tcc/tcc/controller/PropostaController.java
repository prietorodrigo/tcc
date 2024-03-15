package com.tcc.tcc.controller;

import com.tcc.tcc.model.Area;
import com.tcc.tcc.model.Curso;
import com.tcc.tcc.model.Proposta;
import com.tcc.tcc.model.User;
import com.tcc.tcc.repository.PropostaRepository;
import com.tcc.tcc.service.AreaService;
import com.tcc.tcc.service.CursoService;
import com.tcc.tcc.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class PropostaController {
    @Autowired
    PropostaRepository propostaRepository;

    @Autowired
    AreaService areaService;

    @Autowired
    CursoService cursoService;

    @Autowired
    UserService userService;

    @RequestMapping(value="/inicioP", method = RequestMethod.GET)
    public String inicio() { return "index"; }

    @RequestMapping(value="/novaProposta", method=RequestMethod.GET)
    public String novaProposta(Model model) {
        model.addAttribute("proposta", new Proposta());
        List<Area> areas = areaService.findAll();
        List<Curso> cursos = cursoService.findAll();
        List<User> usuarios = userService.findAll();
        model.addAttribute("areas", areas);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("cursos", cursos);
        return "cadastrarProposta";
    }

    @RequestMapping(value="/novaProposta", method=RequestMethod.POST)
    public String cadastroProposta(@Valid Proposta proposta, BindingResult result, RedirectAttributes msg){
        if(result.hasErrors()) {
            msg.addFlashAttribute("erro", "Erro ao cadastrar. Por favor, preencha todos os campos");
            return "redirect:/novaProposta";
        }
        proposta.setData(LocalDate.now());
        propostaRepository.save(proposta);
        msg.addFlashAttribute("sucesso", "Proposta cadastrada.");

        return "redirect:/novaProposta";
    }

    @RequestMapping(value="/listarPropostas", method=RequestMethod.GET)
    public ModelAndView getPropostas() {
        ModelAndView mv = new ModelAndView("listarPropostas");
        List<Proposta> propostas = propostaRepository.findAll();
        mv.addObject("propostas", propostas);
        return mv;
    }

    @RequestMapping(value="/editarProposta/{id}", method=RequestMethod.GET)
    public String editar(@PathVariable("id") Long id, Model model) {
        Proposta proposta = propostaRepository.findById(id).orElse(null);
        List<Area> area = areaService.findAll();
        List<Curso> curso = cursoService.findAll();
        List<User> usuario = userService.findAll();
        model.addAttribute("proposta", proposta);
        model.addAttribute("area", area);
        model.addAttribute("usuario", usuario);
        model.addAttribute("curso", curso);
        return "editarProposta";
    }

    @RequestMapping(value="/editarProposta/{id}", method=RequestMethod.POST)
    public String editarPropostaBanco(@PathVariable("id") Long id, @Valid Proposta proposta,
                                      BindingResult result, RedirectAttributes msg) {
        if(result.hasErrors()) {
            msg.addFlashAttribute("erro", "Erro ao editar. Por favor, preencha todos os campos.");
            return "redirect:/editarProposta/" + id;
        }
        proposta.setData(LocalDate.now());
        propostaRepository.save(proposta);
        msg.addFlashAttribute("success", "Proposta atualizada com sucesso.");
        return "redirect:/listarPropostas";
    }
}
