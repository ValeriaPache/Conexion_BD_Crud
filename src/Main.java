import java.sql.*;

public class Main {
    public static void main(String[] args) {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRes = null;
        try {
            myConn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project1",
                    "root",
                    "berna2006"
            );
            System.out.println("Genial, nos conectamos");

            /*// realizamos una consulta a la base de datos
            myStmt = myConn.createStatement();
            myRes = myStmt.executeQuery("SELECT * FROM empleados");

            // iteramos los resultados para imprimir en consola.
            while (myRes.next()){
                System.out.println(myRes.getString("nombre"));
            }*/

            // Llamada a las funciones CRUD
            insertarEmpleado(myConn, "Carlos", "Gerente", 55000);
            consultarEmpleados(myConn);
            actualizarEmpleado(myConn, 1, "Carlos Perez", "Gerente General", 65000);
            eliminarEmpleado(myConn, 2);
            consultarEmpleados(myConn);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Algo salio mal :(");
        }finally {
            if (myConn != null) {
                try {
                    myConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Método para insertar un empleado
    private static void insertarEmpleado(Connection conexion, String nombre, String cargo, double salario)
            throws SQLException {
        String sql = "INSERT INTO empleados (nombre, cargo, salario) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, cargo);
            pstmt.setDouble(3, salario);
            pstmt.executeUpdate();
            System.out.println("Empleado insertado correctamente!");
        }
    }

    // Método para consultar empleados
    private static void consultarEmpleados(Connection conexion) throws SQLException {
        String sql = "SELECT * FROM empleados";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d, Nombre: %s, Cargo: %s, Salario: %.2f%n",
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("cargo"),
                        rs.getDouble("salario"));
            }
        }
    }

    // Método para actualizar un empleado
    private static void actualizarEmpleado(Connection conexion, int id, String nombre, String cargo, double salario)
            throws SQLException {
        String sql = "UPDATE empleados SET nombre = ?, cargo = ?, salario = ? WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, cargo);
            pstmt.setDouble(3, salario);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            System.out.println("Empleado actualizado correctamente!");
        }
    }

    // Método para eliminar un empleado
    private static void eliminarEmpleado(Connection conexion, int id) throws SQLException {
        String sql = "DELETE FROM empleados WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Empleado eliminado correctamente!");
        }
    }
}
