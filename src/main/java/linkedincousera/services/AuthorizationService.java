package linkedincoursera.services;

import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Component
public class AuthorizationService {
    public static String access_token = "";

    public String authorizeLinkedinByPost(String code, String redirect_uri, String apikey, String apisecret) {
        URL obj = null;

        String newUri = "https://www.linkedin.com/uas/oauth2/accessToken";
        try {

            obj = new URL(newUri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpsURLConnection con = null;
        try {
            con = (HttpsURLConnection) obj.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //add reuqest header
        try {
            con.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty
                ("Content-Type", "application/x-www-form-urlencoded");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = null;
        String contents ="";
        try {
            wr = new DataOutputStream(con.getOutputStream());
            contents = "grant_type=authorization_code&code="+code+"&redirect_uri="+redirect_uri+"&client_id="+apikey+"&client_secret="+apisecret;
            wr.writeBytes(contents);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Sending 'POST' request to URL : " + newUri);
        System.out.println("Post parameters : " + contents);

        BufferedReader in = null;
        StringBuilder response = new StringBuilder();
        try {
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        access_token = response.substring(17,response.length()-23);
        System.out.println("Access_Token : " + access_token);
        return access_token;
    }
}
