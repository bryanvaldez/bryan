package pe.gob.onpe.rae.controller.security.auth;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Controller
public class login {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request){
        
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("USUARIO_AUTENTICADO") != null){
            return new ModelAndView("main/index");
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
        
        ModelAndView view = new ModelAndView("security/login/index");
        view.addObject("numbers", numbersCollection);
        view.addObject("letters", lettersCollection);
        return view;
    }    

}