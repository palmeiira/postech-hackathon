package br.com.postech.fiap.telemedicine;

import br.com.postech.fiap.telemedicine.entities.Slot;
import br.com.postech.fiap.telemedicine.exceptions.HandledException;
import br.com.postech.fiap.telemedicine.repository.SlotRepository;
import br.com.postech.fiap.telemedicine.service.SlotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class SlotServiceTests {
    @Mock
    private SlotRepository slotRepository;

    @InjectMocks
    private SlotService slotService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_found() {
        Slot s = new Slot();
        s.setId(1L);

        when(slotRepository.findById(1L))
                .thenReturn(Optional.of(s));

        Slot result = slotService.findById(1L);

        assertEquals(1L, (long) result.getId());
    }

    @Test
    void findById_not_found() {
        when(slotRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(HandledException.class, () -> slotService.findById(1L));
    }

    @Test
    void save() {
        Slot s = new Slot();
        s.setId(1l);

        when(slotRepository.save(s))
                .thenReturn(s);

        slotService.save(s);

        assertEquals(1, (long) s.getId());
    }
}
