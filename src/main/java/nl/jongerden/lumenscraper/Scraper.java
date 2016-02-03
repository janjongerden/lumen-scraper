package nl.jongerden.lumenscraper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


/**
 * Created by jan on 13 January 2016.
 */
public class Scraper {
    public static String getAllMovies() throws IOException {

        String url = //"https://selfsolve.apple.com/wcResults.do";
        "https://www.filmhuis-lumen.nl/wp-admin/admin-ajax.php?action=wpt_pagination_update_listing&args%5Bcat%5D=false&args%5Bcategory%5D=false&args%5Bcategory__and%5D=false&args%5Bcategory__in%5D=false&args%5Bcategory__not_in%5D=false&args%5Bcategory_name%5D=false&args%5Bday%5D=false&args%5Bend%5D=false&args%5Bgroupby%5D=day&args%5Blimit%5D=false&args%5Bmonth%5D=false&args%5Border%5D=asc&args%5Bpaginateby%5D%5B%5D=day&args%5Bpaginateby%5D%5B%5D=category&args%5Bpost__in%5D=false&args%5Bpost__not_in%5D=false&args%5Bseason%5D=false&args%5Bstart%5D=today&args%5Byear%5D=false&filter%5Bname%5D=wpt_day&filter%5Bvalue%5D=";

//        String params = "action=wpt_pagination_update_listing&filter%5Bname%5D=wpt_day&filter%5Bvalue%5D=&args%5Bpaginateby%5D" +
//                "%5B%5D=day&args%5Bpaginateby%5D%5B%5D=category&args%5Bpost__in%5D=false&args%5Bpost__not_in%5D=false" +
//                "&args%5Bcategory%5D=false&args%5Bcat%5D=false&args%5Bcategory_name%5D=false&args%5Bcategory__and%5D=false" +
//                "&args%5Bcategory__in%5D=false&args%5Bcategory__not_in%5D=false&args%5Bday%5D=false&args%5Bmonth%5D=false" +
//                "&args%5Byear%5D=false&args%5Bseason%5D=false&args%5Bstart%5D=today&args%5Bend%5D=false&args%5Bgroupby" +
//                "%5D=day&args%5Blimit%5D=false&args%5Border%5D=asc";

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        HttpEntity multipart = builder.build();
        httpPost.setEntity(multipart);
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }

    public static String getMovieInfo(String url) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        HttpResponse response = null;
        String html = null;
        try {
            response = httpClient.execute(httpGet);
            html = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("html ziet er zo uit :" + html);
        return html;
    }
}
