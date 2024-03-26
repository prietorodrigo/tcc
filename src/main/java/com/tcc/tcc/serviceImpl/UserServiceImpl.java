package com.tcc.tcc.serviceImpl;

import com.tcc.tcc.dto.UserDto;
import com.tcc.tcc.model.Role;
import com.tcc.tcc.model.User;
import com.tcc.tcc.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.tcc.tcc.repository.UserRepository;
import com.tcc.tcc.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        try {
            User user = new User();
            user.setName(userDto.getFirstName() + " " + userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setFoto(userDto.getFoto());
            user.setMatricula(userDto.getMatricula());
            user.setSiape(userDto.getSiape());

            // Obtén o crea los roles
            Role roleEstudiante = checkRoleExist("ROLE_ESTUDIANTE");
            Role roleOrientador = checkRoleExist("ROLE_ORIENTADOR");
            Role roleAdmin = checkRoleExist("ROLE_ADMIN");

            // Asigna roles al usuario según su tipo
            if (userDto.getRoleName().equals("ESTUDIANTE")) {
                user.setRoles(Arrays.asList(roleEstudiante));
            } else if (userDto.getRoleName().equals("ORIENTADOR")) {
                user.setRoles(Arrays.asList(roleOrientador));
            } else {
                user.setRoles(Arrays.asList(roleAdmin));
            }
            userRepository.save(user);
            System.out.println("Usuario guardado correctamente: " + userDto.getEmail());
        }
        catch (Exception e) {
            System.out.println("Error al guardar el usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void updateUser(UserDto userDto) {
        try {
            // Buscar el usuario existente por su ID
            User user = userRepository.findById(userDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado para el ID proporcionado: " + userDto.getId()));

            // Actualizar los campos comunes del usuario
            user.setName(userDto.getFirstName() + " " + userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setFoto(userDto.getFoto());
            user.setMatricula(userDto.getMatricula());
            user.setSiape(userDto.getSiape());

            // Obtener los roles existentes
            Role roleEstudiante = roleRepository.findByName("ROLE_ESTUDIANTE");
            Role roleOrientador = roleRepository.findByName("ROLE_ORIENTADOR");
            Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");

            // Crear una nueva lista de roles
            List<Role> roles = new ArrayList<>();

            // Asignar roles al usuario según su tipo
            if (userDto.getRoleName().equals("ESTUDIANTE")) {
                user.setRoles(Arrays.asList(roleEstudiante));
            } else if (userDto.getRoleName().equals("ORIENTADOR")) {
                user.setRoles(Arrays.asList(roleOrientador));
            } else {
                user.setRoles(Arrays.asList(roleAdmin));
            }

            // Verificar si se proporciona una nueva contraseña
            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                // Codificar y establecer la nueva contraseña
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }

            // Establecer la nueva lista de roles
            user.setRoles(roles);

            // Guardar los cambios en el usuario
            userRepository.save(user);
            System.out.println("Usuario actualizado correctamente: " + userDto.getEmail());
        } catch (Exception e) {
            System.out.println("Error al actualizar el usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Role getRoleIfExists(Role role) {
        return role != null ? role : new Role();
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findUserByNameLike(String nome) {
        return userRepository.findUserByNameLike(nome);
    }

    private UserDto convertEntityToDto(User user){
        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");
        userDto.setId(user.getId());
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());
        userDto.setRoleName(user.getRoles().get(0).getName());
        userDto.setFoto(user.getFoto());
        return userDto;
    }

    private Role checkRoleExist(String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
        return role;
    }
}
