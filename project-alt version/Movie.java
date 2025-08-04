package p2;
import java.util.ArrayList;

public class Movie implements Comparable<Movie> {

    private String titulo;
    private ArrayList<String> genre;
    private String duracion;
    // private ArrayList<String> langs;
    private String langs;
    private String sinopsis;

    // private String duration;
public static void main(String[] args) {
    System.out.println("Main de Movie");
}
    public Movie(String titulo, ArrayList<String> genre, String duracion, String langs) {
        this.titulo = titulo;
        this.genre = genre;
        this.duracion = duracion;
        this.langs = langs;
    }

    public String toString() {

        String cadena = "Titulo='" + this.titulo + "'---Genero='" + this.genre + "'---Duracion:'" + this.duracion + "'"
                + "'---Langs:'" + this.langs + "'";
        return cadena;
    }

    public String toStringHTML() {

        String cadena = "---Idiomas='" + this.langs + "'---Genero='" + this.genre + "'---Sinopsis:'" + this.sinopsis
                + "'";
        return cadena;
    }

    public String getLangs() {
        return this.langs;
    }

    public String getSinopsis() {
        return this.sinopsis;
    }

    public ArrayList<String> getGenresXML2() {
        return this.genre;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getGenresXML() {
        String genres = "";
        int iteracion = 0;
        if ((this.genre.size()) == 1) {
            return this.genre.get(0);
        } else {
            for (int i = 0; i < (this.genre.size() - 1); i++) {
                genres.concat(this.genre.get(i));
                genres.concat(",");
                iteracion++;
            }
            genres.concat(this.genre.get(iteracion));
        }
        return genres;
    }

    public int compareTo(Movie MovieExt) {

        if (this.langs.length() > MovieExt.langs.length()) {
            return 1;
        }
        if (MovieExt.langs.length() > this.langs.length()) {
            return -1;
        }
        if (this.langs.length() == MovieExt.langs.length()) { // Mesmos langs miramos por orde alfabético

            if (this.titulo.compareTo(MovieExt.titulo) == 1) {
                return 1;
            }

            else if (this.titulo.compareTo(MovieExt.titulo) == -1) {
                return -1;
            }

            else {
                return this.titulo.compareTo(MovieExt.titulo);
            }
        }
        return 0;
    }

}