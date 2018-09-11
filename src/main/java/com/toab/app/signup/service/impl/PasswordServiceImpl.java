package com.toab.app.signup.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.toab.app.signup.service.PasswordService;

@Component
public class PasswordServiceImpl implements PasswordService {
	
	@Value("${password.min.length}")
	private Integer passwordMinimumLength;
	@Value("${password.min.lower.case}")
	private Integer passwordMinimumLower;
	@Value("${password.min.upper.case}")
	private Integer passwordMinimumUpper;
	@Value("${password.min.special.characters}")
	private Integer passwordMinimumSpecial;
	@Value("${password.min.numbers}")
	private Integer passwordMinimumNumbers;
	@Value("#{'${password.allowed.special.characters}'.split(',')}")
	private List<Character> allowedSpecials;

	@Override
	public char[] demask(char[] masked) {
		int size = masked.length;
		String rawPassword = "";
		for(int i = 0 ; i < size ; i++) {
			int ascii = masked[i] - size;
			rawPassword = rawPassword + (char) ascii;
		}
		String rp = new String(rawPassword.getBytes());
		return rp.toCharArray();
	}

	@Override
	public Boolean isComplaint(char[] password) {
		String p = new String(password);
		if(p.length() < passwordMinimumLength) {
			return false;
		} else {
			int uc = 0, lc = 0, sc = 0, d = 0;
			for(int i = 0 ; i < p.length() ; i++) {
				Character c = p.charAt(i);
				int ascii = c;
				if(ascii >= 65 && ascii <= 90) {
					uc++;
				} else if(ascii >= 97 && ascii <= 122) {
					lc++;
				} else if(ascii >= 48 && ascii <= 57) {
					d++;
				} else if(allowedSpecials.contains(c)) {
					sc++;
				}
			}
			if(uc < passwordMinimumUpper || lc < passwordMinimumLower || d < passwordMinimumNumbers || sc < passwordMinimumSpecial) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public char[] mask(char[] raw) {
		int size = raw.length;
		String rp = "";
		for(int i = 0 ; i < size ; i++) {
			int ascii = raw[i] + size;
			rp = rp + (char) ascii;
		}
		String mp = new String(rp.getBytes());
		return mp.toCharArray();
	}

}
