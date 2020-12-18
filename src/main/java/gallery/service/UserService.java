package gallery.service;
import gallery.model.Role;
import gallery.model.User;
import gallery.repository.RoleRepository;
import gallery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
        @Autowired
        private UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
        @Autowired
        private BCryptPasswordEncoder cryptPasswordEncoder;

        public void save(User user) {
            user.setPassword(cryptPasswordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(new Role(1L, "USER")));
            userRepository.save(user);
        }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public User findByUsername(String login) {
            return userRepository.findByLogin(login);
        }

        public User findByEmail(String email){
            return userRepository.findByEmail(email);
        }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid User");
        } else {
            Set<GrantedAuthority> grantedAuthorities = user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toSet());

            return new org.springframework.security.core.userdetails
                    .User(user.getEmail(), user.getPassword(), grantedAuthorities);
        }
    }
}
