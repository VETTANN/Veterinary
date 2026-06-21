package com.veterinary;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AppointmentInfoDTOTest {
    @Test
    void testMultipleAppointmentsDTO() {
        List<AppointmentInfoDTO> reportList = new ArrayList<>();

        AppointmentInfoDTO first = new AppointmentInfoDTO();
        first.setOwnerLastName("Коваленко");
        first.setPetName("Barsik");
        first.setVetFullName("Петренко І.В.");

        AppointmentInfoDTO second = new AppointmentInfoDTO();
        second.setOwnerLastName("Сидоренко");
        second.setPetName("Reks");
        second.setVetFullName("Петренко І.В.");

        reportList.add(first);
        reportList.add(second);

        assertEquals(2, reportList.size());
        assertEquals("Barsik", reportList.get(0).getPetName());
        assertEquals("Сидоренко", reportList.get(1).getOwnerLastName());
    }
}