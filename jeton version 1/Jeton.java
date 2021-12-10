import java.util.Scanner;

/**
 * Created by zulupero on 24/09/2021.
 */
public class Jeton {
    static final Scanner input = new Scanner(System.in);
    public static String[] state;
    static final int NCASES = 15;
    static final int NLIGNES = 5;
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

            initJeu();
            afficheJeu();

            int val = 1;
            int idCaseJouee;

            int n=0;
            boolean b=false;
            for (int i=1;i<=(NCASES-1)/2;i++) {
              System.out.println("Au tour des Bleus");
              n = input.nextInt();
              b = jouer("B",i,n);
              while (b==false){
                n = input.nextInt();
                b = jouer("B",i,n);
              }
              afficheJeu();
              System.out.println("Au tour des Rouges");
              n = input.nextInt();
              b = jouer("R",i,n);
              while (b==false){
                n = input.nextInt();
                b = jouer("R",i,n);
              }
              afficheJeu();
            }

            int sumR = sommeVoisins("R");
            int sumB = sommeVoisins("B");
            System.out.println(sumR);

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
    public static void initJeu() {
      state = new String[NCASES];
      for (int i=0;i<=NCASES-1;i++) {
        String b = Integer.toString(i);
        state[i] = "___";
      }
    }

    /**
     * Affiche le plateau de jeu en mode texte
     */
    public static void afficheJeu(){
      int a=0;
      int espace=NLIGNES*2;
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
          System.out.print(state[a+b]);
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
    public static boolean jouer( String couleur, int val, int pos){
      if (pos>=0 && pos <NCASES && state[pos]=="___"){
        if (couleur=="B"){
          if (val<10) {
              state[pos] = ANSI_BLUE_BACKGROUND + ANSI_WHITE + "B"+Integer.toString(val) + " " + ANSI_RESET;
            return true;
          }
            state[pos] = ANSI_BLUE_BACKGROUND + ANSI_WHITE + "B"+Integer.toString(val) + ANSI_RESET;
          return true;
        }
        if (couleur=="R"){
          if (val<10) {
              state[pos] = ANSI_RED_BACKGROUND + ANSI_WHITE + "R"+Integer.toString(val) +" " + ANSI_RESET;
            return true;
          }
            state[pos] = ANSI_RED_BACKGROUND + ANSI_WHITE + "R"+Integer.toString(val) + ANSI_RESET;
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
       return idCase+x-1;
     }


    /**
     * Renvoie la position du jeton manquant
     * @return l'indice de la case non occupée
     */
    public static int getIdVide(){
      for(int k = 0; k < NCASES; k++){
        if(state[k]=="___"){
          return k;
        }
      }
      return 0;
    }

    /**
     * fait la somme des poids des voisins de couleur col
     * (6 voisins au maximum)
     *
     * @param col couleur des voisins considérés
     * @return somme des poids
     */
     /*RETURN INT!!!*/
    public static int sommeVoisins(String col){
        int k = getIdVide();
        int l = 0;
        while(k>=idDebutLigne(l)){
          l=l+1;
        }
        l=l-1;
        int resultat=0;
        /* Si vide est sur la dernière ligne à gauche*/
        if (NLIGNES-1==l) {
          if (idDebutLigne(NLIGNES-1)==k) {
            if (state[k-l].contains(col)==true)
              resultat=resultat+Integer.valueOf((state[k-l].substring(11,13)).replaceAll("\\s+",""));
            if (state[k+1].contains(col)==true)
              resultat=resultat+Integer.valueOf((state[k+1].substring(11,13)).replaceAll("\\s+",""));
            return resultat;
          }
          /* Si vide est sur la dernière ligne à droite*/
          if (idFinLigne(NLIGNES-1)==k) {
            if (state[k-l-1].contains(col)==true)
              resultat=resultat+Integer.valueOf((state[k-l-1].substring(11,13)).replaceAll("\\s+",""));
            if (state[k-1].contains(col)==true)
              resultat=resultat+Integer.valueOf((state[k-1].substring(11,13)).replaceAll("\\s+",""));
            return resultat;
          }
          /* Si vide est sur la dernière ligne au milieu*/
          if (state[k-l-1].contains(col)==true)
            resultat=resultat+Integer.valueOf((state[k-l-1].substring(11,13)).replaceAll("\\s+",""));
          if (state[k-l].contains(col)==true)
            resultat=resultat+Integer.valueOf((state[k-l].substring(11,13)).replaceAll("\\s+",""));
          if (state[k-1].contains(col)==true)
            resultat=resultat+Integer.valueOf((state[k-1].substring(11,13)).replaceAll("\\s+",""));
          if (state[k+1].contains(col)==true)
            resultat=resultat+Integer.valueOf((state[k+1].substring(11,13)).replaceAll("\\s+",""));
          return resultat;
        }
        /* Si vide est sur la première ligne*/
        if (k==0) {
          if (state[1].contains(col)==true)
            resultat=resultat+Integer.valueOf((state[1].substring(11,13)).replaceAll("\\s+",""));
          if (state[2].contains(col)==true)
            resultat=resultat+Integer.valueOf((state[2].substring(11,13)).replaceAll("\\s+",""));
          return resultat;
        }
        /* Si vide est sur une ligne quelconque à gauche de la ligne*/
        if (idDebutLigne(l)==k && k!=0) {
          if (state[k-l].contains(col)==true)
            resultat=resultat+Integer.valueOf((state[k-l].substring(11,13)).replaceAll("\\s+",""));
          if (state[k+1].contains(col)==true)
            resultat=resultat+Integer.valueOf((state[k+1].substring(11,13)).replaceAll("\\s+",""));
          if (state[k+l+1].contains(col)==true)
            resultat=resultat+Integer.valueOf((state[k+l+1].substring(11,13)).replaceAll("\\s+",""));
          if (state[k+l+2].contains(col)==true)
            resultat=resultat+Integer.valueOf((state[k+l+2].substring(11,13)).replaceAll("\\s+",""));
          return resultat;
        }
        /* Si vide est sur une state[k+1]ligne quelconque à droite de la ligne*/
        if (idFinLigne(l)==k && k!=0) {
          if (state[k-l-1].contains(col)==true)
            resultat=resultat+Integer.valueOf((state[k-l-1].substring(11,13)).replaceAll("\\s+",""));
          if (state[k-1].contains(col)==true)
            resultat=resultat+Integer.valueOf((state[k-1].substring(11,13)).replaceAll("\\s+",""));
          if (state[k+l+1].contains(col)==true)
            resultat=resultat+Integer.valueOf((state[k+l+1].substring(11,13)).replaceAll("\\s+",""));
          if (state[k+l+2].contains(col)==true)
            resultat=resultat+Integer.valueOf((state[k+l+2].substring(11,13)).replaceAll("\\s+",""));
          return resultat;
        }
        /* Si vide est sur une ligne quelconque au milieu de la ligne*/
        if (state[k-l-1].contains(col)==true)
          resultat=resultat+Integer.valueOf((state[k-l-1].substring(11,13)).replaceAll("\\s+",""));
        if (state[k-l].contains(col)==true)
          resultat=resultat+Integer.valueOf((state[k-l].substring(11,13)).replaceAll("\\s+",""));
        if (state[k-1].contains(col)==true)
          resultat=resultat+Integer.valueOf((state[k-1].substring(11,13)).replaceAll("\\s+",""));
        if (state[k+1].contains(col)==true)
          resultat=resultat+Integer.valueOf((state[k+1].substring(11,13)).replaceAll("\\s+",""));
        if (state[k+l+1].contains(col)==true)
          resultat=resultat+Integer.valueOf((state[k+l+1].substring(11,13)).replaceAll("\\s+",""));
        if (state[k+l+2].contains(col)==true)
          resultat=resultat+Integer.valueOf((state[k+l+2].substring(11,13)).replaceAll("\\s+",""));
        return resultat;
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
