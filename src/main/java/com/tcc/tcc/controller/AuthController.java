package com.tcc.tcc.controller;

import com.tcc.tcc.dto.UserDto;
import com.tcc.tcc.model.Role;
import com.tcc.tcc.model.User;
import com.tcc.tcc.repository.RoleRepository;
import com.tcc.tcc.repository.UserRepository;
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
import java.util.Arrays;
import java.util.List;

@Controller
public class AuthController {

    private UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // método manipulador para lidar com a solicitação da página inicial
    @GetMapping("index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // método manipulador para lidar com a solicitação do formulário de registro do usuário
    @GetMapping("register")
    public String showRegistrationForm(Model model){
        // cria um objeto modelo para armazenar dados do formulário
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // método manipulador para lidar com solicitação de envio de formulário de usuário de registro
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model,
                               @RequestParam("file") MultipartFile foto){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "Já existe uma conta registrada com esse e-mail");
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
        return "users";
    }

    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");
        userDto.setId(user.getId());
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());
        userDto.setRoleName(user.getRoles().get(0).getName()); // Obtén el primer rol del usuario
        userDto.setFoto(user.getFoto());
        userDto.setMatricula(user.getMatricula());
        userDto.setSiape(user.getSiape());
        return userDto;
    }

    @GetMapping("/editarUser/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        UserDto userDto = convertEntityToDto(user);
        model.addAttribute("user", userDto);
        return "editarUser";
    }

    @PostMapping("/editarUser/{id}")
    public String editUser(@PathVariable("id") Long id, @Validated(EditValidation.class) UserDto userDto,
                           BindingResult result, RedirectAttributes msg, @RequestParam("file") MultipartFile foto) {
        if (result.hasErrors()) {
            msg.addFlashAttribute("erro", "Erro ao editar. Por favor, preencha todos os campos.");
            return "redirect:/editarUser/" + id;
        }

        // Obtén el usuario existente
        User existingUser = userService.findById(id);
        if (existingUser == null) {
            msg.addFlashAttribute("erro", "Erro ao editar. Usuário não encontrado.");
            return "redirect:/users";
        }

        // Actualiza los datos del usuario con los datos del DTO
        existingUser.setName(userDto.getFirstName() + " " + userDto.getLastName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setMatricula(userDto.getMatricula());
        existingUser.setSiape(userDto.getSiape());

        // Actualiza el rol del usuario
        Role role = roleRepository.findByName(userDto.getRoleName());
        if (role == null) {
            // Crea el rol si no existe
            role = new Role();
            role.setName(userDto.getRoleName());
            roleRepository.save(role);
        }
        existingUser.setRoles(Arrays.asList(role)); // Asigna el nuevo rol al usuario

        // Actualiza la foto si se proporciona una nueva
        try {
            if (!foto.isEmpty()) {
                byte[] bytes = foto.getBytes();
                Path caminho = Paths.get("./src/main/resources/static/img/" + foto.getOriginalFilename());
                Files.write(caminho, bytes);
                existingUser.setFoto(foto.getOriginalFilename());
            }
        } catch (IOException e) {
            msg.addFlashAttribute("erro", "Erro ao processar a imagem.");
            return "redirect:/editarUser/" + id;
        }

        // Convierte el usuario existente a UserDto
        UserDto existingUserDto = convertEntityToDto(existingUser);

        // Guarda los cambios en la base de datos
        userService.updateUser(existingUserDto);

        msg.addFlashAttribute("success", "Usuário atualizado com sucesso.");
        return "redirect:/users";
    }

}
