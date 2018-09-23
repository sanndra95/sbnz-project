package com.example.sbnz.security;

import com.example.sbnz.SbnzApplication;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.sbnz.model.User;
import com.example.sbnz.repository.UserRepository;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KieContainer kieContainer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {

            KieServices ks = KieServices.Factory.get();
            KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
            kbconf.setOption(EventProcessingOption.STREAM);
            KieBase kbase = kieContainer.newKieBase(kbconf);
            KieSession kieSession = kbase.newKieSession();

            if(!SbnzApplication.kieSessions.containsKey("kieSession-"+username)) {
                SbnzApplication.kieSessions.put("kieSession-"+username, kieSession);
            }
            if(!SbnzApplication.allUsers.containsKey("currentUser-"+username))
                SbnzApplication.allUsers.put("currentUser-"+username, user);

            return JwtUserFactory.create(user);
        }
    }
}
