package com.tcc.tcc.controller;

import com.tcc.tcc.model.Ideia;
import com.tcc.tcc.repository.IdeiaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class IdeiaController {
    @Autowired
    IdeiaRepository ideiaRepository;

    @RequestMapping(value="/inicioI", method = RequestMethod.GET)
    public String inicio() { return "index"; }

    @RequestMapping(value="/novaIdeia", method=RequestMethod.GET)
    public String novaIdeia() {
        return "cadastrarIdeia";
    }

    @RequestMapping(value="/novaIdeia", method=RequestMethod.POST)
    public String cadastroIdeia(@Valid Ideia ideia, BindingResult result, RedirectAttributes msg) {
        if(result.hasErrors()) {
            msg.addFlashAttribute("erro", "Erro ao cadastrar. Por favor, preencha todos os campos");
            return "redirect:/novaIdeia";
        }

        ideia.setData(LocalDate.now());
        ideia.setReservada(false);

        ideiaRepository.save(ideia);
        msg.addFlashAttribute("sucesso", "Ideia cadastrada.");

        return "redirect:/novaIdeia";
    }

    @RequestMapping(value="/listarIdeias", method=RequestMethod.GET)
    public ModelAndView getIdeias() {
        ModelAndView mv = new ModelAndView("listarIdeias");
        // Obtener el rol del usuario actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        List<Ideia> ideias = null;
        // Filtrar las ideias según el rol del usuario
        if (role.equals("ROLE_ADMIN")) {
            ideias = ideiaRepository.findAll();
        } else if (role.equals("ROLE_ESTUDIANTE") || role.equals("ROLE_ORIENTADOR")) {
            ideias = ideiaRepository.findIdeiasByReservada(false);
        }
        mv.addObject("ideias", ideias);
        return mv;
    }

    @RequestMapping(value="/editarIdeia/{id}", method=RequestMethod.GET)
    public ModelAndView editar(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("editarIdeia");
        Optional<Ideia> ideia = ideiaRepository.findById(id);
        mv.addObject("tema", ideia.get().getTema());
        mv.addObject("descricao", ideia.get().getDescricao());
        mv.addObject("reservada", ideia.get().isReservada());
        mv.addObject("id", ideia.get().getId());
        return mv;
    }

    @RequestMapping(value="/editarIdeia/{id}", method=RequestMethod.POST)
    public String editarIdeiaBanco(Ideia ideia, RedirectAttributes msg){
        Ideia ideiaExistente = ideiaRepository.findById(ideia.getId()).orElse(null);
        ideiaExistente.setTema(ideia.getTema());
        ideiaExistente.setDescricao(ideia.getDescricao());
        ideiaExistente.setReservada(ideia.isReservada());
        ideiaRepository.save(ideiaExistente);
        return "redirect:/listarIdeias";
    }

    @RequestMapping(value="/excluirIdeia/{id}", method=RequestMethod.GET)
    public String excluirIdeia(@PathVariable("id") Long id) {
        ideiaRepository.deleteById(id);
        return "redirect:/listarIdeias";
    }
}
