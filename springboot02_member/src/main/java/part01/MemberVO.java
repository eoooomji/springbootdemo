package part01;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor  //final이나 @NonNull인 멤버변수 값만 파라미터로 받는 생성자를 생성
//@EqualsAndHashCode
//@ToString 
@NoArgsConstructor //파라미터가 없는 생성자를 생성
@AllArgsConstructor  //모든 멤버변수 값을 파라미터로 받는 생성자를 생성
//@Getter
//@Setter

@Data
public class MemberVO {
	@NonNull
	private String name;
	private int age;
	@NonNull
	private String loc;    
}




