import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by darle on 5/24/2017.
 */

public class main {



    public static void main(String[] args) throws IOException {


        //Lectura de consola
         Scanner scanner = new Scanner(System.in);
         String url = scanner.next();

        //Lectura de la pagina HTML.
        Document doc = Jsoup.connect(url).get();


        //Impresión del HTML completo
        //System.out.print(doc.toString());

        //a) Indicar la cantidad de lineas del recurso retornado.
        int nlineas = doc.html().split("\n").length;
        System.out.println("Lineas: " + nlineas);

        //b) Indicar la cantidad de párrafos (p) que contiene el documento HTML.
        Elements p = doc.getElementsByTag("p");
        System.out.println("Parrafos: " + p.size());

        /*c) Indicar la cantidad de imágenes (img) dentro de los párrafos que
        contiene el archivo HTML.*/
        Elements img = doc.select("p").select("img");
        System.out.println("Imagenes dentro de parrafos: " + img.size());

        /*d) indicar la cantidad de formularios (form) que contiene el HTML por
        categorizando por el método implementado POST o GET. */
        Elements formspost = doc.select("form[method=POST]");
        System.out.println("forms (post): " + formspost.size());

        Elements formsget = doc.select("form[method=GET]");
        System.out.println("forms (get): " + formsget.size());

        /*e) Para cada formulario mostrar los campos del tipo input y su
        respectivo tipo que contiene en el documento HTML.*/

        Elements forms = doc.select("form");

        for( Element element : forms ){
            Elements inputs = element.select( "input" );

            for ( Element input : inputs ){
                String Itype = input.attr( "type" );

                System.out.println( "Campo: " + input + " -> " + "Type: " + Itype );
            }
        }

        /*f) Para cada formulario “parseado”, identificar que el método de envío
        del formulario sea por utilizando el método POST y enviar una
        petición al servidor, con el parámetro llamado asignatura y valor
        practica1 y mostrar la respuesta por la salida estandar.*/

        forms = doc.select("form");

        for(Element element : forms){

            int n = element.attr("action").length();
            String action = element.attr("action") ;

            if(Character.toString(element.attr("action").charAt(0)).matches(".")) {
                action = element.attr("action").substring(1, n);

            }

            Document d = Jsoup.connect(url + action).data("asignatura","practica1").post();
            System.out.println(d);
        }

    }
}
