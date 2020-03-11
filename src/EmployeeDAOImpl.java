import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements employeeDAO{
    //connect database
    public static String driverName = "org.sqlite.JDBC";
    public static String url ="jdbc:sqlite:D:/OOPLAB10DATABASE/companys.sqlite";
    public static Connection conn = null;
    //constant operators
    //CRUD
    public static final String GET_ALL_EMP = "select * from employee";
    public static final String ADD_EMP = "insret into Employe"+
            "(empID,name ,position,Salary)values(?,?,?,?)";
    public static final  String UPDATE_EMP = "Update Employee set"+
            " name =? ,position =?, salary =? where empID =? ";
    public static final String DELETE_EMP = "delete from Employee"+
            " where empID =?";
    public static final String FIND_EMP_ID = "select *from Employee"+
            " where empID =?";

    private static EmployeeDAOImpl instant = new EmployeeDAOImpl();
    public static EmployeeDAOImpl getInstance(){
        return instant;
    }
    //constructor


    public EmployeeDAOImpl() {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Load Driver Successfully.");
    }

    //getAllEmp

    @Override
    public List<Employee> getAllEmp() {
        List<Employee> emp =new ArrayList<Employee>();

        try {
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_EMP);
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String position = rs.getString(3);
                double salary = rs.getDouble(4);
                // add data to object
                emp.add(new Employee(id, name, position, salary));
            }
            //close connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emp;
    }

    @Override
    public void addEmp(Employee newEmp) {
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement ps =conn.prepareStatement(ADD_EMP);
            //set parameter
            ps.setInt(1,newEmp.getEmpID());
            ps.setString(2,newEmp.getName());
            ps.setString(3,newEmp.getPosition());
            ps.setDouble(4,newEmp.getSalary());

            boolean rs = ps.execute();
            if (rs == true){
                System.out.println("Could not add data to database.");
                System.exit(1);

            }
            System.out.println("Alreadty add your data to databse.");
            //close connection
            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateEmp(Employee employee) {
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(UPDATE_EMP);
            //set parameter
            ps.setString(1,employee.getName());
            ps.setString(2,employee.getPosition());
            ps.setDouble(3,employee.getSalary());
            ps.setInt(4,employee.getEmpID());
            int rs =ps.executeUpdate();
            if (rs !=0){
                System.out.println("Data with empID"+
                        employee.getEmpID()+ "was update.");
            }else{
                System.out.println("Cloud not update data with empID"
                +employee.getEmpID());
            }
            ps.close();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteEmp(int id) {
            try {
                conn = DriverManager.getConnection(url);
                PreparedStatement ps =conn.prepareStatement(DELETE_EMP);
                //set paremeter
                ps.setInt(1,id);
                int rs = ps.executeUpdate();
                if (rs !=0){
                    System.out.println("Employee with empID"
                    +id +"was deleted.");
                }else {
                    System.out.println("could not delete Employee"+
                            "with empID"+id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }


    @Override
    public Employee findEmp(int id) {
        Employee emp = null;
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(FIND_EMP_ID);
            //set parameter
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int empid = rs.getInt(1);
                String name = rs.getString(2);
                String position = rs.getString(3);
                double salary = rs.getDouble(4);
                emp = new Employee(empid, name, position, salary);

            } else {
                System.out.println();
            }
            rs.close();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emp;
    }
    }//class