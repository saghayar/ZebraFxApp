package az.com.cybernet.zebra.print;

import fr.w3blog.zpl.model.ZebraPrintException;
import fr.w3blog.zpl.utils.ZebraUtils;
import fr.w3blog.zpl.utils.ZplUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Lenovo
 */
public class PrintWithZPL2 {

    public static void print(String data, String productName) throws ZebraPrintException {

        /*
        
        ==450   > 3
        ==225   > 4
        
        
         */
//        String data = new String(new char[370]).replace('\0', UUID.randomUUID().toString().charAt(0));
        data = StringUtils.rightPad(data, 250, ' ').substring(0, 250);
        Data d = getSizeInfo(data.length());
        System.out.println("===========" + data);
        System.out.println("===========");
        String qrCode = "^XA\n"
                + "^FO" + d.getyCoordinat() + "," + d.getxCoordinat() + "\n"
                + "^BQN,2,'" + d.getMagnification() + "'\n"
                + "^FDMM,"
                + data
                + "^FS\n";

        String barCode = ""
                + "^BY2,2,58"
                + "^FT856,310"
                + "^BEN,,N,N\n"
                + "^FD'" + "4760".concat(data.split(":")[0]) + "'"
                + "^FS\n"
                + "^PQ1,0,1,Y\n";
//
//        String textCommand = ""
//                + "^FO52,300"                
//                + "^MMT\n"
//                + "^PW1063\n"
//                + "^LL0307\n"
//                + "^LS0\n"
//                + "^FB350,4,,C"
////                + "^FB600,500,500,C,500"///wrap
//                + "^FT296,166^A0N,42,40^FH\\^FD'" + productName + "'^FS\n"
//                + "^PQ1,0,1,Y^XZ";
         String textCommand = ""
                + "^LL800"
                 + "^FO300,100"
                 + "^FB464,10,0,C,0"
                 + "^ADN,20, 10"
                 + "^A0N,42,40"
                 + "^FH"
                 + "^FD'"+productName+"'^FS"
                + "^XZ";

        StringBuilder sb = ZplUtils.zplCommand(qrCode)
                .append(barCode)
                .append(textCommand);
        
        System.out.println("QQQQQQQQ"+sb.toString());

        ZebraUtils.printZpl(sb.toString(), "ZDesigner ZT420-300dpi ZPL");
    }

    private static Data getSizeInfo(int dataLength) {
        Data data = new Data();

        if (dataLength > 350 && dataLength <= 480) {
            data.setMagnification(3);
            data.setxCoordinat(2);
            data.setyCoordinat(52);
        } else if (dataLength <= 350) {
            data.setMagnification(4);
            data.setxCoordinat(1);
            data.setyCoordinat(50);
        }

        return data;
    }

}
