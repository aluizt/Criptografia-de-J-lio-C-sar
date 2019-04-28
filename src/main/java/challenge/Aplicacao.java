package challenge;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;



public class Aplicacao{

    public static void main(String[] args) throws IOException, URISyntaxException, NoSuchAlgorithmException {
        String token = "5bc6a22805d5383dba74e8240821a3cb2dc69c91";

        //Efetua a requisição e salva o Json na variavel json
        String json = Request
                .Get(new URI("https://api.codenation.dev/v1/challenge/dev-ps/generate-data?token=" + token))
                .execute()
                .returnContent()
                .asString();
       
 
        //Instancia um novo objeto Gson e configura conforme necessario,
        Gson gson = new GsonBuilder()
        		.setPrettyPrinting()
        		.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        		.create();
        
        //Cria a instancia de answer com os dados do objeto json 
        Answer answer = gson.fromJson(json, Answer.class);
        
        answer.setDecifrado(CriptografiaJulioCesar.encrypt(answer.getCifrado(), answer.getNumero_casas()));
        
        answer.setResumo_criptografico(ResumoCriptograficoSHA1.execute(answer.getDecifrado()));

        File answerJson = new File("answer.json");
        
        FileWriter writer = new FileWriter(answerJson);
        
        gson.toJson(answer, writer);
        
        writer.flush();
        
        writer.close();

        HttpEntity form =  MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .setCharset(Charset.forName("UTF-8"))
                .addBinaryBody("answer", answerJson)
                .build();

            
        Content responsta = Request
                .Post(new URI("https://api.codenation.dev/v1/challenge/dev-ps/submit-solution?token=" + token))
                .body(form)
                .execute()
                .returnContent();

        System.out.println("response = " + responsta);
    }

}
