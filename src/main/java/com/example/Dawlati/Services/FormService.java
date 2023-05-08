package com.example.Dawlati.Services;

import com.example.Dawlati.Models.Form;
import com.example.Dawlati.Repositories.FormRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FormService {

    private final FormRepository formRepository;
    public List<Form> getForms() {
        return formRepository.findAll();
    }
    public Form findById(Integer id) {
        return formRepository.findById(id)
                .orElseThrow( () -> new IllegalStateException("Form not found"));
    }
}
