package payroll;

import java.sql.*;

public class PayrollService {

    // View Employees

    public void viewEmployees() {

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM employees";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            System.out.println("\n===== EMPLOYEES =====");

            while (rs.next()) {

                System.out.println(
                        rs.getInt("employee_id")
                        + " | "
                        + rs.getString("employee_name")
                        + " | "
                        + rs.getString("department")
                        + " | "
                        + rs.getDouble("basic_salary"));
            }

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    // Add Employee

    public void addEmployee(
            String name,
            String department,
            double salary) {

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "INSERT INTO employees(employee_name,department,basic_salary) VALUES(?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, department);
            ps.setDouble(3, salary);

            ps.executeUpdate();

            System.out.println(
                    "Employee Added Successfully");

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    // Calculate Salary

    public void calculateSalary(int employeeId) {

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT e.basic_salary,s.hra,s.da,s.bonus "
                    + "FROM employees e "
                    + "JOIN salaries s "
                    + "ON e.employee_id=s.employee_id "
                    + "WHERE e.employee_id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, employeeId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                double basic =
                        rs.getDouble("basic_salary");

                double hra =
                        rs.getDouble("hra");

                double da =
                        rs.getDouble("da");

                double bonus =
                        rs.getDouble("bonus");

                double gross =
                        basic + hra + da + bonus;

                double tax =
                        gross * 0.10;

                double net =
                        gross - tax;

                String insertPayroll =
                        "INSERT INTO payroll(employee_id,gross_salary,tax,net_salary) VALUES(?,?,?,?)";

                PreparedStatement payPs =
                        con.prepareStatement(insertPayroll);

                payPs.setInt(1, employeeId);
                payPs.setDouble(2, gross);
                payPs.setDouble(3, tax);
                payPs.setDouble(4, net);

                payPs.executeUpdate();

                System.out.println(
                        "Payroll Generated Successfully");
            }

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    // Payslip Generation

    public void generatePayslip(int employeeId) {

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT e.employee_name,e.department,"
                    + "p.gross_salary,p.tax,p.net_salary "
                    + "FROM employees e "
                    + "JOIN payroll p "
                    + "ON e.employee_id=p.employee_id "
                    + "WHERE e.employee_id=? "
                    + "ORDER BY p.payroll_id DESC LIMIT 1";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, employeeId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                System.out.println("\n===== PAYSLIP =====");

                System.out.println(
                        "Employee : "
                        + rs.getString("employee_name"));

                System.out.println(
                        "Department : "
                        + rs.getString("department"));

                System.out.println(
                        "Gross Salary : "
                        + rs.getDouble("gross_salary"));

                System.out.println(
                        "Tax : "
                        + rs.getDouble("tax"));

                System.out.println(
                        "Net Salary : "
                        + rs.getDouble("net_salary"));
            }

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    // Aggregate Functions

    public void payrollSummary() {

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT "
                    + "SUM(net_salary) totalSalary, "
                    + "AVG(net_salary) avgSalary, "
                    + "MAX(net_salary) maxSalary "
                    + "FROM payroll";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                System.out.println("\n===== PAYROLL SUMMARY =====");

                System.out.println(
                        "Total Payroll : "
                        + rs.getDouble("totalSalary"));

                System.out.println(
                        "Average Salary : "
                        + rs.getDouble("avgSalary"));

                System.out.println(
                        "Highest Salary : "
                        + rs.getDouble("maxSalary"));
            }

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    // Batch Processing

    public void batchInsertEmployees() {

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "INSERT INTO employees(employee_name,department,basic_salary) VALUES(?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, "Ravi");
            ps.setString(2, "IT");
            ps.setDouble(3, 35000);
            ps.addBatch();

            ps.setString(1, "Priya");
            ps.setString(2, "HR");
            ps.setDouble(3, 30000);
            ps.addBatch();

            ps.setString(1, "Ajay");
            ps.setString(2, "Sales");
            ps.setDouble(3, 45000);
            ps.addBatch();

            int[] result =
                    ps.executeBatch();

            System.out.println(
                    result.length +
                    " Employees Added Successfully");

        } catch (Exception e) {

            System.out.println(e);
        }
    }
}
