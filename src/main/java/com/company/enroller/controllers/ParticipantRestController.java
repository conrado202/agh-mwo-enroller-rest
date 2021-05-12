package com.company.enroller.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
