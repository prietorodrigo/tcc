package com.tcc.tcc.controller;

import com.tcc.tcc.model.Area;
import com.tcc.tcc.repository.AreaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class AreaController {
    @Autowired
    AreaRepository areaRepository;

    @RequestMapping(value="/inicioA", method = RequestMethod.GET)
    public String inicio() { return "index"; }

    @RequestMapping(value="/novaArea", method=RequestMethod.GET)
    public String novaArea() {
        return "cadastrarArea";
    }

    @RequestMapping(value="/novaArea", method=RequestMethod.POST)
    public String cadastroArea(@Valid Area area, BindingResult result, RedirectAttributes msg) {
        if(result.hasErrors()) {
            msg.addFlashAttribute("erro", "Erro ao cadastrar. Por favor, preencha todos os campos");
            return "redirect:/novaArea";
        }

        areaRepository.save(area);
        msg.addFlashAttribute("sucesso", "Area cadastrada.");

        return "redirect:/novaArea";
    }

    @RequestMapping(value="/listarAreas", method=RequestMethod.GET)
    public ModelAndView getAreas() {
        ModelAndView mv = new ModelAndView("listarAreas");
        List<Area> areas = areaRepository.findAll();
        mv.addObject("areas", areas);
        return mv;
    }

    @RequestMapping(value="/editarArea/{id}", method=RequestMethod.GET)
    public ModelAndView editar(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("editarArea");
        Optional<Area> area = areaRepository.findById(id);
        mv.addObject("nome", area.get().getNome());
        mv.addObject("id", area.get().getId());
        return mv;
    }

    @RequestMapping(value="/editarArea/{id}", method=RequestMethod.POST)
    public String editarAreaBanco(Area area, RedirectAttributes msg) {
        Area areaExistente = areaRepository.findById(area.getId()).orElse(null);
        areaExistente.setNome(area.getNome());
        areaRepository.save(areaExistente);
        return "redirect:/listarAreas";
    }

    @RequestMapping(value="/excluirArea/{id}", method=RequestMethod.GET)
    public String excluirArea(@PathVariable("id") Long id) {
        areaRepository.deleteById(id);
        return "redirect:/listarAreas";
    }
}
