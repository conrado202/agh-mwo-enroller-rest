package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;


@Component("meetingService")
public class MeetingService {

//	DatabaseConnector connector;
	Session session;
	
	
	public MeetingService() {
//		connector = DatabaseConnector.getInstance();
		session = DatabaseConnector.getInstance().getSession();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
//		Query query = connector.getSession().createQuery(hql);
		Query query = this.session.createQuery(hql);
		return query.list();
	}


	public Meeting findMeetingById(int id) {
		Meeting meeting = (Meeting) this.session.get(Meeting.class, id);
		return meeting;
	}
	
	public void addMeeting(Meeting meeting) {
		Transaction transaction = this.session.beginTransaction();
		this.session.save(meeting);
		transaction.commit();
		
	}

	public void deleteMeeting(Meeting meeting) {
		Transaction transaction = this.session.beginTransaction();
		this.session.delete(meeting);
		transaction.commit();
		
	}

	public boolean existingMeeting(Meeting meeting) {
		return true;
	}

	public void updateMeeting(Meeting meeting) {
		Transaction transaction = this.session.beginTransaction();
		this.session.merge(meeting);
		transaction.commit();
		
	}





}
