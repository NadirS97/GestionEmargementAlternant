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

public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private FacadeUtilisateur facadeUtilisateur;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Utilisateur utilisateur = facadeUtilisateur.findById(s).get();
        if (utilisateur==null) {
            throw  new UsernameNotFoundException("User "+s+" not found");
        }
        Role roles = utilisateur.getRole();

        return User.builder()
                .username(utilisateur.getLogin())
                .password(passwordEncoder.encode(utilisateur.getPassword()))
                .roles(String.valueOf(roles))
                .build();
    }
}
