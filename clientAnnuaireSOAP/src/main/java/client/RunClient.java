package client;

import service.Annuaire;
import service.AnnuaireService;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

public class RunClient {

    public static void main(String [] args) {

        try {
            URL url = new URL("http://localhost:9191/annuaire?wsdl");
            AnnuaireService serviceWithUrl =
                    new AnnuaireService(url, new QName("http://service/","AnnuaireService"));
            Annuaire servicePortWithUrl = serviceWithUrl.getAnnuairePort();
            System.out.println(servicePortWithUrl.getAllNom());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

}
