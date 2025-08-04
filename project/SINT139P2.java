package p2;
import p2.*;

import java.io.*;
import java.net.MalformedURLException;
import java.time.Year;
import java.util.*;
import java.net.*;
import org.w3c.dom.Document;

/*
import javax.servlet.*;
import javax.servlet.http.*; */


//                                                          javac -classpath ~/apache-tomcat-9.0.38/lib/servlet-api.jar *.java
//                                                          http://localhost:7139/sint139/P2M


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

public class SINT139P2 extends HttpServlet {

    public static final String ROOT_DIR = "./";

    FrontEnd FrontEnd = new FrontEnd(); // Instanciamos la clase FrontEnd

    public String XML_teleco = "http://alberto.gil.webs.uvigo.es/SINT/21-22/mml2001.xml";
    public static String p = "343SINT139"; // contrasinal
    public static HashMap<String, Document> DocMap = new HashMap<String, Document>();
  /*   private static ArrayList<Warning> warnings = new ArrayList<Warning>();    
    private static ArrayList<Error> errors = new ArrayList<Error>();
    private static ArrayList<FatalError> fatalerrors = new ArrayList<FatalError>();*/
    private static ArrayList<SAXParseException> errors =new ArrayList<SAXParseException>();
    private static ArrayList<SAXParseException> warnings =new ArrayList<SAXParseException>();
    private static ArrayList<SAXParseException> fatalerrors =new ArrayList<SAXParseException>(); 



    // creo xpath
    private static XPathFactory xpathfactory = XPathFactory.newInstance();
    private static XPath xpath = xpathfactory.newXPath();

    public void init(ServletConfig conf) {

        ServletContext context = conf.getServletContext();
        try {
            Parser(context.getResource("/p2/MML.xsd"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
   
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{

        String pfase = req.getParameter("pphase");
        String password = req.getParameter("p");
        String pyear = req.getParameter("pyear");
        String pmovie = req.getParameter("pmovie");
        String pcast = req.getParameter("pcast");
        String auto = req.getParameter("auto");
        
    

        control_erros(res,"primeira parte Doget");
        /*
         * ArrayList<String> Years = new ArrayList<String>();
         * ArrayList<String> Movies = new ArrayList<String>();
         * ArrayList<String> Casts = new ArrayList<String>();
         */
        ArrayList<String> Langs = new ArrayList<String>();    //Antes era Years
        ArrayList<Movie> Movies = new ArrayList<Movie>();
        ArrayList<Cast> Casts = new ArrayList<Cast>();

        if (auto == null) {
            auto = "false"; // Auto desactivado por defecto
        }

        if (pfase == null) {
            pfase = "01"; // pphase = 01 por defecto
        }

        if (password == null) {

            if (auto.equals("false")) {
                FrontEnd.HTMLFALTACONTRASEÑA(req, res, "Non hai contraseña");
                return;
            } else if (auto.equals("true")) {
                FrontEnd.XMLFALTACONTRASEÑA(req, res, "no passwd");
                return;
            }
        }

        if (auto.equals("true")) {

            if (pyear == null) {
                if (pfase.equals("12") || pfase.equals("13")) {
                    FrontEnd.XMLFALTAPARAMETRO(req, res, "pyear");
                    return;
                }
            } else if (pmovie == null) {
                if (pfase.equals("13")) {
                    FrontEnd.XMLFALTAPARAMETRO(req, res, "pmovie");
                    return;
                }
            }
        }

        if (auto.equals("false")) {

            if (pyear == null) {
                if (pfase.equals("12") || pfase.equals("13")) {
                    FrontEnd.HTMLFALTAPARAMETRO(req, res, "pyear");
                    return;
                }
            } else if (pmovie == null) {
                if (pfase.equals("13")) {
                    FrontEnd.HTMLFALTAPARAMETRO(req, res, "pmovie");
                    return;
                }
            }
        }

        control_erros(res,"comprobou parametros");

        // Distribuo as funcións
        if (pfase.equals("11")) {

           // ArrayList<Cast> Cyears =  DataModel.getQ2Cast("en");
            Langs = DataModel.getQ2Langs3();
        }
        if (pfase.equals("12")) {
            Casts = DataModel.getQ2Cast(pyear);
        }
        if (pfase.equals("13")) {
            Movies = DataModel.getQ2Movies(pyear, pmovie);
        }

        if (auto.equals(true)) {
            switch (pfase) {
                case "01":
                    FrontEnd.XMLPHASE01(req, res); /// 01 XML
                    break;
                case "02":
                   // FrontEnd.doGetFase02XML(req, res,warnings,errors,fatalerrors); /// 02 XML
                    break;
                case "11":
                    FrontEnd.XMLPHASE11(req, res, Langs); /// 11 XML
                    break;
                case "12":
                    FrontEnd.XMLPHASE12(req, res, Movies); /// 12 XML
                    break;
                case "13":

                    FrontEnd.XMLPHASE13(req, res, Casts); /// 13 XML
                    break;
                default:
                    FrontEnd.XMLPHASE01(req, res); /// 01 XML
                    break;
            }
        }

        else {
            switch (pfase) { // Modo auto: off
                case "01":
                    FrontEnd.HTMLPHASE01(req, res); /// 01 HTML
                    break;
                case "02":

                FrontEnd.HTMLPHASE02(req, res, toArrayList(warnings), toArrayList(errors), toArrayList(fatalerrors)); /// 02


                 //   P2M.HTMLPHASE02(req, res, warnings, errors, fatalerrors); /// 02
                  /*  control_erros2(res, Years.size());
                    for(int n=0;n<Years.size();n++){
                    control_erros(res, Years.get(n)); 
                       }*/
                //    P2M.HTMLPHASE02(req,res,warnings,errors,fatalerrors);
                    // HTML
                    break;
                case "11":
                    // ArrayList<String> Degrees = new ArrayList<String>();
                    // Degrees = getC1Degrees();
                  //  if(Years.isEmpty() || (Years.size() ==0)){
                  //      P2M.HTMLPASSMAL(req, res, "YEARS EMPTY");
                   // }
                    FrontEnd.HTMLPHASE11(req, res, Langs); /// 11 HTML
                    break;
                case "12":
                    // ArrayList<Subject> Subjects = new ArrayList<Subject>();
                    // Subjects = getC1Subjects(pdegree);
                    FrontEnd.HTMLPHASE12(req, res, Casts); /// 12 HTML
                    break;
                case "13":
                    // ArrayList<Student> Students = new ArrayList<Student>();
                    // Students = getC1Students(pdegree,psubject);
                    FrontEnd.HTMLPHASE13(req, res, Movies); /// 13 HTML
                    break;
                    case"14":
                    FrontEnd.HTMLFALTAPARAMETRO(req,res,pfase+ "14");

                default:
                    FrontEnd.HTMLFALTAPARAMETRO(req,res,pfase);
                 //   P2M.HTMLPHASE01(req, res); /// 01 HTML
                    break;
            }
        }

    }

    public void Parser(URL url_actual) {
      //  control_erros2("inicio parser");
        LinkedList<URL> sen_parsear = new LinkedList<URL>();
        LinkedList<URL> parseados = new LinkedList<URL>();
        URL url_inicial = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        SchemaFactory factoryXSD = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        Schema xsd = null;

       // control_erros2("primer try parser");
        try {
            xsd = factoryXSD.newSchema(url_actual);

        } catch (SAXException e) {
            e.printStackTrace();
        }
        factory.setSchema(xsd);

        DocumentBuilder dBuilder = null;
        try {
            dBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

       dBuilder.setErrorHandler(new XMLParserErrorHandler());

        try {
            url_inicial = new URL(XML_teleco);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
                                                                                     // Engado o MML a lista
        sen_parsear.add(url_inicial);

       // control_erros2("Engade MML a lista entra en bucle do parser");
        while (!sen_parsear.isEmpty()) {

            Document documento = null;
            NodeList masMML = null;
            String yearXpath = "";
            URLConnection src = null;

            try {
                src = sen_parsear.pop().openConnection();
                documento = dBuilder.parse(src.getInputStream(), src.getURL().toString());
                parseados.add(src.getURL());

                yearXpath = (String) xpath.evaluate("/Movies/Year", documento, XPathConstants.STRING); // Movies/Years
                                                                                                      
                masMML = (NodeList) xpath.evaluate("/Movies/Movie/Cast/MML", documento, XPathConstants.NODESET);

            } catch (XPathExpressionException | IOException e) {
                parseados.add(src.getURL());
                System.out.println("Erro no Xpath \n");
                e.printStackTrace();
                continue;
            } /*catch (IOException a) {
                parseados.add(src.getURL());
                System.out.println("Erro IOexception \n");
                a.printStackTrace();
                continue; */
             catch (SAXException ex) {
                parseados.add(src.getURL());
                ex.printStackTrace();
                continue;
            }
    /*        try{
            yearXpath = (String) xpath.evaluate("/Movies/Year", documento, XPathConstants.STRING); // Movies/Year
        }
        catch(XPathExpressionException eas){
            eas.printStackTrace();
        }     */

            DocMap.put(yearXpath, documento); // Andimos el documento parseado al hash map

            // while(masMML.getLength()!=0){
            for (int n = 0; n < masMML.getLength(); n++) {
                String nodoxpath = "";
                URL nextmml = null;
                try {
                    nodoxpath = (String) xpath.evaluate("text()", masMML.item(n), XPathConstants.STRING);
                    nextmml = new URL(src.getURL(), nodoxpath);

                } catch (XPathExpressionException | MalformedURLException e) {
                    System.out.println("Erro no xpath expression");
                    e.printStackTrace();
                    continue;
               } /*catch (MalformedURLException a) {
                    a.printStackTrace();
                }   */

                if ((!sen_parsear.contains(nextmml)) && (!parseados.contains(nextmml))) {
                    sen_parsear.add(nextmml); // Revisamos que o seguinte MML non o teñamos xa, e o agregamos
                }
            }
        }
    }
    public static HashMap<String,Document> getHashMap(){
    return DocMap;
}
public static XPath getxpath(){
    return xpath;
}


    public class XMLParserErrorHandler extends DefaultHandler{

   /*  public ArrayList<SAXParseException> errors = new ArrayList<SAXParseException>();
    public ArrayList<SAXParseException> warnings = new ArrayList<SAXParseException>();
    public ArrayList<SAXParseException> fatalerrors = new ArrayList<SAXParseException>(); */


        public XMLParserErrorHandler(){
            super();
        }
        public void warning(SAXParseException w) throws SAXException {
      /*        for (int i = 0; i < warnings.size(); i++) {
                if ((warnings.get(i).getSystemId()).equals(w.getSystemId())) {
                    warnings.get(i).addMessage(w.toString());
                    // return;
                    throw (w);
                }
            }
            warnings.add(new Warning(w.getSystemId(), w.toString()));
            throw (w); */

            warnings.add(w);
            throw(w);

        }


        public void error(SAXParseException e) throws SAXException {
          /*   for (int i = 0; i < errors.size(); i++) {
                if ((errors.get(i).getSystemId()).equals(e.getSystemId())) {
                    errors.get(i).addMessage(e.toString());
                    throw (e);
                }

            }
            errors.add(new Error(e.getSystemId(), e.toString()));
            throw (e);
*/

           errors.add(e);
            throw(e);

        }

 

        public void fatalError(SAXParseException fe) throws SAXException {
      /*    for (int i = 0; i < fatalerrors.size(); i++) {
             if ((fatalerrors.get(i).getSystemId()).equals(fe.getSystemId())) {
                 fatalerrors.get(i).addMessage(fe.toString());
                 throw (fe);
                }

            }
            fatalerrors.add(new FatalError(fe.getSystemId(), fe.toString()));
            throw (fe);    */
        
        fatalerrors.add(fe);
        throw(fe);
        }
        }

        public ArrayList<String> toArrayList(ArrayList<SAXParseException> errores){
            ArrayList <String> obx = new ArrayList<String>();
            for(int p=0;p<errores.size();p++){
                obx.add(errores.get(p).getSystemId());
            }
            return obx;
        }

    public void control_erros(HttpServletResponse res,String arg) {
        
        res.setCharacterEncoding("utf-8");
        res.setContentType("text/html");
         PrintWriter out;

         try{
            out = res.getWriter();
            


      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='utf-8'/><title>P2M</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Chegou a: "+ arg +"</h1>");

      out.println("</body>");
      out.println("</html>");
         }
      catch(IOException e){
        e.printStackTrace();
              }

        return;
    }
    public void control_erros2(HttpServletResponse res,int arg) {
        
        res.setCharacterEncoding("utf-8");
        res.setContentType("text/html");
         PrintWriter out;

         try{
            out = res.getWriter();
            


      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='utf-8'/><title>P2M</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Tamaño: "+ arg +"</h1>");

      out.println("</body>");
      out.println("</html>");
         }
      catch(IOException e){
        e.printStackTrace();
              }

        return;
    }
}

/*
 * 
 * 
 */