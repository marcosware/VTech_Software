package VdeVigilancia.Projeto_OS.Dominio;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
    public class CriarPDF {
        public static void main(String[] args) {
            String dest = "exemplo.pdf";

            try {
                PdfWriter writer = new PdfWriter(dest);
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                document.add(new Paragraph("Olá, PDF criado com iText 7!"));

                document.close();

                System.out.println("PDF criado com sucesso!");
                System.out.println("Diretório atual: " + System.getProperty("user.dir"));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
