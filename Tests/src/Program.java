import com.gembox.spreadsheet.*;

class Program {

    public static void main(String[] args) throws java.io.IOException {
        // If using Professional version, put your serial key below.
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");

        ExcelFile workbook = new ExcelFile();
        ExcelWorksheet worksheet = workbook.addWorksheet("Styles and Formatting");

        worksheet.getCell(0, 1).setValue("Cell style examples:");
        worksheet.getPrintOptions().setPrintGridlines(true);

        int row = 0;

        // Column width of 4, 30 and 36 characters.
        worksheet.getColumn(0).setWidth(4 * 256);
        worksheet.getColumn(1).setWidth(30 * 256);
        worksheet.getColumn(2).setWidth(36 * 256);

        worksheet.getCell(row += 2, 1).setValue(".Style.Borders.SetBorders(...)");
        worksheet.getCell(row, 2).getStyle().getBorders().setBorders(MultipleBorders.outside(), SpreadsheetColor.fromArgb(252, 1, 1), LineStyle.THIN);

        worksheet.getCell(row += 2, 1).setValue(".Style.FillPattern.SetPattern(...)");
        worksheet.getCell(row, 2).getStyle().getFillPattern().setPattern(FillPatternStyle.THIN_HORIZONTAL_CROSSHATCH, SpreadsheetColor.fromName(ColorName.GREEN), SpreadsheetColor.fromName(ColorName.YELLOW));

        worksheet.getCell(row += 2, 1).setValue(".Style.Font.Color =");
        worksheet.getCell(row, 2).setValue("Color.Blue");
        worksheet.getCell(row, 2).getStyle().getFont().setColor(SpreadsheetColor.fromName(ColorName.BLUE));

        worksheet.getCell(row += 2, 1).setValue(".Style.Font.Italic =");
        worksheet.getCell(row, 2).setValue("true");
        worksheet.getCell(row, 2).getStyle().getFont().setItalic(true);

        worksheet.getCell(row += 2, 1).setValue(".Style.Font.Name =");
        worksheet.getCell(row, 2).setValue("Comic Sans MS");
        worksheet.getCell(row, 2).getStyle().getFont().setName("Comic Sans MS");

        worksheet.getCell(row += 2, 1).setValue(".Style.Font.ScriptPosition =");
        worksheet.getCell(row, 2).setValue("ScriptPosition.Superscript");
        worksheet.getCell(row, 2).getStyle().getFont().setScriptPosition(ScriptPosition.SUPERSCRIPT);

        worksheet.getCell(row += 2, 1).setValue(".Style.Font.Size =");
        worksheet.getCell(row, 2).setValue("18 * 20");
        worksheet.getCell(row, 2).getStyle().getFont().setSize(18 * 20);

        worksheet.getCell(row += 2, 1).setValue(".Style.Font.Strikeout =");
        worksheet.getCell(row, 2).setValue("true");
        worksheet.getCell(row, 2).getStyle().getFont().setStrikeout(true);

        worksheet.getCell(row += 2, 1).setValue(".Style.Font.UnderlineStyle =");
        worksheet.getCell(row, 2).setValue("UnderlineStyle.Double");
        worksheet.getCell(row, 2).getStyle().getFont().setUnderlineStyle(UnderlineStyle.DOUBLE);

        worksheet.getCell(row += 2, 1).setValue(".Style.Font.Weight =");
        worksheet.getCell(row, 2).setValue("ExcelFont.BoldWeight");
        worksheet.getCell(row, 2).getStyle().getFont().setWeight(ExcelFont.BOLD_WEIGHT);

        worksheet.getCell(row += 2, 1).setValue(".Style.HorizontalAlignment =");
        worksheet.getCell(row, 2).setValue("HorizontalAlignmentStyle.Center");
        worksheet.getCell(row, 2).getStyle().setHorizontalAlignment(HorizontalAlignmentStyle.CENTER);

        worksheet.getCell(row += 2, 1).setValue(".Style.Indent");
        worksheet.getCell(row, 2).setValue("five");
        worksheet.getCell(row, 2).getStyle().setHorizontalAlignment(HorizontalAlignmentStyle.LEFT);
        worksheet.getCell(row, 2).getStyle().setIndent(5);

        worksheet.getCell(row += 2, 1).setValue(".Style.IsTextVertical = ");
        worksheet.getCell(row, 2).setValue("true");
        // Set row height to 60 points.
        worksheet.getRow(row).setHeight(60 * 20);
        worksheet.getCell(row, 2).getStyle().setTextVertical(true);

        worksheet.getCell(row += 2, 1).setValue(".Style.NumberFormat");
        worksheet.getCell(row, 2).setValue(1234);
        worksheet.getCell(row, 2).getStyle().setNumberFormat("#,##0.00 [$UZS]");

        worksheet.getCell(row += 2, 1).setValue(".Style.Rotation");
        worksheet.getCell(row, 2).setValue("35 degrees up");
        worksheet.getCell(row, 2).getStyle().setRotation(35);

        worksheet.getCell(row += 2, 1).setValue(".Style.ShrinkToFit");
        worksheet.getCell(row, 2).setValue("This property is set to true so this text appears shrunk.");
        worksheet.getCell(row, 2).getStyle().setShrinkToFit(true);

        worksheet.getCell(row += 2, 1).setValue(".Style.VerticalAlignment =");
        worksheet.getCell(row, 2).setValue("VerticalAlignmentStyle.Top");
        // Set row height to 30 points.
        worksheet.getRow(row).setHeight(30 * 20);
        worksheet.getCell(row, 2).getStyle().setVerticalAlignment(VerticalAlignmentStyle.TOP);

        worksheet.getCell(row += 2, 1).setValue(".Style.WrapText");
        worksheet.getCell(row, 2).setValue("This property is set to true so this text appears broken into multiple lines.");
        worksheet.getCell(row, 2).getStyle().setWrapText(true);

        worksheet.getCell(40,3).setValue(2);
        worksheet.getCell(40,4).setValue(3);

        worksheet.getCell(40,5).setFormula(
                worksheet.getCell(40,3).getName()
        +"+"+ worksheet.getCell(40,4).getName()
                );

        System.out.println(
                worksheet.getCell(40,3).getName()
               +"+" + worksheet.getCell(40,4).getName());


//        workbook.save("Styles and Formatting.xlsx");
        workbook.save("C:\\Users\\abdul\\OneDrive\\Рабочий стол\\Export\\Styles and Formatting.xls");
    }
}