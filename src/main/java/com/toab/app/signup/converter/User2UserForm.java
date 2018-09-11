package com.toab.app.signup.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.toab.app.signup.entity.User;
import com.toab.app.signup.model.EmailID;
import com.toab.app.signup.model.PhoneNumber;
import com.toab.app.signup.model.UserVO;

@Component
public class User2UserForm implements Converter<User, UserVO> {
	
	@Override
	public UserVO convert(User user) {
		UserVO vo = new UserVO();
		vo.setId(user.getId());
		vo.setEmail(parseEmail(user.getEmailId().toString()));
		vo.setFirstName(user.getFirstName());
		vo.setLastName(user.getLastName());
		vo.setPhone(parsePhone(user.getPhoneNumber().toString()));
		vo.setPassword(user.getSecretHash());
		return vo;
	}

	private EmailID parseEmail(String email) {
		EmailID eid = new EmailID();
		eid.setName(email.split("@")[0]);
		eid.setDomain(email.split("@")[1]);
		return eid;
	}
	
	private PhoneNumber parsePhone(String phone) {
		int endIndex = phone.indexOf("-");
		String countryCode = phone.substring(0, endIndex);
		PhoneNumber pno = new PhoneNumber();
		pno.setCountryCode(countryCode);
		pno.setNumber(phone.substring(endIndex + 1));
		return pno;
	}
	
}
