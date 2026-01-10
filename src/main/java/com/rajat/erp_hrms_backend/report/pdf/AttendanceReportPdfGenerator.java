package com.rajat.erp_hrms_backend.report.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.rajat.erp_hrms_backend.report.dto.MonthlyAttendanceReport;

import java.io.ByteArrayOutputStream;

public class AttendanceReportPdfGenerator {

    public static byte[] generate(MonthlyAttendanceReport report) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document();
        PdfWriter.getInstance(document, out);

        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        document.add(new Paragraph("Monthly Attendance Report", titleFont));
        document.add(new Paragraph(" "));

        document.add(new Paragraph("Employee: " + report.getEmployeeName(), normalFont));
        document.add(new Paragraph("Employee ID: " + report.getEmployeeId(), normalFont));
        document.add(new Paragraph("Month: " + report.getMonth() + "/" + report.getYear(), normalFont));
        document.add(new Paragraph(" "));

        document.add(new Paragraph("Present Days: " + report.getPresentDays(), normalFont));
        document.add(new Paragraph("Total Worked Hours: " + report.getTotalWorkedHours(), normalFont));

        document.close();

        return out.toByteArray();
    }
}
