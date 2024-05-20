package com.tcc.tcc.controller;

import com.tcc.tcc.dto.UserDto;
import com.tcc.tcc.model.Curso;
import com.tcc.tcc.model.Role;
import com.tcc.tcc.model.User;
import com.tcc.tcc.repository.RoleRepository;
import com.tcc.tcc.repository.UserRepository;
import com.tcc.tcc.service.CursoService;
import com.tcc.tcc.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AuthController {

    private UserService userService;

    @Autowired CursoService cursoService;

    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // handler method to handle user registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model,
                               @RequestParam("file") MultipartFile foto){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }

        try {
            if (!foto.isEmpty()) {
                byte[] bytes = foto.getBytes();
                Path caminho = Paths.get("./src/main/resources/static/img/"+foto.getOriginalFilename());
                Files.write(caminho, bytes);
                user.setFoto(foto.getOriginalFilename());
            }
        } catch (IOException e) {
            System.out.println("Erro imagem");
        }

        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @RequestMapping(value="/foto/{foto}", method=RequestMethod.GET)
    @ResponseBody
    public byte[] getFotos(@PathVariable("foto") String foto) throws IOException {
        File caminho = new File ("./src/main/resources/static/img/"+foto);
        if (foto != null || foto.trim().length() > 0) {
            return Files.readAllBytes(caminho.toPath());
        }
        return null;
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);

        List<List<Curso>> cursosPorUsuario = new ArrayList<>();
        for(UserDto user : users) {
            List<Curso> cursos = cursoService.findCursosByUserId(user.getId());
            cursosPorUsuario.add(cursos);
        }
        model.addAttribute("cursosPorUsuario", cursosPorUsuario);
        return "users";
    }

    private UserDto convertEntityToDto(User user){
        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");
        userDto.setId(user.getId());
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());
        userDto.setMatricula(user.getMatricula());
        userDto.setSiape(user.getSiape());
        userDto.setLattes(user.getLattes());
        Set<String> roleNames = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().getName())
                .collect(Collectors.toSet());
        userDto.setRoleName(roleNames);
        userDto.setFoto(user.getFoto());
        return userDto;
    }

    @GetMapping("/editUser/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        UserDto userDto = convertEntityToDto(user);
        model.addAttribute("user", userDto);
        return "editUser";
    }

    @PostMapping("/editUser/{id}")
    public String editUser(@PathVariable("id") Long id, @Validated(EditValidation.class) UserDto userDto,
                           BindingResult result, RedirectAttributes msg, @RequestParam("file") MultipartFile foto) {

        // Obtener el usuario existente
        User existingUser = userService.findById(id);
        if (existingUser == null) {
            msg.addFlashAttribute("erro", "Erro ao editar. Usuário não encontrado.");
            return "redirect:/users";
        }

        // Actualizar los datos del usuario con los datos del DTO
        existingUser.setName(userDto.getFirstName() + " " + userDto.getLastName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setMatricula(userDto.getMatricula());
        existingUser.setSiape(userDto.getSiape());
        existingUser.setLattes(userDto.getLattes());

        // Guardar los cambios en la base de datos
        userService.updateUser(existingUser);

        msg.addFlashAttribute("success", "Usuário atualizado com sucesso.");
        return "redirect:/users";
    }
}
