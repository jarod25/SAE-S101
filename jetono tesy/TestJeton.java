/* BASQUIN Nicolas, KOHLER JAROD, S1A1*/

        import java.util.*;

/**
 * Created by zulupero on 24/09/2021.
 */
public class Jeton {
    static final Scanner input = new Scanner(System.in);
    static Random rand = new Random();
    public static String[] state;
    static final int NCASES = 21;
    static final int NLIGNES = 6;
    static final String[] COULEURS = {"B", "R"};
    static double[] xcase = new double[NCASES];
    static double[] ycase = new double[NCASES];
    public static final String CLEAR = "\033\143"; //Clear le terminal
    public static final String RESET = "\u001B[0m"; //Couleur par défaut du texte
    public static final String TEXTBC = "\u001B[37m"; //Couleur blanche pour le texte
    public static final String FONDR = "\u001B[41m"; //Fond rouge derrière le texte
    public static final String FONDB = "\u001B[44m"; //Fond bleu derrière le texte
    static final int SIZE = 1000; //Taille de la fenêtre StdDraw
    static final double RADIUS = (SIZE/(NLIGNES*2)); //Taille des cercles StdDraw


    static boolean estOui(char reponse) {
        return "yYoO".indexOf(reponse) != -1;
    }

    static boolean estNon(char reponse) {
        return "nN".indexOf(reponse) != -1;
    }

    public static void main(String[] args) throws InterruptedException {

        StdDraw.setXscale(0, SIZE);
        StdDraw.setYscale(0, SIZE);

        boolean newDeal;
        int scoreBleus = 0;
        int scoreRouges = 0;
        int egalite = 0;

        do {
            System.out.println("Jouer seul ? ");
            char reponse = input.next().charAt(0);
            boolean single = estOui(reponse);
            boolean duo = estNon(reponse);

            initJeu();
            if(single){
                System.out.println("Quel IA voulez vous affronter ? (1 : facile, 2 : moyen, 3 : difficile, 4 : Combat entre les IA)");
                int dif = input.nextInt();
                switch(dif){
                    case 1:
                        mainIA1();
                        break;
                    case 2:
                        mainIA2();
                        break;
                    case 3:
                        mainIA3();
                        break;
                    case 4:
                        System.out.println("Quels IA voulez vous voir s'affronter ? (1 : Combat entre les IA 1 et 2, 2 : Combat entre les IA 1 et 3, 3 : Combat entre les IA 2 et 3)");
                        int ia = input.nextInt();
                        switch(ia){
                            case 1:
                                main12IA();
                                break;
                            case 2:
                                main13IA();
                                break;
                            case 3:
                                main23IA();
                                break;
                            default:
                                main12IA();
                        }
                        break;
                    default:
                        mainIA1();
                }
                int sumR = sommeVoisins("R");
                int sumB = sommeVoisins("B");
                if ( sumB < sumR) {
                    System.out.println("Les bleus gagnent par " + sumB + " à " + sumR);
                    scoreBleus++;
                }
                else if (sumB == sumR) {
                    System.out.println("Égalité : " + sumB + " partout !");
                    egalite++;
                }
                else {
                    System.out.println("Les rouges gagnent par " + sumR + " à " + sumB);
                    scoreRouges++;
                }
                System.out.println("Score Bleu :" + scoreBleus);
                System.out.println("Score Rouge :" + scoreRouges);
                System.out.println("Nombres d'égalités : " + egalite);
            }
            else if(duo) {
                mainNotIA();
                int sumR = sommeVoisins("R");
                int sumB = sommeVoisins("B");
                if ( sumB < sumR) {
                    System.out.println("Les bleus gagnent par " + sumB + " à " + sumR);
                    scoreBleus++;
                }
                else if (sumB == sumR) {
                    System.out.println("Égalité : " + sumB + " partout !");
                    egalite++;
                }
                else {
                    System.out.println("Les rouges gagnent par " + sumR + " à " + sumB);
                    scoreRouges++;
                }
                System.out.println("Score Bleu :" + scoreBleus);
                System.out.println("Score Rouge :" + scoreRouges);
                System.out.println("Nombres d'égalités : " + egalite);
            }
            else{
                System.out.println("Erreur, tapez o/O/y/Y pour jouer seul (contre l'IA) ou n/N pour jouer a 2");
            }

            System.out.println("Nouvelle partie ? ");
            reponse = input.next().charAt(0);
            newDeal = estOui(reponse);
            System.out.print(CLEAR);
            StdDraw.clear();
        } while (newDeal);
        System.out.println("Bye Bye !");
        System.exit(0);

    }

    public static void mainNotIA(){
        System.out.println("Bonne chance a chacun !");
        afficheJeu();
        afficheJeton();

        int val = 1;
        int idCaseJouee;

        boolean b;
        for (val = 1; val <= (NCASES - 1) / 2; val++) {
            System.out.println("Au tour du bleu");
            idCaseJouee = input.nextInt();
            jouerStddraw("B", val, idCaseJouee);
            b = jouer("B", val, idCaseJouee);
            while (!b) {
                idCaseJouee = input.nextInt();
                jouerStddraw("B", val, idCaseJouee);
                b = jouer("B", val, idCaseJouee);
            }
            afficheJeu();
            System.out.println("Au tour des Rouges");
            idCaseJouee = input.nextInt();
            jouerStddraw("R", val, idCaseJouee);
            b = jouer("R", val, idCaseJouee);
            while (!b) {
                idCaseJouee = input.nextInt();
                jouerStddraw("R", val, idCaseJouee);
                b = jouer("R", val, idCaseJouee);
            }
            afficheJeu();
        }
    }

    public static void mainIA1(){
        System.out.println("Bonne chance a toi contre l'IA facile !");
        afficheJeu();
        afficheJeton();
        int val = 1;
        int idCaseJouee;

        boolean b;
        for (val = 1; val <= (NCASES - 1) / 2; val++) {
            System.out.println("Au tour du bleu");
            idCaseJouee = input.nextInt();
            jouerStddraw("B", val, idCaseJouee);
            b = jouer("B", val, idCaseJouee);
            while (!b) {
                idCaseJouee = input.nextInt();
                jouerStddraw("B", val, idCaseJouee);
                b = jouer("B", val, idCaseJouee);
            }
            afficheJeu();
            System.out.println("Au tour de l'IA 1");
            idCaseJouee = iaRouge1();
            jouerStddraw("R", val, idCaseJouee);
            b = jouer("R", val, idCaseJouee);
            while (!b) {
                idCaseJouee = iaRouge1();
                jouerStddraw("R", val, idCaseJouee);
                b = jouer("R", val, idCaseJouee);
            }
            System.out.println(idCaseJouee);

            afficheJeu();
        }
    }

    public static void mainIA2(){
        System.out.println("Bonne chance a toi contre l'IA de niveau moyen !");
        afficheJeu();
        afficheJeton();
        int val = 1;
        int idCaseJouee;

        boolean b;
        for (val = 1; val <= (NCASES - 1) / 2; val++) {
            System.out.println("Au tour du bleu");
            idCaseJouee = input.nextInt();
            jouerStddraw("B", val, idCaseJouee);
            b = jouer("B", val, idCaseJouee);
            while (!b) {
                idCaseJouee = input.nextInt();
                jouerStddraw("B", val, idCaseJouee);
                b = jouer("B", val, idCaseJouee);
            }
            afficheJeu();
            System.out.println("Au tour de l'IA 2");
            idCaseJouee = iaRouge2();
            jouerStddraw("R", val, idCaseJouee);
            b = jouer("R", val, idCaseJouee);
            while (!b) {
                idCaseJouee = iaRouge2();
                jouerStddraw("R", val, idCaseJouee);
                b = jouer("R", val, idCaseJouee);
            }
            System.out.println(idCaseJouee);

            afficheJeu();
        }
    }

    public static void mainIA3(){
        System.out.println("Bonne chance a toi contre l'IA de niveau difficile !");
        afficheJeu();
        afficheJeton();
        int val = 1;
        int idCaseJouee;

        boolean b;
        for (val = 1; val <= (NCASES - 1) / 2; val++) {
            System.out.println("Au tour du bleu");
            idCaseJouee = input.nextInt();
            jouerStddraw("B", val, idCaseJouee);
            b = jouer("B", val, idCaseJouee);
            while (!b) {
                idCaseJouee = input.nextInt();
                jouerStddraw("B", val, idCaseJouee);
                b = jouer("B", val, idCaseJouee);
            }
            afficheJeu();
            System.out.println("Au tour de l'IA 3");
            idCaseJouee = iaRouge3(val);
            jouerStddraw("R", val, idCaseJouee);
            b = jouer("R", val, idCaseJouee);
            while (!b) {
                idCaseJouee = iaRouge3(val);
                jouerStddraw("R", val, idCaseJouee);
                b = jouer("R", val, idCaseJouee);
            }
            System.out.println(idCaseJouee);

            afficheJeu();
        }
    }

    public static void main12IA(){
        System.out.println("Combat des IA 1 et 2");
        afficheJeu();
        afficheJeton();
        int val = 1;
        int idCaseJouee;

        boolean b;
        for (val = 1; val <= (NCASES - 1) / 2; val++) {
            System.out.println("Au tour de l'IA 1");
            idCaseJouee = iaRouge1();
            jouerStddraw("B", val, idCaseJouee);
            b = jouer("B", val, idCaseJouee);
            while (!b) {
                idCaseJouee = iaRouge1();
                jouerStddraw("B", val, idCaseJouee);
                b = jouer("B", val, idCaseJouee);
            }
            afficheJeu();
            System.out.println("Au tour de l'IA 2");
            idCaseJouee = iaRouge2();
            jouerStddraw("R", val, idCaseJouee);
            b = jouer("R", val, idCaseJouee);
            while (!b) {
                idCaseJouee = iaRouge2();
                jouerStddraw("R", val, idCaseJouee);
                b = jouer("R", val, idCaseJouee);
            }
            System.out.println(idCaseJouee);

            afficheJeu();
        }
    }

    public static void main13IA(){
        System.out.println("Combat des IA 1 et 3");
        afficheJeu();
        afficheJeton();
        int val = 1;
        int idCaseJouee;

        boolean b;
        for (val = 1; val <= (NCASES - 1) / 2; val++) {
            System.out.println("Au tour de l'IA 1");
            idCaseJouee = iaRouge1();
            jouerStddraw("B", val, idCaseJouee);
            b = jouer("B", val, idCaseJouee);
            while (!b) {
                idCaseJouee = iaRouge1();
                jouerStddraw("B", val, idCaseJouee);
                b = jouer("B", val, idCaseJouee);
            }
            afficheJeu();
            System.out.println("Au tour de l'IA 3");
            idCaseJouee = iaRouge3(val);
            jouerStddraw("R", val, idCaseJouee);
            b = jouer("R", val, idCaseJouee);
            while (!b) {
                idCaseJouee = iaRouge3(val);
                jouerStddraw("R", val, idCaseJouee);
                b = jouer("R", val, idCaseJouee);
            }
            System.out.println(idCaseJouee);

            afficheJeu();
        }
    }

    public static void main23IA(){
        System.out.println("Combat des IA 2 et 3");
        afficheJeu();
        afficheJeton();
        int val = 1;
        int idCaseJouee;

        boolean b;
        for (val = 1; val <= (NCASES - 1) / 2; val++) {
            System.out.println("Au tour de l'IA 2");
            idCaseJouee = iaRouge2();
            jouerStddraw("B", val, idCaseJouee);
            b = jouer("B", val, idCaseJouee);
            while (!b) {
                idCaseJouee = iaRouge2();
                jouerStddraw("B", val, idCaseJouee);
                b = jouer("B", val, idCaseJouee);
            }
            afficheJeu();
            System.out.println("Au tour de l'IA 3");
            idCaseJouee = iaRouge3(val);
            jouerStddraw("R", val, idCaseJouee);
            b = jouer("R", val, idCaseJouee);
            while (!b) {
                idCaseJouee = iaRouge3(val);
                jouerStddraw("R", val, idCaseJouee);
                b = jouer("R", val, idCaseJouee);
            }
            System.out.println(idCaseJouee);

            afficheJeu();
        }
    }

    /**
     * Initialise le jeu avec un double/triple underscore à chaque case, signifiant 'case vide'
     */
    public static void initJeu() {
        state = new String[NCASES];
        for (int i=0;i<NCASES;i++) {
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
     * @param couleur couleur du jeton : "B" ou "R"
     * @param val valeur faciale du jeton
     * @param pos position (indice) de l'emplacement où placer le jeton
     * @return true si le jeton a pu être posé, false sinon.
     */
    public static boolean jouer(String couleur, int val, int pos){
        if (pos>=0 && pos <NCASES && state[pos].equals("___")){
            if (couleur.equals("B")){
                if (val<10) {
                    state[pos] = FONDB + TEXTBC + "B"+Integer.toString(val) + " " + RESET;
                    return true;
                }
                state[pos] = FONDB + TEXTBC + "B"+Integer.toString(val) + RESET;
                return true;
            }
            if (couleur.equals("R")){
                if (val<10) {
                    state[pos] = FONDR + TEXTBC + "R"+Integer.toString(val) +" " + RESET;
                    return true;
                }
                state[pos] = FONDR + TEXTBC + "R"+Integer.toString(val) + RESET;
                return true;
            }
        }
        return false;
    }


    /**
     * Place un jeton sur le plateau StdDraw, si possible.
     * @param couleur couleur du jeton : "B" ou "R"
     * @param val valeur faciale du jeton
     * @param pos position (indice) de l'emplacement où placer le jeton
     */
    public static void jouerStddraw(String couleur, int val, int pos){
        double xb=RADIUS;
        double yb=SIZE-RADIUS;
        double xr=SIZE-RADIUS;
        double yr=SIZE-RADIUS;
        String nombre = String.valueOf(val+1);
        int nb = Integer.parseInt(nombre);
        String value = String.valueOf(val);
        if (pos>=0 && pos <NCASES && state[pos].equals("___")){
            if (couleur.equals("B")){
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.filledCircle(xcase[pos],ycase[pos],RADIUS);
                StdDraw.filledCircle(xb,yb,RADIUS);
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.text(xcase[pos],ycase[pos],value);
                StdDraw.text(xb,yb,nombre);
                if(nb>NCASES/2){
                    StdDraw.setPenColor(StdDraw.GRAY);
                    StdDraw.filledCircle(xb,yb,RADIUS);
                }
                return;
            }
            if (couleur.equals("R")){
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.filledCircle(xcase[pos],ycase[pos],RADIUS);
                StdDraw.filledCircle(xr,yr,RADIUS);
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.text(xcase[pos],ycase[pos],value);
                StdDraw.text(xr,yr,nombre);
                if(nb>NCASES/2){
                    StdDraw.setPenColor(StdDraw.GRAY);
                    StdDraw.filledCircle(xr,yr,RADIUS);
                }
            }
        }
        return;
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
            if(state[k].equals("___")){
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
                if (state[k-l].contains(col))
                    resultat+=Integer.valueOf(state[k-l].substring(11,13).replaceAll("\\s",""));
                if (state[k+1].contains(col))
                    resultat+=Integer.valueOf(state[k+1].substring(11,13).replaceAll("\\s",""));
                return resultat;
            }
            /* Si vide est sur la dernière ligne à droite*/
            if (idFinLigne(NLIGNES-1)==k) {
                if (state[k-l-1].contains(col))
                    resultat+=Integer.valueOf(state[k-l-1].substring(11,13).replaceAll("\\s",""));
                if (state[k-1].contains(col))
                    resultat+=Integer.valueOf(state[k-1].substring(11,13).replaceAll("\\s",""));
                return resultat;
            }
            /* Si vide est sur la dernière ligne au milieu*/
            if (state[k-l-1].contains(col))
                resultat+=Integer.valueOf(state[k-l-1].substring(11,13).replaceAll("\\s",""));
            if (state[k-l].contains(col))
                resultat+=Integer.valueOf(state[k-l].substring(11,13).replaceAll("\\s",""));
            if (state[k-1].contains(col))
                resultat+=Integer.valueOf(state[k-1].substring(11,13).replaceAll("\\s",""));
            if (state[k+1].contains(col))
                resultat+=Integer.valueOf(state[k+1].substring(11,13).replaceAll("\\s",""));
            return resultat;
        }
        /* Si vide est sur la première ligne*/
        if (k==0) {
            if (state[1].contains(col))
                resultat+=Integer.valueOf(state[1].substring(11,13).replaceAll("\\s",""));
            if (state[2].contains(col))
                resultat+=Integer.valueOf(state[2].substring(11,13).replaceAll("\\s",""));
            return resultat;
        }
        /* Si vide est sur une ligne quelconque à gauche de la ligne*/
        if (idDebutLigne(l)==k && k!=0) {
            if (state[k-l].contains(col))
                resultat+=Integer.valueOf(state[k-l].substring(11,13).replaceAll("\\s",""));
            if (state[k+1].contains(col))
                resultat+=Integer.valueOf(state[k+1].substring(11,13).replaceAll("\\s",""));
            if (state[k+l+1].contains(col))
                resultat+=Integer.valueOf(state[k+l+1].substring(11,13).replaceAll("\\s",""));
            if (state[k+l+2].contains(col))
                resultat+=Integer.valueOf(state[k+l+2].substring(11,13).replaceAll("\\s",""));
            return resultat;
        }
        /* Si vide est sur une state[k+1]ligne quelconque à droite de la ligne*/
        if (idFinLigne(l)==k && k!=0) {
            if (state[k-l-1].contains(col))
                resultat+=Integer.valueOf(state[k-l-1].substring(11,13).replaceAll("\\s",""));
            if (state[k-1].contains(col))
                resultat+=Integer.valueOf(state[k-1].substring(11,13).replaceAll("\\s",""));
            if (state[k+l+1].contains(col))
                resultat+=Integer.valueOf(state[k+l+1].substring(11,13).replaceAll("\\s",""));
            if (state[k+l+2].contains(col))
                resultat+=Integer.valueOf(state[k+l+2].substring(11,13).replaceAll("\\s",""));
            return resultat;
        }
        /* Si vide est sur une ligne quelconque au milieu de la ligne*/
        if (state[k-l-1].contains(col))
            resultat+=Integer.valueOf(state[k-l-1].substring(11,13).replaceAll("\\s",""));
        if (state[k-l].contains(col))
            resultat+=Integer.valueOf(state[k-l].substring(11,13).replaceAll("\\s",""));
        if (state[k-1].contains(col))
            resultat+=Integer.valueOf(state[k-1].substring(11,13).replaceAll("\\s",""));
        if (state[k+1].contains(col))
            resultat+=Integer.valueOf(state[k+1].substring(11,13).replaceAll("\\s",""));
        if (state[k+l+1].contains(col))
            resultat+=Integer.valueOf(state[k+l+1].substring(11,13).replaceAll("\\s",""));
        if (state[k+l+2].contains(col))
            resultat+=Integer.valueOf(state[k+l+2].substring(11,13).replaceAll("\\s",""));
        return resultat;
    }

    public static int sommeVoisins2(String col, int i){
        int k = i;
        int l = 0;
        while(k>=idDebutLigne(l)){
            l=l+1;
        }
        l=l-1;
        int resultat=0;
        /* Si vide est sur la dernière ligne à gauche*/
        if (NLIGNES-1==l) {
            if (idDebutLigne(NLIGNES-1)==k) {
                if (state[k-l].contains(col))
                    resultat+=Integer.valueOf(state[k-l].substring(11,13).replaceAll("\\s",""));
                if (state[k+1].contains(col))
                    resultat+=Integer.valueOf(state[k+1].substring(11,13).replaceAll("\\s",""));
                return resultat;
            }
            /* Si vide est sur la dernière ligne à droite*/
            if (idFinLigne(NLIGNES-1)==k) {
                if (state[k-l-1].contains(col))
                    resultat+=Integer.valueOf(state[k-l-1].substring(11,13).replaceAll("\\s",""));
                if (state[k-1].contains(col))
                    resultat+=Integer.valueOf(state[k-1].substring(11,13).replaceAll("\\s",""));
                return resultat;
            }
            /* Si vide est sur la dernière ligne au milieu*/
            if (state[k-l-1].contains(col))
                resultat+=Integer.valueOf(state[k-l-1].substring(11,13).replaceAll("\\s",""));
            if (state[k-l].contains(col))
                resultat+=Integer.valueOf(state[k-l].substring(11,13).replaceAll("\\s",""));
            if (state[k-1].contains(col))
                resultat+=Integer.valueOf(state[k-1].substring(11,13).replaceAll("\\s",""));
            if (state[k+1].contains(col))
                resultat+=Integer.valueOf(state[k+1].substring(11,13).replaceAll("\\s",""));
            return resultat;
        }
        /* Si vide est sur la première ligne*/
        if (k==0) {
            if (state[1].contains(col))
                resultat+=Integer.valueOf(state[1].substring(11,13).replaceAll("\\s",""));
            if (state[2].contains(col))
                resultat+=Integer.valueOf(state[2].substring(11,13).replaceAll("\\s",""));
            return resultat;
        }
        /* Si vide est sur une ligne quelconque à gauche de la ligne*/
        if (idDebutLigne(l)==k && k!=0) {
            if (state[k-l].contains(col))
                resultat+=Integer.valueOf(state[k-l].substring(11,13).replaceAll("\\s",""));
            if (state[k+1].contains(col))
                resultat+=Integer.valueOf(state[k+1].substring(11,13).replaceAll("\\s",""));
            if (state[k+l+1].contains(col))
                resultat+=Integer.valueOf(state[k+l+1].substring(11,13).replaceAll("\\s",""));
            if (state[k+l+2].contains(col))
                resultat+=Integer.valueOf(state[k+l+2].substring(11,13).replaceAll("\\s",""));
            return resultat;
        }
        /* Si vide est sur une state[k+1]ligne quelconque à droite de la ligne*/
        if (idFinLigne(l)==k && k!=0) {
            if (state[k-l-1].contains(col))
                resultat+=Integer.valueOf(state[k-l-1].substring(11,13).replaceAll("\\s",""));
            if (state[k-1].contains(col))
                resultat+=Integer.valueOf(state[k-1].substring(11,13).replaceAll("\\s",""));
            if (state[k+l+1].contains(col))
                resultat+=Integer.valueOf(state[k+l+1].substring(11,13).replaceAll("\\s",""));
            if (state[k+l+2].contains(col))
                resultat+=Integer.valueOf(state[k+l+2].substring(11,13).replaceAll("\\s",""));
            return resultat;
        }
        /* Si vide est sur une ligne quelconque au milieu de la ligne*/
        if (state[k-l-1].contains(col))
            resultat+=Integer.valueOf(state[k-l-1].substring(11,13).replaceAll("\\s",""));
        if (state[k-l].contains(col))
            resultat+=Integer.valueOf(state[k-l].substring(11,13).replaceAll("\\s",""));
        if (state[k-1].contains(col))
            resultat+=Integer.valueOf(state[k-1].substring(11,13).replaceAll("\\s",""));
        if (state[k+1].contains(col))
            resultat+=Integer.valueOf(state[k+1].substring(11,13).replaceAll("\\s",""));
        if (state[k+l+1].contains(col))
            resultat+=Integer.valueOf(state[k+l+1].substring(11,13).replaceAll("\\s",""));
        if (state[k+l+2].contains(col))
            resultat+=Integer.valueOf(state[k+l+2].substring(11,13).replaceAll("\\s",""));
        return resultat;
    }

    /**
     * Renvoie le prochain coup à jouer pour les rouges
     * Algo naïf = la première case dispo
     * @return id de la case
     */
    public static int iaRouge1(){
        for (int i=0;i<NCASES;i++) {
            if (state[i].equals("___"))
                return i;
        }
        return NCASES-1;
    }

    /**
     return Integer.parseInt(input.next());
     * Renvoie le prochain coup à jouer pour les rouges
     * IA place aléatoirement sur les espaces diponibles le jeton
     * @return id de la case
     */
    public static int iaRouge2(){
        double c;
        String b="";
        for (int i=0; i<NCASES; i++){
            c=Math.random();
            c=c*i;
            b=String.valueOf(Math.round(c));
        }
        return Integer.parseInt(b);
    }

    public static int iaRouge3(int val){
        double c;
        String b="";
        if (val==NCASES/2){
            int i=0;
            while(!state[i].equals("___")){
                i+=1;
            }
            int vide1B = sommeVoisins("B");
            int vide1R = sommeVoisins("R");
            int vide1id = i;
            i=i+1;
            while(!state[i].equals("___")){
                i+=1;
            }
            int vide2B = sommeVoisins2("B",i);
            int vide2R = sommeVoisins2("R",i);
            int vide2id = i;
            if ((vide1R-vide1B)>=(vide2R-vide2B))
                return vide1id;
            return vide2id;
        }
        for (int i=0; i<NCASES; i++){
            c=Math.random();
            c=c*i;
            b=String.valueOf(Math.round(c));
        }
        return Integer.parseInt(b);
    }

    /**
     * Renvoie dans le tableau xcase les positions x de chaque cercle
     * Renvoie dans le tableau ycase les positions y de chaque cercle
     */
    public static void afficheJeton(){
        double xb=RADIUS;
        double yb=SIZE-RADIUS;
        double xr=SIZE-RADIUS;
        double yr=SIZE-RADIUS;
        String nombre = "1";
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledCircle(xb,yb,RADIUS);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(xb,yb,nombre);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(xr,yr,RADIUS);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(xr,yr,nombre);
        StdDraw.setPenRadius(0.001);
        StdDraw.setPenColor(StdDraw.BLACK);
        int idcase = 0;
        double x;
        double y=SIZE+RADIUS;
        String id;
        for (int i = 0 ; i < NLIGNES ; i++ ) {  //parcours les lignes
            y-=((SIZE)/NLIGNES);//division de int par int donc pas besoin de double
            for ( int j = 0 ; j <= i ; j++) { //parcours les cases par lignes
                x=((SIZE/2)+(RADIUS*2)*j-(RADIUS*(i)));//division de int par int donc pas besoin de double
                StdDraw.circle(x,y,RADIUS);
                id = String.valueOf(idcase);
                StdDraw.text((x+(RADIUS*0.2)),y-(RADIUS*0.4),id);
                xcase[idcase]=x;
                ycase[idcase]=y;
                idcase++; // numéro de la case
            }
        }
    }
}
