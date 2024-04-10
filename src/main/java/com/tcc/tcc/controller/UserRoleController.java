package com.tcc.tcc.controller;

import com.tcc.tcc.model.Role;
import com.tcc.tcc.model.User;
import com.tcc.tcc.model.UserRole;
import com.tcc.tcc.repository.RoleRepository;
import com.tcc.tcc.repository.UserRepository;
import com.tcc.tcc.repository.UserRoleRepository;
import com.tcc.tcc.service.RoleService;
import com.tcc.tcc.service.UserRoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class UserRoleController {
    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(value="/inicioUR", method = RequestMethod.GET)
    public String inicio() { return "index"; }

    @RequestMapping(value="/listarUserRole/{id}", method=RequestMethod.GET)
    public ModelAndView getUsuarioRole(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("listarUserRole");
        Optional<User> user = userRepository.findById(id);
        List<UserRole> userRoles = userRoleRepository.findByUser(user);
        mv.addObject("userRoles", userRoles);

        List<Role> roles = roleService.findAll();
        mv.addObject("roles", roles);
        return mv;
    }

    @RequestMapping(value="/excluirUserRole/{id}", method=RequestMethod.GET)
    @Transactional
    public String excluirUserRole(@PathVariable("id") Long id) {
        userRoleRepository.excluir(id);
        return "redirect:/users";
    }

    @RequestMapping(value = "/listarUserRole/{id}", method = RequestMethod.POST)
    public String addUserRole(@PathVariable("id") Long id, @RequestParam("roles") Long roleId) {
        Optional<User> userOptional = userRepository.findById(id);
        Optional<Role> roleOptional = roleRepository.findById(roleId);

        if (!userOptional.isPresent() || !roleOptional.isPresent()) {
            // Manejar el caso donde el usuario o el rol no existen
            return "redirect:/listarUserRole/" + id;
        }

        User user = userOptional.get();
        Role role = roleOptional.get();

        UserRole newUserRole = new UserRole();
        newUserRole.setUser(user);
        newUserRole.setRole(role);

        userRoleRepository.save(newUserRole);

        return "redirect:/listarUserRole/" + id;
    }
}
