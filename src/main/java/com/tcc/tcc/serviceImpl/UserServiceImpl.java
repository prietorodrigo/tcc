package com.tcc.tcc.serviceImpl;

import com.tcc.tcc.dto.UserDto;
import com.tcc.tcc.model.Role;
import com.tcc.tcc.model.User;
import com.tcc.tcc.model.UserRole;
import com.tcc.tcc.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tcc.tcc.repository.UserRepository;
import com.tcc.tcc.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, InitializingBean {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initializeRoles();
    }

    private void initializeRoles() {
        List<String> roleNames = Arrays.asList("ROLE_ADMIN", "ROLE_ESTUDIANTE", "ROLE_ORIENTADOR");
        for (String roleName : roleNames) {
            if (!roleRepository.existsByName(roleName)) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }
    }

    @Override
    public void saveUser(UserDto userDto) {
        try{
            User user = new User();
            user.setName(userDto.getFirstName() + " " + userDto.getLastName());
            user.setEmail(userDto.getEmail());

            //encrypt the password once we integrate spring security
            //user.setPassword(userDto.getPassword());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));

            user.setFoto(userDto.getFoto());
            user.setMatricula(userDto.getMatricula());
            user.setSiape(userDto.getSiape());
            user.setLattes(userDto.getLattes());
            Set<UserRole> userRoles = assignRoles(userDto.getRoleName(), user);
            user.setUserRoles(userRoles);

            userRepository.save(user);
            System.out.println("Usuario guardado correctamente: " + userDto.getEmail());
        } catch (Exception e) {
            System.out.println("Error al guardar el usuario: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
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

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<User> findByRole(String roleName) {
        return userRepository.findByUserRoles_Role_Name(roleName);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
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

    private Set<UserRole> assignRoles(Set<String> roleNames, User user) {
        Set<UserRole> userRoles = new HashSet<>();
        if (roleNames != null && !roleNames.isEmpty()) {
            for (String roleName : roleNames) {
                Role role = roleRepository.findByName(roleName);
                if (role != null) {
                    UserRole userRole = new UserRole();
                    userRole.setUser(user);
                    userRole.setRole(role);
                    userRoles.add(userRole);
                }
            }
        }
        return userRoles;
    }
}
