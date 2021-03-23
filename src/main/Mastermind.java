package main;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Mastermind {

    static MainWindow mainWindow = new MainWindow();
    static Font font, plainFont, menuFont, boldFont;
    static Image buttonReleased, buttonPressed, panel, grey, red, blue, green, yellow, extendedPanelLeft, extendedPanelRight,
            leftArrow, rightArrow, selected, unselected, glossyGrey, glossyRed, glossyBlue, glossyGreen, glossyYellow;
    static Code[] codemakerGrid;
    static Code[][] codebreakerGrid;
    static int[][] checkGrid;

    public enum Code {
        BLANK(grey, glossyGrey, 0),
        BLUE(blue, glossyBlue, 1),
        GREEN(green, glossyGreen, 2),
        YELLOW(yellow, glossyYellow, 3),
        RED(red, glossyRed, 4);

        public final Image regular;
        public final Image glossy;
        public final int value;

        Code(Image regular, Image glossy, int value) {
            this.regular = regular;
            this.glossy = glossy;
            this.value = value;
        }
    }

    public static void main(String[] args) {

        loadResources();
        codemakerGrid = new Code[4];
        codemakerGrid = blankCodemakerGrid(codemakerGrid);
        codebreakerGrid = new Code[10][4];
        codebreakerGrid = blankCodebreakerGrid(codebreakerGrid);
        checkGrid = new int[10][2];
        checkGrid = blankGrid(checkGrid);
        mainWindow.setIconImage(Mastermind.red);
        mainWindow.paintStartScreen();
    }

    public static void playSound(String path) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println("sound load error");
        }
    }

    private static void loadResources() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/res/font/font.ttf"));
            plainFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/res/font/plain_font.ttf"));
            menuFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/res/font/menu_font.ttf"));
            boldFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/res/font/bold_font.ttf"));
        } catch (IOException | FontFormatException e) {
            System.err.println("font load error");
        }
        try {
            buttonReleased = ImageIO.read(Mastermind.class.getResource("res/img/released.png"));
            buttonReleased = buttonReleased.getScaledInstance(170, 25, 8);
            buttonPressed = ImageIO.read(Mastermind.class.getResource("res/img/pressed.png"));
            buttonPressed = buttonPressed.getScaledInstance(170, 23, 8);
            panel = ImageIO.read(Mastermind.class.getResource("res/img/panel.png"));
            panel = panel.getScaledInstance(210, 62, 8);
            grey = ImageIO.read(Mastermind.class.getResource("res/img/grey.png"));
            grey = grey.getScaledInstance(40, 40, 8);
            red = ImageIO.read(Mastermind.class.getResource("res/img/red.png"));
            red = red.getScaledInstance(40, 40, 8);
            blue = ImageIO.read(Mastermind.class.getResource("res/img/blue.png"));
            blue = blue.getScaledInstance(40, 40, 8);
            green = ImageIO.read(Mastermind.class.getResource("res/img/green.png"));
            green = green.getScaledInstance(40, 40, 8);
            yellow = ImageIO.read(Mastermind.class.getResource("res/img/yellow.png"));
            yellow = yellow.getScaledInstance(40, 40, 8);
            glossyGrey = ImageIO.read(Mastermind.class.getResource("res/img/grey_g.png"));
            glossyGrey = glossyGrey.getScaledInstance(40, 40, 8);
            glossyRed = ImageIO.read(Mastermind.class.getResource("res/img/red_g.png"));
            glossyRed = glossyRed.getScaledInstance(40, 40, 8);
            glossyBlue = ImageIO.read(Mastermind.class.getResource("res/img/blue_g.png"));
            glossyBlue = glossyBlue.getScaledInstance(40, 40, 8);
            glossyGreen = ImageIO.read(Mastermind.class.getResource("res/img/green_g.png"));
            glossyGreen = glossyGreen.getScaledInstance(40, 40, 8);
            glossyYellow = ImageIO.read(Mastermind.class.getResource("res/img/yellow_g.png"));
            glossyYellow = glossyYellow.getScaledInstance(40, 40, 8);
            extendedPanelLeft = ImageIO.read(Mastermind.class.getResource("res/img/big_white_panel.png"));
            extendedPanelLeft = extendedPanelLeft.getScaledInstance(210, 514, 16);
            extendedPanelRight = ImageIO.read(Mastermind.class.getResource("res/img/big_yellow_panel.png"));
            extendedPanelRight = extendedPanelRight.getScaledInstance(60, 514, 16);
            leftArrow = ImageIO.read(Mastermind.class.getResource("res/img/arrow_left.png"));
            rightArrow = ImageIO.read(Mastermind.class.getResource("res/img/arrow_right.png"));
            selected = ImageIO.read(Mastermind.class.getResource("res/img/selected.png"));
            selected = selected.getScaledInstance(17, 17, 8);
            unselected = ImageIO.read(Mastermind.class.getResource("res/img/unselected.png"));
            unselected = unselected.getScaledInstance(17, 17, 8);
        } catch (IOException e) {
            System.err.println("image load error");
        }
    }

    public static Code[] blankCodemakerGrid(Code[] codemakerGrid) {

        Code[] grid = codemakerGrid;

        for (int i = 0; i < 4; i++) {
            grid[i] = Code.BLANK;
        }
        return grid;
    }

    public static Code[][] blankCodebreakerGrid(Code[][] codebreakerGrid) {

        Code[][] grid = codebreakerGrid;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 4; j++) {
                grid[i][j] = Code.BLANK;
            }
        }
        return grid;
    }

    public static int[][] blankGrid(int[][] checkGrid) {

        int[][] grid = checkGrid;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 2; j++) {
                grid[i][j] = 0;
            }
        }
        return grid;
    }

    public static void randomizeGrid(int level) {
        Random random = new Random();
        int value;

        for (int i = 0; i < 4; i++) {
            value = random.nextInt(level);
            switch (value) {
                case 0:
                    codemakerGrid[i] = Code.BLUE;
                    break;
                case 1:
                    codemakerGrid[i] = Code.GREEN;
                    break;
                case 2:
                    codemakerGrid[i] = Code.YELLOW;
                    break;
                case 3:
                    codemakerGrid[i] = Code.RED;
            }
        }
    }

    public static void compareGrids() {

        if (MainWindow.stageCheck <= 10) {
            boolean[] redPegs = new boolean[4];
            boolean[] whitePegs = new boolean[4];

            for (int i = 0; i < 4; i++) {
                redPegs[i] = true;
                whitePegs[i] = true;
            }
            for (int i = 0; i < 4; i++) {
                if (codebreakerGrid[MainWindow.stageCheck][i].equals(codemakerGrid[i])) {
                    whitePegs[i] = false;
                    redPegs[i] = false;
                    checkGrid[MainWindow.stageCheck][1]++;
                }
            }
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (redPegs[i] && whitePegs[j] && codebreakerGrid[MainWindow.stageCheck][i].equals(codemakerGrid[j])) {
                        checkGrid[MainWindow.stageCheck][0]++;
                        whitePegs[j] = false;
                        break;
                    }
                }
            }
        }
    }
}