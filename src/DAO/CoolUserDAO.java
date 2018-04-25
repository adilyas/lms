package DAO;

import Objects.Librarian;
import Objects.Patron;
import Objects.User;

import java.sql.SQLException;
import java.util.NoSuchElementException;

public class CoolUserDAO {

    static void insert(User user) throws SQLException {
        switch (user.getType()) {
            case "student":
                PatronDAO.insert((Patron) user);
                break;
            case "instructor":
                PatronDAO.insert((Patron) user);
                break;
            case "professor":
                PatronDAO.insert((Patron) user);
                break;
            case "TA":
                PatronDAO.insert((Patron) user);
                break;
            case "VP":
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
            case "admin":
                LibrarianDAO.delete((Librarian) user);
                break;
            default:
                throw new NoSuchElementException("Wrong type");
        }
    }

    static void delete(User user) throws SQLException {
        switch (user.getType()) {
            case "student":
                PatronDAO.delete((Patron) user);
                break;
            case "instructor":
                PatronDAO.delete((Patron) user);
                break;
            case "professor":
                PatronDAO.delete((Patron) user);
                break;
            case "TA":
                PatronDAO.delete((Patron) user);
                break;
            case "VP":
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
            case "admin":
                LibrarianDAO.delete((Librarian) user);
                break;
            default:
                throw new NoSuchElementException("Wrong type");
        }
    }

    static User get(int id) throws SQLException {
        User user = UserDAO.get(id);

        switch (user.getType()) {
            case "student":
                return PatronDAO.get(id);
            case "instructor":
                return PatronDAO.get(id);
            case "professor":
                return PatronDAO.get(id);
            case "TA":
                return PatronDAO.get(id);
            case "VP":
                return PatronDAO.get(id);
            case "librarian1":
                return LibrarianDAO.get(id);
            case "librarian2":
                return LibrarianDAO.get(id);
            case "librarian3":
                return LibrarianDAO.get(id);
            case "admin":
                return LibrarianDAO.get(id);
            default:
                throw new NoSuchElementException("Wrong type");
        }
    }

    public static User getByEmail(String email) throws SQLException {
        User user = UserDAO.getByEmail(email);

        switch (user.getType()) {
            case "student":
                return PatronDAO.getByEmail(email);
            case "instructor":
                return PatronDAO.getByEmail(email);
            case "professor":
                return PatronDAO.getByEmail(email);
            case "TA":
                return PatronDAO.getByEmail(email);
            case "VP":
                return PatronDAO.getByEmail(email);
            case "librarian1":
                return LibrarianDAO.getByEmail(email);
            case "librarian2":
                return LibrarianDAO.getByEmail(email);
            case "librarian3":
                return LibrarianDAO.getByEmail(email);
            case "admin":
                return LibrarianDAO.getByEmail(email);
            default:
                throw new NoSuchElementException("Wrong type");
        }
    }

    static void update(User user) throws SQLException {
        switch (user.getType()) {
            case "student":
                PatronDAO.update((Patron) user);
                break;
            case "instructor":
                PatronDAO.update((Patron) user);
                break;
            case "professor":
                PatronDAO.update((Patron) user);
                break;
            case "TA":
                PatronDAO.update((Patron) user);
                break;
            case "VP":
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
            case "admin":
                LibrarianDAO.delete((Librarian) user);
                break;
            default:
                throw new NoSuchElementException("Wrong type");
        }
    }
}
