package test;

import javax.servlet.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class TestCtrl {
	@GetMapping("/test")
	public String test(Model model, @RequestParam(value="name", defaultValue="aaa", required=false) String name) {
		model.addAttribute("greeting", "안녕하세요, " + name);	
		return "test"; 
	}
	
	@GetMapping("/test2")
	public String test2(String name, HttpServletRequest request) {
		request.setAttribute("greeting", "안녕하세요2, " + name);	
		return "test";
	}

}
