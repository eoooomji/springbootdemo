package com.example.board.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Value;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageDTO;
import com.example.board.service.BoardService;

// http://localhost:8090/board/list

//@CrossOrigin("*")
@CrossOrigin(origins= {"http://localhost:3000"})
@RestController
public class BoardController {
	@Autowired
	private BoardService service;

	@Autowired
	private PageDTO pdto;

	private int currentPage;

	@Value("${spring.servlet.multipart.location}")
	private String filePath;

	public BoardController() {

	}

	public void setService(BoardService service) {
		this.service = service;
	}

	// @PathVariable은 확인하기 위해 넣은 값이다.
	@RequestMapping("/board/list/{currentPage}")
	public Map<String, Object> listMethod(@PathVariable("currentPage") int currentPage, PageDTO pv,
			Map<String, Object> map) {
		// System.out.println("currentPage : " + currentPage);
		map = new HashMap<>();
		int totalRecord = service.countProcess();
		if (totalRecord >= 1) {
			if (pv.getCurrentPage() == 0)
				this.currentPage = 1;
			else
				this.currentPage = pv.getCurrentPage();

			this.pdto = new PageDTO(this.currentPage, totalRecord);
			List<BoardDTO> aList = service.listProcess(this.pdto);
			// System.out.println(aList);
			map.put("aList", aList);
			map.put("pv", this.pdto);

		}
		return map;
	} // end listMethod()

	@RequestMapping(value = "/board/write", method = RequestMethod.GET)
	public ModelAndView writeMethod(BoardDTO dto, PageDTO pv, ModelAndView mav) {
		// 답변글이면
		if (dto.getRef() != 0) {
			mav.addObject("currentPage", pv.getCurrentPage());
			mav.addObject("dto", dto);
		}
		mav.setViewName("board/write");
		return mav;
	} // end writeMethod()
	
	// RequestBody : json => 자바객체
	// ResponseBody : 자바객체 => json
	// ParhVariable : /board/list/:num => /board/list/1 => /board/list/{num}
	// RequestParam : /board/list?name=value => /board/ilst?num=1 => /board/list
	// multipart/form-date : RequestBody 선언 없이 그냥 받음 BoardDTO dto

	@RequestMapping(value = "/board/write", method = RequestMethod.POST)
	public String writeProMethod(BoardDTO dto, PageDTO pv, HttpServletRequest request) throws IllegalStateException, IOException {
		MultipartFile file = dto.getFilename();
		if (file != null && !file.isEmpty()) {
			UUID random = saveCopyFile(file, request);
			dto.setUpload(random + "_" + file.getOriginalFilename());
			// c:\\download\\temp 경로에 첨부파일 저장
			file.transferTo(new File(file.getOriginalFilename()));
		}
		dto.setIp(request.getRemoteAddr()); // 클라이언트 ip주소 가져오기

		service.insertProcess(dto);

		// 답변글이면
		if (dto.getRef() != 0) {
			return "redirect:/board/list/" + pv.getCurrentPage();
		} else {
			// 제목글이면
			return "redirect:/board/list/1";
		}

	} // end writeProMethod()

	@RequestMapping(value = "/board/update/{num}", method = RequestMethod.GET)
	public BoardDTO updateMethod(int num) {
		return service.updateSelectProcess(num);
	} // end updateMethod()

	@RequestMapping(value = "/board/update", method = RequestMethod.PUT)
	public void updateProMethod(@RequestBody BoardDTO dto, HttpServletRequest request) {
		System.out.printf("num:%d, writer:%s\n", dto.getNum(), dto.getWriter());
		MultipartFile file = dto.getFilename();
		if (file != null && !file.isEmpty()) {
			UUID random = saveCopyFile(file, request);
			dto.setUpload(random + "_" + file.getOriginalFilename());
		}
		service.updateProcess(dto, urlPath(request));
	} // end updateProMethod()

	@RequestMapping(value = "/board/delete/{num}", method = RequestMethod.DELETE)
	public void deleteMehtod(@PathVariable("num") int num, HttpServletRequest request) {
		service.deleteProcess(num, urlPath(request));

		int totalRecord = service.countProcess();
		this.pdto = new PageDTO(this.currentPage, totalRecord);

	};

	private UUID saveCopyFile(MultipartFile file, HttpServletRequest request) {
		String fileName = file.getOriginalFilename();
		// 중복파일명을 처리하기 위해 난수 발생
		UUID random = UUID.randomUUID();

		File fe = new File(urlPath(request));
		if (!fe.exists()) {
			fe.mkdir();
		}

		File ff = new File(urlPath(request), random + "_" + fileName);

		try {
			FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(ff));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return random;
	} // end saveCopyFile()

	private String urlPath(HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("/");
		// D:\bic_study\spring_workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\spring_08_board
		System.out.println("root:" + root);
		String saveDirectory = root + "temp" + File.separator;
		return saveDirectory;
	} // end urlPath()

	@RequestMapping("/board/view/{num}")
	public BoardDTO viewMethod(@PathVariable("num") int num) {
		return service.contentProcess(num);
	} // end viewMethod()

	@RequestMapping("/board/contentdownload")
	public ModelAndView downMethod(int num, ModelAndView mav) {
		mav.addObject("num", num);
		mav.setViewName("download");
		return mav;
	} // end downMethod()

} // end class
