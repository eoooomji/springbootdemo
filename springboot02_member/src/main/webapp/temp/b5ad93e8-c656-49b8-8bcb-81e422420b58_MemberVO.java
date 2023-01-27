package part01;

//https://zi-c.tistory.com/entry/JAVA-Lombok-%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98-Data
//https://freehoon.tistory.com/90
// https://the-dev.tistory.com/27
// java -jar lombok.jar


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.NonFinal;

@RequiredArgsConstructor  //final이나 @NonNull인 필드 값만 파라미터로 받는 생성자
@NoArgsConstructor  // 파라미터가 없는 기본 생성자를 생성
@AllArgsConstructor //모든 필드 값을 파라미터로 받는 생성자를 생성

@EqualsAndHashCode
@ToString   
@Setter
@Getter
//@Data
public class MemberVO {
	@NonNull
	private String name;

	private int age;
	@NonNull
	private String loc;

}
