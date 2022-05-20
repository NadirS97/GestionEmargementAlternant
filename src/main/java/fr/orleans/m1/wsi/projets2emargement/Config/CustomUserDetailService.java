package fr.orleans.m1.wsi.projets2emargement.Config;


import fr.orleans.m1.wsi.projets2emargement.Facade.FacadeUtilisateur;
import fr.orleans.m1.wsi.projets2emargement.Modele.Role;
import fr.orleans.m1.wsi.projets2emargement.Modele.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
     FacadeUtilisateur facadeUtilisateur;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Utilisateur> utilisateur = facadeUtilisateur.findById(s);
        if (utilisateur.isEmpty()) {
            throw  new UsernameNotFoundException("User "+s+" not found");
        }
        Role roles = utilisateur.get().getRole();

        return User.builder()
                .username(utilisateur.get().getLogin())
                .password(passwordEncoder.encode(utilisateur.get().getPassword()))
                .roles(String.valueOf(roles))
                .build();
    }
}
