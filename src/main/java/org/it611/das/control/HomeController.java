package org.it611.das.control;

import org.it611.das.domain.CouchDB;
import org.it611.das.domain.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@Autowired
	private CouchDB couchDB;

	@Value("${couchdb.url}")
	private String url;
	
	@RequestMapping("/")
	public String index(Model model){
		Msg msg =  new Msg("测试标题","测试内容","额外信息，只对管理员显示");
		model.addAttribute("msg", msg);
		return "home";
	}


	@RequestMapping("/t2")
	@ResponseBody
	public String t2(){
		return url;
	}



}
