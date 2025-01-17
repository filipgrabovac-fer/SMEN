package com.smen.Services;

import com.smen.DTO.User.UserDto;
import com.smen.Models.Language;
import com.smen.Models.Role;
import com.smen.Models.User;
import com.smen.Repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseEntityService<User, Long> {

    private final IUserRepository userRepository;
    private final IRatingRepository ratingRepository;
    private final IWorkshopRepository workshopRepository;
    private final ILanguageRepository languageRepository;
    private final IRoleRepository roleRepository;

    public UserService(IUserRepository userRepository, IRatingRepository ratingRepository, IWorkshopRepository workshopRepository, ILanguageRepository ILanguageRepository, IRoleRepository IRoleRepository) {
        super(userRepository);
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.workshopRepository = workshopRepository;
        this.languageRepository = ILanguageRepository;
        this.roleRepository = IRoleRepository;
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::map)
                .orElse(null);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::map)
                .collect(Collectors.toList());
    }

    public UserDto createUser(UserDto userDto) {
        Language language = languageRepository.findById(userDto.getLanguageId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid language ID."));
        Role role = roleRepository.findById(userDto.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid role ID."));
        User user = userDto.toEntity(language, role);
        User savedUser = userRepository.save(user);
        return UserDto.map(savedUser);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        Optional<User> oldUser = userRepository.findById(id);
        if (oldUser.isEmpty())
            return null;

        Language language = languageRepository.findById(userDto.getLanguageId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid language ID."));
        Role role = roleRepository.findById(userDto.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid role ID."));

        User updatedUser = oldUser.get();
        updatedUser.setFirstName(userDto.getFirstName());
        updatedUser.setLastName(userDto.getLastName());
        updatedUser.setEmail(userDto.getEmail());
        updatedUser.setLanguageId(language.getId());
        updatedUser.setRoleId(role.getId());

        User savedUser = userRepository.save(updatedUser);
        return UserDto.map(savedUser);
    }

    public boolean deleteUser(Long id) {
        return deleteById(id);
    }

    public List<User> getUserByName(String name) {
        var idx = name.indexOf(' ');
        if (idx > -1) {
            var firstname = name.substring(0, idx);
            var lastname = name.substring(idx + 1);
            return userRepository. findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(firstname, lastname);
        }
        return userRepository. findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
