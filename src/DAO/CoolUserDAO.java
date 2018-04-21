package DAO;

import Objects.Librarian;
import Objects.Patron;
import Objects.User;

import java.sql.SQLException;
import java.util.NoSuchElementException;

public class CoolUserDAO {

    static void insert(User user) throws SQLException {
        switch (user.getType()) {
            case "patron":
                PatronDAO.insert((Patron) user);
                break;
            case "librarian1":
                LibrarianDAO.insert((Librarian) user);
                break;
            case "librarian2":
                LibrarianDAO.insert((Librarian) user);
                break;
            case "librarian3":
                LibrarianDAO.insert((Librarian) user);
                break;
            default:
                throw new NoSuchElementException("Wrong type");
        }
    }

    static void delete(User user) throws SQLException {
        switch (user.getType()) {
            case "patron":
                PatronDAO.delete((Patron) user);
                break;
            case "librarian1":
                LibrarianDAO.delete((Librarian) user);
                break;
            case "librarian2":
                LibrarianDAO.delete((Librarian) user);
                break;
            case "librarian3":
                LibrarianDAO.delete((Librarian) user);
                break;
            default:
                throw new NoSuchElementException("Wrong type");
        }
    }

    static User get(int id) throws SQLException {
        User user = UserDAO.get(id);

        switch (user.getType()) {
            case "patron":
                return PatronDAO.get(id);
            case "librarian1":
                return LibrarianDAO.get(id);
            case "librarian2":
                return LibrarianDAO.get(id);
            case "librarian3":
                return LibrarianDAO.get(id);
            default:
                throw new NoSuchElementException("Wrong type");
        }
    }

    static void update(User user) throws SQLException {
        switch (user.getType()) {
            case "patron":
                PatronDAO.update((Patron) user);
                break;
            case "librarian1":
                LibrarianDAO.update((Librarian) user);
                break;
            case "librarian2":
                LibrarianDAO.update((Librarian) user);
                break;
            case "librarian3":
                LibrarianDAO.update((Librarian) user);
                break;
            default:
                throw new NoSuchElementException("Wrong type");
        }
    }
}
