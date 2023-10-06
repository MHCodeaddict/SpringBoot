package ca.sheridancollege.hussamos.repositories;

import java.util.List;

import ca.sheridancollege.hussamos.beans.PasswordRecord;

public interface PasswordRecordList {
	public List<PasswordRecord> getAllRecords();
	public void addRecord(PasswordRecord record);
	public PasswordRecord getRecord(Long searchId);
}
