package com.app.library.controller;

import com.app.library.service.VolunteerInfoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/library")
@AllArgsConstructor
public class VolunteerInfoResource {

    @Autowired
    private VolunteerInfoService volunteerInfoService;

    /**
     * register volunter on library server
     * @param volunteerId
     * @return boolean
     */
    @PostMapping("/register/{volunteerId}")
    public ResponseEntity<Boolean> registerVolunteer(@PathVariable Long volunteerId){
        return ResponseEntity.ok(volunteerInfoService.registerVolunteer(volunteerId));
    }
}
