package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.unisa.model.ProdottoBean;
import it.unisa.model.ProdottoDao;

@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProdottoDao prodDao = new ProdottoDao();
        ProdottoBean bean = new ProdottoBean();
        String sort = sanitizeInput(request.getParameter("sort"));
        String action = sanitizeInput(request.getParameter("action"));
        String redirectedPage = sanitizeInput(request.getParameter("page"));

        try {
            if (action != null) {
                if (action.equalsIgnoreCase("add")) {
                    bean.setNome(sanitizeInput(request.getParameter("nome")));
                    bean.setDescrizione(sanitizeInput(request.getParameter("descrizione")));
                    bean.setIva(sanitizeInput(request.getParameter("iva")));
                    bean.setPrezzo(Double.parseDouble(sanitizeInput(request.getParameter("prezzo"))));
                    bean.setQuantit�(Integer.parseInt(sanitizeInput(request.getParameter("quantit�"))));
                    bean.setPiattaforma(sanitizeInput(request.getParameter("piattaforma")));
                    bean.setGenere(sanitizeInput(request.getParameter("genere")));
                    bean.setImmagine(sanitizeInput(request.getParameter("img")));
                    bean.setDataUscita(sanitizeInput(request.getParameter("dataUscita")));
                    bean.setDescrizioneDettagliata(sanitizeInput(request.getParameter("descDett")));
                    bean.setInVendita(true);
                    prodDao.doSave(bean);
                } else if (action.equalsIgnoreCase("modifica")) {
                    bean.setIdProdotto(Integer.parseInt(sanitizeInput(request.getParameter("id"))));
                    bean.setNome(sanitizeInput(request.getParameter("nome")));
                    bean.setDescrizione(sanitizeInput(request.getParameter("descrizione")));
                    bean.setIva(sanitizeInput(request.getParameter("iva")));
                    bean.setPrezzo(Double.parseDouble(sanitizeInput(request.getParameter("prezzo"))));
                    bean.setQuantit�(Integer.parseInt(sanitizeInput(request.getParameter("quantit�"))));
                    bean.setPiattaforma(sanitizeInput(request.getParameter("piattaforma")));
                    bean.setGenere(sanitizeInput(request.getParameter("genere")));
                    bean.setImmagine(sanitizeInput(request.getParameter("img")));
                    bean.setDataUscita(sanitizeInput(request.getParameter("dataUscita")));
                    bean.setDescrizioneDettagliata(sanitizeInput(request.getParameter("descDett")));
                    bean.setInVendita(true);
                    prodDao.doUpdate(bean);
                }

                request.getSession().removeAttribute("categorie");
            }
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }

        try {
            request.getSession().removeAttribute("products");
            request.getSession().setAttribute("products", prodDao.doRetrieveAll(sort));
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/" + redirectedPage);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private String sanitizeInput(String input) {
        if (input != null) {
            input = input.replaceAll("[<>\"'&]", "");
        }
        return input;
    }
}
