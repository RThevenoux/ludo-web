package io.ludoweb.core.user.member.email;

import java.util.List;
import java.util.stream.Collectors;

import io.ludoweb.core.user.member.MemberView;
import lombok.Data;

@Data
public class EmailResult {

	List<String> emails;
	
	List<MemberView> invalidEmailMembers;
	
	List<MemberView> noEmailMembers;
	
	public String joinValidEmail() {
		if(emails==null) {
			return "";
		}
		
		return emails.stream().collect(Collectors.joining(" ; "));
	}
}
