package com.example.demo.backend_todolist.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * @Data 
 * - @Getter, @Setter, @ToString, @EqualAndHashCode, @RequestedArgsConstructor 를 합쳐 놓은 어노테이션이다.
 */

// @Component : 특정한 의미가 없는 클래스에 선언하는, 정의해주는 어노테이션

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@Data
public class TodoDTO {
	
	private int id;
	private int completed;
	private String todoname;
	
	
}
