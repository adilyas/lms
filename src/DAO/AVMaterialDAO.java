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

    static private Database db;

    static void setDb(Database dbb) {
        db = dbb;
    }

    static void insert(AVMaterial avMaterial) throws SQLException {
        DocumentDAO.insert(avMaterial);

        String query = "INSERT INTO av_materials (document_id) VALUES (?);";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, avMaterial.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static void delete(AVMaterial avMaterial) throws SQLException {
        DocumentDAO.delete(avMaterial);

        String query = "DELETE FROM av_materials WHERE document_id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, avMaterial.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static Book get(int id) throws SQLException {
        Document document = DocumentDAO.get(id);

        String query = "SELECT 1 FROM av_materials WHERE document_id = ?";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if (rs.next())
            return (Book) document;
        else
            throw new NoSuchElementException();
    }

    static void update(AVMaterial avMaterial) throws SQLException {
        DocumentDAO.update(avMaterial);
    }
}
