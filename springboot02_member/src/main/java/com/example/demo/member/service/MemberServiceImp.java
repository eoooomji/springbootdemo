package com.example.demo.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.member.dao.MemberDAO;
import com.example.demo.member.vo.MemberVO;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class MemberServiceImp  implements MemberService{
	
    @Autowired	
	private MemberDAO memberDAO;
	
	public MemberServiceImp() {

	}
	


	@Override
	public List<MemberVO> listMembers() throws Exception {		 
		return memberDAO.selectAllMemberList();
	}

	@Override
	public int addMember(MemberVO memberVO) throws Exception {		
		return memberDAO.insertMember(memberVO);
	}

	@Override
	public int removeMember(String id) throws Exception {		
		return memberDAO.deleteMember(id);
	}

	@Override
	public MemberVO login(MemberVO memberVO) throws Exception {		
		return memberDAO.loginById(memberVO);
	}
}
