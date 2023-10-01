package ca.sheridancollege.hussamos.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

import ca.sheridancollege.hussamos.beans.PasswordRecord;

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
/*	
	@Override
	public List<PasswordRecord> getRecord(Long id){
		for (Long rec : passwordRecords) {
			
		}
	}*/
}
