public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Reader r = new Reader("/home/timeo/IdeaProjects/testLecturePDF/src/ressources/editionConvocation-4.pdf");
        r.readFile();
        System.out.println(r.getText());
    }
}