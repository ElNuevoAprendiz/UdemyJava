package manejoDeArchivos;

import javax.swing .*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt .*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;





    public class PDFTranslatorApp extends JFrame implements ActionListener {

        private JButton selectFileButton;
        private JTextArea sourceTextArea;
        private JComboBox<String> sourceLangCombo;
        private JComboBox<String> targetLangCombo;
        private JButton translateButton;
        private JTextArea translatedTextArea;
        private JFileChooser fileChooser;

        private static final String LIBRETRANSLATE_API_URL = "https://libretranslate.de/translate";
        private static final String[] LANGUAGES = {"es", "en", "fr", "de", "pt", "it"}; // Add more if needed
        private static final String[] LANGUAGE_NAMES = {"Español", "Inglés", "Francés", "Alemán", "Portugués", "Italiano"};

        public PDFTranslatorApp() {
            setTitle("PDF Translator");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout(10, 10));
            setPreferredSize(new Dimension(800, 600));

            // File Selection Panel
            JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            selectFileButton = new JButton("Seleccionar PDF");
            selectFileButton.addActionListener(this);
            fileChooser = new JFileChooser();
            FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter("Archivos PDF (*.pdf)", "pdf");
            fileChooser.setFileFilter(pdfFilter);
            filePanel.add(selectFileButton);
            add(filePanel, BorderLayout.NORTH);

            // Text Areas and Language Selection Panel
            JPanel textPanel = new JPanel(new GridLayout(1, 2, 10, 10));

            JPanel sourcePanel = new JPanel(new BorderLayout());
            JLabel sourceLabel = new JLabel("Texto Original:");
            sourceTextArea = new JTextArea();
            sourceTextArea.setLineWrap(true);
            sourceTextArea.setWrapStyleWord(true);
            JScrollPane sourceScrollPane = new JScrollPane(sourceTextArea);
            JPanel sourceLangPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel sourceLangLabel = new JLabel("Idioma Original:");
            sourceLangCombo = new JComboBox<>(LANGUAGE_NAMES);
            sourceLangPanel.add(sourceLangLabel);
            sourceLangPanel.add(sourceLangCombo);
            sourcePanel.add(sourceLabel, BorderLayout.NORTH);
            sourcePanel.add(sourceScrollPane, BorderLayout.CENTER);
            sourcePanel.add(sourceLangPanel, BorderLayout.SOUTH);
            textPanel.add(sourcePanel);

            JPanel targetPanel = new JPanel(new BorderLayout());
            JLabel targetLabel = new JLabel("Texto Traducido:");
            translatedTextArea = new JTextArea();
            translatedTextArea.setLineWrap(true);
            translatedTextArea.setWrapStyleWord(true);
            translatedTextArea.setEditable(false);
            JScrollPane translatedScrollPane = new JScrollPane(translatedTextArea);
            JPanel targetLangPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel targetLangLabel = new JLabel("Idioma Destino:");
            targetLangCombo = new JComboBox<>(LANGUAGE_NAMES);
            targetLangPanel.add(targetLangLabel);
            targetLangPanel.add(targetLangCombo);
            targetPanel.add(targetLabel, BorderLayout.NORTH);
            targetPanel.add(translatedScrollPane, BorderLayout.CENTER);
            targetPanel.add(targetLangPanel, BorderLayout.SOUTH);
            textPanel.add(targetPanel);

            add(textPanel, BorderLayout.CENTER);

            // Translate Button Panel
            JPanel translatePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            translateButton = new JButton("Traducir");
            translateButton.addActionListener(this);
            translatePanel.add(translateButton);
            add(translatePanel, BorderLayout.SOUTH);

            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == selectFileButton) {
                int returnVal = fileChooser.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        String text = extractTextFromPdf(selectedFile);
                        sourceTextArea.setText(text);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error al leer el archivo PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (e.getSource() == translateButton) {
                String textToTranslate = sourceTextArea.getText();
                if (!textToTranslate.isEmpty()) {
                    String sourceLangCode = LANGUAGES[sourceLangCombo.getSelectedIndex()];
                    String targetLangCode = LANGUAGES[targetLangCombo.getSelectedIndex()];
                    translateText(textToTranslate, sourceLangCode, targetLangCode);
                } else {
                    JOptionPane.showMessageDialog(this, "Por favor, selecciona un archivo PDF primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        private String extractTextFromPdf(File pdfFile) throws IOException {
            try (PDDocument document = PDDocument.load(pdfFile)) {
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(document);
            }
        }

        private void translateText(String text, String sourceLang, String targetLang) {
            SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
                @Override
                protected String doInBackground() throws Exception {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(LIBRETRANSLATE_API_URL))
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .POST(HttpRequest.BodyPublishers.ofString(
                                    "q=" + java.net.URLEncoder.encode(text, "UTF-8") +
                                            "&source=" + sourceLang +
                                            "&target=" + targetLang
                            ))
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if (response.statusCode() == 200) {
                        String responseBody = response.body();
                        JsonParser parser = new JsonParser();
                        JsonObject jsonObject = parser.parse(responseBody).getAsJsonObject();
                        if (jsonObject.has("translatedText")) {
                            return jsonObject.get("translatedText").getAsString();
                        } else {
                            return "Error: No se encontró el texto traducido en la respuesta.";
                        }
                    } else {
                        return "Error en la traducción: Código de estado " + response.statusCode();
                    }
                }

                @Override
                protected void done() {
                    try {
                        String translatedText = get();
                        translatedTextArea.setText(translatedText);
                    } catch (Exception ex) {
                        translatedTextArea.setText("Error al traducir: " + ex.getMessage());
                    }
                    translateButton.setEnabled(true); // Re-enable the button after translation
                }

                @Override
                protected void process(java.util.List<Void> chunks) {
                    translateButton.setEnabled(false); // Disable the button during translation
                    translatedTextArea.setText("Traduciendo...");
                }
            };
            worker.execute();
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(PDFTranslatorApp::new);
        }
    }


/*

        **Explicación del Código:**

            1.  **Importaciones:** Se importan las clases necesarias para la interfaz gráfica Swing, manejo de archivos, peticiones HTTP, y la librería PDFBox para extraer texto de PDFs y Gson para parsear la respuesta JSON de la API.

2.  **Clase `PDFTranslatorApp`:**
            * Extiende `JFrame` para crear la ventana de la aplicación.
    * Implementa `ActionListener` para manejar los eventos de los botones.
    * Declara los componentes de la interfaz gráfica: botones, áreas de texto, combos de idioma y el selector de archivos.
            * Define la URL de la API de LibreTranslate y arrays para los códigos y nombres de los idiomas.

            3.  **Constructor `PDFTranslatorApp()`:**
            * Inicializa la ventana y establece su diseño.
    * Crea y configura el botón para seleccionar el archivo PDF (`selectFileButton`) y el `JFileChooser` con un filtro para archivos PDF.
            * Crea dos `JTextArea` (`sourceTextArea` y `translatedTextArea`) para mostrar el texto original y el traducido, respectivamente, dentro de `JScrollPane` para permitir el desplazamiento.
            * Crea dos `JComboBox` (`sourceLangCombo` y `targetLangCombo`) para seleccionar los idiomas de origen y destino.
            * Crea y configura el botón de traducción (`translateButton`).
            * Organiza los componentes usando `BorderLayout` y `GridLayout`.
            * Finalmente, hace visible la ventana.

            4.  **`actionPerformed(ActionEvent e)`:**
            * Este método se llama cuando se hace clic en un botón.
    * Si el evento proviene de `selectFileButton`:
            * Muestra el `JFileChooser` para que el usuario seleccione un archivo PDF.
            * Si se selecciona un archivo, llama a `extractTextFromPdf()` para obtener el texto del PDF y lo muestra en `sourceTextArea`.
            * Maneja las posibles `IOException` durante la lectura del archivo.
    * Si el evento proviene de `translateButton`:
            * Obtiene el texto del `sourceTextArea`.
            * Verifica que el texto no esté vacío.
        * Obtiene los códigos de idioma seleccionados de los `JComboBox`.
            * Llama al método `translateText()` para realizar la traducción.

            5.  **`extractTextFromPdf(File pdfFile)`:**
            * Recibe un objeto `File` representando el archivo PDF.
            * Utiliza la librería Apache PDFBox (`PDDocument` y `PDFTextStripper`) para abrir el archivo PDF y extraer su contenido de texto.
            * Cierra el documento PDF después de la extracción.
            * Lanza una `IOException` si ocurre un error durante la lectura del archivo.

6.  **`translateText(String text, String sourceLang, String targetLang)`:**
            * Recibe el texto a traducir, el código del idioma de origen y el código del idioma de destino.
    * Utiliza un `SwingWorker` para realizar la petición HTTP a la API de LibreTranslate en un hilo separado, evitando bloquear la interfaz gráfica.
            * **`doInBackground()`:**
            * Crea un `HttpClient` para realizar la petición HTTP.
        * Construye una petición `POST` a la URL de la API de LibreTranslate.
            * Establece el encabezado `Content-Type` como `application/x-www-form-urlencoded`.
            * Crea el cuerpo de la petición con los parámetros `q` (texto a traducir), `source` (idioma de origen) y `target` (idioma de destino), codificándolos para la URL.
            * Envía la petición y obtiene la respuesta.
        * Verifica el código de estado de la respuesta (debería ser 200 para éxito).
            * Parsea la respuesta JSON utilizando Gson (`JsonParser` y `JsonObject`) para extraer el valor del campo `"translatedText"`.
            * Devuelve el texto traducido o un mensaje de error si la respuesta no es la esperada.
            * **`done()`:**
            * Este método se ejecuta en el hilo de eventos de Swing una vez que la tarea en `doInBackground()` ha terminado.
            * Obtiene el resultado de la traducción del `SwingWorker`.
            * Muestra el texto traducido en `translatedTextArea` o muestra un mensaje de error si hubo algún problema durante la traducción.
        * Re-habilita el botón de traducción.
    * **`process()`:**
            * Este método se ejecuta periódicamente en el hilo de eventos de Swing mientras la tarea en `doInBackground()` está en curso.
        * Aquí se deshabilita el botón de traducción y se muestra un mensaje de "Traduciendo..." en el área de texto traducido para indicar la actividad.

            7.  **`main(String[] args)`:**
            * El punto de entrada de la aplicación.
    * Utiliza `SwingUtilities.invokeLater()` para crear y mostrar la interfaz gráfica en el hilo de eventos de Swing, asegurando la seguridad de los hilos en las aplicaciones Swing.

**Antes de Ejecutar:**

            1.  **Asegúrate de tener las dependencias necesarias en tu proyecto:**
            * **Apache PDFBox:** Para trabajar con archivos PDF. Puedes agregarlo a tu proyecto Maven o Gradle:
            ```xml
            <dependency>
            <groupId>org.apache.pdfbox</groupId>
            <artifactId>pdfbox</artifactId>
            <version>2.0.29</version>
        </dependency>
            ```
            ```gradle
    // Gradle
    implementation 'org.apache.pdfbox:pdfbox:2.0.29'
            ```
            * **Gson:** Para parsear la respuesta JSON de la API.
            ```xml
            <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.10.1</version>
        </dependency>
            ```
            ```gradle
    // Gradle
    implementation 'com.google.code.gson:gson:2.10.1'
            ```

            2.  **Conexión a Internet:** La aplicación necesita una conexión a Internet activa para comunicarse con la API de LibreTranslate.

**Cómo Usar:**

            1.  Ejecuta el programa Java.
            2.  Haz clic en el botón "Seleccionar PDF".
            3.  Navega hasta el archivo PDF que deseas traducir y selecciónalo. El texto del PDF aparecerá en el área de texto "Texto Original".
            4.  Selecciona el idioma original del PDF en el desplegable "Idioma Original".
            5.  Selecciona el idioma al que deseas traducir en el desplegable "Idioma Destino".
            6.  Haz clic en el botón "Traducir".
            7.  Espera a que la API de LibreTranslate procese la traducción. El resultado aparecerá en el área de texto "Texto Traducido".

            **Limitaciones:**

            * **Calidad de la Traducción:** La calidad de la traducción dependerá de la API de LibreTranslate. Las traducciones automáticas pueden no ser perfectas.
* **Tamaño del PDF:** Para archivos PDF muy grandes, la extracción del texto puede llevar tiempo.
* **Formato del PDF:** La extracción de texto puede no ser perfecta para PDFs con diseños complejos, imágenes con texto incrustado o PDFs escaneados sin OCR (reconocimiento óptico de caracteres).
            * **Tasas de Límite de la API:** La API de LibreTranslate puede tener limitaciones en la cantidad de solicitudes que se pueden realizar en un cierto período de tiempo. Si realizas demasiadas traducciones rápidamente, podrías experimentar errores.
* **Manejo de Errores:** El manejo de errores en este ejemplo es básico. Para una aplicación más robusta, se necesitarían mecanismos de manejo de errores más detallados.
}


 */