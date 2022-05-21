package fr.orleans.m1.wsi.projets2emargement.Config;

import fr.orleans.m1.wsi.projets2emargement.Modele.Etudiant;
import fr.orleans.m1.wsi.projets2emargement.Modele.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class CryptoConfig extends WebSecurityConfigurerAdapter  {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/emargement").permitAll()
                .antMatchers(HttpMethod.PUT,"/emargement/{idEmargement}").hasAnyRole(String.valueOf(Role.Etudiant),String.valueOf(Role.Enseignant))
                .antMatchers(HttpMethod.GET,"/emargement/QR/{idEmargement}").hasAnyRole(String.valueOf(Role.Etudiant),String.valueOf(Role.Enseignant))
                .antMatchers(HttpMethod.GET,"/emargement/{idEmargement}/{idUtilisateur}").hasAnyRole(String.valueOf(Role.Etudiant),String.valueOf(Role.Enseignant))
                .anyRequest().hasRole(String.valueOf(Role.PersonnelAdm))
                .and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }

}
