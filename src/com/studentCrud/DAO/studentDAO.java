package com.studentCrud.DAO;

import com.studentCrud.entities.Criteria;
import com.studentCrud.entities.OrderBy;
import com.studentCrud.entities.Student;
import com.studentCrud.entities.StudentBase;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class studentDAO implements CrudRequests<Student, StudentBase> {

    private dbConnection dbConnection = new dbConnection();

    public studentDAO(dbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Student> getAll(int page, int size) {
        List<Student> allStudent = new ArrayList<>();
        try (
                PreparedStatement statement = dbConnection.getConnection().prepareStatement("SELECT * FROM student limit ? offset ?");
        ) {

            statement.setInt(1, size);
            statement.setInt(2, size * (page - 1));//sinon ca va sauter une page
            try (
                    ResultSet rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    Student student = new Student(rs.getString("sex"), rs.getTimestamp(3), rs.getString("group_id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("reference"), rs.getString(1));

                    allStudent.add(student);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allStudent;
    }

    @Override
    public Student getById(String reference) {
        Student student = null;
        try (
                PreparedStatement statement = dbConnection.getConnection().prepareStatement("select * from student WHERE reference=?");
        ) {
            statement.setString(1, reference);
            try (
                    ResultSet rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    student = new Student(rs.getString("sex"), rs.getTimestamp(3), rs.getString("group_id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("reference"), rs.getString(1));

                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }

    @Override
    public void deleteById(String ref) {
        try (
                PreparedStatement statement = dbConnection.getConnection().prepareStatement("DELETE FROM student WHERE reference=?");
        ) {
            statement.setString(1, ref);
            int deleted = statement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<StudentBase> updateById(String ref, StudentBase studentBase) {

        try (
                PreparedStatement statement = dbConnection.getConnection().prepareStatement("");
        ) {
            int updated = statement.executeUpdate();
            switch (updated) {
                case (1) -> Optional.of(studentBase);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Student> save(Student entity) {

        return Optional.empty();
    }


    @Override
    public Optional<List<Student>> saveAll(List<Student> students) {
        return Optional.empty();
    }

    public List<Student> filterByName(int size, int page, String firstName, String lastname) {
        List<Student> students = new ArrayList<>();
        try (
                PreparedStatement statement = dbConnection.getConnection().prepareStatement("SELECT * FROM student WHERE firstname ILIKE ? AND lastname ILIKE ? ORDER BY firstname ASC limit ? offset ?")
        ) {
            statement.setString(1, "%" + firstName + "%");
            statement.setString(2, "%" + lastname + "%");
            statement.setInt(3, size);
            statement.setInt(4, size * (page - 1));
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    students.add(new Student(rs.getString("sex"), rs.getTimestamp(3), rs.getString("group_id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("reference"), rs.getString(1)));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    public List<Student> filterByBirthDate(int page, int size, Timestamp birthdayDebut, Timestamp birthdayEnd) {
        List<Student> students = new ArrayList<>();
        try (
                PreparedStatement statement = dbConnection.getConnection().prepareStatement(" SELECT * FROM  student WHERE birthdate>= ? AND birthdate<? ORDER BY firstname ASC limit ? offset ?")
        ) {
            statement.setTimestamp(1, birthdayDebut);
            statement.setTimestamp(2, birthdayEnd);
            statement.setInt(3, size);
            statement.setInt(4, size * (page - 1));
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    students.add(new Student(rs.getString("sex"), rs.getTimestamp(3), rs.getString("group_id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("reference"), rs.getString(1)));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    public List<Student> orderByName(int page, int size) {
        List<Student> studentList = new ArrayList<>();
        try (
                PreparedStatement statement = dbConnection.getConnection().prepareStatement(" select * from student order by firstname ,lastname ASC offset ? limit ?")
        ) {
            statement.setInt(1, size * (page - 1));
            statement.setInt(2, size);
            try (
                    ResultSet rs = statement.executeQuery();
            ) {
                while (rs.next()) {
                    studentList.add(new Student(rs.getString("sex"), rs.getTimestamp(3), rs.getString("group_id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("reference"), rs.getString(1)));
                }


            }
            return studentList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Student> orderByBirthdate(int page, int size) {
        List<Student> students = new ArrayList<>();
        try (PreparedStatement statement = dbConnection.getConnection().prepareStatement("select * from student order by birthdate ASC offset ? limit ?")) {
            statement.setInt(1, size * (page - 1));
            statement.setInt(2, size);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    students.add(new Student(rs.getString("sex"), rs.getTimestamp(3), rs.getString("group_id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("reference"), rs.getString(1)));
                }
            }
            return students;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Student> findByCriteria(List<Criteria> criteriaList, int page, int size, OrderBy orderBy) {
        List<Student> students = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM student WHERE 1=1");
        List<Object> params = new ArrayList<>();

        for (Criteria criteria : criteriaList) {
            String column = criteria.getCriteriaName();
            String operator = criteria.getOperator();
            Object value = criteria.getValue();
            if (column.equalsIgnoreCase("firstname") || column.equalsIgnoreCase("lastname")) {
                query.append(" AND ").append(column).append(" LIKE ?");
                params.add("%" + value + "%");
            } else if (column.equalsIgnoreCase("birthdate") || column.equalsIgnoreCase("sex")) {
                query.append(" AND ").append(column).append(" ").append(operator).append(" ?");
                params.add(value);
            } else {
                query.append(" AND ").append(column).append(" = ?");
                params.add(value);
            }
        }
        query.append(orderBy.toString());
        query.append(" LIMIT ? OFFSET ?");
        params.add(size);
        params.add((page - 1) * size);

        try (PreparedStatement statement = dbConnection.getConnection().prepareStatement(query.toString())) {

            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }

            try(
            ResultSet rs = statement.executeQuery();
            ){

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getString("student_id"));
                student.setFirstname(rs.getString("firstname"));
                student.setLastname(rs.getString("lastname"));
                student.setBirthdate(rs.getTimestamp("birthdate"));
                student.setSex(rs.getString("sex"));
                student.setReference(rs.getString("reference"));
                student.setGroup(rs.getString("group_id"));
                students.add(student);
            }

            }
            return students;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


   /* public static void main(String[] args) {
        studentDAO studentDAO = new studentDAO();
        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(new Criteria("lastname", "de", "LIKE"));
        criteriaList.add(new Criteria("ORDER", "DESC", "BY"));
        //criteriaList.add(new Criteria("birthdate",  Timestamp.valueOf("1990-01-01 00:00:00"),"="));
        System.out.println(studentDAO.findByCriteria(criteriaList, 1, 5));
        //System.out.println(studentDAO.filterByBirthDate(1,3,Timestamp.valueOf("2005-01-01 00:00:00"),Timestamp.valueOf(LocalDateTime.now())));
        // System.out.println(studentDAO.filterByName(1,1,"t","e"));
    }*/

}
