package com.example.controller;

import com.example.entity.Custom2ConfigBean;
import com.example.entity.ItemConfig;
import com.example.entity.BeanConfig;
import com.example.entity.CustomConfigBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class IndexController {
	@Autowired
	private ItemConfig itemConfig;
	@Autowired
	private BeanConfig beanConfig;
	@Autowired
	private CustomConfigBean customConfigBean;
	@Autowired
	private Custom2ConfigBean custom2ConfigBean;
	
	@RequestMapping("/")
	String index() {
		log.info("itemConfig : {}, beanConfig : {}, customConfigBean :{}", itemConfig.toString(), beanConfig.toString(), customConfigBean.toString());
		log.info("scores are:");
		//for (int i = 0; i < customConfigBean.getScores().length; i++){
		//	log.info("{}",customConfigBean.getScores()[i]);
		//}
		log.info("custom2ConfigBean:{}", custom2ConfigBean.toString());
		return custom2ConfigBean.toString();
	}
}
