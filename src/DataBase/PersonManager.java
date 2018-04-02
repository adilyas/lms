package DataBase;

import Service.Author;
import Service.Document;
import Service.Person;

import javax.print.Doc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PersonManager extends Database {
    DocumentManager dm;

    public void addPerson(Person person) throws Exception {
        String createQuery = "insert into persons (name, surname) values(?, ?);";
        PreparedStatement st = connection.prepareStatement(createQuery);
        st.setString(1, person.getName());
        st.setString(2, person.getSurname());
        st.executeUpdate();
        connection.commit();

        int personId = getLastPersonId();


        if (person instanceof Author) {
            String addAuthorQuery = "insert into authors values(?);";
            PreparedStatement stAddAuthor =
                    connection.prepareStatement(addAuthorQuery);
            stAddAuthor.setInt(1, personId);
            stAddAuthor.executeUpdate();
            connection.commit();
        }
    }

    public Person find(Person person) throws Exception {
        if (person instanceof Author) {
            String findQuery = "select p.id, p.name, p.surname " +
                    "from authors as a inner join persons as p " +
                    "where a.person_id = p.id AND p.name = ? " +
                    "AND p.surname = ?";
            PreparedStatement st = connection.prepareStatement(findQuery);
            final Author person1 = (Author) person;
            st.setString(1, person1.getName());
            st.setString(2, person1.getSurname());

            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }

            Author resAuthor = new Author(rs.getInt("p.id"),
                    rs.getString("p.name"),
                    rs.getString("p.surname"));

            return resAuthor;
        }

        return null;
    }

    private int getLastPersonId() throws Exception {
        String query = "select max(id) from persons;";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);

        rs.next();

        return rs.getInt("max(id)");
    }
}
