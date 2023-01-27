import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Game {
    private String date = null;
    private String time = null;
    private String competition = null;
    /**
     * True if user is the Crew Chief
     */
    private boolean isFirstRef = false;

    private String secondRefName = null;
    private String secondRefPhone = null;
    private String location = null;
    private String firstTeam;
    private String secondTeam;
    private float money;
    private float distance = -1f;

    private Reader reader = null;
    private String pathToPDF = null;

    private String contenu = null;

    public Game(String path){
        this.pathToPDF = path;
        this.firstTeam = null;
        this.secondTeam = null;
        this.money = -1f;

    }

    public void fillInformation(){
        reader = new Reader(pathToPDF);
        reader.readFile();


         this.contenu = reader.getText();
         reader = null; //free the memory for the PDFReader

        /*Date*/
        this.date = substringRegex("DATE : .* H");
        this.date = date.substring(7, date.indexOf("H")-1);

        /*Game's hour*/
        this.time = substringRegex("HEURE : .* N");
        this.time = time.substring(8, time.indexOf("N") - 1);

        /*Competition*/
        this.competition = (substringRegex("COMPETITION: .*")).substring(12);


        /*Location*/
        this.location = (substringRegex("Correspondant :\\n.*\\n.* [(]")).substring(16);
        this.location = this.location.replace("\n", " ");
        this.location = this.location.substring(0, location.indexOf("(")-1);

        /*distance*/
        String bufferDistance = ((substringRegex("aller :.* I", (isFirstRef ? 1 : 0)).substring(7)).replace("I", ""));
        this.distance = Float.parseFloat(bufferDistance);

        /* Team's name*/
        this.secondTeam = substringRegex("GROUPEMENT SPORTIF VISITEUR : .*").substring(30);
        this.firstTeam = substringRegex("GROUPEMENT SPORTIF RECEVANT : .*").substring(30);


        /* Name of the colleague and who's crew chief*/
        this.secondRefName = substringRegex("Arbitre :.*L").substring(9); // Crew chief on the PDF
        if (this.secondRefName.equals("JACQUIER Timeo (L")){ //if it's me, we extract the 2nd
            this.isFirstRef = true;
            this.secondRefName = substringRegex("Arbitre :.*L", 1).substring(9);
        }
        this.secondRefName = this.secondRefName.substring(0, secondRefName.indexOf("(")-1); //filter the String because i'm unable to do a regex

        /* Indemnity */
        String bufferMoney = substringRegex("Indemnité : .* €", (isFirstRef ? 0 : 1)).substring(11);
        bufferMoney = bufferMoney.substring(0, bufferMoney.indexOf("€")-1);
        this.money = Float.parseFloat(bufferMoney);

        /*Phone number of the colleague*/
        this.secondRefPhone = substringRegex("Téléphone\\(s\\) :Portable : \\d*", (isFirstRef ? 1:0)).substring(25);

        System.out.println("Date : " + date + ", heure : " + time);
        System.out.println("Competition :" + competition);
        System.out.println("Adresse : " + location);
        System.out.println("Indemnité de match : " + this.money + " €");
        System.out.println("Distance : " + distance);
        System.out.println("Collegue : " + secondRefName + " numéro de téléphone : " + secondRefPhone);
        System.out.println("je suis 1er arbitre : " + isFirstRef);
        System.out.println("domicile : " + this.firstTeam);
        System.out.println("visiteur : "+this.secondTeam);

    }

    private String substringRegex(String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.contenu);
        if (matcher.find()){
            return contenu.substring(matcher.start(), matcher.end());
        }
        return "not found";
    }

    /**
     * *
     * @param regex regex to match
     * @param index Occurence wanted of the expression
     * @return String matching the regex for the "index"th times
     */
    private String substringRegex(String regex, int index){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.contenu);
        //return contenu.substring(matcher.start(), matcher.end());
        int i = -1;
        /*if (matcher.find()){
            return contenu.substring(matcher.start(), matcher.end());
        }*/
        while (i<index){
            i++;
            if (!matcher.find()){
                return "not found";
            }
            if (i == index){
                return contenu.substring(matcher.start(), matcher.end());
            }

        }

        return "not found";
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getCompetition() {
        return competition;
    }

    public boolean isFirstRef() {
        return isFirstRef;
    }

    public String getSecondRefName() {
        return secondRefName;
    }

    public String getSecondRefPhone() {
        return secondRefPhone;
    }

    public String getLocation() {
        return location;
    }

    public String getFirstTeam() {
        return firstTeam;
    }

    public String getSecondTeam() {
        return secondTeam;
    }

    public float getMoney() {
        return money;
    }

    public float getDistance() {
        return distance;
    }
}
