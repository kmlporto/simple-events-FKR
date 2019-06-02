package br.edu.ifpb.pweb2.projeto.simpleeventFKR.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.UserDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.User;

@Component
public class UserDetailsSimpleEvent implements UserDetailsService {
	
	@Autowired
	private UserDAO daoUser;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User usuario = daoUser.findByEmail(username);
//		String role;
		
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuário não encontrado!");
		}
		
		
//		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//		grantList.add(new SimpleGrantedAuthority("ROLE_USER"));
//		
//		if (usuario.isAdmin()) {
//			GrantedAuthority autorizacaoAdmin = new SimpleGrantedAuthority("ROLE_ADMIN");
//			grantList.add(autorizacaoAdmin);
//		}
			
//		UserDetails userDetails = (UserDetails) new User(usuario.getNome(),usuario.getSenha(),grantList);
		
		return usuario;
	}
	
	
	
	
}
