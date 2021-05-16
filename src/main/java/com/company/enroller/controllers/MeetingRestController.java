package com.company.enroller.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController {

	 @Autowired
	    MeetingService meetingService;
	 
	 @Autowired
	    ParticipantService participantService;

	    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	    public ResponseEntity<?> getMeeting(@PathVariable("id") int id) {
	        Meeting meeting = meetingService.findMeetingById(id);
	        return new ResponseEntity<>(meeting, HttpStatus.OK);
	    }

	    @RequestMapping(value = "", method = RequestMethod.POST)
	    public ResponseEntity<?> addMeeting(@RequestBody Meeting meeting) {
	        if (meetingService.existingMeeting(meeting)) {
	            return new ResponseEntity<>("Unable to add a meeting because meeting with title " + meeting.getTitle() + " already exist.", HttpStatus.CONFLICT);
	        }
	        meetingService.addMeeting(meeting);
	        return new ResponseEntity<>(meeting, HttpStatus.CREATED);
	    }
	    
	    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	    public ResponseEntity<?> deleteMeeting(@PathVariable("id") int id) {
	        Meeting meeting = meetingService.findMeetingById(id);
	        if (meeting == null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        meetingService.deleteMeeting(meeting);
	        return new ResponseEntity<>(meeting, HttpStatus.NO_CONTENT);
	    }


	    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	    public ResponseEntity<?> updateMeeting(@PathVariable("id") int id, @RequestBody Meeting meeting) {
	        Meeting currentMeeting = meetingService.findMeetingById(id);
	        meeting.setId(currentMeeting.getId());
	        meetingService.updateMeeting(meeting);
	        if (currentMeeting == null) {
	            return new ResponseEntity<String>("Meeting with id " + meeting.getId() + " does not exist. Cannot update the requested meeting.", HttpStatus.NOT_FOUND);
	        }
	        else {
	            return new ResponseEntity<>(HttpStatus.OK);
	        }
	    }
	    
	    @RequestMapping(value = "{id}/participants", method = RequestMethod.GET)
	    public ResponseEntity<?> getParticipantsFromMeeting(@PathVariable("id") int id) {
	        Meeting meeting = meetingService.findMeetingById(id);
	        return new ResponseEntity<>(meeting.getParticipants(), HttpStatus.OK);
	    }
	    
	    @RequestMapping(value = "{id}/participants", method = RequestMethod.POST)
	    public ResponseEntity<?> addParticipant(@PathVariable("id") int id, @RequestBody Map<String, String> json) {

	        Meeting currentMeeting = meetingService.findMeetingById(id);
	        String login = json.get("login");
	        if (login == null) {
	            return new ResponseEntity<>("Not possible to find participant", HttpStatus.NOT_ACCEPTABLE);
	        }
	        Participant participantToAdd = participantService.findByLogin(login);
	        currentMeeting.addParticipant(participantToAdd);
	        meetingService.updateMeeting(currentMeeting);
	        return new ResponseEntity<>(currentMeeting.getParticipants(), HttpStatus.OK);
	    }

	    @RequestMapping(value = "{id}/participants/{login}", method = RequestMethod.DELETE)
	    public ResponseEntity<?> removeParticipant(@PathVariable("id") int id, @PathVariable("login") String login) {
	        Meeting meeting = meetingService.findMeetingById(id);
	        Participant participant = participantService.findByLogin(login);
	        meeting.removeParticipant(participant);
	        meetingService.updateMeeting(meeting);
	        if (participant == null) {
	            return new ResponseEntity(HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<>(meeting.getParticipants(), HttpStatus.OK);
	    }

}
