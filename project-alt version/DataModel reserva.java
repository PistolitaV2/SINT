package p2;
import p2.*;

import java.io.*;
import java.net.MalformedURLException;
import java.time.Year;
import java.util.LinkedList;
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
import java.util.HashMap;

import p2.SINT139P2;

public class DataModel {
    

public static ArrayList<String> getQ1Years() { // Utilizo Año porque Year xa está pillado de Java time
    ArrayList<String> Años = new ArrayList<String>();
    HashMap <String,Document> DocMap = SINT139P2.getHashMap();
    Años.addAll(DocMap.keySet());
    Collections.sort(Años, Collections.reverseOrder());

    return Años;

}

public static ArrayList<Movie> getQ1Movies(String year) {
    ArrayList<Movie> Movies = new ArrayList<Movie>();
    HashMap <String,Document> DocMap = SINT139P2.getHashMap();

    Document doc = DocMap.get(year);
    NodeList nodeList = null;
    ArrayList<String> xeneros= new ArrayList<String>();
    try {
        nodeList = (NodeList) SINT139P2.getxpath().evaluate("/Movies/Movie", doc, XPathConstants.NODESET);
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
        String duracion_pelicula = "";

        if(
            nlmovie.item(0)!=null){            
            duracion_pelicula = (String) durationMovie.getTextContent().trim();
            }

        nlmovie = movie_en_XSD.getElementsByTagName("Genre");
        for (int i = 0; i < nlmovie.getLength(); i++) {
            Element genreMovie = (Element) nlmovie.item(i);
            String genre_pelicula = (String) genreMovie.getTextContent().trim();
            xeneros.add(genre_pelicula);
        }

        String langs =new String();
    if(!movie_en_XSD.getAttribute("langs").isEmpty()){
         langs = movie_en_XSD.getAttribute("langs").trim();
}
    else{
          langs = "";
}
        /*
         * String titulo_pelicula =
         * movie_en_XSD.getElementsByTagName("tittle").toString();
         * String duracion_pelicula =
         * movie_en_XSD.getElementsByTagName("Duration").toString();
         * String genre_pelicula =
         * movie_en_XSD.getElementsByTagName("Genre").toString();
         * String langs = movie_en_XSD.getAttribute("langs");
         */

String sinopsis=new String();
try{
    sinopsis =(String) SINT139P2.getxpath().evaluate("text()[normalize-space()]",movie_en_XSD,XPathConstants.STRING);
}
catch(XPathException e){
    e.printStackTrace();
}

        Movies.add(new Movie(titulo_pelicula, xeneros, duracion_pelicula, langs,sinopsis)); // meter langs
    }
    Collections.sort(Movies);
    return Movies;

}

public static ArrayList<Cast> getQ1Cast(String year, String titulo) {
    ArrayList<Cast> cast = new ArrayList<Cast>();
    HashMap <String,Document> DocMap = SINT139P2.getHashMap();

    Document doc = DocMap.get(year);
    NodeList nodeList = null;
    boolean contacto;

    try {
        nodeList = (NodeList) SINT139P2.getxpath().evaluate("/Movies/Movie[Title='" + titulo + "']/Cast", doc,
                XPathConstants.NODESET);
    } catch (Exception e) {
        e.printStackTrace();
    }
    for (int n = 0; n < nodeList.getLength(); n++) {
        Element cast_en_XSD = (Element) nodeList.item(n);
        contacto = false;

        NodeList nlcast = cast_en_XSD.getElementsByTagName("Name");
        Element nombreCastElement = (Element) nlcast.item(0);
        String castName = nombreCastElement.getTextContent().trim();

    /*    nlcast = cast_en_XSD.getElementsByTagName("id");
        Element idCastElement = (Element) nlcast.item(0);
        String castid = (String) idCastElement.getTextContent().trim(); */
        String castid = cast_en_XSD.getAttribute("id");

   /*     nlcast = cast_en_XSD.getElementsByTagName("Email");
        Element emailCastElement = (Element) nlcast.item(0);
        String castemail = (String) emailCastElement.getTextContent().trim();

        nlcast = cast_en_XSD.getElementsByTagName("Phone");
        Element phoneCastElement = (Element) nlcast.item(0);
        String castphone = (String) phoneCastElement.getTextContent().trim(); */
         String castemail =new String();
         String castphone =new String();

        nlcast = cast_en_XSD.getElementsByTagName("Email");
        if(nlcast.item(0)!=null){         
        Element emailCastElement = (Element) nlcast.item(0);
        castemail = (String) emailCastElement.getTextContent().trim();
        }
else{
    castemail="";
}

        nlcast = cast_en_XSD.getElementsByTagName("Phone");
        if(nlcast.item(0)!=null){         
        Element phoneCastElement = (Element) nlcast.item(0);
         castphone = (String) phoneCastElement.getTextContent().trim();
        }
else{
    castphone="";
}

        nlcast = cast_en_XSD.getElementsByTagName("Role");
        Element roleCastElement = (Element) nlcast.item(0);
        String castrole = (String) roleCastElement.getTextContent().trim();



        if (!castemail.isEmpty()) {
            cast.add(new Cast(castid, castName, castemail, castrole));
            contacto = true;
        }
        if (!castphone.isEmpty()) {
            cast.add(new Cast(castid, castName, castphone, castrole));
            contacto = true;
        }
        if (!contacto) {
            cast.add(new Cast(castid, castName, "sen contacto", castrole));
        }

    }
        Collections.sort(cast);
        return cast;
}
}