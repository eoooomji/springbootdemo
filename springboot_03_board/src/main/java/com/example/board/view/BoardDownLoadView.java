package com.example.board.view;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.example.board.dao.BoardDAO;

// 다운로드 창을 띄우기 위한 뷰 페이지
public class BoardDownLoadView extends AbstractView {
	private BoardDAO dao;

	public BoardDownLoadView() {

	}

	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int num = Integer.parseInt(request.getParameter("num"));

		String root = request.getSession().getServletContext().getRealPath("/");
		String saveDirectory = root + "temp" + File.separator;

		String upload = dao.getFile(num);
		String fileName = upload.substring(upload.indexOf("_") + 1);

		// 파일명이 한글일 때 인코딩 작업을 한다.
		// 한글로 인코딩을 할 때 파일명에 공백이 포함되어 있다면 + 로 리턴한다.
		String str = URLEncoder.encode(fileName, "UTF-8");
		// +표시를 공백으로 처리해준다.
		str = str.replaceAll("\\+", "%20");

		// 컨텐츠 타입을 지정한다.
		// 파일명이 다르기 때문에 상관이 없이 받기 위함
		response.setContentType("application/octet-stream");

		// 다운로드 창에 보여줄 파일명을 지정한다.
		response.setHeader("Content-Disposition", "attachment;filename=" + str + ";");
		
		// 서버에 저장된 파일을 읽어와 클라이언트에 출력해준다.
		FileCopyUtils.copy(new FileInputStream(new File(saveDirectory, upload)), response.getOutputStream());
	}
}
