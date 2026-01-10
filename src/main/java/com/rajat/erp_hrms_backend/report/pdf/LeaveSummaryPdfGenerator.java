package com.rajat.erp_hrms_backend.report.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.rajat.erp_hrms_backend.report.dto.LeaveSummaryReport;

import java.io.ByteArrayOutputStream;

public class LeaveSummaryPdfGenerator {

    public static byte[] generate(LeaveSummaryReport report) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, out);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        // Title
        Paragraph title = new Paragraph("Leave Summary Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph(" "));

        // Employee Info
        document.add(new Paragraph("Employee Name: " + report.getEmployeeName(), bodyFont));
        document.add(new Paragraph("Employee ID: " + report.getEmployeeId(), bodyFont));
        document.add(new Paragraph("Year: " + report.getYear(), bodyFont));

        document.add(new Paragraph(" "));

        // Summary Table
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        table.addCell(new Phrase("Total Requests", headerFont));
        table.addCell(new Phrase(String.valueOf(report.getTotalRequests()), bodyFont));

        table.addCell(new Phrase("Approved Leaves", headerFont));
        table.addCell(new Phrase(String.valueOf(report.getApprovedLeaves()), bodyFont));

        table.addCell(new Phrase("Rejected Leaves", headerFont));
        table.addCell(new Phrase(String.valueOf(report.getRejectedLeaves()), bodyFont));

        table.addCell(new Phrase("Pending Leaves", headerFont));
        table.addCell(new Phrase(String.valueOf(report.getPendingLeaves()), bodyFont));

        document.add(table);

        document.close();

        return out.toByteArray();
    }
}
