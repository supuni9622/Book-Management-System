
package net.booksmanagement.web;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
//import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.booksmanagement.dao.booksDAO;
import net.booksmanagement.model.Books;

// * This servlet acts as a page controller for the application, handling all
// * requests from the books database.
@WebServlet(name = "bookServlet", urlPatterns = {"/"})
public class bookServlet extends HttpServlet{
    // private static final long serialVersionUID = 1 L;
    private booksDAO booksDao;
    
    /**
     *
     */
    @Override
    public void init(){
        booksDao = new booksDAO();
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                doGet(request, response);
        }
    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/new":
                showNewForm(request, response);
                break;
            case "/insert":
                try{
                 insertBook(request, response);
                } catch(SQLException e){
                }
                break;
            case "/delete":
                try{
                 deleteBook(request, response);
                } catch(SQLException e){
                }
                break;
            case "/edit":
                try{
                 showEditForm(request, response);
                } catch(SQLException e){
                }
                break;
            case "/update":
                try{
                 updateBook(request, response);
                }catch(SQLException e){
                }
                break;
            default:
                try{
                    listBook(request, response);
                }catch(SQLException e){
                }
                break;
        }
    }
    
    private void listBook(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException, ServletException {
        List < Books > listBook = booksDao.selectAllBooks();
        request.setAttribute("listBook", listBook);
        RequestDispatcher dispatcher = request.getRequestDispatcher("book-list.jsp");
        dispatcher.forward(request, response);
    }
        
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("book-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Books existingBook = booksDao.selectBook(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("book-form.jsp");
        request.setAttribute("books", existingBook);
        dispatcher.forward(request, response);

    }
    
    private void insertBook(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException {
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String author = request.getParameter("author");
        Books books = new Books(name, price ,author);
        booksDao.insertBook(books);
        response.sendRedirect("list");
    }
    
    private void updateBook(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String author = request.getParameter("author");

        Books updatedBook = new Books(id, name, price ,author);
        booksDao.updateBook(updatedBook);
        response.sendRedirect("list");
    }
    
    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        booksDao.deleteBook(id);
        response.sendRedirect("list");

    }
}
