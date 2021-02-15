import com.google.gson.Gson;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PurchaseServlet", value = "/PurchaseServlet")
public class PurchaseServlet extends HttpServlet {
    private final static Gson GSON = new Gson();
    private final static String DIGIT_REGEX = "\\d+";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        throw new ServletException("Get not allowed");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ResponseMessage responseMessage = new ResponseMessage();

        String urlPath = request.getPathInfo();

        // check we have a URL!
        if (urlPath == null || urlPath.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseMessage.setError("Url not found");
        } else {
            String[] urlParts = urlPath.split("/");
            // and now validate url path and return the response status code
            // (and maybe also some value if input is valid)
            if (isPurchasePostUrlValid(urlParts)) {
                response.setStatus(HttpServletResponse.SC_OK);
                responseMessage.setMessage("It works!");

                // Extract request body
                PurchaseOrder purchaseOrder = GSON.fromJson(request.getReader().lines().collect(Collectors.joining()), PurchaseOrder.class);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                responseMessage.setError("Url not valid");
            }
            response.getWriter().print(GSON.toJson(responseMessage));
            response.getWriter().flush();
            System.out.println(response);
        }
    }

    private boolean isPurchasePostUrlValid(String[] urlParts) {
        return DateBuilder.getDate(urlParts[5]) != null && urlParts[1].matches(DIGIT_REGEX) && urlParts[3].matches(DIGIT_REGEX);
    }
}
