package com.example.demo.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.member.service.MemberService;
import com.example.demo.member.vo.MemberVO;

@Controller
public class MemberController {
   
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberVO memberVO;
	
	public MemberController() {
	
	}
	
	//http://localhost:8090/
	//http://localhost:8090/main.do
	@RequestMapping(value= {"/","/main.do"}, method=RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request, ModelAndView mav) {
		String viewName = (String)request.getAttribute("viewName");
		if(viewName ==null) {
			viewName = "/main";
		}
		
		mav.setViewName(viewName);		
		return mav;
	}
	

	
	
	// http://localhost:8090/member/listMembers.do
	@RequestMapping(value="/member/listMembers.do", method=RequestMethod.GET)
	public ModelAndView listMembers(ModelAndView mav, HttpServletRequest request) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		System.out.println("list viewName : " + viewName);
		mav.addObject("membersList", memberService.listMembers());
		mav.setViewName("/member/listMembers");
		return mav;
	}
	
	// http://localhost:8090/member/loginForm.do
	// http://localhost:8090/member/memberForm.do
	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	private ModelAndView form(@RequestParam(value= "result", required=false) String result,
			                  @RequestParam(value= "action", required=false) String action,
			                  HttpServletRequest request, 
			                  HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		System.out.println("memberForm viewName : " + viewName);
		HttpSession session = request.getSession();
		session.setAttribute("action", action); 
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.setViewName(viewName);
		return mav;
	}
	
	
	
	@RequestMapping(value="/member/addMember.do" ,method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member,
			                  HttpServletRequest request, HttpServletResponse response) throws Exception {
		//request.setCharacterEncoding("utf-8");
		int result = 0;
		result = memberService.addMember(member);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	
	@RequestMapping(value="/member/removeMember.do" ,method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, 
			           HttpServletRequest request, HttpServletResponse response) throws Exception{
		//request.setCharacterEncoding("utf-8");
		memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	
	 
	@RequestMapping(value = "/member/login.do", method =  RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("member") MemberVO member,
			                  RedirectAttributes  rAttr,
			                  HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		memberVO = memberService.login(member);
		if(memberVO != null) {  
			HttpSession session = request.getSession();
			session.setAttribute("member", memberVO);
			session.setAttribute("isLogOn", true);
			
			String action = (String)session.getAttribute("action");
			session.removeAttribute("action");
			if(action!= null) {
				mav.setViewName("redirect:"+action);
			}else {
				mav.setViewName("redirect:/member/listMembers.do");	
			}
			
		}else {
			rAttr.addAttribute("result","loginFailed");
			mav.setViewName("redirect:/member/loginForm.do");
		}		
		return mav;
	}
	
	@RequestMapping("/member/logout.do")
	public String logout() {
		
		return null;
	}
	
	@RequestMapping(value = "/member/logout.do", method =  RequestMethod.GET)
	public String logout(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("member");
		session.removeAttribute("isLogOn");			
		return "redirect:/member/listMembers.do";
	}	
	
		
}









