package org.it611.das.control;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class HomeController {

	private static Logger logger=Logger.getLogger(HomeController.class);


    @RequestMapping("/login")
    public ModelAndView login(){
		ModelAndView view = new ModelAndView();
		view.setViewName("index_login");
        return view;
    }

	@RequestMapping("/")
	public String index(){
/*		Msg msg =  new Msg("测试标题","测试内容","额外信息，只对管理员显示");
		model.addAttribute("msg", msg);*/
		return "index_home";
	}

	//测试home页
	@RequestMapping("/home2")
	public String  home2(){ return "index_home";}

}
