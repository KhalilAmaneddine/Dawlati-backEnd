package com.example.Dawlati.Services;

import com.example.Dawlati.models.Form;
import com.example.Dawlati.repositories.FormRepository;
import com.example.Dawlati.services.FormService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FormServiceTest {
    @Mock
    private FormRepository formRepository;
    @InjectMocks
    private FormService formService;

    @Test
    void findByIdShouldInvokeFindById() {
        Form form = new Form(1,"Civil Extract");
        when(formRepository.findById(form.getId()))
                .thenReturn(Optional.of(form));
        Form expected = formService.findById(form.getId());
        Assertions.assertEquals(1, expected.getId());
        verify(formRepository).findById(form.getId());
    }
}
