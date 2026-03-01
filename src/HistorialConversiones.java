import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para gestionar el historial de conversiones
 * Guarda las conversiones en un archivo JSON con formato legible
 */
public class HistorialConversiones {

    private static final String ARCHIVO_HISTORIAL = "historial_conversiones.json";
    private final Gson gson;

    public HistorialConversiones() {
        // Crear Gson con adaptador para LocalDateTime
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
                                context.serialize(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                                LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .create();
    }

    /**
     * Guarda una conversión en el historial
     */
    public void guardarConversion(String monedaOrigen, String monedaDestino,
                                  double valorOrigen, double valorDestino, double tasa) {
        try {
            // Leer historial existente
            List<RegistroConversion> historial = leerHistorial();

            // Crear nuevo registro
            RegistroConversion nuevoRegistro = new RegistroConversion(
                    monedaOrigen,
                    monedaDestino,
                    valorOrigen,
                    valorDestino,
                    tasa,
                    LocalDateTime.now()
            );

            // Agregar al historial
            historial.add(nuevoRegistro);

            // Guardar en archivo
            try (FileWriter writer = new FileWriter(ARCHIVO_HISTORIAL)) {
                gson.toJson(historial, writer);
            }

            System.out.println("✅ Conversión guardada en historial");

        } catch (IOException e) {
            System.out.println("⚠️ No se pudo guardar en el historial: " + e.getMessage());
        }
    }

    /**
     * Lee el historial existente del archivo
     */
    /**
     * Lee el historial existente del archivo
     */
    private List<RegistroConversion> leerHistorial() {
        try (FileReader reader = new FileReader(ARCHIVO_HISTORIAL)) {
            Type listType = new TypeToken<ArrayList<RegistroConversion>>(){}.getType();
            List<RegistroConversion> historial = gson.fromJson(reader, listType);
            return historial != null ? historial : new ArrayList<>();
        } catch (java.io.EOFException e) {
            // Archivo corrupto o vacío - crear uno nuevo
            System.out.println("⚠️ Archivo de historial corrupto. Creando uno nuevo...");
            return new ArrayList<>();
        } catch (IOException e) {
            // Si el archivo no existe, retornar lista vacía
            return new ArrayList<>();
        } catch (Exception e) {
            // Cualquier otro error de parseo
            System.out.println("⚠️ Error al leer historial: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Muestra el historial de conversiones
     */
    public void mostrarHistorial() {
        List<RegistroConversion> historial = leerHistorial();

        if (historial.isEmpty()) {
            System.out.println("\n📋 El historial está vacío. No hay conversiones registradas.\n");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║              HISTORIAL DE CONVERSIONES                        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        for (int i = 0; i < historial.size(); i++) {
            RegistroConversion registro = historial.get(i);
            System.out.printf("📌 Conversión #%d\n", i + 1);
            System.out.printf("   Fecha: %s\n", registro.fechaHora.format(formatter));
            System.out.printf("   %.2f %s → %.2f %s\n",
                    registro.valorOrigen, registro.monedaOrigen,
                    registro.valorDestino, registro.monedaDestino);
            System.out.printf("   Tasa: 1 %s = %.6f %s\n",
                    registro.monedaOrigen, registro.tasa, registro.monedaDestino);
            System.out.println("   ─────────────────────────────────────────────");
        }

        System.out.printf("\nTotal de conversiones: %d\n\n", historial.size());
    }
    //conversion interna
    private static class RegistroConversion {
        String monedaOrigen;
        String monedaDestino;
        double valorOrigen;
        double valorDestino;
        double tasa;
        LocalDateTime fechaHora;

        public RegistroConversion(String monedaOrigen, String monedaDestino,
                                  double valorOrigen, double valorDestino,
                                  double tasa, LocalDateTime fechaHora) {
            this.monedaOrigen = monedaOrigen;
            this.monedaDestino = monedaDestino;
            this.valorOrigen = valorOrigen;
            this.valorDestino = valorDestino;
            this.tasa = tasa;
            this.fechaHora = fechaHora;
        }
    }
}