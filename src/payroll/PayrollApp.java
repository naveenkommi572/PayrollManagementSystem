package payroll;

import java.util.Scanner;

public class PayrollApp {

    public static void main(String[] args) {

        Scanner sc =
                new Scanner(System.in);

        PayrollService service =
                new PayrollService();

        while (true) {

            System.out.println(
                    "\n===== PAYROLL MENU =====");

            System.out.println(
                    "1. View Employees");

            System.out.println(
                    "2. Add Employee");

            System.out.println(
                    "3. Calculate Salary");

            System.out.println(
                    "4. Generate Payslip");

            System.out.println(
                    "5. Payroll Summary");

            System.out.println(
                    "6. Batch Insert Employees");

            System.out.println(
                    "7. Exit");

            int choice =
                    sc.nextInt();

            switch (choice) {

                case 1:

                    service.viewEmployees();
                    break;

                case 2:

                    System.out.println(
                            "Enter Employee Name:");

                    String name =
                            sc.next();

                    System.out.println(
                            "Enter Department:");

                    String department =
                            sc.next();

                    System.out.println(
                            "Enter Basic Salary:");

                    double salary =
                            sc.nextDouble();

                    service.addEmployee(
                            name,
                            department,
                            salary);

                    break;

                case 3:

                    System.out.println(
                            "Enter Employee ID:");

                    int empId =
                            sc.nextInt();

                    service.calculateSalary(
                            empId);

                    break;

                case 4:

                    System.out.println(
                            "Enter Employee ID:");

                    int payslipId =
                            sc.nextInt();

                    service.generatePayslip(
                            payslipId);

                    break;

                case 5:

                    service.payrollSummary();
                    break;

                case 6:

                    service.batchInsertEmployees();
                    break;

                case 7:

                    System.exit(0);

                default:

                    System.out.println(
                            "Invalid Choice");
            }
        }
    }
}
