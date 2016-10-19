package net.demo.llg.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * HomeController.java
 *
 * abstract: 主页
 *
 * history:
 * 	
 * mis_llg 2016年10月18日 初始化
 */
@Controller
public class HomeController {

	@Value("${mail.from}")
	private String mailAddress;
	
	/**
	 * 主页
	 *
	 * @return mv
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView getHome() {
		ModelAndView mv = new ModelAndView("home");
		mv.addObject("name", "Lv li guo");
		mv.addObject("mailAddress", mailAddress);

		return mv;
	}
}
