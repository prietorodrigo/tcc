package com.tcc.tcc.controller;

import com.tcc.tcc.model.ProducaoCientifica;
import com.tcc.tcc.model.Proposta;
import com.tcc.tcc.repository.ProducaoCientificaRepository;
import com.tcc.tcc.repository.PropostaRepository;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class ProducaoCientificaController {
    @Autowired
    ProducaoCientificaRepository producaoCientificaRepository;

    @Autowired
    PropostaRepository propostaRepository;

    @RequestMapping(value="/inicioPr", method = RequestMethod.GET)
    public String inicio() { return "index"; }

    @RequestMapping(value="/novaProducao/{id}", method=RequestMethod.GET)
    public String novaProducao() {
        return "cadastrarProducao";
    }

    @RequestMapping(value="/novaProducao/{id}", method=RequestMethod.POST)
    public String cadastroProducao(@PathVariable("id") Long id, @Valid ProducaoCientifica producaoCientifica, BindingResult result, RedirectAttributes msg){
        Optional<Proposta> propostaOptional = propostaRepository.findById(id);
        Proposta proposta = propostaOptional.get();
        if(result.hasErrors()) {
            msg.addFlashAttribute("erro", "Erro ao cadastrar. Por favor, preencha todos os campos");
            return "redirect:/vermaisVaga/{id}";
        }

        producaoCientifica.setProposta(proposta);

        producaoCientificaRepository.save(producaoCientifica);
        msg.addFlashAttribute("sucesso", "Produção Científica cadastrada com sucesso.");

        return "redirect:/listarProducoes";
    }

    @RequestMapping(value = "/listarProducoes", method = RequestMethod.GET)
    public ModelAndView getProducoes() {
        ModelAndView mv = new ModelAndView("listarProducoes");
        List<ProducaoCientifica> producoes = producaoCientificaRepository.findAll();
        for (ProducaoCientifica producao : producoes) {
            Proposta proposta = producao.getProposta();
            producao.setProposta(proposta); // Asumiendo un nuevo campo en ProducaoCientifica para contener los detalles de Proposta obtenidos
        }
        mv.addObject("producoes", producoes);
        return mv;
    }

    @RequestMapping(value="/editarProducao/{id}", method=RequestMethod.GET)
    public ModelAndView editar(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("editarProducao");
        Optional<ProducaoCientifica> producaoCientifica = producaoCientificaRepository.findById(id);
        mv.addObject("semestre", producaoCientifica.get().getSemestre());
        mv.addObject("ano", producaoCientifica.get().getAno());
        mv.addObject("tipo", producaoCientifica.get().getTipo());
        mv.addObject("coorientador", producaoCientifica.get().getCoorientador());
        mv.addObject("resumo", producaoCientifica.get().getResumo());
        mv.addObject("apresentacoes", producaoCientifica.get().getApresentacoes());
        mv.addObject("palavrasChaves", producaoCientifica.get().getPalavrasChaves());
        String palavrasChaves = producaoCientifica.get().getPalavrasChaves();
        List<String> palavrasChavesList = Arrays.asList(palavrasChaves.split("-"));
        mv.addObject("palavrasChavesList", palavrasChavesList); // Pasar la lista al modelo
        mv.addObject("id", producaoCientifica.get().getId());
        return mv;
    }

    @RequestMapping(value="/editarProducao/{id}", method=RequestMethod.POST)
    public String editarProducaoBanco(ProducaoCientifica producao, RedirectAttributes msg) {
        ProducaoCientifica producaoExistente = producaoCientificaRepository.findById(producao.getId()).orElse(null);
        producaoExistente.setSemestre(producao.getSemestre());
        producaoExistente.setAno((producao.getAno()));
        producaoExistente.setTipo(producao.getTipo());
        producaoExistente.setCoorientador(producao.getCoorientador());
        producaoExistente.setResumo(producao.getResumo());
        producaoExistente.setApresentacoes(producao.getApresentacoes());
        producaoExistente.setPalavrasChaves(producao.getPalavrasChaves());
        producaoCientificaRepository.save(producaoExistente);
        return "redirect:/listarProducoes";
    }

    @RequestMapping(value="/excluirProducao/{id}", method=RequestMethod.GET)
    public String excluirProducao(@PathVariable("id") Long id) {
        producaoCientificaRepository.deleteById(id);
        return "redirect:/listarProducoes";
    }
}
