//package com.isa.brtaeasyfill.forms;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.isa.brtaeasyfill.commonFunction.Response;
//
//@RestController
//@RequestMapping("/api/forms")
//@CrossOrigin(origins = "*")
//
//public class FormsController {
//	@Autowired
//	private FormsService formsService;
//	@PostMapping("/create")
//	public Response create(@RequestBody String reqObj) {
//		return formsService.save(reqObj);
//	}
//
//
//}
