package pe.gob.onpe.rae.controller.main;


import java.util.ArrayList;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.gob.onpe.rae.constant.Parametros;
import pe.gob.onpe.rae.dao.UsuarioDAO;
import pe.gob.onpe.rae.model.Usuario;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */

@Controller
@Transactional
@SessionAttributes("usuario")
public class MainController {
    
    @Autowired
    UsuarioDAO usuarioDAO;
    
    @RequestMapping("main")
    public String index(Model model){
       
        return "main/index";
    }          
    
    @RequestMapping("/")
    public String index(HttpServletRequest request){

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("USUARIO_AUTENTICADO") == null){
            return "redirect:/login";
        }else{
            return "main/index";
        }        
    }
    @RequestMapping(value = "cambiar-clave", method = RequestMethod.GET)
    public ModelAndView cambiarClave(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        Usuario user = (Usuario)session.getAttribute("USUARIO_AUTENTICADO");
        Usuario currentUser = usuarioDAO.find(user);
        
        if(currentUser.getEstado() != Parametros.ESTADO_USUARIO_PENDIENTE){
            ModelAndView view = new ModelAndView("main/index");
            return view;
        }
        
        ArrayList<String> numbersCollection = new ArrayList<String>() {
            {
                add("0");
                add("1");
                add("2");
                add("3");
                add("4");
                add("5");
                add("6");
                add("7");
                add("8");
                add("9");
            }
        };
        ArrayList<String> lettersCollection = new ArrayList<String>() {
            {
                add("A");
                add("B");
                add("C");
                add("D");
                add("E");
                add("F");
            }
        };
        Collections.shuffle(numbersCollection);
        Collections.shuffle(lettersCollection);  
        
        ModelAndView view = new ModelAndView("main/cambiarClave");
        view.addObject("numbers", numbersCollection);
        view.addObject("letters", lettersCollection);
        return view;
    }   
    
    @RequestMapping(value = "cambiar-clave", method = RequestMethod.POST, params = {"clave","claverepetida"})
    public String guardarClave(HttpServletRequest request, Model model,RedirectAttributes redirectAttrs, @RequestParam("id") String usuario, @RequestParam("clave") String clave, @RequestParam("claverepetida") String claverepetida){

            ArrayList<String> numbersCollection = new ArrayList<String>() {
                {
                    add("0");
                    add("1");
                    add("2");
                    add("3");
                    add("4");
                    add("5");
                    add("6");
                    add("7");
                    add("8");
                    add("9");
                }
            };
            ArrayList<String> lettersCollection = new ArrayList<String>() {
                {
                    add("A");
                    add("B");
                    add("C");
                    add("D");
                    add("E");
                    add("F");
                }
            };
            Collections.shuffle(numbersCollection);
            Collections.shuffle(lettersCollection);  
        
        
        if(!clave.equals(claverepetida)){
            model.addAttribute("numbers", numbersCollection);
            model.addAttribute("letters", lettersCollection);
            redirectAttrs.addFlashAttribute("message", Parametros.ERROR_CLAVE_DIFERENTE);        
            return "redirect:cambiar-clave";
        }
        
        Usuario user = usuarioDAO.findByLogin(usuario);
        
        if(clave.equals(user.getClave())){
            model.addAttribute("numbers", numbersCollection);
            model.addAttribute("letters", lettersCollection);
            redirectAttrs.addFlashAttribute("message", Parametros.ERROR_CLAVE_IGUALES);        
            return "redirect:cambiar-clave";            
        }
        
        user.setClave(clave);
        user.setEstado(Parametros.ESTADO_USUARIO_ACTIVO);
        
        usuarioDAO.saveDAO(user);
        
        return "redirect:/";
        
    }      
    

}
