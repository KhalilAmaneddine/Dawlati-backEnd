package com.example.Dawlati.services;

import com.example.Dawlati.exceptions.FormNotFoundException;
import com.example.Dawlati.models.Form;
import com.example.Dawlati.repositories.FormRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class FormService {

    private final FormRepository formRepository;
    public Form findById(Integer id) {
           return formRepository.findById(id)
                   .orElseThrow(() -> new FormNotFoundException("Form not found"));

    }
}
