import java.util.InputMismatchException;
import java.util.Scanner;

public class ConversorMonedasMejorado {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsultaMoneda consulta = new ConsultaMoneda();
        HistorialConversiones historial = new HistorialConversiones();
        int opcion = 0;

        String menu = """
                ****************************************************
                *** Bienvenido al Conversor de Monedas del Cholo ***
                ****************************************************
                
                ++++++++++++++++++++++++
                CONVERSIONES RÁPIDAS:
                ++++++++++++++++++++++++
                1) Dólar (USD) => Peso Chileno (CLP)
                2) Peso Chileno (CLP) => Dólar (USD)
                3) Dólar (USD) => Real Brasileño (BRL)
                4) Real Brasileño (BRL) => Dólar (USD)
                5) Dólar (USD) => Peso Colombiano (COP)
                6) Peso Colombiano (COP) => Dólar (USD)
                ___________________
                CONVERSIÓN LIBRE:
                -------------------
                8) Convertir entre cualquier moneda
                9) Ver lista de monedas disponibles
                ___________________
                HISTORIAL:
                -------------------
                10) Ver historial de conversiones
                
                PARA TERMINAR
                -> 7) Salir <-
                
                Elija una opción válida:
                *************************************************
                """;

        while (opcion != 7) {
            System.out.println(menu);

            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                if (opcion == 7) {
                    System.out.println("\n¡Gracias por usar el Conversor de Monedas del Cholo! ");
                    System.out.println("Finalizando programa...");
                    break;
                }

                if (opcion == 9) {
                    mostrarMonedasDisponibles();
                    continue;
                }

                if (opcion == 10) {
                    historial.mostrarHistorial();
                    continue;
                }

                if (opcion == 8) {
                    realizarConversionLibre(scanner, consulta, historial);
                    continue;
                }

                if (opcion < 1 || opcion > 9) {
                    System.out.println("\nOpción inválida. Por favor, elija una opción del 1 al 9.\n");
                    continue;
                }

                realizarConversionRapida(scanner, consulta, historial,opcion);

            } catch (Exception e) {
                System.out.println("\nError: Entrada inválida. Por favor ingrese un número.\n");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static void realizarConversionLibre(Scanner scanner, ConsultaMoneda consulta, HistorialConversiones historial) {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║        CONVERSIÓN LIBRE DE MONEDAS            ║");
        System.out.println("╚═══════════════════════════════════════════════╝");

        // Opción para ver lista antes de convertir
        System.out.println("\n¿Desea ver la lista de monedas disponibles Primero?");
        System.out.print("Ingrese 's' para ver lista o presione Enter para continuar: ");
        String verLista = scanner.nextLine().trim().toLowerCase();

        if (verLista.equals("s") || verLista.equals("si") || verLista.equals("sí")) {
            mostrarMonedasDisponibles();
            System.out.print("\n>>> Presione Enter para continuar... ");
            scanner.nextLine();
        }

        System.out.println("\n=== INGRESE LOS CÓDIGOS DE MONEDA ===");
        System.out.println("Ejemplos: USD, EUR, CLP, KES, XOF, BRL, etc.");
        System.out.println("Tip: Escriba '0' en cualquier momento para volver al menú principal\n");

        // Moneda origen con opción de cancelar
        System.out.print("Moneda origen (3 letras) o '0' para volver: ");
        String monedaOrigen = scanner.nextLine().toUpperCase().trim();

        if (monedaOrigen.equals("0")) {
            System.out.println("\nVolviendo al menú principal...\n");
            return;
        }

        if (monedaOrigen.isEmpty() || monedaOrigen.length() != 3) {
            System.out.println("\n❌ Error: El código debe tener exactamente 3 letras.\n");
            return;
        }

        // Moneda destino con opción de cancelar
        System.out.print("Moneda destino (3 letras) o '0' para volver: ");
        String monedaDestino = scanner.nextLine().toUpperCase().trim();

        if (monedaDestino.equals("0")) {
            System.out.println("\nVolviendo al menú principal...\n");
            return;
        }

        if (monedaDestino.isEmpty() || monedaDestino.length() != 3) {
            System.out.println("\n❌ Error: El código debe tener exactamente 3 letras.\n");
            return;
        }

        // Validar que no sean iguales
        if (monedaOrigen.equals(monedaDestino)) {
            System.out.println("\n❌ Error: Las monedas deben ser diferentes.\n");
            return;
        }

        // Valor a convertir con opción de cancelar
        System.out.print("\nValor a convertir (o '0' para volver): ");
        try {
            double valor = scanner.nextDouble();
            scanner.nextLine();

            if (valor == 0) {
                System.out.println("\nVolviendo al menú principal...\n");
                return;
            }

            if (valor < 0) {
                System.out.println("\n❌ Error: El valor no puede ser negativo.\n");
                return;
            }

            System.out.println("\n⏳ Consultando tasa de cambio...");
            Moneda moneda = consulta.buscarMoneda(monedaOrigen, monedaDestino);

            if (moneda != null) {
                double valorConvertido = valor * moneda.tasaConversion();

                System.out.println("\n╔═══════════════════════════════════════════════╗");
                System.out.println("║           ✓ CONVERSIÓN EXITOSA                ║");
                System.out.println("╚═══════════════════════════════════════════════╝");
                System.out.printf("\n  %.2f [%s]  =  %.2f [%s]\n",
                        valor, monedaOrigen, valorConvertido, monedaDestino);
                System.out.printf("\n  Tasa: 1 %s = %.6f %s\n",
                        monedaOrigen, moneda.tasaConversion(), monedaDestino);
                System.out.println("\n═══════════════════════════════════════════════\n");

                historial.guardarConversion(monedaOrigen, monedaDestino, valor, valorConvertido, moneda.tasaConversion());
                return;

            } else {
                System.out.println("\n╔═══════════════════════════════════════════════╗");
                System.out.println("║              ✗ ERROR EN CONVERSIÓN            ║");
                System.out.println("╚═══════════════════════════════════════════════╝");
                System.out.println("\nPosibles causas:");
                System.out.println("  • Código de moneda inválido");
                System.out.println("  • Moneda no disponible en la API");
                System.out.println("  • Error de conexión a Internet");
                System.out.println("\nVerifique los códigos e intente nuevamente.\n");
            }
        } catch (InputMismatchException e) {  // ← Cambiar a InputMismatchException
            System.out.println("\n❌ Error: Debe ingresar un número válido.\n");
            scanner.nextLine();
        } catch (Exception e) {  // ← Agregar este catch adicional para debug
            System.out.println("\n❌ Error inesperado: " + e.getMessage() + "\n");
            scanner.nextLine();
        }
    }

    private static void realizarConversionRapida(Scanner scanner, ConsultaMoneda consulta, HistorialConversiones historial,  int opcion) {
        System.out.print("\nIngrese el valor que desea convertir: ");

        try {
            double valor = scanner.nextDouble();
            scanner.nextLine();

            if (valor <= 0) {
                System.out.println("\n❌ Error: El valor debe ser mayor que cero.\n");
                return;
            }

            String monedaOrigen = "";
            String monedaDestino = "";

            switch (opcion) {
                case 1:
                    monedaOrigen = "USD";
                    monedaDestino = "CLP";
                    break;
                case 2:
                    monedaOrigen = "CLP";
                    monedaDestino = "USD";
                    break;
                case 3:
                    monedaOrigen = "USD";
                    monedaDestino = "BRL";
                    break;
                case 4:
                    monedaOrigen = "BRL";
                    monedaDestino = "USD";
                    break;
                case 5:
                    monedaOrigen = "USD";
                    monedaDestino = "COP";
                    break;
                case 6:
                    monedaOrigen = "COP";
                    monedaDestino = "USD";
                    break;
            }

            System.out.println("\n⏳ Consultando tasa de cambio...");
            Moneda moneda = consulta.buscarMoneda(monedaOrigen, monedaDestino);

            if (moneda != null) {
                double valorConvertido = valor * moneda.tasaConversion();
                System.out.println("\n====================================================================================");
                System.out.printf("==  ✓ El valor %.2f [%s] corresponde al valor final de =>>> %.2f [%s]",
                        valor, monedaOrigen, valorConvertido, monedaDestino);
                System.out.println ("\n====================================================================================\n");

                historial.guardarConversion(monedaOrigen, monedaDestino, valor, valorConvertido, moneda.tasaConversion());

            } else {
                System.out.println("\n❌ Error al obtener la tasa de conversión.");
                System.out.println("Verifique su conexión a Internet e intente nuevamente.\n");
            }
        } catch (Exception e) {
            System.out.println("\n❌ Error: Debe ingresar un número válido.\n");
            scanner.nextLine();
        }
    }

    private static void mostrarMonedasDisponibles() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          CÓDIGOS DE MONEDAS DISPONIBLES                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ ÁFRICA                                                  │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│ KES  - Chelín Keniano (Kenia)                           │");
        System.out.println("│ TZS  - Chelín Tanzano (Tanzania)                        │");
        System.out.println("│ UGX  - Chelín Ugandés (Uganda)                          │");
        System.out.println("│ ZAR  - Rand Sudafricano (Sudáfrica)                     │");
        System.out.println("│ NGN  - Naira Nigeriana (Nigeria)                        │");
        System.out.println("│ EGP  - Libra Egipcia (Egipto)                           │");
        System.out.println("│ MAD  - Dirham Marroquí (Marruecos)                      │");
        System.out.println("│ XOF  - Franco CFA de África Occidental                  │");
        System.out.println("│ XAF  - Franco CFA de África Central                     │");
        System.out.println("└─────────────────────────────────────────────────────────┘");

        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ AMÉRICA                                                 │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│ USD  - Dólar Estadounidense (Estados Unidos)            │");
        System.out.println("│ CAD  - Dólar Canadiense (Canadá)                        │");
        System.out.println("│ MXN  - Peso Mexicano (México)                           │");
        System.out.println("│ BRL  - Real Brasileño (Brasil)                          │");
        System.out.println("│ ARS  - Peso Argentino (Argentina)                       │");
        System.out.println("│ CLP  - Peso Chileno (Chile)                             │");
        System.out.println("│ COP  - Peso Colombiano (Colombia)                       │");
        System.out.println("│ PEN  - Sol Peruano (Perú)                               │");
        System.out.println("│ UYU  - Peso Uruguayo (Uruguay)                          │");
        System.out.println("└─────────────────────────────────────────────────────────┘");

        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ EUROPA                                                  │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│ EUR  - Euro (Unión Europea)                             │");
        System.out.println("│ GBP  - Libra Esterlina (Reino Unido)                    │");
        System.out.println("│ CHF  - Franco Suizo (Suiza)                             │");
        System.out.println("│ SEK  - Corona Sueca (Suecia)                            │");
        System.out.println("│ NOK  - Corona Noruega (Noruega)                         │");
        System.out.println("│ DKK  - Corona Danesa (Dinamarca)                        │");
        System.out.println("│ PLN  - Zloty Polaco (Polonia)                           │");
        System.out.println("└─────────────────────────────────────────────────────────┘");

        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ ASIA                                                    │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│ JPY  - Yen Japonés (Japón)                              │");
        System.out.println("│ CNY  - Yuan Chino (China)                               │");
        System.out.println("│ INR  - Rupia India (India)                              │");
        System.out.println("│ KRW  - Won Surcoreano (Corea del Sur)                   │");
        System.out.println("│ THB  - Baht Tailandés (Tailandia)                       │");
        System.out.println("│ SGD  - Dólar de Singapur (Singapur)                     │");
        System.out.println("│ MYR  - Ringgit Malayo (Malasia)                         │");
        System.out.println("│ IDR  - Rupia Indonesia (Indonesia)                      │");
        System.out.println("│ PHP  - Peso Filipino (Filipinas)                        │");
        System.out.println("└─────────────────────────────────────────────────────────┘");

        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ OCEANÍA                                                 │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│ AUD  - Dólar Australiano (Australia)                    │");
        System.out.println("│ NZD  - Dólar Neozelandés (Nueva Zelanda)                │");
        System.out.println("└─────────────────────────────────────────────────────────┘");

        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ ORIENTE MEDIO                                           │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│ AED  - Dirham de Emiratos Árabes Unidos                 │");
        System.out.println("│ SAR  - Riyal Saudí (Arabia Saudita)                     │");
        System.out.println("│ ILS  - Shekel Israelí (Israel)                          │");
        System.out.println("│ TRY  - Lira Turca (Turquía)                             │");
        System.out.println("└─────────────────────────────────────────────────────────┘");

        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║ NOTA: Hay más de 168 monedas disponibles en total.         ║");
        System.out.println("║ Estas son las más comunes y utilizadas.                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
    }
}