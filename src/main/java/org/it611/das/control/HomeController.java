package org.it611.das.control;

import org.it611.das.couchdb.CouchDB;
import org.it611.das.domain.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@Autowired
	private CouchDB ccdb;

	@RequestMapping("/")
	public String index(Model model){
		Msg msg =  new Msg("测试标题","测试内容","额外信息，只对管理员显示");
		model.addAttribute("msg", msg);
		return "home";
	}

	@RequestMapping("/t")
	@ResponseBody
	public String t(){
		return ccdb.basicKVQuery("aef01013");
	}

}
