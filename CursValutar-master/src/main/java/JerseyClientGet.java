import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.thoughtworks.xstream.XStream;

public class JerseyClientGet {
    public static void main(String[] args) {

        XStream xstream = new XStream();
        xstream.processAnnotations(ValCurs.class);
        xstream.processAnnotations(Valute.class);
        try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("https://www.bnm.md/en/official_exchange_rates?get_xml=1&date=02.01.2020");

            ClientResponse response = webResource.accept("application/xml")
                    .get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            String output = response.getEntity(String.class);

            ValCurs valCurs = (ValCurs)xstream.fromXML(output);

            for(Valute currentVal : valCurs.getValutes())  {
                System.out.println(currentVal.getName() + "..........."  + currentVal.getValue());
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    
}
