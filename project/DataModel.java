package p2;

import p2.*;

import java.io.*;
import java.lang.reflect.Array;
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


    public static ArrayList<Cast> getQ2Cast(String lang) {
        System.out.println("------------------------------------------------------------------------EMPEZA GETQ2CAST");

        // System.out.println("Lang a buscar -> '"+lang+"'");

        int repetido=0;
        ArrayList<Cast> cast = new ArrayList<Cast>();
        
        ArrayList<String> Años = new ArrayList<String>();
        HashMap<String, Document> DocMap = SINT139P2.getHashMap();
        Años.addAll(DocMap.keySet());
        Collections.sort(Años, Collections.reverseOrder());
        NodeList nodeList = null;
        boolean contacto;
        for (int p = 0; p < Años.size(); p++) {
            // System.out.println("Valor de P -> "+p+"           buscando por año: "+Años.get(p));

            Document doc = DocMap.get(Años.get(p));
            try {
                nodeList = (NodeList) SINT139P2.getxpath().evaluate("/Movies/Movie", doc, XPathConstants.NODESET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int n = 0; n < nodeList.getLength(); n++) {
               // System.out.println("Valor de n -> "+n);

                Element movie_en_XSD = (Element) nodeList.item(n);
                
                String langsmov = movie_en_XSD.getAttribute("langs").trim();
                // System.out.println("Valor de langsmov -> '"+langsmov+"'");

                String[] stringlangs = langsmov.split(" ");

                for (int i = 0; i < stringlangs.length; i++) {
                System.out.println("Stringlangs(i) -> '"+stringlangs[i]+"'");

                    if (stringlangs[i].equals(lang)) {
                        System.out.println("Hai coincidencia de langs");

                        NodeList nlmovie = movie_en_XSD.getElementsByTagName("Title");
                        Element nombreMovie = (Element) nlmovie.item(0);
                        String titulo_pelicula = (String) nombreMovie.getTextContent().trim();

                        System.out.println("Titulo pelicula -> '"+titulo_pelicula+"'");


                        try {
                  //          nodeList = (NodeList) SINT139P2.getxpath().evaluate("/Movies/Movie/@langs='" + lang + "'/Cast", doc, XPathConstants.NODESET);
                              nodeList = (NodeList) SINT139P2.getxpath().evaluate("/Movies/Movie[Title='" + titulo_pelicula + "']/Cast", doc,XPathConstants.NODESET);
                        } catch (Exception e) {
                            System.out.println("ERRO NA NODELIST!!\n");

                            e.printStackTrace();
                        }
                      System.out.println("nodelist.length -> "+nodeList.getLength());

                        for (int m = 0; m < nodeList.getLength(); m++) {
                            Element cast_en_XSD = (Element) nodeList.item(m);
                            contacto = false;
                            System.out.println("Valor de m -> "+m);

                            NodeList nlcast = cast_en_XSD.getElementsByTagName("Name");
                            Element nombreCastElement = (Element) nlcast.item(0);
                            String castName = nombreCastElement.getTextContent().trim();
                            // System.out.println("castname -> "+castName);


                            /*
                             * nlcast = cast_en_XSD.getElementsByTagName("id");
                             * Element idCastElement = (Element) nlcast.item(0);
                             * String castid = (String) idCastElement.getTextContent().trim();
                             */
                            String castid = cast_en_XSD.getAttribute("id");
                            System.out.println("castid -> '"+castid+"'");

                            


                            /*
                             * nlcast = cast_en_XSD.getElementsByTagName("Email");
                             * Element emailCastElement = (Element) nlcast.item(0);
                             * String castemail = (String) emailCastElement.getTextContent().trim();
                             * 
                             * nlcast = cast_en_XSD.getElementsByTagName("Phone");
                             * Element phoneCastElement = (Element) nlcast.item(0);
                             * String castphone = (String) phoneCastElement.getTextContent().trim();
                             */
                            String castemail = new String();
                            String castphone = new String();

                            nlcast = cast_en_XSD.getElementsByTagName("Email");
                            if (nlcast.item(0) != null) {
                                Element emailCastElement = (Element) nlcast.item(0);
                                castemail = (String) emailCastElement.getTextContent().trim();
                            } else {
                                castemail = "";
                            }
                            // System.out.println("castemail -> "+castemail);



                            nlcast = cast_en_XSD.getElementsByTagName("Phone");
                            if (nlcast.item(0) != null) {
                                Element phoneCastElement = (Element) nlcast.item(0);
                                castphone = (String) phoneCastElement.getTextContent().trim();
                            } else {
                                castphone = "";
                            }              
                            // System.out.println("castphone -> "+castphone);



                            nlcast = cast_en_XSD.getElementsByTagName("Role");
                            Element roleCastElement = (Element) nlcast.item(0);
                            String castrole = (String) roleCastElement.getTextContent().trim();
                            // System.out.println("castrole -> "+castrole);


                            int decision=0;
                            if (!castemail.isEmpty()) {
                            //    cast.add(new Cast(castid, castName, castemail, castrole));
                            decision=1;
                                contacto = true;
                            }
                            if (!castphone.isEmpty()) {
                              //  cast.add(new Cast(castid, castName, castphone, castrole));
                                decision=2;
                                contacto = true;
                            }
                            if (!contacto) {
                            //    cast.add(new Cast(castid, castName, "sen contacto", castrole));
                            }
                            
                            String castcontacto= new String();

                            switch (decision) {

                                case 1:
                                castcontacto=castemail;
                                break;

                                case 2:
                                castcontacto=castphone;
                                break;

                                default:
                                castcontacto="sen contacto";
                                break;
                            }
                            
                           Cast cast_novo = new Cast(castid, castName, castcontacto, castrole);

                         /* if(!cast.contains(cast_novo)){
                            cast.add(cast_novo);
                           } */
                  /*         if (!repetido){
                            System.out.println("                         Engado: '"+cast_novo.getID()+"'");
                            cast.add(cast_novo);

                           } */

                           for(int y=0;y<cast.size();y++){
                            System.out.println("comparo con -> '"+cast.get(y).getID()+"' / '"+cast_novo.getID()+"'");

                        if(cast_novo.getID().equals(cast.get(y).getID())){
                            System.out.println("                        ----MATCH----");
                            repetido = 1;
                            System.out.println("                        ----Repetido = "+repetido+" ");
                            break;

                        }
                        else{
                            repetido = 0;
                            }
                             }
                        System.out.println("                        ----Repetido+ = "+repetido+" ");
                        if(repetido==0){
                            cast.add(cast_novo);
                            System.out.println("                        ----Engado----");

                        }
                        repetido = 0;


                        }

                    }
                    else{
                        System.out.println("NoN HOUBO COINCIDENCIAS XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n");
                      //  cast.clear();;
                    }
                }
             //   Collections.sort(cast);
             //   return cast;
                // Nada

            }

        }
        Collections.sort(cast);
        return cast;
      //  cast.add(new Cast("castid", "castName", "sen contacto", "castrole"));
      //          return cast;
    }





    public static ArrayList<String> getQ2Langs2() {

        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");



        ArrayList<String> arraylangs = new ArrayList<String>();
      //  HashMap<String, Document> DocMap = SINT139P2.getHashMap();
        // String lang;
        // String[] langs =new String();
        XPathFactory xpathf = XPathFactory.newInstance();
        XPath xpath = xpathf.newXPath();
        ArrayList<String> Años = new ArrayList<String>();
        Años.addAll(SINT139P2.DocMap.keySet());

        NodeList nodeList = null;
        for (int n = 0; n < Años.size(); n++) {
            Document doc = SINT139P2.DocMap.get(Años.get(n));
            try {
                nodeList = (NodeList) xpath.evaluate("/Movies/Movie", doc, XPathConstants.NODESET);
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (n = 0; n < nodeList.getLength(); n++) {
                Element movie_en_XSD = (Element) nodeList.item(n);

                // String[] langs =new String();
                // if(!movie_en_XSD.getAttribute("langs").isEmpty()){
                // lang = movie_en_XSD.getAttribute("langs").trim();
                // arraylangs.add(lang);
              
                String[] langs = movie_en_XSD.getAttribute("langs").split(" ");
                System.out.println("Valor de langs" + langs);
                for (n = 0; n < langs.length; n++) {
                    arraylangs.add(langs[n]);
                    if (!arraylangs.contains(langs[n])) {
                        arraylangs.add(langs[n]);
                    }
                    // }
                }
                // else;

            }
        }
        return arraylangs;

    }

    public static ArrayList<String> getQ1Years() { // Utilizo Año porque Year xa está pillado de Java time
        ArrayList<String> Años = new ArrayList<String>();
        HashMap<String, Document> DocMap = SINT139P2.getHashMap();
        Años.addAll(DocMap.keySet());
        Collections.sort(Años, Collections.reverseOrder());

        return Años;

    }

    public static ArrayList<String> getQ2Langs3() {

        ArrayList<String> Años = new ArrayList<String>();
        HashMap<String, Document> DocMap = SINT139P2.getHashMap();
        Años.addAll(DocMap.keySet());
        Collections.sort(Años, Collections.reverseOrder());
        ArrayList<String> Langs = new ArrayList<String>();

        String[] arraylangs = null;
      //  HashMap<String, Document> DocMap = SINT139P2.getHashMap();
        System.out.println("**********************************************************************************\nEMPEZA GETQ2LANGS3");
        
        for(int p = 0 ; p<Años.size();p++){
        Document doc = DocMap.get(Años.get(p));
        NodeList nodeList = null;
        try {
            nodeList = (NodeList) SINT139P2.getxpath().evaluate("/Movies/Movie", doc, XPathConstants.NODESET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int n = 0; n < nodeList.getLength(); n++) {
            System.out.println("Valor n = "+n);

            Element movie_en_XSD = (Element) nodeList.item(n);

            

            String langs = new String();
            if (!movie_en_XSD.getAttribute("langs").isEmpty()) {
                langs = movie_en_XSD.getAttribute("langs").trim();
                arraylangs = langs.split(" ");
         //       System.out.println("Valor del array = "+arraylangs.length);

         //       System.out.println("Valor de langs = "+langs);
            } else {
                langs = "";
         //       System.out.println("Valor de langs = "+langs);
            }
 
            for(int l=0;arraylangs.length>l;l++){
          //      System.out.println("Valor del langs(l) = "+arraylangs[l]);
                if(!Langs.contains(arraylangs[l])){
          //          System.out.println("Non o tiña, se engade\n");
                    Langs.add(arraylangs[l]);
                }         
                else{
           //         System.out.println("Repetido\n");
                }  
            }

         //   if(n==2) n++;


            }
    }
        Collections.sort(Langs);
        System.out.println("Valor de LLLLLAngs = "+Langs.size()+"\n");

        return Langs;
    
    }

    public static ArrayList<Movie> getQ2Movies(String lang, String id) {
        System.out.println("()()()()()()()()()()()()()()()()()()()()()()()()()())()())EMPEZA GETQ2Movie");
        System.out.println("Buscase o seguinte -> '"+lang+"' / '"+id+"'");

        ArrayList<Movie> Movies = new ArrayList<Movie>();

        String id_a_buscar=new String();
        ArrayList<String> Años = new ArrayList<String>();
        HashMap<String, Document> DocMap = SINT139P2.getHashMap();
        Años.addAll(DocMap.keySet());
        NodeList nodeList = null;
        NodeList nodeList2 = null;
        ArrayList<String> xeneros = new ArrayList<String>();

        for (int p = 0; p < Años.size(); p++) {
            System.out.println("Valor de P -> "+p+"           buscando por año: "+Años.get(p));

            Document doc = DocMap.get(Años.get(p));
            try {
                nodeList = (NodeList) SINT139P2.getxpath().evaluate("/Movies/Movie", doc, XPathConstants.NODESET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int n = 0; n < nodeList.getLength(); n++) {
               System.out.println("Valor de n -> "+n);

                Element movie_en_XSD = (Element) nodeList.item(n);
                
                String langsmov = movie_en_XSD.getAttribute("langs").trim();
                System.out.println("Valor de langsmov -> '"+langsmov+"'");

                String[] stringlangs = langsmov.split(" ");

                for (int i = 0; i < stringlangs.length; i++) {
                System.out.println("Stringlangs(i) -> '"+stringlangs[i]+"'");

                    if (stringlangs[i].equals(lang)) {
                        System.out.println("Hai coincidencia de langs vou a polo id");

                        NodeList nlmovie = movie_en_XSD.getElementsByTagName("Title");
                        Element nombreMovie = (Element) nlmovie.item(0);
                        String titulo_pelicula = (String) nombreMovie.getTextContent().trim();

                        System.out.println("Titulo pelicula -> '"+titulo_pelicula+"'");


                        try {
                  //          nodeList = (NodeList) SINT139P2.getxpath().evaluate("/Movies/Movie/@langs='" + lang + "'/Cast", doc, XPathConstants.NODESET);
                              nodeList2 = (NodeList) SINT139P2.getxpath().evaluate("/Movies/Movie[Title='" + titulo_pelicula + "']/Cast", doc,XPathConstants.NODESET);
                        } catch (Exception e) {
                            System.out.println("ERRO NA NODELIST!!\n");

                            e.printStackTrace();
                        }
                    
                        for(int c=0;c<nodeList2.getLength();c++){
                        Element cast_en_XSD = (Element) nodeList2.item(c);            
                        String castid = cast_en_XSD.getAttribute("id");
                        System.out.println("[GetQ2Movies] ID -> '"+castid+"'");

                        if(castid.equals(id)){

                           nlmovie = movie_en_XSD.getElementsByTagName("Genre");
                            for (int f = 0; f < nlmovie.getLength(); f++) {
                                Element genreMovie = (Element) nlmovie.item(f);
                                String genre_pelicula = (String) genreMovie.getTextContent().trim();
                                System.out.println("[GetQ2Movies] xenero pelicula "+f+" -> '"+(String) genreMovie.getTextContent().trim()+"'");
                                xeneros.add(genre_pelicula);
                            }

                            String sinopsis = new String();
                            try {
                                sinopsis = (String) SINT139P2.getxpath().evaluate("text()[normalize-space()]", movie_en_XSD,
                                        XPathConstants.STRING);
                                System.out.println("[GetQ2Movies] sinopsis -> '"+sinopsis+"'");
  
                            } catch (XPathException e) {
                                e.printStackTrace();
                            }

                            Movies.add(new Movie(titulo_pelicula, xeneros, "duracion_pelicula", "langs", sinopsis,Años.get(p))); // meter langs
                         }

                        }




                        }
                    }
                    }
                }
                return Movies;
    }

public static ArrayList<Cast> getQ2Cast2(String lang){

    ArrayList<String> Años = new ArrayList<String>();
    HashMap<String, Document> DocMap = SINT139P2.getHashMap();
    Años.addAll(DocMap.keySet());
    Collections.sort(Años, Collections.reverseOrder());
    ArrayList<Cast> cast = new ArrayList<Cast>();
    boolean contacto;

    String[] arraylangs = null;
  //  HashMap<String, Document> DocMap = SINT139P2.getHashMap();
    System.out.println("------------------------------------------------------------------------------------------------\nEMPEZA GETQ2LANGS3");
    
    for(int p = 0 ; p<Años.size();p++){
    Document doc = DocMap.get(Años.get(p));
    NodeList nodeList = null;
    try {
        nodeList = (NodeList) SINT139P2.getxpath().evaluate("/Movies/Movie/Cast", doc, XPathConstants.NODESET);   //igual se precisan dous nodelist
    } catch (Exception e) {
        e.printStackTrace();
    }
    for (int n = 0; n < nodeList.getLength(); n++) {
        System.out.println("Valor n = "+n);

        Element cast_en_XSD = (Element) nodeList.item(n);
        contacto = false;

        NodeList nlcast = cast_en_XSD.getElementsByTagName("Name");
        Element nombreCastElement = (Element) nlcast.item(0);
        String castName = nombreCastElement.getTextContent().trim();

        /*
         * nlcast = cast_en_XSD.getElementsByTagName("id");
         * Element idCastElement = (Element) nlcast.item(0);
         * String castid = (String) idCastElement.getTextContent().trim();
         */
        String castid = cast_en_XSD.getAttribute("id");

        /*
         * nlcast = cast_en_XSD.getElementsByTagName("Email");
         * Element emailCastElement = (Element) nlcast.item(0);
         * String castemail = (String) emailCastElement.getTextContent().trim();
         * 
         * nlcast = cast_en_XSD.getElementsByTagName("Phone");
         * Element phoneCastElement = (Element) nlcast.item(0);
         * String castphone = (String) phoneCastElement.getTextContent().trim();
         */
        String castemail = new String();
        String castphone = new String();

        nlcast = cast_en_XSD.getElementsByTagName("Email");
        if (nlcast.item(0) != null) {
            Element emailCastElement = (Element) nlcast.item(0);
            castemail = (String) emailCastElement.getTextContent().trim();
        } else {
            castemail = "";
        }

        nlcast = cast_en_XSD.getElementsByTagName("Phone");
        if (nlcast.item(0) != null) {
            Element phoneCastElement = (Element) nlcast.item(0);
            castphone = (String) phoneCastElement.getTextContent().trim();
        } else {
            castphone = "";
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
        return cast;
    }




    public static ArrayList<Movie> getQ1Movies(String year) {
        ArrayList<Movie> Movies = new ArrayList<Movie>();
        HashMap<String, Document> DocMap = SINT139P2.getHashMap();



        Document doc = DocMap.get(year);
        NodeList nodeList = null;
        ArrayList<String> xeneros = new ArrayList<String>();
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

            if (nlmovie.item(0) != null) {
                duracion_pelicula = (String) durationMovie.getTextContent().trim();
            }

            nlmovie = movie_en_XSD.getElementsByTagName("Genre");
            for (int i = 0; i < nlmovie.getLength(); i++) {
                Element genreMovie = (Element) nlmovie.item(i);
                String genre_pelicula = (String) genreMovie.getTextContent().trim();
                xeneros.add(genre_pelicula);
            }

            String langs = new String();
            if (!movie_en_XSD.getAttribute("langs").isEmpty()) {
                langs = movie_en_XSD.getAttribute("langs").trim();
            } else {
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

            String sinopsis = new String();
            try {
                sinopsis = (String) SINT139P2.getxpath().evaluate("text()[normalize-space()]", movie_en_XSD,
                        XPathConstants.STRING);
            } catch (XPathException e) {
                e.printStackTrace();
            }

            Movies.add(new Movie(titulo_pelicula, xeneros, duracion_pelicula, langs, sinopsis,"9999")); // meter langs
        }
        Collections.sort(Movies);
        return Movies;

    }

    public static ArrayList<Cast> getQ1Cast(String year, String titulo) {
        ArrayList<Cast> cast = new ArrayList<Cast>();
        HashMap<String, Document> DocMap = SINT139P2.getHashMap();

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

            /*
             * nlcast = cast_en_XSD.getElementsByTagName("id");
             * Element idCastElement = (Element) nlcast.item(0);
             * String castid = (String) idCastElement.getTextContent().trim();
             */
            String castid = cast_en_XSD.getAttribute("id");

            /*
             * nlcast = cast_en_XSD.getElementsByTagName("Email");
             * Element emailCastElement = (Element) nlcast.item(0);
             * String castemail = (String) emailCastElement.getTextContent().trim();
             * 
             * nlcast = cast_en_XSD.getElementsByTagName("Phone");
             * Element phoneCastElement = (Element) nlcast.item(0);
             * String castphone = (String) phoneCastElement.getTextContent().trim();
             */
            String castemail = new String();
            String castphone = new String();

            nlcast = cast_en_XSD.getElementsByTagName("Email");
            if (nlcast.item(0) != null) {
                Element emailCastElement = (Element) nlcast.item(0);
                castemail = (String) emailCastElement.getTextContent().trim();
            } else {
                castemail = "";
            }

            nlcast = cast_en_XSD.getElementsByTagName("Phone");
            if (nlcast.item(0) != null) {
                Element phoneCastElement = (Element) nlcast.item(0);
                castphone = (String) phoneCastElement.getTextContent().trim();
            } else {
                castphone = "";
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