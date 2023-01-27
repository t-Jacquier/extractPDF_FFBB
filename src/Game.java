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
    private String firstTeam = null;
    private String secondTeam = null;
    private float money = -1f;
    private float distance = -1f;

    private Reader reader = null;
    private String pathToPDF = null;

    private String contenu = null;

    public Game(String path){
        this.pathToPDF = path;
    }

    public void fillInformation(){
        reader = new Reader(pathToPDF);
        reader.readFile();


         this.contenu = reader.getText();

        /*Pattern pattern = Pattern.compile("GROUPEMENT SPORTIF VISITEUR : .*");
        Matcher matcher = pattern.matcher(contenu);
        if (matcher.find()){
            System.out.print("Start index: " + matcher.start());
            System.out.print(" End index: " + matcher.end());
            System.out.print(contenu.substring(matcher.start(), matcher.end()).substring(29));
        }*/

        this.secondTeam = substringRegex("GROUPEMENT SPORTIF VISITEUR : .*").substring(30);
        this.firstTeam = substringRegex("GROUPEMENT SPORTIF RECEVANT : .*").substring(30);
        System.out.println("domicile : " + this.firstTeam);
        System.out.println("visiteur : "+this.secondTeam);

       //System.out.println(contenu);
    }

    private String substringRegex(String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.contenu);
        if (matcher.find()){
            return contenu.substring(matcher.start(), matcher.end());
        }
        return "not found";
    }

}
