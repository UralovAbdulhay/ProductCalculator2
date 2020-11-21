package sample.Controllers;

import com.gembox.spreadsheet.*;
import sample.Moodles.Project;
import sample.Moodles.TovarZakaz;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class SaveFile {

    boolean nds1S;  // false - bez;  true - S;

    void saveFile(String path, Project project) {

        nds1S = project.getPrFormulaNum() != 0;

        Map<String, String> map = new HashMap<>();

//        System.out.println(project);

        map.put("B2", "Запрос разместил");
        map.put("E2", project.getPrKritgan().getIsm());
        map.put("B4", "Название проекта");
        map.put("B6", "КМП от комп.");
        map.put("B8", "Приоритет проекта ");
        map.put("B12", "Комментарии");
        map.put("E8", "важны");
        map.put("E10", "срочный");
        map.put("E4", project.getPrNomi());
        map.put("E6", project.getPrKmpCompany().getName());
        map.put("E12", project.getPrKomment());
        map.put("I2", "Руководитель проекта ");
        map.put("I4", "Клиент");
        map.put("I6", "Условия поставки и расчета");
        map.put("I8", "Срок выполнения работ до");
        map.put("K10", "Час");
        map.put("M2", project.getPrRaxbar().getIsm());
        map.put("M4", project.getPrClient().getName());
        map.put("M6", project.getPrFormula());
        map.put("M8", project.getTugashVaqti().toLocalDate() + "");
        map.put("M10", project.getTugashVaqti().toLocalTime() + "");
        map.put("P2", "Ответственное лицо");
        map.put("S2", project.getPrMasul().getIsm());
        map.put("P6", "Дата размещение запроса");
        map.put("Q8", "Время");
        map.put("S6", LocalDate.now() + "");
        map.put("S8", LocalTime.now() + "");


        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");

        ExcelFile workbook = new ExcelFile();
        ExcelWorksheet worksheet = workbook.addWorksheet(project.getPrNomi());

        map.forEach((s, s2) -> {
            worksheet.getCell(s).setValue(s2);
//            worksheet.getCell(s).getStyle().setHorizontalAlignment(HorizontalAlignmentStyle.CENTER);
            worksheet.getCell(s).getStyle().setVerticalAlignment(VerticalAlignmentStyle.CENTER);
        });

        if (project.isPrIsImportant()) {
            worksheet.getCell("F8").getStyle().getFillPattern().
                    setPattern(FillPatternStyle.GRAY_75,
                            SpreadsheetColor.fromColor(Color.RED),
                            SpreadsheetColor.fromColor(Color.RED)
                    );
        }
        if (project.isPrIsShoshilinch()) {
            worksheet.getCell("F10").getStyle().getFillPattern().
                    setPattern(FillPatternStyle.SOLID,
                            SpreadsheetColor.fromColor(Color.RED),
                            SpreadsheetColor.fromColor(Color.RED)
                    );
            worksheet.getCell("F10").getStyle().getBorders().setBorders(
                    MultipleBorders.outside(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.MEDIUM);
        }

        for (int i = 1; i <= 7; i += 2) {

            // 1 ustundagi label lar
            CellRange us1L = CellRange.zzaInternal(worksheet, i, 1, i, 2);
            us1L.getStyle().getBorders().setBorders(
                    MultipleBorders.outside(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.THIN
            );
            us1L.getStyle().getFillPattern().setSolid(SpreadsheetColor.fromColor(new Color(217, 217, 217)));

            if (i <= 5) {
                // 1 ustundagi Value lar
                CellRange.zzaInternal(worksheet, i, 4, i, 6).getStyle().getBorders().setBorders(
                        MultipleBorders.outside(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.MEDIUM
                );
            }
            // 2 ustundagi label lar
            CellRange us2L = CellRange.zzaInternal(worksheet, i, 8, i, 10);
            us2L.getStyle().getBorders().setBorders(
                    MultipleBorders.outside(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.THIN
            );
            us2L.getStyle().getFillPattern().setSolid(SpreadsheetColor.fromColor(new Color(217, 217, 217)));

            // 2 ustundagi Value lar
            CellRange us2V = CellRange.zzaInternal(worksheet, i, 12, i, 13);
            us2V.getStyle().getBorders().setBorders(
                    MultipleBorders.outside(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.MEDIUM
            );

        }

        // komment label
        CellRange commentL = CellRange.zzaInternal(worksheet, 11, 1, 11, 2);
        commentL.getStyle().getBorders().setBorders(
                MultipleBorders.outside(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.THIN
        );
        commentL.getStyle().getFillPattern().setSolid(SpreadsheetColor.fromColor(new Color(217, 217, 217)));


        //komment value
        CellRange.zzaInternal(worksheet, 11, 4, 11, 12).getStyle().getBorders().setBorders(
                MultipleBorders.outside(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.MEDIUM
        );

        //важны
        CellRange.zzaInternal(worksheet, 7, 4, 7, 5).getStyle().getBorders().setBorders(
                MultipleBorders.all(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.MEDIUM
        );
        //срочный
        CellRange.zzaInternal(worksheet, 9, 4, 9, 5).getStyle().getBorders().setBorders(
                MultipleBorders.all(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.MEDIUM
        );

        // 3 ustundagi Label
        CellRange us3L = CellRange.zzaInternal(worksheet, 1, 15, 1, 16);
        us3L.getStyle().getBorders().setBorders(
                MultipleBorders.outside(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.THIN
        );
        us3L.getStyle().getFillPattern().setSolid(SpreadsheetColor.fromColor(new Color(217, 217, 217)));


        // 3 ustundagi Value
        CellRange.zzaInternal(worksheet, 1, 18, 1, 20).getStyle().getBorders().setBorders(
                MultipleBorders.outside(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.MEDIUM
        );

        // soat label
        worksheet.getCell("K10").getStyle().getBorders().setBorders(
                MultipleBorders.outside(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.THIN
        );
        worksheet.getCell("K10").getStyle().getFillPattern()
                .setSolid(SpreadsheetColor.fromColor(new Color(217, 217, 217)));

        // soat Value
        worksheet.getCell("M10").getStyle().getBorders().setBorders(
                MultipleBorders.outside(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.MEDIUM
        );

        //TableLabel
        if (nds1S) {
            CellRange tabLab = CellRange.zzaInternal(worksheet, 14, 1, 14, 30);
            tabLab.getStyle().getFillPattern().setSolid(
                    SpreadsheetColor.fromColor(new Color(217, 217, 217))
            );
            tabLab.getStyle().getBorders().setBorders(
                    MultipleBorders.all(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.MEDIUM
            );
        } else {
            CellRange tabLab = CellRange.zzaInternal(worksheet, 14, 1, 14, 28);
            tabLab.getStyle().getFillPattern().setSolid(
                    SpreadsheetColor.fromColor(new Color(217, 217, 217))
            );
            tabLab.getStyle().getBorders().setBorders(
                    MultipleBorders.all(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.MEDIUM
            );
        }

        // ZAPROS KIRITILGAN VAQT
        CellRange crateDate = CellRange.zzaInternal(worksheet, 5, 15, 5, 16);
        crateDate.getStyle().getFillPattern().setSolid(
                SpreadsheetColor.fromColor(new Color(217, 217, 217))
        );
        crateDate.getStyle().getBorders().setBorders(
                MultipleBorders.outside(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.THIN
        );

        worksheet.getCell("Q8").getStyle().getBorders().setBorders(
                MultipleBorders.outside(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.THIN
        );
        worksheet.getCell("Q8").getStyle().getFillPattern().setSolid(
                SpreadsheetColor.fromColor(new Color(217, 217, 217))
        );
        worksheet.getCell("S6").getStyle().getBorders().setBorders(
                MultipleBorders.all(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.MEDIUM
        );
        worksheet.getCell("S8").getStyle().getBorders().setBorders(
                MultipleBorders.all(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.MEDIUM
        );


        int row = 14;
        int startRow = row;
        int col = 1;
        int startCol = col;

        worksheet.getCell(row, 1).setValue("№");
        worksheet.getCell(row, 2).setValue("Наименование товара");
        worksheet.getCell(row, 3).setValue("Производитель");
        worksheet.getCell(row, 4).setValue("Модель");
        worksheet.getCell(row, 5).setValue("Код ТН ВЭД");
        worksheet.getCell(row, 6).setValue("Kол-во");
        worksheet.getCell(row, 7).setValue("Ед. из.");
        worksheet.getCell(row, 8).setValue("Цена EXW");
        worksheet.getCell(row, 9).setValue("Сумма EXW");
        worksheet.getCell(row, 10).setValue("Траспорт \n" + project.getPrStavkalar().getStTrans());
        worksheet.getCell(row, 11).setValue("Сумма Транс-порта");
        worksheet.getCell(row, 12).setValue("Цена с  Транс-портом");
        worksheet.getCell(row, 13).setValue("Сумма с  Транс-портом");
        worksheet.getCell(row, 14).setValue("Cтавка CIP");
        worksheet.getCell(row, 15).setValue("Цена CIP(USD)");
        worksheet.getCell(row, 16).setValue("Сумма CIP(USD)");
        worksheet.getCell(row, 17).setValue("Цена SUM (CIP) \n" + project.getPrStavkalar().getStUSD_USZ() + " sum");
        worksheet.getCell(row, 18).setValue("Там. сборы \n" + (project.getPrStavkalar().getStBojxona() * 100) + " %");
        worksheet.getCell(row, 19).setValue("Пошлина");
        worksheet.getCell(row, 20).setValue("Cумма пошлин");
        worksheet.getCell(row, 21).setValue("Акциз");
        worksheet.getCell(row, 22).setValue("Cумма Акциз");
        worksheet.getCell(row, 23).setValue("НДС 1 \n" + (project.getPrStavkalar().getStNDS1S() * 100) + " %");
        worksheet.getCell(row, 24).setValue("Приход цена");
        worksheet.getCell(row, 25).setValue("Приход Сумма");
        worksheet.getCell(row, 26).setValue("Cтавка DDP");
        worksheet.getCell(row, 27).setValue("Цена  DDP ");
        worksheet.getCell(row, 28).setValue("Сумма  DDP ");

        if (nds1S) {
            worksheet.getCell(row, 29).setValue("Цена c НДС 2 \n" + (project.getPrStavkalar().getStNDS2() * 100) + " %");
            worksheet.getCell(row, 30).setValue("Сумма с НДС");
        }

        for (TovarZakaz zakaz : project.getProjectZakazList()) {

            row++;

            System.out.println(zakaz);
            worksheet.getCell(row, 1).setValue(zakaz.getTr());
            worksheet.getCell(row, 2).setValue(zakaz.getTovarNomi());
            worksheet.getCell(row, 3).setValue(zakaz.getTovarIshlabChiqaruvchi().getName());
            worksheet.getCell(row, 4).setValue(zakaz.getTovarModel());
            worksheet.getCell(row, 5).setValue(zakaz.getTovarKod());
            worksheet.getCell(row, 6).setValue(zakaz.getZakazSoni());
            worksheet.getCell(row, 7).setValue(zakaz.getTovarUlchovBirligi());
            worksheet.getCell(row, 8).setValue(zakaz.getTovarNarxi());
            worksheet.getCell(row, 8).getStyle().setNumberFormat("#,##0.00 [$USD]");
            worksheet.getCell(row, 9).setValue(zakaz.getZakazSummaExw());
            worksheet.getCell(row, 9).getStyle().setNumberFormat("#,##0.00 [$USD]");
            worksheet.getCell(row, 10).setValue(zakaz.getZakazTransProNatija());
            worksheet.getCell(row, 10).getStyle().setNumberFormat("0%");
            worksheet.getCell(row, 11).setValue(zakaz.getZakazTransSumm());
            worksheet.getCell(row, 11).getStyle().setNumberFormat("#,##0.00 [$USD]");
            worksheet.getCell(row, 12).setValue(zakaz.getZakazTransLiNarx());
            worksheet.getCell(row, 12).getStyle().setNumberFormat("#,##0.00 [$USD]");
            worksheet.getCell(row, 13).setValue(zakaz.getZakazTransLiSumma());
            worksheet.getCell(row, 13).getStyle().setNumberFormat("#,##0.00 [$USD]");
            worksheet.getCell(row, 14).setValue(zakaz.getStCIP());
            worksheet.getCell(row, 15).setValue(zakaz.getZakazCIPNarxiUSD());
            worksheet.getCell(row, 15).getStyle().setNumberFormat("#,##0.00 [$USD]");
            worksheet.getCell(row, 16).setValue(zakaz.getZakazCIPSummUSD());
            worksheet.getCell(row, 16).getStyle().setNumberFormat("#,##0.00 [$USD]");
            worksheet.getCell(row, 17).setValue(zakaz.getZakazCIPNarxiUSZ());
            worksheet.getCell(row, 17).getStyle().setNumberFormat("#,##0.00 [$UZS]");
            worksheet.getCell(row, 18).setValue(zakaz.getZakazBojYigini());
            worksheet.getCell(row, 18).getStyle().setNumberFormat("#,##0.00 [$UZS]");
            worksheet.getCell(row, 19).setValue(zakaz.getTovarPoshlina());
            worksheet.getCell(row, 19).getStyle().setNumberFormat("0%");
            worksheet.getCell(row, 20).setValue(zakaz.getZakazPoshlinaSumm());
            worksheet.getCell(row, 20).getStyle().setNumberFormat("#,##0.00 [$UZS]");
            worksheet.getCell(row, 21).setValue(zakaz.getTovarAksiz());
            worksheet.getCell(row, 21).getStyle().setNumberFormat("0%");
            worksheet.getCell(row, 22).setValue(zakaz.getZakazAksizSumm());
            worksheet.getCell(row, 22).getStyle().setNumberFormat("#,##0.00 [$UZS]");
            worksheet.getCell(row, 23).setValue(zakaz.getZakazNDS1Narxi());
            worksheet.getCell(row, 23).getStyle().setNumberFormat("#,##0.00 [$UZS]");
            worksheet.getCell(row, 24).setValue(zakaz.getZakazKelishNarxi());
            worksheet.getCell(row, 24).getStyle().setNumberFormat("#,##0.00 [$UZS]");
            worksheet.getCell(row, 25).setValue(zakaz.getZakazKelishSumm());
            worksheet.getCell(row, 25).getStyle().setNumberFormat("#,##0.00 [$UZS]");
            worksheet.getCell(row, 26).setValue(zakaz.getTovarDDP());
            worksheet.getCell(row, 27).setValue(zakaz.getZakazDDPnarxi());
            worksheet.getCell(row, 27).getStyle().setNumberFormat("#,##0.00 [$UZS]");
            worksheet.getCell(row, 28).setValue(zakaz.getZakazDDPsumm());
            worksheet.getCell(row, 28).getStyle().setNumberFormat("#,##0.00 [$UZS]");

            if (nds1S) {
                worksheet.getCell(row, 29).setValue(zakaz.getZakazNDS2liNarxi());
                worksheet.getCell(row, 29).getStyle().setNumberFormat("#,##0.00 [$UZS]");
                worksheet.getCell(row, 30).setValue(zakaz.getZakazNDS2liSumm());
                worksheet.getCell(row, 30).getStyle().setNumberFormat("#,##0.00 [$UZS]");
            }
        }

        if (nds1S) {

            for (int i = 1; i <= 30; i++) {
                int max = 20;
                for (int j = 14; j <= row; j++) {
                    if (max < worksheet.getColumn(i).getCell(j).getValue().toString().length()) {
                        max = worksheet.getColumn(i).getCell(j).getValue().toString().length() + 5;
                        if (max > 35) {
                            max = 35;
                            worksheet.getColumn(i).getCell(j).getStyle().setWrapText(true);
                        }
                    }
                    worksheet.getColumn(i).getCell(j).getStyle().setHorizontalAlignment(HorizontalAlignmentStyle.CENTER);
                    worksheet.getColumn(i).getCell(j).getStyle().setVerticalAlignment(VerticalAlignmentStyle.CENTER);
                }
                worksheet.getColumn(i).setWidth(max * 256);
            }

            CellRange cells = CellRange.zzaInternal(worksheet, startRow, startCol, row, startCol + 29);
            cells.getStyle().getBorders().setBorders(
                    MultipleBorders.all(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.THIN
            );
        } else {
            for (int i = 1; i <= 28; i++) {
                int max = 20;
                for (int j = 14; j <= row; j++) {
                    if (max < worksheet.getColumn(i).getCell(j).getValue().toString().length()) {
                        max = worksheet.getColumn(i).getCell(j).getValue().toString().length() + 5;
                        if (max > 35) {
                            max = 35;
                            worksheet.getColumn(i).getCell(j).getStyle().setWrapText(true);
                        }
                    }
                    worksheet.getColumn(i).getCell(j).getStyle().setHorizontalAlignment(HorizontalAlignmentStyle.CENTER);
                    worksheet.getColumn(i).getCell(j).getStyle().setVerticalAlignment(VerticalAlignmentStyle.CENTER);
                }
                worksheet.getColumn(i).setWidth(max * 256);
            }

            CellRange cells = CellRange.zzaInternal(worksheet, startRow, startCol, row, startCol + 27);
            cells.getStyle().getBorders().setBorders(
                    MultipleBorders.all(), SpreadsheetColor.fromColor(Color.BLACK), LineStyle.THIN
            );
        }

        try {
            workbook.save(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
