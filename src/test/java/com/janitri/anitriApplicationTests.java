package com.janitri;

import com.janitri.model.User;
import com.janitri.model.Patient;
import com.janitri.model.HeartRate;
import com.janitri.service.UserService;
import com.janitri.service.PatientService;
import com.janitri.service.HeartRateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JanitriApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private HeartRateService heartRateService;

    @Test
    void contextLoads() {
        // This test ensures that the Spring application context loads successfully.
    }

    @Test
    void testUserRegistrationAndLogin() {
        // Test User Registration
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser.getId());
        assertEquals("test@example.com", registeredUser.getEmail());

        // Test User Login
        User loggedInUser = userService.loginUser("test@example.com", "password123");
        assertNotNull(loggedInUser);
        assertEquals("test@example.com", loggedInUser.getEmail());

        // Test Invalid Login
        User invalidUser = userService.loginUser("test@example.com", "wrongpassword");
        assertNull(invalidUser);
    }

    @Test
    void testPatientManagement() {
        // Test Add Patient
        Patient patient = new Patient();
        patient.setName("John Doe");
        patient.setAge(30);
        patient.setGender("Male");
        Patient savedPatient = patientService.addPatient(patient);

        assertNotNull(savedPatient.getId());
        assertEquals("John Doe", savedPatient.getName());

        // Test Get Patient by ID
        Patient retrievedPatient = patientService.getPatientById(savedPatient.getId());
        assertNotNull(retrievedPatient);
        assertEquals("John Doe", retrievedPatient.getName());
    }

    @Test
    void testHeartRateRecordingAndRetrieval() {
        // Add a Patient for Heart Rate Recording
        Patient patient = new Patient();
        patient.setName("Jane Doe");
        patient.setAge(25);
        patient.setGender("Female");
        Patient savedPatient = patientService.addPatient(patient);

        // Test Record Heart Rate
        HeartRate heartRate = new HeartRate();
        heartRate.setHeartRate(72);
        heartRate.setTimestamp(LocalDateTime.now());
        heartRate.setPatient(savedPatient);
        HeartRate recordedHeartRate = heartRateService.recordHeartRate(heartRate);

        assertNotNull(recordedHeartRate.getId());
        assertEquals(72, recordedHeartRate.getHeartRate());

        // Test Retrieve Heart Rates by Patient ID
        List<HeartRate> heartRates = heartRateService.getHeartRatesByPatientId(savedPatient.getId());
        assertFalse(heartRates.isEmpty());
        assertEquals(72, heartRates.get(0).getHeartRate());
    }
}