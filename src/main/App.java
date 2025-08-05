package main;


import dao.ApplicantDAO;
import db.DBConnection;
import model.Applicant;
import util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ApplicantDAO dao = new ApplicantDAO();

        while (true) {
            System.out.println("\n--- Applicant Management System ---");
            System.out.println("1. Add Applicant");
            System.out.println("2. View Applicant");
            System.out.println("3. Update Experience Background and Resume");
            System.out.println("4. Delete Applicant");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    sc.nextLine();
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter experience background: ");
                    String expBg = sc.nextLine();

                    System.out.print("Enter resume file path: ");
                    String path = sc.nextLine();

                    try {
                        byte[] data = FileUtils.readFileToBytes(path);
                        dao.addApplicant(new Applicant(name, expBg, data));
                        System.out.println("Applicant added successfully.");
                    } catch (IOException e) {
                        System.out.println("Failed to read the resume file. Please check the path and try again.");
                    }

                    break;

                case 2:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    Applicant ap = dao.getApplicantById(id);
                    if (ap != null) {
                        System.out.println("ID: " + ap.getId());
                        System.out.println("Name: " + ap.getName());
                        System.out.println("Experience Background: " + ap.getExperienceBackground());

                        try {
                            FileUtils.writeBytesToFile(ap.getResume(), "resume_" + id + ".pdf");
                            System.out.println("Resume saved as resume_" + id + ".pdf");
                        } catch (IOException e) {
                            System.out.println("Failed to write the resume file to disk.");
                        }
                    } else {
                        System.out.println("Applicant not found.");
                    }
                    break;

                case 3:
                    sc.nextLine();
                    System.out.print("Enter ID: ");
                    int upId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("New experience background: ");
                    String newExpBg = sc.nextLine();

                    System.out.print("New resume file path (or leave blank to skip updating resume): ");
                    String newResumePath = sc.nextLine();

                    if (!newResumePath.isBlank()) {
                        try {
                            byte[] newResume = FileUtils.readFileToBytes(newResumePath);
                            dao.updateResumeAndExperience(upId, newExpBg, newResume);
                            System.out.println("Experience background and resume updated.");
                        } catch (IOException e) {
                            System.out.println("Error reading the new resume file.");
                        }
                    } else {
                        dao.updateExperienceBackground(upId, newExpBg);
                        System.out.println("Only experience background updated.");
                    }

                    break;

                case 4:
                    System.out.print("Enter ID: ");
                    int delId = sc.nextInt();
                    dao.deleteApplicant(delId);
                    System.out.println("Applicant deleted.");

                    File resumeFile = new File("resume_" + delId + ".pdf");
                    if (resumeFile.exists()) {
                        if (resumeFile.delete()) {
                            System.out.println("Deleted resume file from disk.");
                        } else {
                            System.out.println("Failed to delete resume file from disk.");
                        }
                    }

                    break;

                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
