package com.shc.alumni.springboot.service;

import com.shc.alumni.springboot.entity.FunFact;
import com.shc.alumni.springboot.repository.FunFactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FunFactService {

    @Autowired
    private FunFactRepository funFactRepository;

    private static final List<String> FUN_FACTS = List.of(
            "Bananas are berries, but strawberries aren't.",
            "Octopuses have three hearts.",
            "The Eiffel Tower can be 15 cm taller during the summer.",
            "Honey never spoils. Archaeologists found 3,000-year-old honey in ancient tombs!",
            "Honey never spoils and has an infinite shelf life.",
            "The majority of your brain is fat."
        );

        public String getFunFactForToday() {
            // Use the current date to pick a fun fact
            int index = LocalDate.now().getDayOfYear() % FUN_FACTS.size();
            return FUN_FACTS.get(index);
        }
}
