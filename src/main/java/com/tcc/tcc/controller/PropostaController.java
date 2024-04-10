package com.tcc.tcc.controller;

import com.tcc.tcc.model.*;
import com.tcc.tcc.repository.IdeiaRepository;
import com.tcc.tcc.repository.PropostaRepository;
import com.tcc.tcc.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class PropostaController {
    @Autowired
    PropostaRepository propostaRepository;

    @Autowired
    AreaService areaService;

    @Autowired
    CursoService cursoService;

    @Autowired
    UsuarioCursoService usuarioCursoService;

    @Autowired
    UserService userService;

    @Autowired
    IdeiaRepository ideiaRepository;

    @RequestMapping(value="/inicioP", method = RequestMethod.GET)
    public String inicio() { return "index"; }

    @RequestMapping(value="/novaProposta", method=RequestMethod.GET)
    public String novaProposta(Model model) {
        Proposta proposta = new Proposta();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User usuarioLogueado = userService.findByEmail(username);
        proposta.setEstudante(usuarioLogueado);
        List<User> orientadores = userService.findByRole("ROLE_ORIENTADOR");
        List<Curso> cursosInscritos = usuarioCursoService.findCursosByUsuario(usuarioLogueado);
        List<Area> areas = areaService.findAll();
        model.addAttribute("nombreUsuario", usuarioLogueado.getName());
        model.addAttribute("proposta", proposta);
        model.addAttribute("areas", areas);
        model.addAttribute("orientadores", orientadores);
        model.addAttribute("cursos", cursosInscritos);
        return "cadastrarProposta";
    }

    @RequestMapping(value="/novaPropostaIdeia/{id}", method=RequestMethod.GET)
    public String novaPropostaIdeia(@PathVariable("id") Long id, Model model) {
        Optional<Ideia> ideiaOptional = ideiaRepository.findById(id);
        if (ideiaOptional.isEmpty()) {
            return "redirect:/listarIdeias";
        }
        Ideia ideia = ideiaOptional.get();
        Proposta proposta = new Proposta();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User usuarioLogueado = userService.findByEmail(username);
        proposta.setEstudante(usuarioLogueado);
        List<User> orientadores = userService.findByRole("ROLE_ORIENTADOR");
        List<Curso> cursosInscritos = usuarioCursoService.findCursosByUsuario(usuarioLogueado);
        List<Area> areas = areaService.findAll();
        model.addAttribute("nombreUsuario", usuarioLogueado.getName());
        model.addAttribute("proposta", proposta);
        model.addAttribute("areas", areas);
        model.addAttribute("orientadores", orientadores);
        model.addAttribute("cursos", cursosInscritos);
        model.addAttribute("ideia", ideia);
        model.addAttribute("tema", ideia.getTema());
        model.addAttribute("descricao", ideia.getDescricao());
        return "cadastrarPropostaIdeia";
    }

    @RequestMapping(value="/novaProposta", method=RequestMethod.POST)
    public String cadastroProposta(@Valid Proposta proposta, BindingResult result, RedirectAttributes msg){
        if(result.hasErrors()) {
            msg.addFlashAttribute("erro", "Erro ao cadastrar. Por favor, preencha todos os campos");
            return "redirect:/novaProposta";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User usuarioLogueado = userService.findByEmail(username);
        proposta.setEstudante(usuarioLogueado);

        proposta.setData(LocalDate.now());
        propostaRepository.save(proposta);
        msg.addFlashAttribute("sucesso", "Proposta cadastrada.");

        return "redirect:/novaProposta";
    }

    @RequestMapping(value="/novaPropostaIdeia/{id}", method=RequestMethod.POST)
    public String cadastroPropostaIdeia(@PathVariable("id") Long id, @Valid Proposta proposta, BindingResult result, RedirectAttributes msg){
        if(result.hasErrors()) {
            msg.addFlashAttribute("erro", "Erro ao cadastrar. Por favor, preencha todos os campos");
            return "redirect:/novaProposta";
        }
        Optional<Ideia> ideiaOptional = ideiaRepository.findById(id);
        Ideia ideia = ideiaOptional.get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User usuarioLogueado = userService.findByEmail(username);
        proposta.setEstudante(usuarioLogueado);
        proposta.setIdeia(ideia);
        proposta.setData(LocalDate.now());
        ideia.setReservada(true);
        propostaRepository.save(proposta);
        msg.addFlashAttribute("sucesso", "Proposta cadastrada.");

        return "redirect:/novaProposta";
    }

    @RequestMapping(value="/listarPropostas", method=RequestMethod.GET)
    public ModelAndView getPropostas() {
        ModelAndView mv = new ModelAndView("listarPropostas");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User usuarioLogueado = userService.findByEmail(username);
        List<Proposta> propostas;
        if (usuarioLogueado.getUserRoles().stream().anyMatch(r -> r.getRole().equals("ROLE_ORIENTADOR"))) {
            propostas = propostaRepository.findByOrientador(usuarioLogueado);
        } else if (usuarioLogueado.getUserRoles().stream().anyMatch(r -> r.getRole().equals("ROLE_ESTUDIANTE"))) {
            propostas = propostaRepository.findByEstudante(usuarioLogueado);
        } else {
            propostas = propostaRepository.findAll();
        }

        mv.addObject("propostas", propostas);
        return mv;
    }

    @RequestMapping(value="/editarProposta/{id}", method=RequestMethod.GET)
    public String editar(@PathVariable("id") Long id, Model model) {
        Proposta proposta = propostaRepository.findById(id).orElse(null);
        User estudante = proposta.getEstudante();
        List<Curso> cursosInscritos = usuarioCursoService.findCursosByUsuario(estudante);
        List<Area> area = areaService.findAll();
        List<User> orientadores = userService.findByRole("ROLE_ORIENTADOR");
        model.addAttribute("proposta", proposta);
        model.addAttribute("area", area);
        model.addAttribute("nombreUsuario", estudante.getName());
        model.addAttribute("orientadores", orientadores);
        model.addAttribute("curso", cursosInscritos);
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

    @RequestMapping(value="/excluirProposta/{id}", method=RequestMethod.GET)
    public String excluirProposta(@PathVariable("id") Long id) {
        propostaRepository.deleteById(id);
        return "redirect:/listarPropostas";
    }

    @RequestMapping(value={"/pesquisar"}, method=RequestMethod.POST)
    public ModelAndView getPropostas(@RequestParam("texto") String pesquisar) {
        ModelAndView mv = new ModelAndView("listarPropostas");
        List<Proposta> propostas = propostaRepository.findPropostasLike("%"+pesquisar+"%");
        mv.addObject("propostas", propostas);
        return mv;
    }

    @RequestMapping(value="/aceitarProposta/{id}", method=RequestMethod.POST)
    public String aceitarProposta(@PathVariable("id") Long id) {
        Proposta proposta = propostaRepository.findById(id).orElse(null);
        if (proposta != null) {
            proposta.setAceitada(true);
            propostaRepository.save(proposta);
        }
        return "redirect:/listarPropostas";
    }
}
