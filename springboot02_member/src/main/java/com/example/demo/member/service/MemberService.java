package com.example.demo.member.service;

import java.util.List;

import com.example.demo.member.vo.MemberVO;

public interface MemberService {
  public List<MemberVO> listMembers() throws Exception;
  public int addMember(MemberVO memberVO) throws Exception;
  public int removeMember(String id) throws Exception;
  public MemberVO login(MemberVO memberVO) throws Exception;
}
