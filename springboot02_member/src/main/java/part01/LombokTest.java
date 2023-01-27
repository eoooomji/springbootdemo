package part01;

public class LombokTest {

	public static void main(String[] args) {
		MemberVO mv = new MemberVO();
		mv.setName("홍길동");
		mv.setAge(30);
		mv.setLoc("서울");

		System.out.printf("%s %d %s\n", mv.getName(), mv.getAge(), mv.getLoc());
		// part01.MemberVO@28f67ac7
		// MemberVO(name=홍길동, age=30, loc=서울)
		System.out.println(mv.toString());

		mv = new MemberVO("여진구", 28, "경기");

		System.out.printf("%s %d %s\n", mv.getName(), mv.getAge(), mv.getLoc());
		// part01.MemberVO@256216b3
		// MemberVO(name=여진구, age=28, loc=경기)
		System.out.println(mv.toString());

		MemberVO vo1 = new MemberVO("여진구", 28, "경기");
		MemberVO vo2 = new MemberVO("여진구", 28, "경기");
		MemberVO vo3 = new MemberVO("여진구", 28, "제주");
		// false
		// true
		System.out.println(vo1.equals(vo2));
		// false
		System.out.println(vo1.equals(vo3));
		// 1918627686
		// -1323333202
		System.out.println(vo1.hashCode());
		// 716143810
		// -1323333202
		System.out.println(vo2.hashCode());
		// -1323109125
		System.out.println(vo3.hashCode());

		//mv = new MemberVO(null, 0, null);
		//System.out.printf("%s %d %s\n", mv.getName(), mv.getAge(), mv.getLoc());
		
		mv = new MemberVO("차현우","광주");
		System.out.printf("%s %d %s\n", mv.getName(), mv.getAge(), mv.getLoc());
		
		
	}

}
