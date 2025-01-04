package com.smen.Services;

import com.smen.Models.User;
import com.smen.Repositories.IRatingRepository;
import com.smen.Repositories.IUserRepository;
import com.smen.Repositories.IWorkshopRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends BaseEntityService<User, Long> {

    private final IUserRepository userRepository;
    private final IRatingRepository ratingRepository;
    private final IWorkshopRepository workshopRepository;

    public UserService(IUserRepository userRepository, IRatingRepository ratingRepository, IWorkshopRepository workshopRepository) {
        super(userRepository); // Prosljeđujemo specifični repozitorij za korisnike
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.workshopRepository = workshopRepository;
    }

    public User updateUser(Long id, User newUser) {
        Optional<User> oldUser = (Optional<User>) userRepository.findById(id);
        if (oldUser.isEmpty())
            return null;
        User updatedUser = oldUser.get();

        updatedUser.setFirstName(newUser.getFirstName());
        updatedUser.setLastName(newUser.getLastName());
        updatedUser.setEmail(newUser.getEmail());
        updatedUser.setTeam(newUser.getTeam());
        updatedUser.setRole(newUser.getRole());
        updatedUser.setAnonymity(newUser.getAnonymity());

        return userRepository.save(updatedUser);
    }
}
