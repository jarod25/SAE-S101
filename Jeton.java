import java.util.Scanner;

/**
 * Created by zulupero on 24/09/2021.
 */
public class Jeton {
    static final Scanner input = new Scanner(System.in);
    public static String[] state;
    static final int NCASES = 21;
    static final int NLIGNES = 6;
    static final String[] COULEURS = {"B", "R"};
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";

    static boolean estOui(char reponse) {
        return "yYoO".indexOf(reponse) != -1;
    }

    public static void main(String[] args) throws InterruptedException {

        boolean newDeal;
        int scoreBleus = 0;
        int scoreRouges = 0;

        do {
            System.out.println("Jouer seul ? ");
            char reponse = input.next().charAt(0);
            boolean single = estOui(reponse);

            String[] tabInit = initJeu();
            afficheJeu(tabInit);
            int n=0;
            boolean b=false;
            for (int i=1;i<=10;i++) {
              System.out.println("Au tour des Bleus");
              n = input.nextInt();
              b = jouer(tabInit,"B",i,n);
              while (b==false){
                n = input.nextInt();
                b = jouer(tabInit,"B",i,n);
              }
              afficheJeu(tabInit);
              System.out.println("Au tour des Rouges");
              n = input.nextInt();
              b = jouer(tabInit,"R",i,n);
              while (b==false){
                n = input.nextInt();
                b = jouer(tabInit,"R",i,n);
              }
              afficheJeu(tabInit);
            }

            int val = 1;
            int idCaseJouee;

		/*
			le code de votre partie ici
		*/

            int sumR = sommeVoisins("R");
            int sumB = sommeVoisins("B");

            if ( sumB < sumR)
                System.out.println("Les bleus gagnent par "+sumB+" à "+sumR);
            else if (sumB == sumR)
                System.out.println("Égalité : "+sumB+" partout !");
            else
                System.out.println("Les rouges gagnent par "+sumR+" à "+sumB);

            System.out.println("Nouvelle partie ? ");
            reponse = input.next().charAt(0);
            newDeal = estOui(reponse);
        } while (newDeal);
        System.out.println("Bye Bye !");
        System.exit(0);

    }

    /**
     * Initialise le jeu avec un double/triple underscore à chaque case, signifiant 'case vide'
     */
    public static String[] initJeu() {
      String[] tabInit = new String[NCASES];
      for (int i=0;i<=20;i++) {
        String b = Integer.toString(i);
        tabInit[i] = "___";
      }
      return tabInit;
    }

    /**
     * Affiche le plateau de jeu en mode texte
     */
    public static void afficheJeu(String[] tabInit){
      int a=0;
      int espace=10;
      for (int y=0;y<NLIGNES;y++) {
        System.out.println();
        if (a+y<10) {
          System.out.print(" ");
        }
        System.out.print(a+y+": ");
        for (int u=0;u<espace;u=u+1) {
          System.out.print(" ");
        }
        a=a+y;
        int b=0;
        espace=espace-2;
        for (int z=0;z<=y;z++) {
          System.out.print(tabInit[a+b]);
          System.out.print(" ");
          b=b+1;
        }
      }
      System.out.println(" ");
    }

    /**
     * Place un jeton sur le plateau, si possible.
     * @param couleur couleur du jeton : COULEURS[0] ou COULEURS[1]
     * @param val valeur faciale du jeton
     * @param pos position (indice) de l'emplacement où placer le jeton
     * @return true si le jeton a pu être posé, false sinon.
     */
    public static boolean jouer( String[] tabInit,String couleur, int val, int pos){
      if (tabInit[pos]=="___"){
        if (couleur=="B"){
          if (val<10) {
              tabInit[pos] = ANSI_BLUE_BACKGROUND + ANSI_WHITE + "B"+Integer.toString(val) + " " + ANSI_RESET;
            return true;
          }
            tabInit[pos] = ANSI_BLUE_BACKGROUND + ANSI_WHITE + "B"+Integer.toString(val) + ANSI_RESET;
          return true;
        }
        if (couleur=="R"){
          if (val<10) {
              tabInit[pos] = ANSI_RED_BACKGROUND + ANSI_WHITE + "R"+Integer.toString(val) +" " + ANSI_RESET;
            return true;
          }
            tabInit[pos] = ANSI_RED_BACKGROUND + ANSI_WHITE + "R"+Integer.toString(val) + ANSI_RESET;
          return true;
        }
      }
      return false;
    }

    /**
     * Retourne l'indice de la case débutant la ligne idLigne
     * @param idLigne indice de la ligne. La première ligne est la ligne #0.
     * @return l'indice de la case la plus à gauche de la ligne
     */
    public static int idDebutLigne(int idLigne){
      int idCase = 0;
      int x=0;
      for (int j=0;j<=idLigne;j++) {
        idCase=idCase+x;
        x=x+1;
      }
      return idCase;
    }

    /**
     * Retourne l'indice de la case terminant la ligne idLigne
     * @param idLigne indice de la ligne. La première ligne est la ligne #0.
     * @return l'indice de la case la plus à droite de la ligne
     */
    public static int idFinLigne(int idLigne){
      int idCase = 0;
      int x=0;
      for (int j=0;j<=idLigne;j++) {
        idCase=idCase+x;
        x=x+1;
      }
      return idCase;
    }

    /**
     * Renvoie la position du jeton manquant
     * @return l'indice de la case non occupée
     */
    public static int getIdVide(){
        throw new java.lang.UnsupportedOperationException("à compléter");
    }

    /**
     * fait la somme des poids des voisins de couleur col
     * (6 voisins au maximum)
     *
     * @param col couleur des voisins considérés
     * @return somme des poids
     */
    public static int sommeVoisins(String col){
        throw new java.lang.UnsupportedOperationException("à compléter");
    }

    /**
     * Renvoie le prochain coup à jouer pour les rouges
     * Algo naïf = la première case dispo
     * @return id de la case
     */
    public static int iaRouge(){
	/*
		Écire un véritable code sachant jouer.
		La ligne du return ci-dessous doit donc naturellement aussi être ré-écrite.
		Cette version ne permet que de reproduire le fonctionnement à 2 joueurs
		tout en conservant l'appel à la fonction,
		cela peut s'avérer utile lors du développement.
	*/
        return Integer.parseInt(input.next());
    }
}
