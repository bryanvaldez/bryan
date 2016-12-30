package pe.gob.onpe.rae.controller.security.server;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.onpe.rae.controller.BaseController;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Controller
public class ServerController extends BaseController {
    
	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied(Principal user) {

		ModelAndView model = new ModelAndView();

		if (user != null) {
			model.addObject("msg", "Hi " + user.getName()
			+ ", you do not have permission to access this page!");
		} else {
			model.addObject("msg",
			"You do not have permission to access this page!");
		}

		model.setViewName("403");
		return model;

	}
    
    @RequestMapping(value = "/unsupported", method = RequestMethod.GET)
    public String unsupoortedPage(HttpServletRequest request) {
        return "security/http/unsupported";
    }
}
