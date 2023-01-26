package com.example.demo.backend_todolist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.backend_todolist.dto.TodoDTO;

// @Mapper : Mapper에서 경로를 안잡고 어노테이션을 줘서 자동으로 잡게 해준다.
// @Repository

@Mapper
@Repository
public interface TodoDAO {
	
	public List<TodoDTO> getTodoList() throws Exception;
	
	public int insertTodoList(TodoDTO dto) throws Exception;
	
	public int updateTodoList(TodoDTO dto) throws Exception;
	
	public int deleteTodoList(int id) throws Exception;
	
}
