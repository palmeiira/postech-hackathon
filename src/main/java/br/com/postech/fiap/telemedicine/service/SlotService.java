package br.com.postech.fiap.telemedicine.service;

import br.com.postech.fiap.telemedicine.entities.Patient;
import br.com.postech.fiap.telemedicine.entities.Slot;
import br.com.postech.fiap.telemedicine.exceptions.HandledException;
import br.com.postech.fiap.telemedicine.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SlotService {

    @Autowired
    private SlotRepository slotRepository;

    public Slot findById(Long id) {
        Optional<Slot> opt = slotRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new HandledException("Couldn't find Slot with ID = " + id);
    }

    public void save(Slot s) {
        slotRepository.save(s);
    }
}
