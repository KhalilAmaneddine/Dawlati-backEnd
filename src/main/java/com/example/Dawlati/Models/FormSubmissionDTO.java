package com.example.Dawlati.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FormSubmissionDTO {
    private String data;
    private LocalDate date;
}
