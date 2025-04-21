package com.example.patientservice.mapper;


import com.example.patientservice.dto.PatientRequestDTO;
import com.example.patientservice.dto.PatientResponseDTO;
import com.example.patientservice.model.Patient;

import java.time.LocalDate;
import java.util.Date;

public class PatientMapper {

    public static PatientResponseDTO patientToPatientResponseDTO(Patient patient){
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setId(patient.getId().toString());
         patientResponseDTO.setName(patient.getName());
         patientResponseDTO.setGender(patient.getGender());
         patientResponseDTO.setDateOfBirth(patient.getDateOfBirth().toString());
         patientResponseDTO.setEmail(patient.getEmail());
         patientResponseDTO.setAddress(patient.getAddress());
         patientResponseDTO.setPhone(patient.getPhone());
        return patientResponseDTO;
    }

    public static Patient patientResponseDTOToPatient(PatientRequestDTO patientResponseDTO){
        Patient patient = new Patient();
        patient.setName(patientResponseDTO.getName());
        patient.setGender(patientResponseDTO.getGender());
        patient.setAddress(String.valueOf(patientResponseDTO.getAddress()));
        patient.setPhone(String.valueOf(patientResponseDTO.getPhone()));
        patient.setEmail(String.valueOf(patientResponseDTO.getEmail()));
        patient.setDateOfBirth(LocalDate.parse(patientResponseDTO.getDateOfBirth()));
        patient.setRegisteredDate(String.valueOf(new Date()));
        return patient;
    }
}
