package part01;

public class Test {

	public static void main(String[] args) {
		MemberVO mv = new MemberVO();
		mv.setName("홍길동");
		mv.setAge(30);
		mv.setLoc("서울");
		System.out.printf("%s %d %s\n", mv.getName(), mv.getAge(), mv.getLoc());
        System.out.println(mv.toString());
        
		mv= new MemberVO("여진구", 25, "경기");
		System.out.printf("%s %d %s\n", mv.getName(), mv.getAge(), mv.getLoc());
        System.out.println(mv.toString());
        
//        mv = new MemberVO("송중기",0,null);
//        System.out.printf("%s %d %s\n", mv.getName(), mv.getAge(), mv.getLoc());
//        System.out.println(mv.toString());
        
        MemberVO  vo1 = new MemberVO("홍길동","제주");
        MemberVO  vo2 = new MemberVO("홍길동","제주");
        MemberVO  vo3 = new MemberVO("홍길동","대전");
       System.out.println(vo1.equals(vo2));
       System.out.println(vo1.equals(vo3));
       System.out.println(vo1.hashCode());
       System.out.println(vo2.hashCode());
       
               
	}

}
