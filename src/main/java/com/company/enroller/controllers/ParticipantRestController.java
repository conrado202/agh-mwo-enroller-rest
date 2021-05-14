package com.company.enroller.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.enroller.model.Participant;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/participants")
// localhost:8080/
// CRUD - Create Read Update
// localhost:8080/participants		// polaczenie z endpointem l=listy uczestnikow
// localhost:8080/meetings
// GET localhost:8080/participants/3	// pobierz z endpointa uczestnika o nr 3
// POST localhost:8080/participants/3	// jak chcemy dodac uczestnika to uzywamy POST - metoda http uzywana w przegladarce
public class ParticipantRestController {

	@Autowired
	ParticipantService participantService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipants() {
		Collection<Participant> participants = participantService.getAll();
		return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
	}
	
	// GET localhost:8080/participants/3
	// GET localhost:8080/meetings/3
	// GET localhost:8080/meetings/3/participants 	// ten endpoint powinien nam zwrocic liczbe uczestnikow
	
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMeeting(@PathVariable("id") String login) {
	    Participant participant = participantService.findByLogin(login);
	if (participant == null) { 
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	} 

	return new ResponseEntity<Participant>(participant, HttpStatus.OK); 
	}
	
	
	public ResponseEntity<?> registerParticipant(@RequestBody Participant participant) {
		Participant foundParticipant = participantService.findByLogin(participant.getLogin());
		if (foundParticipant != null) {
			return new ResponseEntity("Unable to create. A participant with login " + participant.getLogin() + " already exist.", HttpStatus.CONFLICT);

		}
		participantService.add(participant);
		return new ResponseEntity<>(participant, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)

	public ResponseEntity<?> delete(@PathVariable("id") String login) {
		Participant participant = participantService.findByLogin(login);
		if (participant == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		participantService.delete(participant);
		return new ResponseEntity<>(participant, HttpStatus.OK);
	}
	 

}
