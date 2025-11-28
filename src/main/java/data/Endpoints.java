package data;

public class Endpoints {


    public final static String publicHost = "https://reqres.in";

    public final static String localHost = "http://localhost:9000";

    private static int getRandomNumber() {
        int randomNum;
        return randomNum = (int)(Math.random() * 10);
    }

    public static String getIrishWildsURL() {
        String url = "https://d21j22mhfwmuah.cloudfront.net/0Debug/SB_HTML5_IrishWilds94/index.html" +
                "?gameCode=SB_HTML5_IrishWilds94" +
                "&token=DEMO_PP_040c4151-fccd-4c1d-a424-029ee0b73" + getRandomNumber() + getRandomNumber() + getRandomNumber() +
                "&homeUrl=spinberrysite" +
                "&rgsUrl=https://rgstorgs.stage.pariplaygames.com&lang=EN" +
                "&DebugMode=False" +
                "&currencyCode=USD" +
                "&disableRotation=False" +
                "&ExtraData=networkId%3dPP&HideCoins=True" +
                "&CoinsDefault=True";

        return url;
    }
}
