package com.tcc.tcc.controller;

import com.tcc.tcc.model.Curso;
import com.tcc.tcc.repository.CursoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class CursoController {
    @Autowired
    CursoRepository cursoRepository;

    @RequestMapping(value="/inicioC", method = RequestMethod.GET)
    public String inicio() { return "index"; }

    @RequestMapping(value="/novoCurso", method=RequestMethod.GET)
    public String novoCurso() {
        return "cadastrarCurso";
    }

    @RequestMapping(value="/novoCurso", method=RequestMethod.POST)
    public String cadastroCurso(@Valid Curso curso, BindingResult result, RedirectAttributes msg) {
        if(result.hasErrors()) {
            msg.addFlashAttribute("erro", "Erro ao cadastrar. Por favor, preencha todos os campos");
            return "redirect:/novoCurso";
        }

        cursoRepository.save(curso);
        msg.addFlashAttribute("sucesso", "Curso cadastrado.");

        return "redirect:/novoCurso";
    }

    @RequestMapping(value="/listarCursos", method=RequestMethod.GET)
    public ModelAndView getCursos() {
        ModelAndView mv = new ModelAndView("listarCursos");
        List<Curso> cursos = cursoRepository.findAll();
        mv.addObject("cursos", cursos);
        return mv;
    }

    @RequestMapping(value="/editarCurso/{id}", method=RequestMethod.GET)
    public ModelAndView editar(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("editarCurso");
        Optional<Curso> curso = cursoRepository.findById(id);
        mv.addObject("nome", curso.get().getNome());
        mv.addObject("abreviatura", curso.get().getAbreviatura());
        mv.addObject("disciplinastcc", curso.get().getDisciplinastcc());
        mv.addObject("id", curso.get().getId());
        return mv;
    }

    @RequestMapping(value="/editarCurso/{id}", method=RequestMethod.POST)
    public String editarCursoBanco(Curso curso, RedirectAttributes msg) {
        Curso cursoExistente = cursoRepository.findById(curso.getId()).orElse(null);
        cursoExistente.setNome(curso.getNome());
        cursoExistente.setAbreviatura(curso.getAbreviatura());
        cursoExistente.setDisciplinastcc(curso.getDisciplinastcc());
        cursoRepository.save(cursoExistente);
        return "redirect:/listarCursos";
    }

    @RequestMapping(value="/excluirCurso/{id}", method=RequestMethod.GET)
    public String excluirCurso(@PathVariable("id") Long id) {
        cursoRepository.deleteById(id);
        return "redirect:/listarCursos";
    }
}
