package com.app.volunteer.controller;

import com.app.volunteer.model.Volunteer;
import com.app.volunteer.repository.VolunteerRepository;
import com.app.volunteer.service.VolunteerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class VolunteerResource {

    private final Logger log = LoggerFactory.getLogger(VolunteerResource.class);

    private final VolunteerService volunteerService;

    private final VolunteerRepository volunteerRepository;

    public VolunteerResource(VolunteerService volunteerService, VolunteerRepository volunteerRepository) {
        this.volunteerService = volunteerService;
        this.volunteerRepository = volunteerRepository;
    }

    @PutMapping("/volunteers/{id}")
    public ResponseEntity<Volunteer> updateVolunteer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Volunteer volunteer
    ) throws URISyntaxException {
        log.debug("REST request to update Volunteer : {}, {}", id, volunteer);
        if (volunteer.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, volunteer.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }

        if (!volunteerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        Volunteer result = volunteerService.update(volunteer);
        return ResponseEntity
            .ok()
            .body(result);
    }


    @GetMapping("/volunteers-by-user")
    public Volunteer getVolunteers(HttpServletRequest httpServletRequest) {
        log.debug("REST request to get all Volunteers");
        return volunteerService.findByUserId(httpServletRequest);
    }


    @GetMapping("/volunteers/{id}")
    public ResponseEntity<Volunteer> getVolunteer(@PathVariable Long id) {
        log.debug("REST request to get Volunteer : {}", id);
        Optional<Volunteer> volunteer = volunteerService.findOne(id);
        return ResponseEntity.ok(volunteer.get());
    }

}
