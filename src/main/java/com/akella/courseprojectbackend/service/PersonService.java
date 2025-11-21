package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.ApplicationUtils;
import com.akella.courseprojectbackend.converter.PassportDetailsConverter;
import com.akella.courseprojectbackend.dto.person.*;
import com.akella.courseprojectbackend.model.Person;
import com.akella.courseprojectbackend.repository.PersonRepository;
import com.akella.courseprojectbackend.repository.view.PersonInsuranceRepository;
import com.akella.courseprojectbackend.repository.view.PersonMedicRepository;
import com.akella.courseprojectbackend.type.PassportDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonInsuranceRepository personInsuranceRepository;
    private final PersonMedicRepository personMedicRepository;

    public List<? extends PersonDto> getAllByAccidentIds(List<Long> accidentIds) {
        switch (ApplicationUtils.getRoleFromContext()) {
            case MEDIC -> {
                return personMedicRepository.findAllByAccidentIds(accidentIds);
            }
            case INSURANCE -> {
                return personInsuranceRepository.findAllByAccidentIds(accidentIds);
            }
            default -> {
                return personRepository.findAllByAccidentIds(accidentIds);
            }
        }
    }

    public void createPerson(Person person) {
        personRepository.save(person);
    }

    public void updateEmailByPassportDetails(PassportDetails passportDetails, String email) {
        PassportDetailsConverter passportDetailsConverter = new PassportDetailsConverter();
        String passportDetailsJson = passportDetailsConverter.convertToDatabaseColumn(passportDetails);
        personRepository.setEmailByPassportDetails(passportDetailsJson, email);
    }

    public List<PersonMedicDto> getAllByNameAndSurnameAndPatronymic(String name, String surname, String patronymic) {
        return personRepository.findAllByNameAndSurnameAndPatronymic(name, surname, patronymic);
    }

    public PersonBaseDto getById(Long id) {
        return personRepository.findByPersonId(id);
    }

    public Person getOneByPassportDetails(PassportDetails passportDetails) {
        return personRepository.findByPassportDetails(passportDetails);
    }
}
