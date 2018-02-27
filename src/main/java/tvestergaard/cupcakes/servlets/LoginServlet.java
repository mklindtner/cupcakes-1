package tvestergaard.cupcakes.servlets;

import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.cupcakes.Config;
import tvestergaard.cupcakes.Language;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.users.MysqlUserDAO;
import tvestergaard.cupcakes.database.users.User;
import tvestergaard.cupcakes.database.users.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet
{

    /**
     * The success message provided to the user when they successfully log in.
     */
    private static final String SUCCESS_MESSAGE = "You are now logged in.";

    /**
     * The page to redirect to on errors.
     */
    private static String REDIRECT_ON_ERROR = "login";

    /**
     * The POST field containing the username.
     */
    private static String USERNAME_FIELD = "username";

    /**
     * The POST field containing the password.
     */
    private static String PASSWORD_FIELD = "password";

    /**
     * The location of the login jfp-file that is served on GET request.
     */
    private static String LOGIN_JSP = "WEB-INF/login.jsp";

    /**
     * The warning message provided when the provided username or password are incorrect.
     */
    private static String LOGIN_ERROR = "Incorrect username or password.";

    /**
     * Serves the /login page where anonymous users can log in to their user account.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        new Notifications(request);
        request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
    }

    /**
     * Handles POSTs from the /login page.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications notifications = new Notifications(request);

        try {
            UserDAO userDAO = new MysqlUserDAO(new PrimaryDatabase());
            User    user    = userDAO.findFromUsername(request.getParameter(USERNAME_FIELD));

            if (user == null) {
                notifications.warning(LOGIN_ERROR);
                response.sendRedirect(REDIRECT_ON_ERROR);
                return;
            }

            if (!BCrypt.checkpw(request.getParameter(PASSWORD_FIELD), user.getPassword())) {
                notifications.warning(LOGIN_ERROR);
                response.sendRedirect(REDIRECT_ON_ERROR);
                return;
            }

            String      referer = request.getParameter("referer");
            HttpSession session = request.getSession();
            notifications.success(SUCCESS_MESSAGE);
            session.setAttribute(Config.USER_SESSION_KEY, user);
            response.sendRedirect(referer == null ? "shop" : referer);

        } catch (Exception e) {
            notifications.error(Language.GENERAL_ERROR);
            response.sendRedirect(REDIRECT_ON_ERROR);
            return;
        }
    }
}
