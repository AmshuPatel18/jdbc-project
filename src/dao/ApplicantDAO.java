package dao;



import db.DBConnection;
import model.Applicant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ApplicantDAO {

    public void addApplicant(Applicant a) {
        String sql = "INSERT INTO applicants(name, experience_background, resume) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getName());
            ps.setString(2, a.getExperienceBackground());
            ps.setBytes(3, a.getResume());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Applicant getApplicantById(int id) {
        String sql = "SELECT * FROM applicants WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Applicant(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("experience_background"),
                        rs.getBytes("resume")
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateExperienceBackground(int id, String newBackground) {
        String sql = "UPDATE applicants SET experience_background = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newBackground);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateResumeAndExperience(int id, String newBackground, byte[] newResume) {
        String sql = "UPDATE applicants SET experience_background = ?, resume = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newBackground);
            ps.setBytes(2, newResume);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to update experience background and resume", e);
        }
    }

    public void deleteApplicant(int id) {
        String sql = "DELETE FROM applicants WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
