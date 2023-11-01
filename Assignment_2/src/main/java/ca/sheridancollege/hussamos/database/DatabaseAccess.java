package ca.sheridancollege.hussamos.database;
/*
 * By: Mostafa Hussain
 * Student ID: 991332466
 */
import java.util.List;

import ca.sheridancollege.hussamos.beans.PasswordRecord;
//This interface in part of CRUD operation
public interface DatabaseAccess {
	public void insertRecord(PasswordRecord record);
	public List<PasswordRecord> getRecordList();
	public void deleteRecordById(Long Id);
	public List<PasswordRecord> getRecordListById(Long Id);
}
