public class CsvCtVerfier {
    static String csvPath = "/home/k1/Documents/ACH/990724.csv";
    public static void main(String[] args) {

        Double aDouble = Double.parseDouble("150000000".replaceAll("\\p{Cc}", "").replace(" ",""));
        System.out.println("aDouble = " + aDouble);
        CsvParser csvParser = new CsvParser();
        csvParser.parse(csvPath).values().stream().forEach(System.out::println);
    }
}
