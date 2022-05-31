package eus.evernature.evern.service.expert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eus.evernature.evern.models.Expert;
import eus.evernature.evern.models.Role;
import eus.evernature.evern.repository.ExpertRepository;
import eus.evernature.evern.repository.RoleRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@Transactional
@Slf4j
public class ExpertServiceImpl implements ExpertService, UserDetailsService {

    @Autowired
    private ExpertRepository expertRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Expert expert = expertRepository.findByUsername(username);
        
        if(expert == null) {
            log.error("User not found in the database instance");
            throw new UsernameNotFoundException("User not found in database instance");
        } else {
            log.info("User found in database: {}", username);
        }

        Collection<SimpleGrantedAuthority> authorities =  new ArrayList<>();
        expert.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new User(expert.getUsername(), expert.getPassword(), authorities);
    }

    @Override
    public Expert saveUser(Expert expert) {
        log.info("Saving new user to the database");
        expert.setPassword(passwordEncoder.encode(expert.getPassword()));

        if(expertRepository.findByUsername(expert.getUsername()) != null) {
            log.error("User already exists in the database");
            throw new RuntimeException("User already exists in the database");
        }

        return expertRepository.save(expert);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        Expert expert = expertRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        expert.getRoles().add(role);
    }

    @Override
    public Expert getExpert(String username) {
        log.info("Getting user {} from database", username);
        return expertRepository.findByUsername(username);
    }

    @Override
    public List<Expert> getExperts() {
        log.info("Fetching all users from database");
        return expertRepository.findAll();
    }
}
