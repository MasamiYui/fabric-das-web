package org.it611.das.control;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class HomeController {

	private static Logger logger=Logger.getLogger(HomeController.class);


	//login页
    @RequestMapping("/login")
    public ModelAndView login(){
        ModelAndView view = new ModelAndView();
		view.setViewName("index_login");
        return view;
    }

    //home页
	@RequestMapping("/")
	public ModelAndView index(){

        ModelAndView view = new ModelAndView();
        view.setViewName("index_home");
        return view;
	}

	//home页
	@RequestMapping("/home")
	public ModelAndView home(){
        ModelAndView view = new ModelAndView();
        view.setViewName("index_home");
        return view;
    }

}
