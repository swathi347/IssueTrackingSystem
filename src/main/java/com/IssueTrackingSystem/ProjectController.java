package com.IssueTrackingSystem;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    String jdbcURL = "jdbc:mysql://localhost:3307/IssueTrackingSystem";

    @PostMapping("/addproject")
    public String registerDBAdmin(
            @RequestParam("button") String button,
            @RequestParam("ProjectName") String Name,
            @RequestParam("Description") String Description,
            @RequestParam("StartDate") String StartDate,
            @RequestParam("EndDate") String EndDate) {
        System.out.println("Admin registered");
        Connection con = null;
        try {
            con = DriverManager.getConnection(jdbcURL, "root", "root");
            String sql = "INSERT INTO project VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, Name);
            ps.setString(2, Description);
            ps.setString(3, StartDate);
            ps.setString(4, EndDate);
            ps.execute();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "user";
    }

    @PostMapping("/deleteproject")
    public String deleteProjectByName(@RequestParam("Name") String name) {
        System.out.println("Deleting project with name: " + name);
        Connection con = null;
        try {
            con = DriverManager.getConnection(jdbcURL, "root", "root");
            String sql = "DELETE FROM project WHERE Name = ?"; // Adjust column name to match your table
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            int rowsAffected = ps.executeUpdate();
            con.close();

            if (rowsAffected > 0) {
                System.out.println("Project deleted successfully.");
            } else {
                System.out.println("No project found with the given name.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "user";
    }

    @PostMapping("/updateproject")
    public String updateProject(
            @RequestParam("Name") String name,
            @RequestParam("Description") String description,
            @RequestParam("StartDate") String startDate,
            @RequestParam("EndDate") String endDate) {
        System.out.println("Updating project with name: " + name);
        Connection con = null;
        try {
            con = DriverManager.getConnection(jdbcURL, "root", "root");
            String sql = "UPDATE project SET Description = ?, StartDate = ?, EndDate = ? WHERE Name = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, description);
            ps.setString(2, startDate);
            ps.setString(3, endDate);
            ps.setString(4, name);
            int rowsAffected = ps.executeUpdate();
            con.close();

            if (rowsAffected > 0) {
                System.out.println("Project updated successfully.");
            } else {
                System.out.println("No project found with the given name.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "user";
    }

    @GetMapping("/")
    public String roles() {
        return "user";
    }

    @PostMapping("/getEmployeeDashboard")
    public String getEmployee(@RequestParam("button") String buttons) {
        System.out.println("inside getEmployeeDashboard");
        if (buttons.equals("button1")) {
            return "employeedashboard";
        }
        if (buttons.equals("button2")) {
            return "register";
        } else {
            return "error";
        }
    }

    @GetMapping("/getQADashboard")
    public String getQA(@RequestParam("button") String buttons) {
        if (buttons.equals("button1")) {
            return "qadashboard";
        }
        if (buttons.equals("button2")) {
            return "register";
        } else {
            return "error";
        }
    }

    @GetMapping("/getProjectOwnerDashboard")
    public String getProjectOwner(@RequestParam("button") String buttons) {
        if (buttons.equals("button1")) {
            return "projectownerdashboard";
        }
        if (buttons.equals("button2")) {
            return "register";
        } else {
            return "error";
        }
    }

    @GetMapping("/getRoles")
    public String login(@RequestParam("button") String button) {
        if (button.equals("button3")) {
            return "qatester login";
        } else if (button.equals("button2")) {
            return "project owner login";
        }
        return "employee_login";
    }

    @PostMapping("/getRegister")
    public String getRegi(@RequestParam("button") String button) {
        System.out.println("inside getRegister with button value: " + button);
        if (button.equals("button1")) {
            return "user";
        }
        if (button.equals("button2")) {
            return "user";
        } else {
            return "error";
        }
    }

    @PostMapping("/Projects")
    public String getProject(@RequestParam("button") String button) {
        System.out.println("inside getRegister with button value: " + button);
        if (button.equals("button1")) {
            return "createproject";
        }
        if (button.equals("button2")) {
            return "deleteproject";
        }
        if (button.equals("button3")) {
            return "updateproject";
        } else {
            return "redirect:/viewProject";
        }
    }

    @GetMapping("/viewProject")
    public String viewProject(Model model) {
        List<Map<String, Object>> prjlist = getProjectDetails();
        model.addAttribute("prjlist", prjlist);
        System.out.println("inside viewProject with button value: ");
        return "viewproject";
    }

    @ModelAttribute("prjlist")
    public List<Map<String, Object>> getProjectDetails() {
        List<Map<String, Object>> projectList = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(jdbcURL, "root", "root");
            String sql = "SELECT * FROM project";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> hm = new HashMap<>();
                hm.put("ProjectName", rs.getString("Name"));
                hm.put("Description", rs.getString("Description"));
                hm.put("StartDate", rs.getDate("StartDate"));
                hm.put("EndDate", rs.getDate("EndDate"));
                projectList.add(hm);
            }
            System.out.println("Mapdata is " + projectList);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return projectList;
    }
}
