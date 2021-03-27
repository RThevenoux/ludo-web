package io.ludoweb.core.user.member;

import io.ludoweb.core.user.MyUserDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Principal expect by Spring-Security
 *
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MemberUserDetails extends MyUserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	long id;
}
