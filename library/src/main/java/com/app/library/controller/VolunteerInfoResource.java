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
@RequestMapping("/api/account")
@AllArgsConstructor
public class VolunteerInfoResource {

    @Autowired
    private VolunteerInfoService volunteerInfoService;

    @PostMapping("/register/{volunteerId}")
    public ResponseEntity<Boolean> registerVolunteer(@PathVariable Long volunteerId){
        return ResponseEntity.ok(volunteerInfoService.registerVolunteer(volunteerId));
    }
}
