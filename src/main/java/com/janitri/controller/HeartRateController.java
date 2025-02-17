package com.janitri.controller;

import com.janitri.model.HeartRate;
import com.janitri.service.HeartRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/heart-rates")
public class HeartRateController {
    @Autowired
    private HeartRateService heartRateService;

    @PostMapping
    public HeartRate recordHeartRate(@RequestBody HeartRate heartRate) {
        return heartRateService.recordHeartRate(heartRate);
    }

    @GetMapping("/patient/{patientId}")
    public List<HeartRate> getHeartRatesByPatientId(@PathVariable Long patientId) {
        return heartRateService.getHeartRatesByPatientId(patientId);
    }
}