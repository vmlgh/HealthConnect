package com.healthconnect.platform.webapp.service.appointment;

import java.util.List;

import javax.validation.Valid;

import com.healthconnect.platform.dto.appointment.AppointmentDto;

public interface AppointmentService {
	
	AppointmentDto createAppointment(@Valid AppointmentDto appointmentDto);
	
	AppointmentDto addPaymentAndVideoChannel(String paymentUrl, String videoChannel, long recordId);
	
	List<AppointmentDto> getAppointments();

}
