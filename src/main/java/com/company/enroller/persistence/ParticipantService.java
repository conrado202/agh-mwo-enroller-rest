package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Participant;

@Component("participantService")
public class ParticipantService {

//	private static final String session = null;
	Session session;
	DatabaseConnector connector;

	public ParticipantService() {
		connector = DatabaseConnector.getInstance();
		session = (Session) DatabaseConnector.getInstance().getSession();
	}

	public Collection<Participant> getAll() {		// pobiera wszystkich uczestnikow i zwraca ta liste
//		return connector.getSession().createCriteria(Participant.class).list();
		return session.createCriteria(Participant.class).list();
	}

	public Participant findByLogin(String login) {
		Participant participant = (Participant) connector.getSession().get(Participant.class, login);
		return participant;
	}

	public Participant add(Participant participant) {
		Transaction transaction = this.session.beginTransaction();
		session.save(participant);
		transaction.commit();
		return participant;
		
	}

	public void delete(Participant participant) {
		Transaction transaction = this.session.beginTransaction();
		session.delete(participant);
		transaction.commit();
	}

}
