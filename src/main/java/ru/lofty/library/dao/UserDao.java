package ru.lofty.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.lofty.library.model.User;
import ru.lofty.library.repository.UserRepository;

/**
 * @author Alex Lavrentyev
 */

@Service
public class UserDao {

    private final UserRepository userRepository;

    @Autowired
    public UserDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean create(User user) {
        userRepository.save(user);

        return true;
    }

    public Page<User> readAll(int pageNumber, String sortField, String sortDir) {

        int pageSize = 3;

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending()
        );

        return userRepository.findAll(pageable);
    }

    public User read(Long id) {
        return userRepository.getById(id);
    }

    public boolean update(User user, Long id) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    public boolean delete(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
