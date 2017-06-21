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

    public static void print(String data) throws ZebraPrintException {

        /*
        
        ==450   > 3
        ==225   > 4
        
        
         */
//        String data = new String(new char[370]).replace('\0', UUID.randomUUID().toString().charAt(0));
        data = StringUtils.rightPad(data, 450, ' ').substring(0, 450);
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
                + "^FD1234567891231"
                + "^FS\n"
                + "^PQ1,0,1,Y\n";

        String textCommand = ""
                + "^MMT\n"
                + "^PW1063\n"
                + "^LL0307\n"
                + "^LS0\n"
                // + "^FB600,500,500,L,500"///wrap
                + "^FT296,166^A0N,42,40^FH\\^FDTestTestTestTestTest^FS\n"
                + "^PQ1,0,1,Y^XZ";

        StringBuilder sb = ZplUtils.zplCommand(qrCode)
                .append(barCode)
                .append(textCommand);

        ZebraUtils.printZpl(sb.toString(), "ZDesigner ZT420-300dpi ZPL");
    }

    private static Data getSizeInfo(int dataLength) {
        Data data = new Data();

        if (dataLength > 350 && dataLength <= 450) {
            data.setMagnification(3);
            data.setxCoordinat(1);
            data.setyCoordinat(50);
        } else if (dataLength <= 350) {
            data.setMagnification(3);
            data.setxCoordinat(1);
            data.setyCoordinat(50);
        }

        return data;
    }

}
