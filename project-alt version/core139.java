package p2;
import java.io.*;
import java.net.MalformedURLException;
import java.time.Year;
import java.util.LinkedList;
import java.util.*;
import java.net.*;
import org.w3c.dom.Document;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.*;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.helpers.DefaultHandler;

//public class SINT139P2 extends HttpServlet {
    public class core139 extends HttpServlet {

    public static final String ROOT_DIR = "./";

    FrontEnd FrontEnd = new FrontEnd(); // Instanciamos la clase FrontEnd

    public static String XML_teleco = "DENTRO DE POCO EN MOOVI";
    public static String p = "343SINT139"; // contrasinal
    private static HashMap<String, Document> DocMap = new HashMap<String, Document>();

    // creo xpath
    private static XPathFactory xpathfactory = XPathFactory.newInstance();
    private static XPath xpath = xpathfactory.newXPath();

    public void init(ServletConfig conf) {

        ServletContext context = conf.getServletContext();
        try {
            ParserXML(context.getResource("/WEB-INF/classes/p2/eaml.xsd"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        String pfase = request.getParameter("pphase");
        String password = request.getParameter("p");
        String pyear = request.getParameter("pyear");
        String pmovie = request.getParameter("pmovie");
        String pcast = request.getParameter("pcast");
        String auto = request.getParameter("auto");

        /*
         * ArrayList<String> Years = new ArrayList<String>();
         * ArrayList<String> Movies = new ArrayList<String>();
         * ArrayList<String> Casts = new ArrayList<String>();
         */
        ArrayList<String> Years = new ArrayList<String>();
        ArrayList<Movie> Movies = new ArrayList<Movie>();
        ArrayList<Cast> Casts = new ArrayList<Cast>();

        if (auto == null) {
            auto = "false"; // Auto desactivado por defecto
        }

        if (pfase == null) {
            pfase = "01"; // pphase = 01 por defecto
        }

        if (password == null) {

            if (auto.equals("true")) {
                P2EA.HTMLFALTACONTRASEÑA(request, response, "Non hai contraseña");
                return;
            } else if (auto.equals("false")) {
                P2EA.XMLFALTACONTRASEÑA(request, response, "no passwd");
                return;
            }
        }

        if (auto.equals("true")) {

            if (pmovie == null) {
                if (pfase.equals("12") || pfase.equals("13")) {
                    P2EA.XMLFALTAPARAMETRO(request, response, "pmovie");
                    return;
                }
            } else if (pcast == null) {
                if (pfase.equals("13")) {
                    P2EA.XMLFALTAPARAMETRO(request, response, "pcast");
                    return;
                }
            }
        }

        if (auto.equals("false")) {

            if (pmovie == null) {
                if (pfase.equals("12") || pfase.equals("13")) {
                    P2EA.HTMLFALTAPARAMETRO(request, response, "pmovie");
                    return;
                }
            } else if (pcast == null) {
                if (pfase.equals("13")) {
                    P2EA.HTMLFALTAPARAMETRO(request, response, "pcast");
                    return;
                }
            }
        }
        // Distribuo as funcións
        if (pfase.equals("11")) {
            Years = getQ1Years();
        }
        if (pfase.equals("12")) {
            Movies = getQ1Movies(pyear);
        }
        if (pfase.equals("13")) {
            Casts = getQ1Cast(pyear, pmovie);
        }

        if (auto.equals(true)) {
            switch (pfase) {
                case "01":
                    P2EA.XMLPHASE01(req, res); /// 01 XML
                    break;
                case "02":
                    // P2EA.doGetFase02XML(req, res,warnings,errors,fatalerrors); /// 02 XML
                    break;
                case "11":
                    P2EA.XMLPHASE11(req, res, Years); /// 11 XML
                    break;
                case "12":
                    P2EA.XMLPHASE12(req, res, Movies); /// 12 XML
                    break;
                case "13":

                    P2EA.XMLPHASE13(req, res, Casts); /// 13 XML
                    break;
                default:
                    P2EA.XMLPHASE01(req, res); /// 01 XML
                    break;
            }
        }

        else {
            switch (pfase) { // Modo auto: off
                case "01":
                    P2EA.HTMLPHASE01(request, response); /// 01 HTML
                    break;
                case "02":
                    // P2EA.HTMLPHASE02(request, response, warnings, errors, fatalerrors); /// 02
                    // HTML
                    break;
                case "11":
                    // ArrayList<String> Degrees = new ArrayList<String>();
                    // Degrees = getC1Degrees();
                    P2EA.HTMLPHASE11(request, response, Years); /// 11 HTML
                    break;
                case "12":
                    // ArrayList<Subject> Subjects = new ArrayList<Subject>();
                    // Subjects = getC1Subjects(pdegree);
                    P2EA.HTMLPHASE12(request, response, Movies); /// 12 HTML
                    break;
                case "13":
                    // ArrayList<Student> Students = new ArrayList<Student>();
                    // Students = getC1Students(pdegree,psubject);
                    FrontEnd.doGetFase13HTML(request, response, Casts); /// 13 HTML
                    break;
                default:
                    P2EA.HTMLPHASE01(request, response); /// 01 HTML
                    break;
            }
        }

    }

    public void Parser(URL url_actual) {
        LinkedList<URL> sen_parsear = new LinkedList<URL>();
        LinkedList<URL> parseados = new LinkedList<URL>();
        URL url_inicial = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        SchemaFactory factoryXSD = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        Schema xsd = null;

        try {
            xsd = factoryXSD.newSchema(url_actual);

        } catch (Exception e) {
            e.printStackTrace();
        }
        factory.setSchema(xsd);

        DocumentBuilder dBuilder = null;
        try {
            dBuilder = factory.newDocumentBuilder();
        } catch (Exception e) {
            e.printStackTrace();
        }

        dBuilder.setErrorHandler(new XMLParserErrorHandler());

        try {
            url_inicial = new URL(XML_teleco);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Engado o MML a lista
        sen_parsear.add(url_inicial);
        while (!sen_parsear.isEmpty()) {

            Document documento = null;
            NodeList masMML = null;
            String yearXpath = "";
            URLConnection src = null;

            try {
                src = sen_parsear.pop().openConnection();
                documento = dBuilder.parse(src.getInputStream(), src.getURL().toString());
                parseados.add(src.getURL());

                yearXpath = (String) xpath.evaluate("/Movies/Year", documento, XPathConstants.STRING); // Movies/Year
                                                                                                       // ????????
                masMML = (NodeList) xpath.evaluate("/Movies/Movie/Cast/MML", documento, XPathConstants.NODESET);

            } catch (XPathExpressionException e) {
                parseados.add(src.getURL());
                System.out.println("Erro no Xpath \n");
                e.printStackTrace();
                continue;
            } catch (IOException a) {
                parseados.add(src.getURL());
                System.out.println("Erro IOexception \n");
                a.printStackTrace();
                continue;
            } catch (SAXException ex) {
                parseados.add(src.getURL());
                ex.printStackTrace();
                continue;
            }
            DocMap.put(yearXpath, documento); // Andimos el documento parseado al hash map

            // while(masMML.getLength()!=0){
            for (int n = 0; n < masMML.getLength(); n++) {
                String nodoxpath = "";
                URL nextmml = null;
                try {
                    nodoxpath = (String) xpath.evaluate("text()", masMML.item(n), XPathConstants.STRING);
                    nextmml = new URL(src.getURL(), nodoxpath);

                } catch (XPathExpressionException e) {
                    System.out.println("Erro no xpath expression");
                    e.printStackTrace();
                } catch (Exception a) {
                    a.printStackTrace();
                }

                if ((!sen_parsear.contains(nextmml)) && (!parseados.contains(nextmml))) {
                    sen_parsear.add(nextmml); // Revisamos que o seguinte MML non o teñamos xa, e o agregamos
                }
            }
        }
    }

    public ArrayList<String> getQ1Years() { // Utilizo Año porque Year xa está pillado de Java time
        ArrayList<String> Años = new ArrayList<String>();
        Años.addAll(DocMap.keySet());
        Collections.sort(Años, Collections.reverseOrder());

    }

    public ArrayList<Movie> getQ1Movies(String year) {
        ArrayList<Movie> Movies = new ArrayList<Movie>();
        Document doc = DocMap.get(year);
        NodeList nodeList = null;
        ArrayList<String> xeneros;
        try {
            nodeList = (NodeList) xpath.evaluate("/Movies/Movie", doc, XPathConstants.NODESET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int n = 0; n < nodeList.getLength(); n++) {
            Element movie_en_XSD = (Element) nodeList.item(n);
            xeneros.clear();
            /*
             * String titulo_pelicula = (String)
             * movie_en_XSD.getElementsByTagName("Titulo");
             * String duracion_pelicula = (String)
             * movie_en_XSD.getElementsByTagName("Duration");
             * String genre_pelicula = (String) movie_en_XSD.getElementsByTagName("Genre");
             */
            NodeList nlmovie = movie_en_XSD.getElementsByTagName("Title");
            Element nombreMovie = (Element) nlmovie.item(0);
            String titulo_pelicula = (String) nombreMovie.getTextContent().trim();

            nlmovie = movie_en_XSD.getElementsByTagName("Duration");
            Element durationMovie = (Element) nlmovie.item(0);
            String duracion_pelicula = (String) durationMovie.getTextContent().trim();

            nlmovie = movie_en_XSD.getElementsByTagName("Genre");
            for (int i = 0; i < nlmovie.getLength(); i++) {
                Element genreMovie = (Element) nlmovie.item(i);
                String genre_pelicula = (String) genreMovie.getTextContent().trim();
                xeneros.add(genre_pelicula);
            }

            String langs = movie_en_XSD.getAttribute("langs").trim();

            /*
             * String titulo_pelicula =
             * movie_en_XSD.getElementsByTagName("tittle").toString();
             * String duracion_pelicula =
             * movie_en_XSD.getElementsByTagName("Duration").toString();
             * String genre_pelicula =
             * movie_en_XSD.getElementsByTagName("Genre").toString();
             * String langs = movie_en_XSD.getAttribute("langs");
             */

            Movies.add(new Movie(titulo_pelicula, xeneros, duracion_pelicula, langs)); // meter langs
        }
        Collections.sort(Movies);
        return Movies;

    }

    public ArrayList<Cast> getQ1Cast(String year, String titulo) {
        ArrayList<Cast> cast = new ArrayList<Cast>();
        Document doc = DocMap.get(year);
        NodeList nodeList = null;
        boolean contacto;

        try {
            nodeList = (NodeList) xpath.evaluate("/Movies/Movie[Tittle='" + titulo + "']/Cast", doc,
                    XPathConstants.NODESET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int n = 0; n < nodeList.getLength(); n++) {
            Element cast_en_XSD = (Element) nodeList.item(n);
            contacto = false;

            NodeList nlcast = cast_en_XSD.getElementsByTagName("Name");
            Element nombreCastElement = (Element) nlcast.item(0);
            String castName = (String) nombreCastElement.getTextContent().trim();

            nlcast = cast_en_XSD.getElementsByTagName("id");
            Element idCastElement = (Element) nlcast.item(0);
            String castid = (String) idCastElement.getTextContent().trim();

            nlcast = cast_en_XSD.getElementsByTagName("Email");
            Element emailCastElement = (Element) nlcast.item(0);
            String castemail = (String) emailCastElement.getTextContent().trim();

            nlcast = cast_en_XSD.getElementsByTagName("Phone");
            Element phoneCastElement = (Element) nlcast.item(0);
            String castphone = (String) phoneCastElement.getTextContent().trim();

            nlcast = cast_en_XSD.getElementsByTagName("Role");
            Element roleCastElement = (Element) nlcast.item(0);
            String castrole = (String) roleCastElement.getTextContent().trim();

            if (!castemail.isEmpty()) {
                cast.add(new Cast(castid, castName, castemail, castrole));
                contacto = true;
            }
            if (!castphone.isEmpty()) {
                cast.add(new Cast(castid, castName, castemail, castrole));
                contacto = true;
            }
            if (!contacto) {
                cast.add(new Cast(castid, castName, "sen contacto", castrole));
            }
            Collections.sort(cast);

        }
    }

}

/*
 * O XMLPARSERERRORHANDLER que é??
 * 258 ese text()????
 * Cando se usa getAttribute ou getElementsByTagName
 * 
 */