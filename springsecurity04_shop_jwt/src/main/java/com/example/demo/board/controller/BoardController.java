package com.example.demo.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.board.dto.BoardDTO;
import com.example.demo.board.dto.PageDTO;
import com.example.demo.board.service.BoardService;

// http://localhost:8090/board/list/1

//@CrossOrigin("*")
@Controller
public class BoardController {

	@Autowired
	private PageDTO pdto;

	@Autowired
	private BoardService service;

	private int currentPage;
	
	@Value("${spring.servlet.multipart.location}")
	private String filePath;

	public BoardController() {

	}


	@ResponseBody
	@RequestMapping("/board/list/{currentPage}")
	public Map<String, Object> listMethod(@PathVariable("currentPage") int currentPage, HttpServletRequest request, PageDTO pv, ModelAndView mav) {
		//String viewName = (String) request.getAttribute("viewName");
        Map<String, Object> map = new HashMap<String, Object>();
		int totalRecord = service.countProcess();
		if (totalRecord >= 1) {
			if (pv.getCurrentPage() == 0)
				currentPage = 1;
			else
				currentPage = pv.getCurrentPage();

			pdto = new PageDTO(currentPage, totalRecord);
			List<BoardDTO> aList = service.listProcess(pdto);
			//mav.addObject("aList", aList);
			//mav.addObject("pv", pdto);
			map.put("aList", aList);
			map.put("pv", pdto);
			
		}
	

		// mav.setViewName("/board/list");
		//mav.setViewName(viewName);
		//return mav;
		return map;
	}// end listMethod()////////////////////////////////////

	@ResponseBody
	@RequestMapping("/board/view/{num}")
	public BoardDTO viewMethod(@PathVariable("num") int num) {		
		return service.contentProcess(num);
	}// end viewMethod()////////////////////////////

	@RequestMapping(value = "/board/write", method = RequestMethod.GET)
	public ModelAndView writeMethod(HttpServletRequest request, BoardDTO dto, PageDTO pv, ModelAndView mav) {
		String viewName = (String) request.getAttribute("viewName");
		if (dto.getRef() != 0) { // 답변글이면...
			System.out.println(pv.getCurrentPage());
			mav.addObject("currentPage", pv.getCurrentPage());
			mav.addObject("dto", dto);
			
		}

		// mav.setViewName("board/write");
		mav.setViewName(viewName);
		return mav;
	}// end writeMethod()//////////////////////////////////
	
	//RequestBody : json => 자바객체 
	//ResponseBody : 자바객체 => json
	//@PathVariable : /board/list/:num  => /board/list/1  => /bard/list/{num}
	//@RequestParam : /board/list?name=value   => /board/list?num=1  => /bard/list
	//multipart/form-data : 그냥 받음 BoardDTO dto
	
	
	@ResponseBody
	@RequestMapping(value = "/board/write", method = RequestMethod.POST)
	public String writeProMethod( BoardDTO dto, PageDTO pv, HttpServletRequest request, HttpServletResponse response) {
		
		MultipartFile file = dto.getFilename();
		if (file !=null && !file.isEmpty()) {
			
			UUID random = saveCopyFile(file);
			dto.setUpload(random + "_" + file.getOriginalFilename());
		}

		dto.setIp(request.getRemoteAddr());
		
		service.insertProcess(dto);
		
		//답변글이면
		if(dto.getRef()!=0) {			
			//return "redirect:/board/list.do?currentPage=" + pv.getCurrentPage();
			return "redirect:/board/list?currentPage=" + pv.getCurrentPage();  //String
		   //  response.sendRedirect("/board/list/"+ pv.getCurrentPage());   //void
		}else {//제목글이면			
			//return "redirect:/board/list.do";
			return "redirect:/board/list/1";
			// response.sendRedirect("/board/list/1");
		}
		
	}// end writeProMethod()/////////////////////////////////

	@ResponseBody
	@RequestMapping("/board/contentdownload/{filename}")	
	public ResponseEntity<Resource> downMethod(@PathVariable("filename") String filename) throws IOException { 
		
		String fileName = filename.substring(filename.indexOf("_")+1);
		
		//파일명이 한글일때 인코딩 작업을 한다.
		String str = URLEncoder.encode(fileName, "UTF-8"); 
		
		//원본파일명에서 공백이 있을 때, +로 표시가 되므로 공백으로 처리해줌
		str = str.replaceAll("\\+","%20");
		
		//컨텐트 타입
		//response.setContentType("application/octet-stream");
		
		//다운로드창에 보여 줄 파일명을 지정한다.
		//response.setHeader("Content-Disposition", "attachment;filename="+str+";");
		
		//서버에 저장된 파일을 읽어와 클라이언트에 출력해 준다.
		//FileCopyUtils.copy(new FileInputStream(new File(saveDirectory, upload)), response.getOutputStream());	
		
		Path path = Paths.get(filePath+"\\"+filename);
		Resource resource = new InputStreamResource(Files.newInputStream(path));
		
		System.out.println("resource:" + resource.getFilename());
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+str+";")
				.body(resource);
	}
	
	@ResponseBody
	@RequestMapping(value="/board/update/{num}", method=RequestMethod.GET)
	public BoardDTO updateMethod(@PathVariable("num") int num) {		 	
		return service.updateSelectProcess(num);
	}	
	
	@ResponseBody
	@RequestMapping(value="/board/update", method=RequestMethod.PUT)
	public void updateProMethod(BoardDTO dto ) {
		MultipartFile file = dto.getFilename();
		if (file !=null && !file.isEmpty()) {
			UUID random = saveCopyFile(file);
			dto.setUpload(random + "_" + file.getOriginalFilename());
		}		
		service.updateProcess(dto, filePath);		
	}
	
	@ResponseBody
	@RequestMapping(value="/board/delete/{num}", method=RequestMethod.DELETE)
	public void deleteMethod(@PathVariable("num") int num) {
		service.deleteProcess(num, filePath);		
	}  
	
	

	// 업로드 첨부파일
	private UUID saveCopyFile(MultipartFile file) {
		String fileName = file.getOriginalFilename();
	
       
		// 중복파일명을 처리하기 위해 난수 발생
		UUID random = UUID.randomUUID();
		
		File fe = new File(filePath); 
		if (!fe.exists()) {
			fe.mkdir();
		}

		File ff = new File(filePath, random + "_" + fileName);

		try {
			FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(ff));

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		return random;

	}// end saveCopyFile()///////////////////////////////
}// end class
