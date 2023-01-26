package com.example.demo;

public class LombokTest {

	public static void main(String[] args) {
		MemDTO dto = new MemDTO();
		dto.setName("홍길동");
		dto.setAge(30);
		dto.setLoc("서울");

		System.out.printf("%s %d %s\n", dto.getName(), dto.getAge(), dto.getLoc());

		MemDTO dto2 = new MemDTO("전혜빈", 25, "대전");
		System.out.printf("%s %d %s\n", dto2.getName(), dto2.getAge(), dto2.getLoc());
	}

}
