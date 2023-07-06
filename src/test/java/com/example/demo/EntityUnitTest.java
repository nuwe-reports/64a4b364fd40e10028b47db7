package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	@Autowired
	private TestEntityManager entityManager;

	private Doctor d1;

	private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;

    /** TODO
     * Implement tests for each Entity class: Doctor, Patient, Room and Appointment.
     * Make sure you are as exhaustive as possible. Coverage is checked ;)
     */

    @Test
    public void testSaveDoctor() {
        Doctor doctor = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        Doctor savedDoctorData = this.entityManager.persistAndFlush(doctor);

        Assert.assertNotNull(savedDoctorData.getId());
        assertThat(savedDoctorData.getFirstName()).isEqualTo("Perla");
        assertThat(savedDoctorData.getLastName()).isEqualTo("Amalia");
        assertThat(savedDoctorData.getAge()).isEqualTo(24);
        assertThat(savedDoctorData.getEmail()).isEqualTo("p.amalia@hospital.accwe");

    }

    @Test
    public void testDeleteDoctor() throws Exception{
        Doctor doctor = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");

        Doctor doctorData = this.entityManager.persistAndFlush(doctor);

        this.entityManager.remove( this.entityManager.find( Doctor.class, doctorData.getId() ) );

        doctorData = this.entityManager.find(Doctor.class, doctorData.getId());

        Optional<Doctor> opt = Optional.ofNullable(doctorData);
        assertThat(opt).isEmpty();

    }

    @Test
    public void testFindDoctor() throws Exception{
        Doctor doctor = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");

        Doctor SavedDoctorData = this.entityManager.persistAndFlush(doctor);


        Doctor foundDoctorData = this.entityManager.find(Doctor.class, SavedDoctorData.getId());

        Optional<Doctor> opt = Optional.ofNullable(foundDoctorData);
        assertThat(opt).contains(SavedDoctorData);

    }

    @Test
    public void testSavePatient() throws Exception{
        Patient patient = new Patient("Cornelio","Andrea", 59, "c.andrea@hospital.accwe");

        Patient savedPatientData = this.entityManager.persistAndFlush(patient);
        assertThat(savedPatientData.getFirstName()).isEqualTo("Cornelio");
        assertThat(savedPatientData.getLastName()).isEqualTo("Andrea");
        assertThat(savedPatientData.getAge()).isEqualTo(59);
        assertThat(savedPatientData.getEmail()).isEqualTo("c.andrea@hospital.accwe");
    }

    @Test
    public void testDeletePatient() throws Exception{
        Patient patient = new Patient ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");

        Patient patientData = this.entityManager.persistAndFlush(patient);

        this.entityManager.remove( this.entityManager.find( Patient.class, patientData.getId() ) );

        patientData = this.entityManager.find(Patient.class, patientData.getId());

        Optional<Patient> opt = Optional.ofNullable(patientData);
        assertThat(opt).isEmpty();

    }

    @Test
    public void testFindPatient() throws Exception{
        Patient patient = new Patient ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");

        Patient SavedPatientData = this.entityManager.persistAndFlush(patient);

        Patient foundPatientData = this.entityManager.find(Patient.class, SavedPatientData.getId());

        Optional<Patient> opt = Optional.ofNullable(foundPatientData);
        assertThat(opt).contains(SavedPatientData);

    }

    @Test
    public void testSaveRoom() {
        Room room = new Room("Operations");
        Room savedRoomData = this.entityManager.persistAndFlush(room);
        assertThat(savedRoomData.getRoomName()).isEqualTo("Operations");
    }

    @Test
    public void testDeleteRoom() throws Exception{
        Room room = new Room ("Operations");

        Room roomData = this.entityManager.persistAndFlush(room);

        this.entityManager.remove( this.entityManager.find( Room.class, roomData.getRoomName() ) );

        roomData = this.entityManager.find(Room.class, roomData.getRoomName());

        Optional<Room> opt = Optional.ofNullable(roomData);
        assertThat(opt).isEmpty();

    }

    @Test
    public void testFindRoom() throws Exception{
        Room room = new Room ("Operations");

        Room SavedRoomData = this.entityManager.persistAndFlush(room);

        Room foundRoomData = this.entityManager.find(Room.class, SavedRoomData.getRoomName());

        Optional<Room> opt = Optional.ofNullable(foundRoomData);
        assertThat(opt).contains(SavedRoomData);

    }

    @Test
    public void testSaveAppointment() {
        Patient patient = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        Doctor doctor = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        Room room = new Room("Dermatology");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        LocalDateTime startsAt= LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:30 24/04/2023", formatter);

        Appointment appointment = new Appointment(patient, doctor, room, startsAt, finishesAt);

        Appointment savedAppointmentData = this.entityManager.persistAndFlush(appointment);
        assertThat(savedAppointmentData.getPatient().getFirstName()).isEqualTo("Jose Luis");
        assertThat(savedAppointmentData.getPatient().getLastName()).isEqualTo("Olaya");
        assertThat(savedAppointmentData.getPatient().getAge()).isEqualTo(37);
        assertThat(savedAppointmentData.getPatient().getEmail()).isEqualTo("j.olaya@email.com");
        assertThat(savedAppointmentData.getDoctor().getFirstName()).isEqualTo("Perla");
        assertThat(savedAppointmentData.getDoctor().getLastName()).isEqualTo("Amalia");
        assertThat(savedAppointmentData.getDoctor().getAge()).isEqualTo(24);
        assertThat(savedAppointmentData.getDoctor().getEmail()).isEqualTo("p.amalia@hospital.accwe");
        assertThat(savedAppointmentData.getRoom().getRoomName()).isEqualTo("Dermatology");
        assertThat(savedAppointmentData.getStartsAt()).isEqualTo(LocalDateTime.parse("19:30 24/04/2023", formatter));
        assertThat(savedAppointmentData.getFinishesAt()).isEqualTo(LocalDateTime.parse("20:30 24/04/2023", formatter));

    }

    @Test
    public void testDeleteAppointment() throws Exception{
        Patient patient = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        Doctor doctor = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        Room room = new Room("Dermatology");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        LocalDateTime startsAt= LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:30 24/04/2023", formatter);

        Appointment appointment = new Appointment(patient, doctor, room, startsAt, finishesAt);

        Appointment appointmentData = this.entityManager.persistAndFlush(appointment);

        this.entityManager.remove( this.entityManager.find( Appointment.class, appointmentData.getId() ) );

        appointmentData = this.entityManager.find(Appointment.class, appointmentData.getId());

        Optional<Appointment> opt = Optional.ofNullable(appointmentData);
        assertThat(opt).isEmpty();

    }

    @Test
    public void testFindAppointment() throws Exception{
        Patient patient = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        Doctor doctor = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        Room room = new Room("Dermatology");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        LocalDateTime startsAt= LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:30 24/04/2023", formatter);

        Appointment appointment = new Appointment(patient, doctor, room, startsAt, finishesAt);

        Appointment SavedAppointmentData = this.entityManager.persistAndFlush(appointment);


        Appointment foundAppointmentData = this.entityManager.find(Appointment.class, SavedAppointmentData.getId());

        Optional<Appointment> opt = Optional.ofNullable(foundAppointmentData);
        assertThat(opt).contains(SavedAppointmentData);

    }
}
