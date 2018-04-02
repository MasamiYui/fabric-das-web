package org.it611.das.web;

import org.it611.das.dao.SysUserRepository;
import org.it611.das.domain.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@Autowired
	private SysUserRepository userDao;
	
	@RequestMapping("/")
	public String index(Model model){
		Msg msg =  new Msg("测试标题","测试内容","额外信息，只对管理员显示");
		model.addAttribute("msg", msg);
		return "home";
	}

	@RequestMapping("/t")
	@ResponseBody
	public String t1(Model model){
		return userDao.findByUsername("wyf").getPassword();
	}

}
