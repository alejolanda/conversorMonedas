import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaMoneda {

    // Nueva API gratuita que NO requiere clave
    private static final String API_URL = "https://open.er-api.com/v6/latest/";

    public Moneda buscarMoneda(String monedaOrigen, String monedaDestino) {
        try {
            // Construir URL: obtiene todas las tasas para la moneda origen
            String url = API_URL + monedaOrigen;

            System.out.println("🔍 Consultando: " + url);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            String jsonString = response.body();
            //System.out.println("📥 JSON recibido: " + jsonString.substring(0, Math.min(200, jsonString.length())) + "...");

            if (response.statusCode() == 200) {
                JsonObject jsonResponse = JsonParser.parseString(jsonString).getAsJsonObject();

                // Verificar que la respuesta sea exitosa
                if (jsonResponse.has("result") && jsonResponse.get("result").getAsString().equals("success")) {

                    // Obtener el objeto "rates" que contiene todas las tasas
                    JsonObject rates = jsonResponse.getAsJsonObject("rates");

                    // Verificar si existe la tasa para la moneda destino
                    if (rates.has(monedaDestino)) {
                        double rate = rates.get(monedaDestino).getAsDouble();

                        System.out.println("✅ Tasa obtenida: 1 " + monedaOrigen + " = " + rate + " " + monedaDestino);
                        return new Moneda(monedaOrigen, monedaDestino, rate);
                    } else {
                        System.out.println("❌ Moneda destino '" + monedaDestino + "' no encontrada");
                        System.out.println("💡 Verifique que el código sea correcto (ej: USD, EUR, CLP)");
                    }
                } else {
                    System.out.println("❌ Error en la respuesta de la API");
                    if (jsonResponse.has("error-type")) {
                        System.out.println("Tipo de error: " + jsonResponse.get("error-type").getAsString());
                    }
                }
            } else {
                System.out.println("❌ Error HTTP: " + response.statusCode());
            }

        } catch (Exception e) {
            System.out.println("❌ Error al consultar la API: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}