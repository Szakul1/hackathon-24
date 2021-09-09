package pl.envelo.academy.app.company.structure.service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import pl.envelo.academy.app.company.structure.model.EmployeeModel;

import javax.servlet.ServletOutputStream;
import java.awt.*;
import java.util.List;

public class PDFService {
    private final EmployeeService employeeService;

    public PDFService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void getFile(ServletOutputStream writer) {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, writer);

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of employees", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{4f, 2.25f, 2.25f, 4f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Full Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Phone number", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Supervisor full name", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        List<EmployeeModel> employeeModels = employeeService.list();
        for (EmployeeModel employeeModel : employeeModels) {
            table.addCell(employeeModel.getName() + " " + (employeeModel.getLastName() == null ? "": employeeModel.getLastName()));
            table.addCell(employeeModel.getEmail());
            table.addCell(employeeModel.getPhoneNumber());
            if (employeeModel.getSupervisorId() != null) {
                EmployeeModel supervisor = employeeService.read(employeeModel.getSupervisorId()).get();
                table.addCell(supervisor.getName() + " " + supervisor.getLastName());
            } else {
                table.addCell("--BOSS--");
            }
        }
    }
}
