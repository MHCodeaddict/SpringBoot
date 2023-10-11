package ca.sheridancollege.hussamos.database;

import java.util.List;

import ca.sheridancollege.hussamos.beans.PasswordRecord;

public interface DatabaseAccess {
	public void insertRecord(PasswordRecord record);
	//public void insertRecordHardCoded();
	public List<PasswordRecord> getRecordList();
	public void deleteRecordById(Long Id);
	public List<PasswordRecord> getRecordListById(Long Id);
}
