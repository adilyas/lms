package DAO;

import Database.Database;
import Objects.AVMaterial;
import Objects.Book;
import Objects.Document;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class AVMaterialDAO {

    static private Database database;

    public static void setDatabase(Database database) {
        AVMaterialDAO.database = database;
    }

    static void insert(AVMaterial avMaterial) throws SQLException {
        DocumentDAO.insert(avMaterial);

        String query = "INSERT INTO av_materials (document_id) VALUES (?);";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, avMaterial.getId());
        st.executeUpdate();

    }

    static void delete(AVMaterial avMaterial) throws SQLException {
        String query = "DELETE FROM av_materials WHERE document_id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, avMaterial.getId());
        st.executeUpdate();


        DocumentDAO.delete(avMaterial);
    }

    static AVMaterial get(int id) throws SQLException {
        Document document = DocumentDAO.get(id);

        String query = "SELECT 1 FROM av_materials WHERE document_id = ?";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if (rs.next())
            return (AVMaterial) document;
        else
            throw new NoSuchElementException();
    }

    static void update(AVMaterial avMaterial) throws SQLException {
        DocumentDAO.update(avMaterial);
    }
}
