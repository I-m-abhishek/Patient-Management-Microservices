package com.example.patientservice.services;


import billing.BillingResponse;
import com.example.patientservice.dto.PatientRequestDTO;
import com.example.patientservice.grpc.BillingServiceGrpcClient;
import com.example.patientservice.kafka.KafkaProducer;
import com.example.patientservice.repository.PatientRepository;
import com.example.patientservice.dto.PatientResponseDTO;
import com.example.patientservice.exception.EmailAlreadyExists;
import com.example.patientservice.exception.PatientNotFoundException;
import com.example.patientservice.mapper.PatientMapper;
import com.example.patientservice.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;
    public PatientService(PatientRepository patientRepository ,
                          BillingServiceGrpcClient billingServiceGrpcClient , KafkaProducer kafkaProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientResponseDTOs = patients.stream().map(patient->
            PatientMapper.patientToPatientResponseDTO(patient)).toList();

        return patientResponseDTOs;
    }

    public PatientResponseDTO addPatient(PatientRequestDTO patient){

        if(patientRepository.existsByEmail(patient.getEmail())){
            throw new EmailAlreadyExists("The patient with the same email already exists");
        }
        Patient newPatient = patientRepository.save(PatientMapper.patientResponseDTOToPatient(patient));

        BillingResponse res=  billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(), newPatient.getName(), newPatient.getEmail());
        log.info("received response from billing grpc service: " +  res);
        kafkaProducer.sendEvent(newPatient);
        return PatientMapper.patientToPatientResponseDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patient){
        Patient oldPatient = patientRepository.findById(id).orElseThrow(
                ()-> new PatientNotFoundException("Patient is not found for id: " + id)
        );

        if(patientRepository.existsByEmailAndIdNot(patient.getEmail() , id)){
            throw new EmailAlreadyExists("Other patient with the same email already exists");
        }

        oldPatient.setName(patient.getName());
        oldPatient.setGender(patient.getGender());
        oldPatient.setEmail(patient.getEmail());
        oldPatient.setPhone(patient.getPhone());
        oldPatient.setAddress(patient.getAddress());
        oldPatient.setDateOfBirth(LocalDate.parse(patient.getDateOfBirth()));
        Patient updatedPatient = patientRepository.save(oldPatient);
        return PatientMapper.patientToPatientResponseDTO(updatedPatient);

    }

    public void deletePatient(UUID id){
        Patient patient = patientRepository.findById(id).orElseThrow(
                ()-> new PatientNotFoundException("Patient is not found for id: " + id +  " to delete")
        );
        patientRepository.delete(patient);

    }

}
