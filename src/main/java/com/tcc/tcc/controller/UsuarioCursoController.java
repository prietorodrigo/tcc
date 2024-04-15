package com.tcc.tcc.controller;

import com.tcc.tcc.dto.UserDto;
import com.tcc.tcc.model.Curso;
import com.tcc.tcc.model.User;
import com.tcc.tcc.model.UsuarioCurso;
import com.tcc.tcc.repository.CursoRepository;
import com.tcc.tcc.repository.UserRepository;
import com.tcc.tcc.repository.UsuarioCursoRepository;
import com.tcc.tcc.service.CursoService;
import com.tcc.tcc.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UsuarioCursoController {
    @Autowired
    UsuarioCursoRepository usuarioCursoRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CursoService cursoService;

    @Autowired
    CursoRepository cursoRepository;

    @RequestMapping(value="/inicioUC", method = RequestMethod.GET)
    public String inicio() { return "index"; }

    @RequestMapping(value="/listarUsuariosCursos/{id}", method=RequestMethod.POST)
    public String cadastroUsuarioCurso(@PathVariable("id") Long id,
                                       @RequestParam("ano") int ano,
                                       @RequestParam("semestre") int semestre,
                                       @Valid UsuarioCurso usuarioCurso,
                                       @RequestParam("cursos") Long cursoId) {
        Optional<User> userOptional = userRepository.findById(id);
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        if (!userOptional.isPresent() || !cursoOptional.isPresent()) {
            // Manejar el caso donde el usuario o el rol no existen
            return "redirect:/listarUsuariosCursos/" + id;
        }
        User user = userOptional.get();
        Curso curso = cursoOptional.get();

        usuarioCurso = new UsuarioCurso();
        usuarioCurso.setUsuario(user);
        usuarioCurso.setCurso(curso);
        usuarioCurso.setAno(ano);
        usuarioCurso.setSemestre(semestre);

        usuarioCursoRepository.save(usuarioCurso);

        return "redirect:/listarUsuariosCursos/" + id;
    }

    @RequestMapping(value="/listarUsuariosCursos/{id}", method=RequestMethod.GET)
    public ModelAndView getUsuariosCursos(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("listarUsuariosCursos");
        Optional<User> user = userRepository.findById(id);
        List<UsuarioCurso> usuariosCursos = usuarioCursoRepository.findByUsuario(user);
        mv.addObject("usuariosCursos", usuariosCursos);

        List<Curso> cursos = cursoService.findAll();
        mv.addObject("cursos", cursos);
        return mv;
    }

    @RequestMapping(value="/excluirUsuarioCurso/{id}", method=RequestMethod.GET)
    @Transactional
    public String excluirUsuarioCurso(@PathVariable("id") Long id) {
        usuarioCursoRepository.deleteById(id);
        return "redirect:/users";
    }
}
