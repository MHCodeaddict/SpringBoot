package ca.sheridancollege.hussamos.repositories;
/*
 * By: Mostafa Hussain
 * Student ID: 991332466
 */
import java.util.List;

import ca.sheridancollege.hussamos.beans.PasswordRecord;
//not used in this assignment anymore
public interface PasswordRecordList {
	public List<PasswordRecord> getAllRecords();
	public void addRecord(PasswordRecord record);
	public PasswordRecord getRecord(Long searchId);
}
