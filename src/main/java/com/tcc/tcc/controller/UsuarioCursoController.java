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

    @RequestMapping(value="/novoUsuarioCurso/{id}", method=RequestMethod.GET)
    public String novoUsuarioCurso(@PathVariable("id") Long id, Model model) {
        Optional<User> usuarioOptional = userRepository.findById(id);
        if (!usuarioOptional.isPresent()) {
            return "redirect:/listarUsuariosCursos";
        }
        User usuario = usuarioOptional.get(); // Obtener el usuario
        UsuarioCurso usuarioCurso = new UsuarioCurso();
        usuarioCurso.setUsuario(usuario);
        List<Curso> cursos = cursoService.findAll();
        model.addAttribute("usuarioCurso", usuarioCurso);
        model.addAttribute("usuarios", usuario);
        model.addAttribute("cursos", cursos);
        return "cadastrarUsuarioCurso";
    }

    @RequestMapping(value="/novoUsuarioCurso/{id}", method=RequestMethod.POST)
    public String cadastroUsuarioCurso(@PathVariable("id") Long id,
                                       @Valid UsuarioCurso usuarioCurso,
                                       BindingResult result,
                                       RedirectAttributes msg) {
        if(result.hasErrors()) {
            msg.addFlashAttribute("erro", "Erro ao cadastrar. Por favor, preencha todos os campos");
            return "redirect:/novoUsuarioCurso/" + id;
        }

        // Obtener el usuario por su ID
        Optional<User> usuarioOptional = userRepository.findById(id);
        if (!usuarioOptional.isPresent()) {
            // Manejar el caso en que el usuario no existe
            return "redirect:/listarUsuariosCursos";
        }

        // Asignar el usuario al objeto UsuarioCurso
        usuarioCurso.setUsuario(usuarioOptional.get());

        usuarioCursoRepository.save(usuarioCurso);
        msg.addFlashAttribute("sucesso", "UsuarioCurso cadastrado.");

        return "redirect:/listarUsuariosCursos";
    }

    @RequestMapping(value="/listarUsuariosCursos", method=RequestMethod.GET)
    public ModelAndView getUsuariosCursos() {
        ModelAndView mv = new ModelAndView("listarUsuariosCursos");
        List<UsuarioCurso> usuariosCursos = usuarioCursoRepository.findAll();
        mv.addObject("usuariosCursos", usuariosCursos);
        return mv;
    }

    @RequestMapping(value="/editarUsuarioCurso/{id}", method=RequestMethod.GET)
    public String editar(@PathVariable("id") Long id, Model model) {
        UsuarioCurso usuarioCurso = usuarioCursoRepository.findById(id).orElse(null);
        User usuario = usuarioCurso.getUsuario();
        List<Curso> curso = cursoService.findAll();
        model.addAttribute("usuarioCurso", usuarioCurso);
        model.addAttribute("usuario", usuario);
        model.addAttribute("curso", curso);
        return "editarUsuarioCurso";
    }

    @RequestMapping(value="/editarUsuarioCurso/{id}", method=RequestMethod.POST)
    public String editarUsuarioCursoBanco(@PathVariable("id") Long id, @Valid UsuarioCurso usuarioCurso,
                                     BindingResult result, RedirectAttributes msg) {
        if(result.hasErrors()) {
            msg.addFlashAttribute("erro", "Erro ao editar. Por favor, preencha todos os campos.");
            return "redirect:/editarUsuarioCurso/" + id;
        }

        usuarioCursoRepository.save(usuarioCurso);
        msg.addFlashAttribute("success", "UsuarioCurso atualizado com sucesso.");
        return "redirect:/listarUsuariosCursos";
    }

    @RequestMapping(value="/excluirUsuarioCurso/{id}", method=RequestMethod.GET)
    public String excluirUsuarioCurso(@PathVariable("id") Long id) {
        usuarioCursoRepository.deleteById(id);
        return "redirect:/listarUsuariosCursos";
    }
}
