package model;

public class Applicant {
    private int id;
    private String name;
    private String experienceBackground;
    private byte[] resume;

    public Applicant(String name, String experienceBackground, byte[] resume) {
        this.name = name;
        this.experienceBackground = experienceBackground;
        this.resume = resume;
    }

    public Applicant(int id, String name, String experienceBackground, byte[] resume) {
        this.id = id;
        this.name = name;
        this.experienceBackground = experienceBackground;
        this.resume = resume;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getExperienceBackground() {
        return experienceBackground;
    }

    public byte[] getResume() {
        return resume;
    }
}
