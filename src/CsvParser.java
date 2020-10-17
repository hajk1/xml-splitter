import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

public class CsvParser {

    public HashMap<String, Transaction> parse(String path){
        HashMap<String,Transaction> map = new HashMap<>(5000);
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] trx = line.split(cvsSplitBy);

                System.out.println("Trx [amnt= " + trx[0] + ",track="+trx[1]+" , txId=" + trx[2] + "]");
                map.put(trx[2],new Transaction(new BigDecimal(trx[0].replaceAll("[\uFEFF-\uFFFF]", "")).doubleValue(),trx[1],trx[2]));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    return map;
    }
}
