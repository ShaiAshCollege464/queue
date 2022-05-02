import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class HospitalScene extends JPanel {

    private Queue<Patient> waiting;
    private ArrayList<DoctorRoom> doctorRoomList;
    private static final int MAX_RGB_VALUE = 255;
    private Patient lastPatient;
    private Random random;
    private static final int INITIAL_PATIENTS = 8;
    private static final int DISTANCE_BETWEEN_PATIENTS = 10;
    private static final int PATIENT_SIZE = 50;
    private static final int DOCTORS_ROOMS = 2;
    private static final int DOCTOR_ROOM_WIDTH = 225;
    private static final int DOCTOR_ROOM_HEIGHT = 125;
    private static final int DISTANCE_BETWEEN_DOCTORS_ROOMS = 50;
    private static final int MINIMAL_TREATMENT_TIME = 1000;
    private static final int MAXIMAL_ADDITIONAL_TREATMENT_TIME = 5000;

    private Patient addPatient (Patient lastPatient) {
        Color color = new Color(this.randomRgb(), this.randomRgb(), this.randomRgb());
        int xPatient;
        if (lastPatient == null) {
            xPatient = DISTANCE_BETWEEN_PATIENTS;
        } else {
            xPatient = lastPatient.getX() + DISTANCE_BETWEEN_PATIENTS + lastPatient.getWidth();
        }
        Patient patient = new Patient(
                xPatient,
                DISTANCE_BETWEEN_PATIENTS,
                PATIENT_SIZE,
                PATIENT_SIZE,
                color
        );
        this.waiting.add(patient);
        lastPatient = patient;
        this.lastPatient = lastPatient;
        return lastPatient;
    }

    public HospitalScene (int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        this.setLayout(null);
        waiting = new LinkedList<>();
        Patient lastPatient = null;
        random = new Random();
        for (int i = 0; i < INITIAL_PATIENTS; i++) {
            lastPatient = addPatient(lastPatient);
        }
        this.doctorRoomList = new ArrayList<>();
        DoctorRoom lastDoctorRoom = null;
        for (int i = 0; i < DOCTORS_ROOMS; i++) {
            int xDoctorRoom;
            if (lastDoctorRoom == null) {
                xDoctorRoom = 0;
            } else {
                xDoctorRoom = lastDoctorRoom.getX() + DISTANCE_BETWEEN_DOCTORS_ROOMS + lastDoctorRoom.getWidth();
            }
            DoctorRoom doctorRoom = new DoctorRoom(
                    xDoctorRoom,
                    height - DOCTOR_ROOM_HEIGHT,
                    DOCTOR_ROOM_WIDTH,
                    DOCTOR_ROOM_HEIGHT
            );
            this.doctorRoomList.add(doctorRoom);
            lastDoctorRoom = doctorRoom;
        }
        this.mainGameLoop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (DoctorRoom doctorRoom : this.doctorRoomList) {
            doctorRoom.paint(g);
        }
        for (Patient patient : this.waiting) {
            patient.paint(g);
        }

    }

    private void mainGameLoop () {
        new Thread(() -> {
            while (true) {
                try {
                    Patient lastPatient = null;
                    for (Patient patient : this.waiting) {
                        if (lastPatient == null) {
                            patient.moveTowards();
                            lastPatient = patient;
                        } else {
                            patient.moveTowards(lastPatient);
                            lastPatient = patient;
                        }
                    }
                    repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(() -> {
            Random random = new Random();
            while (true) {
                try {
                    for (DoctorRoom doctorRoom : doctorRoomList) {
                        Patient nextInQueue = this.waiting.peek();
                        if (nextInQueue != null) {
                            while (!nextInQueue.reachedToDoctorRoom(doctorRoom)) {
                                nextInQueue.moveTowards(doctorRoom);
                                Thread.sleep(10);
                            }
                            this.waiting.poll();
                        }
                        doctorRoom.setBusy(true);
                        repaint();
                        Thread.sleep(MINIMAL_TREATMENT_TIME + random.nextInt(MAXIMAL_ADDITIONAL_TREATMENT_TIME));
                        doctorRoom.setBusy(false);
                        repaint();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    this.lastPatient = addPatient(this.lastPatient);
                    Thread.sleep(random.nextInt((MAXIMAL_ADDITIONAL_TREATMENT_TIME + MINIMAL_TREATMENT_TIME) * 3));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private int randomRgb() {
        return this.random.nextInt(MAX_RGB_VALUE);
    }
}
