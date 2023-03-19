package com.app.account.controller;

import com.app.account.service.VolunteerInfoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class VolunteerInfoResource {
    @Autowired
    private VolunteerInfoService volunteerInfoService;

    /**
     * Register volunteer on account application
     * @param volunteerId
     * @return boolean
     */
    @PostMapping("/register/{volunteerId}")
    public ResponseEntity<Boolean> registerVolunteer(@PathVariable Long volunteerId){
        return ResponseEntity.ok(volunteerInfoService.registerVolunteer(volunteerId));
    }
}
