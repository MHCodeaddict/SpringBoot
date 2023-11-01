package ca.sheridancollege.hussamos.repositories;
/*
 * By: Mostafa Hussain
 * Student ID: 991332466
 */
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ca.sheridancollege.hussamos.beans.PasswordRecord;
//not used in this assignment anymore
@Component
public class PasswordRecordListImpl implements PasswordRecordList {
	private final List<PasswordRecord> passwordRecords = new ArrayList<>();
	
	@Override
	public List<PasswordRecord> getAllRecords(){
		return passwordRecords;
	}
	
	@Override
	public void addRecord(PasswordRecord record) {
		passwordRecords.add(record);
	}
	
	@Override
	public PasswordRecord getRecord( Long searchId) {
		
		for(PasswordRecord record : passwordRecords) {
			if(record.getId().equals(searchId)) {
				return record;
			}
		}
		return null;
	}

}
