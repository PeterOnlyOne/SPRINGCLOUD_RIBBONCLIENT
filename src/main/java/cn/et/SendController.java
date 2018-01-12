package cn.et;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Controller
public class SendController {

	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/sendClient")
	public String send(String email_to,String email_subject,String email_content){
		//通过注册中心客户端负载均衡，获取一台主机俩调用
		try {
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
			requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("email_to", email_to);
			map.put("email_subject", email_subject);
			map.put("email_content", email_content);
			HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String,Object>>(map,requestHeaders);
			restTemplate.postForObject("http://REGMAIL/send",request, String.class);
		} catch (RestClientException e) {
			e.printStackTrace();
			return "redirect:/error.html";
		}
		return "redirect:/suc.html";
	}
}
