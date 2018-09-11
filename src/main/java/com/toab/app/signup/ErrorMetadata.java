package com.toab.app.signup;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public interface ErrorMetadata {
	
	static final Comparator<ErrorMetadata> CODE_CMP = new Comparator<ErrorMetadata>() {
		
		@Override
		public int compare(ErrorMetadata o1, ErrorMetadata o2) {
			// TODO Auto-generated method stub
			return o1.getCode().compareTo(o2.getCode());
		}
	};
	
	static final Comparator<ErrorMetadata> KEY_CMP = new Comparator<ErrorMetadata>() {
		
		@Override
		public int compare(ErrorMetadata o1, ErrorMetadata o2) {
			// TODO Auto-generated method stub
			return o1.getKey().compareTo(o2.getKey());
		}
	};
	
	public abstract Integer getStatus();
	
	public abstract Integer getCode();
	
	public abstract String getKey();
	
	public abstract List<? extends ErrorMetadata> getValues();
	
	/*default String render() {
		return " [Error code: " + getCode() + "]";
	}*/
	
	default ErrorMetadata getByCode(Integer item) throws ClassNotFoundException {
		List<? extends ErrorMetadata> copy = getValues();
		Collections.sort(copy, CODE_CMP);
		int high = copy.size() - 1;
		int low = 0;
		while(low <= high) {
			int mid = (low + high) / 2;
			ErrorMetadata em = copy.get(mid);
			if(em.getCode().equals(item)) {
				return em;
			} else if(em.getCode() < item) {
				low = mid + 1;
			} else if(em.getCode() > item) {
				high = mid - 1;
			}
		}
		return null;
	}
	
	
	default ErrorMetadata getByKey(String item) throws ClassNotFoundException {
		List<? extends ErrorMetadata> copy = getValues();
		Collections.sort(copy, KEY_CMP);
		int high = copy.size() - 1;
		int low = 0;
		while(low <= high) {
			int mid = (low + high) / 2;
			ErrorMetadata em = copy.get(mid);
			if(em.toString().compareTo(item) == 0) {
				return em;
			} else if(em.toString().compareTo(item) < 0) {
				low = mid + 1;
			} else if(em.toString().compareTo(item) > 0) {
				high = mid - 1;
			}
		}
		return null;
	}

}
