package test_1;

import com.gembox.spreadsheet.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Export {


    public static void main(String[] args) {


        try {
            aVoid();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void aVoid() throws IOException {

         Map<String, String> map = new HashMap<>();

        map.put("B2", "Запрос разместил" );
        map.put("E2","Жамшид ");
        map.put("B4","Название проекта");
        map.put("B6","КМП от комп.");
        map.put("B8","Приоритет проекта ");
        map.put("E8","важны");
        map.put("E10","срочный");
        map.put("E4","PARK");
        map.put("E6","ITV SOFT");
        map.put("K2","Руководитель проекта ");
        map.put("K4","Клиент");
        map.put("K6","Условия поставки и расчета");
        map.put("K8","Срок выполнения работ до");
        map.put("M10","Час");
        map.put("N2","Аваз");
        map.put("N4","OOO Sgb Soft");
        map.put("N6","DDP с НДС");
        map.put("N8","13.04.2020");
        map.put("N10","14:00:00");
        map.put("S2","Ответственное лицо");
        map.put("V2","Жасур");



        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");

        ExcelFile workbook = new ExcelFile();
        ExcelWorksheet worksheet = workbook.addWorksheet("Styles and Formatting");

        map.forEach((s, s2) -> {
            worksheet.getCell(s).setValue(s2);
        });
        System.out.println("ass  length = "+"ass".length());

        int row = 12;
        int col = 1;

        worksheet.getCell(row, 1).setValue("№");
        worksheet.getCell(row, 2).setValue("Наименование товара");
        worksheet.getCell(row, 3).setValue("Производитель");
        worksheet.getCell(row, 4).setValue("Модель");
        worksheet.getCell(row, 5).setValue("Код ТН ВЭД");
        worksheet.getCell(row, 6).setValue("Kол-во");
        worksheet.getCell(row, 7).setValue("Ед. из.");
        worksheet.getCell(row, 8).setValue("Цена EXW");
        worksheet.getCell(row, 9).setValue("Сумма EXW");
        worksheet.getCell(row, 10).setValue("Траспорт %");
        worksheet.getCell(row, 11).setValue("Сумма Транс-порта");
        worksheet.getCell(row, 12).setValue("Цена с  Транс-портом");
        worksheet.getCell(row, 13).setValue("Сумма с  Транс-портом");
        worksheet.getCell(row, 14).setValue("Cтавка CIP");
        worksheet.getCell(row, 15).setValue("Цена CIP(USD)");
        worksheet.getCell(row, 16).setValue("Сумма CIP(USD)");
        worksheet.getCell(row, 17).setValue("Цена SUM ");
        worksheet.getCell(row, 18).setValue("Там. сборы");
        worksheet.getCell(row, 19).setValue("Пошлина");
        worksheet.getCell(row, 20).setValue("Cумма пошлин");
        worksheet.getCell(row, 21).setValue("Акциз");
        worksheet.getCell(row, 22).setValue("Cумма Акциз");
        worksheet.getCell(row, 23).setValue("НДС 1");
        worksheet.getCell(row, 24).setValue("Приход цена");
        worksheet.getCell(row, 25).setValue("Приход Сумма");
        worksheet.getCell(row, 26).setValue("Cтавка DDP");
        worksheet.getCell(row, 27).setValue("Цена  DDP ");
        worksheet.getCell(row, 28).setValue("Сумма  DDP ");



        workbook.save("C:\\Users\\abdul\\OneDrive\\Рабочий стол\\Export\\Yangi_Export_.xlsx");


    }
}

