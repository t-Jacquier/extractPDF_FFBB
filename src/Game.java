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

    public Game(String path){
        this.pathToPDF = path;
    }

    public void fillInformation(){
        reader = new Reader(pathToPDF);
        reader.readFile();

        String contenu = reader.getText();
        String str = contenu.substring(contenu.indexOf("GROUPEMENT SPORTIF VISITEUR : "));
        System.out.println(contenu);
    }

}
