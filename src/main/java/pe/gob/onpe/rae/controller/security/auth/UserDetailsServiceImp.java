package pe.gob.onpe.rae.controller.security.auth;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.onpe.rae.constant.Parametros;
import pe.gob.onpe.rae.dao.UsuarioDAO;
import pe.gob.onpe.rae.model.Usuario;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Service("userDetailsService")
@Transactional(readOnly = true)
public class UserDetailsServiceImp implements UserDetailsService{

    @Autowired
    UsuarioDAO usuarioDAO;    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDAO.findByLogin(username);
        if(usuario == null){
            throw new BadCredentialsException("Usuario no encontrado");
        }
        if(usuario.getPerfil().getId() == Parametros.PERFIL_ONPE){
            if(usuario.getEstado() == Parametros.ESTADO_USUARIO_INACTIVO){
                throw new BadCredentialsException("Usuario no habilitado");
            }
        }
        if(usuario.getPerfil().getId() == Parametros.PERFIL_AMBITO){
            if(usuario.getEstado() == Parametros.ESTADO_USUARIO_INACTIVO){
                throw new BadCredentialsException("Usuario no habilitado");
            }
            if(usuario.getEstado() == Parametros.ESTADO_USUARIO_ACTIVO || usuario.getEstado() == Parametros.ESTADO_USUARIO_PENDIENTE){
                Date date = new Date();
                if(date.after(usuario.getFechaInicial()) && date.before(usuario.getFechaFinal()) ){

                }else{
                    throw new BadCredentialsException("Usuario no habilitado");
                }
            }            
        }        
        
        String password = usuario.getClave();
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();

        roles.add(new SimpleGrantedAuthority(usuario.getPerfil().getNombre()));
        return new User(username, password, roles);
    }

}
