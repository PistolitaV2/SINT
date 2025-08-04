package p2;

import p2.*;

import java.io.*;

import java.util.*;

import java.net.*;

//import javax.servlet.http.*;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.xml.XMLConstants;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class FrontEnd extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String pass = req.getParameter("p");
        String phase = req.getParameter("pphase");

        /*
         * ArrayList<String> Years = new ArrayList<String>();
         * ArrayList<Movie> Movies = new ArrayList<Movie>();
         * ArrayList<Cast> Casts = new ArrayList<Cast>();
         */

        /*
         * if (phase == null)
         * phase = "01";
         * switch (phase) {
         * case "01":
         * HTMLPHASE01(req, res);
         * break;
         * case "02":
         * HTMLPHASE02(req, res);
         * break;
         * case "11":
         * HTMLPHASE11(req, res, years);
         * break;
         * case "12":
         * HTMLPHASE12(req, res,movies);
         * break;
         * default:
         * HTMLPHASE01(req, res);
         * break;
         * }
         */
        return;
    }

    public void HTMLPHASE01(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        String pass = req.getParameter("p");

        // res.setContentType("html/text");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>P2EA</title>");
        out.println("<meta charset='UTF-8'>");
        out.println("<link rel='stylesheet' href='p1.css'> ");
        out.println("</head>");
        out.println("<body>");
        out.println("<l1>Servicio de consulta de Peliculas</l1>");
        out.println("<l2>Bienvenido a este servicio</l2>");
        // out.println("<l3>Ver los ficheros erróneos</l3>");
        out.println("<l2>Selecciona una consulta:</l2>");
        // out.println("<form>");
        // out.println("<input type='hidden' name='pphase' value='11'>Consulta 1");
        out.println("<br>");
        out.println("<a href='?p=" + pass + "&pphase=02'>Ver ficheros erróneos</a>");
        out.println("<br>");
        out.println("<a href='?p=" + pass + "&pphase=11'>Consulta 1: reparto de una pel&iacutecula de un año</a>");

        // out.println("<input type='submit' value='Continuar'>");
        // out.println("</form>");

        out.println("<hr>");
        out.println("<footer>Pedro Otero Rivas</footer>");
        out.println("</body>");
        out.println("</html>");

    }

    public void HTMLPHASE02(HttpServletRequest req, HttpServletResponse res, ArrayList<String> warnings,
            ArrayList<String> errors, ArrayList<String> fatalerrors) throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        int n = 0;
        // res.setContentType("html/text");
        out.println("<head>");
        out.println(" <title>P2EA</title>  ");
        out.println("<meta charset='UTF-8'>");
        out.println(" <link rel='stylesheet' href='p1.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<l3>Se han encontrado " + warnings.size() + " ficheros con warnings:</l3>");
        out.println("<br>");
        for (n = 0; warnings.size() > n; n++) {
            out.println("<l4>" + warnings.get(n) + "</l4>");
            out.println("<br>");

        }
        out.println("<br>");
        out.println("<l3>Se han encontrado " + errors.size() + " ficheros con errores: </l3>");
        out.println("<br>");
        for (n = 0; errors.size() > n; n++) {
            out.println("<l3>" + errors.get(n) + "</l3>");
            out.println("<br>");
            /*
             * for (int i=0; i<errors.get(n).getMessage().size();i++){
             * out.println("<l3>"+errors.get(n).getMessage().get(i)+"</l3>");
             * out.println("<br>");
             * }
             */
        }
        out.println(" <br>");
        out.println("<l3>Se han encontrado " + fatalerrors.size() + " ficheros con errores fatales: </l3>");
        out.println("<br>");
        for (n = 0; fatalerrors.size() > n; n++) {
            out.println("<l4>" + fatalerrors.get(n) + "</l4>");
            out.println("<br>");
            /*
             * for (int i=0; i<fatalerrors.get(n).getMessage().size();i++){
             * out.println("<l3>"+fatalerrors.get(n).getMessage().get(i)+"</l3>");
             * out.println("<br>");
             * }
             */
        }
        out.println("<br>");
        out.println("<form>");
        out.println("<input type='hidden' name='pphase' value='01'>");
        out.println("<input type='submit' value='Atrás'>");
        out.println("</form>");
        out.println("  <hr>");
        out.println("<footer>Pedro Otero Rivas</footer>");
        out.println("</body>");
        return;
    }

    public void HTMLPHASE11(HttpServletRequest req, HttpServletResponse res, ArrayList<String> langs) // en relaidade
                                                                                                      // son langs
            throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        String pass = req.getParameter("p");

   /*   if (langs.isEmpty()){
            res.setCharacterEncoding("utf-8");
            res.setContentType("text/html");
            out.println("<html>");
            out.println(" <head>");
            out.println(" <title>P2EA</title>   ");
            out.println(" <meta charset='UTF-8'>");
            out.println(" <link rel='stylesheet' href='p1.css'>   ");
            out.println("</head>");
            out.println("<body>");
            out.println("  NON HAI NADA EN LANGS");
            out.println(" <footer>Pedro Otero Rivas</footer>");
            out.println("</body>");
            out.println("</html>");
        }
            else{
                res.setCharacterEncoding("utf-8");
                res.setContentType("text/html");
                out.println("<html>");
                out.println(" <head>");
                out.println(" <title>P2EA</title>   ");
                out.println(" <meta charset='UTF-8'>");
                out.println(" <link rel='stylesheet' href='p1.css'>   ");
                out.println("</head>");
                out.println("<body>");
                out.println("  HAI COUSAS EN LANGS OLE OLE");
                out.println(" <footer>Pedro Otero Rivas</footer>");
                out.println("</body>");
                out.println("</html>");

            } */
        
        try {
            res.setCharacterEncoding("utf-8");
            res.setContentType("text/html");
            out.println("<html>");
            out.println(" <head>");
            out.println(" <title>P2EA</title>   ");
            out.println(" <meta charset='UTF-8'>");
            out.println(" <link rel='stylesheet' href='p1.css'>   ");
            out.println("</head>");
            out.println("<body>");
            out.println("   <l3>Consulta 1: Fase 1</l3>");
            out.println("  <br>");
            out.println(" <l3>Seleccione un idioma: " + langs.size() + "</l3>");
            out.println(" <br>");

            out.println(" <ol>");

            for (int n = 0; n < langs.size(); n++) {
                out.println(
                        "   <a href='?p=" + pass + "&pphase=12&pyear=" + langs.get(n) + "'>" + langs.get(n) + "</a>");
                out.println("   <br>");
            }
            out.println(" </ol>");

            out.println(" <a href='?p=" + pass + "&pphase=01'><input type='button' value= 'Inicio'> </a>");

            out.println("  <hr>");
            out.println(" <footer>Pedro Otero Rivas</footer>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // out.println("<hr>");
        // out.println("<hr>");
        return;
    }

    public void HTMLPHASE12(HttpServletRequest req, HttpServletResponse res, ArrayList<Cast> cast)
            throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        String lang = req.getParameter("pyear");
        String pass = req.getParameter("p");

        out.println("<head>");
        out.println("<title>P2EA</title>   ");
        out.println("<meta charset='UTF-8'>");
        out.println(" <link rel='stylesheet' href='p1.css'>    ");
        out.println("</head>");
        out.println("<body>");
      //  out.println("<l3>Consulta 1: Fase 2 (Lang = " + year + "</l3>");
        out.println(" <br>");
        out.println(" <l3>Selecciona un actor</l3>");
        // out.println("Titulacións aportadas polo propio EA.xml??");
        out.println("<ol>");

        for (int n = 0; n < cast.size(); n++) {
          //  out.println("<a>" + cast.get(n).toStringhtml() + "</a>");
          out.println(" <a href='?p=" + pass + "&pphase=13&pyear="+lang+"&pmovie="+cast.get(n).getID()+"'>"+cast.get(n).getName()+"</a>");
          out.println("<a>" + cast.get(n).toStringhtml2() + "</a>");

          out.println("<br>");
        }

        out.println("</ol>");

        out.println(" <a href='?p=" + pass + "&pphase=01'><input type='button' value= 'Inicio'> </a>");

        out.println(" <hr>");
        out.println(" <footer>Pedro Otero Rivas</footer>");
        out.println("</body> ");
        return;
    }

    public void HTMLPHASE13(HttpServletRequest req, HttpServletResponse res, ArrayList<Movie> movies)
            throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        String year = req.getParameter("pyear");
        String movie = req.getParameter("pmovie");
        String pass = req.getParameter("p");

        out.println("<head>");
        out.println("<title>P2EA</title>   ");
        out.println("<meta charset='UTF-8'>");
        out.println(" <link rel='stylesheet' href='p1.css'>    ");
        out.println("</head>");
        out.println("<body>");
        out.println("<l3>Consulta 1: Fase 3 (Idioma = " + year + ", Id = " + movie + ")</l3>");
        out.println(" <br>");
        out.println(" <l3>Este es el resultado de la consulta</l3>");
        // out.println("Titulacións aportadas polo propio EA.xml??");
        out.println("<ol>");
        // out.println("<l3>Numero de elementos cast: " +cast.size()+"</l3><br>");
        for (int n = 0; n < movies.size(); n++) {
            out.println("<a>" + movies.get(n).toStringHTMLQ2() + "</a>");
            out.println("   <br>");
        }
        out.println("</ol>");

        out.println(" <a href='?p=" + pass + "&pphase=01'><input type='button' value= 'Inicio'> </a>");

        /*
         * out.println(" <form>");
         * out.println("  <input type='hidden' name='pphase' value='12'>");
         * out.println("  <input type='submit' value='Atrás'>");
         * out.println(" </form>");
         * out.println(" <form>");
         * out.println(" <input type='hidden' name='pphase' value='01'>");
         * out.println(" <input type='submit' value='Inicio'>");
         * out.println(" </form>");
         */
        out.println(" <hr>");
        out.println(" <footer>Pedro Otero Rivas</footer>");
        out.println("</body> ");
        return;
    }

    public void HTMLFALTACONTRASEÑA(HttpServletRequest req, HttpServletResponse res, String error) {
        String pass = req.getParameter("p");

        try {
            res.setCharacterEncoding("utf-8");
            res.setContentType("text/html");

            PrintWriter out = res.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='utf-8'/><title>Consulta</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Se ha producido un error:</h1><br><br>");
            out.println("<h2>" + error + "</h2><br><br>");
            out.println("<h2>" + pass + "</h2>");
            out.println("<footer>Pedro Otero Rivas</footer>");
            out.println("</body>");
            out.println("</html>");

        } catch (Exception ex) {
            System.out.println("Algo fue mal en la ejecución del servlet: " + ex.toString());
        }

        return;
    }

    public void HTMLPASSMAL(HttpServletRequest req, HttpServletResponse res, String error)
            throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>P2EA</title>");
        out.println("<meta charset='UTF-8'>");
        out.println("<link rel='stylesheet' href='p1.css'> ");
        out.println("</head>");
        out.println("<body>");
        out.println("<l1>Se ha producido un error un error</l1>");
        out.println("<l2>" + error + "</l2>");
        out.println("<footer>Pedro Otero Rivas</footer>");
        out.println("</body>");
        out.println("</html>");
    }

    public void HTMLFALTAPARAMETRO(HttpServletRequest req, HttpServletResponse res, String error)
            throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>P2EA</title>");
        out.println("<meta charset='UTF-8'>");
        out.println("<link rel='stylesheet' href='p1.css'> ");
        out.println("</head>");
        out.println("<body>");
        out.println("<l1>Falta un parámetro:</l1>");
        out.println("<l2>parámetro necesario:" + error + "</l2>");
        out.println("<footer>Pedro Otero Rivas</footer>");
        out.println("</body>");
        out.println("</html>");
    }

    public void XMLPHASE01(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        res.setCharacterEncoding("utf-8");
        res.setContentType("text/xml");

        try {
            out.println("<?xml version='1.0' encoding='utf-8'?>");
            out.println("<service>");
            out.println("   <status>OK/status>");
            out.println("</service>");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void XMLPHASE11(HttpServletRequest req, HttpServletResponse res, ArrayList<String> langs)
            throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        try {
            res.setCharacterEncoding("utf-8");
            res.setContentType("text/xml");
            out.println("<?xml version='1.0' encoding='utf-8' ?>");
            out.println("<langs>");
            for (int n = 0; n < langs.size(); n++) {
                out.println("<lang>" + langs.get(n) + "</lang>");

            }
            out.println("</langs>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void XMLPHASE12(HttpServletRequest req, HttpServletResponse res, ArrayList<Cast> cast)
            throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        try {
            res.setCharacterEncoding("utf-8");
            res.setContentType("text/xml");
            out.println("<?xml version='1.0' encoding='utf-8' ?>");
            out.println("<movies>");
            for (int n = 0; n < cast.size(); n++) {
                out.println("<movie langs ='" + movies.get(n).getLangs() + "' genres= '" + movies.get(n).getGenresXML()
                        + "' sypnosis='" + movies.get(n).getSinopsis() + ">" + movies.get(n).getTitulo()
                        + "</movie>");
            }
            out.println("</movies>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void XMLPHASE13(HttpServletRequest req, HttpServletResponse res, ArrayList<Cast> casts)
            throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        try {
            res.setCharacterEncoding("utf-8");
            res.setContentType("text/xml");
            out.println("<?xml version='1.0' encoding='utf-8' ?>");
            out.println("<thecast>");
            for (int n = 0; n < casts.size(); n++) {
                out.println("<cast id ='" + casts.get(n).getID() + "' role= '" + casts.get(n).getRole()
                        + "' contact='" + casts.get(n).getContacto() + ">" + casts.get(n).getName()
                        + "</cast>");
            }
            out.println("</thecast>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void XMLFALTAPARAMETRO(HttpServletRequest req, HttpServletResponse res, String error) {

        try {

            res.setCharacterEncoding("utf-8");
            res.setContentType("text/xml");

            PrintWriter out = res.getWriter();

            out.println("<?xml version='1.0' encoding='utf-8'?>");
            out.println("<wrongRequest>no param:" + error + "</wrongRequest>");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return;
    }

    public void XMLFALTACONTRASEÑA(HttpServletRequest req, HttpServletResponse res, String error) {

        try {

            res.setCharacterEncoding("utf-8");
            res.setContentType("text/xml");

            PrintWriter out = res.getWriter();

            out.println("<?xml version='1.0' encoding='utf-8'?>");
            out.println("<wrongRequest>" + error + "</wrongRequest>");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return;
    }

}
