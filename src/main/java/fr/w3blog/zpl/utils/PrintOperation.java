package fr.w3blog.zpl.utils;

 
import javafx.collections.ObservableSet;
import javafx.print.Printer;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

/**
 * Created by sakit on 1/7/2017. freelance projects
 */
public class PrintOperation {

    private static PrintOperation ourInstance = new PrintOperation();

    public static PrintOperation getInstance() {
        return ourInstance;
    }

    private static ObservableSet<Printer> printers;

    private PrintService service;

    private PrintOperation() {
    }

    public ObservableSet<Printer> getAvailablePrinters() {
        if (printers == null) {
            printers = Printer.getAllPrinters();
        }
        return printers;
    }

    public String getDefaultPrinterName() {
        Printer defaultPrinter = getDefaultPrinter();
        if (defaultPrinter != null) {
            return defaultPrinter.getName();
        } else {
            return null;
        }
    }

    public Printer getDefaultPrinter() {
        return Printer.getDefaultPrinter();
    }

    public Printer findPrinter(String name) {
        return printers.stream().filter(p -> p.getName().toLowerCase().equals(name.toLowerCase())).findFirst().get();
    }

    public PrintService getDefaultPrintService() {
        return PrintServiceLookup.lookupDefaultPrintService();
    }

    public PrintService getPrintService(String printerName) {
        if (service == null || !service.getName().equals(printerName)) {
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            for (PrintService ps : services) {
                if (ps.getName().equals(printerName)) {
                    service = ps;
                    break;
                }
            }
        }
        return service;
    }

     
 
}
