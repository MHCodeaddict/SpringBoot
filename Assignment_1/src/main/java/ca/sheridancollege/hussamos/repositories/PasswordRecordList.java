package ca.sheridancollege.hussamos.repositories;

import java.util.List;

import ca.sheridancollege.hussamos.beans.PasswordRecord;

public interface PasswordRecordList {
	List<PasswordRecord> getAllRecords();
	public void addRecord(PasswordRecord record);
	//public List<PasswordRecord> getRecord(Long id);

}
