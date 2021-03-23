package main;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class MainWindow extends JFrame implements ActionListener {

    int codemakerWin = 2;
    int codemakerScore = 0;
    int codebreakerScore = 0;
    int difficultyLevel = 3;
    boolean gameOver = false;
    boolean isCodemaker = false;
    static int loop = 0;
    static int stageCheck = 0;

    public MainWindow() {
        this.setSize(317, 338);
        this.setTitle("Mastermind");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
    }

    public JLabel addCheckLabel(int x, int y, int i, int j) {

        JLabel label = new JLabel();
        int value = Mastermind.checkGrid[i][j];
        if (i < stageCheck) {
            label.setText(String.valueOf(value));
        }
        label.setBounds(x, y, 25, 25);
        label.setFont(Mastermind.font.deriveFont(30f));
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.CENTER);

        return label;
    }

    public JLabel addMakerLabel(int x, int y, int i) {

        JLabel label = new JLabel();
        label.setBounds(x, y, 40, 40);
        Mastermind.Code type = Mastermind.codemakerGrid[i];
        label.setIcon(new ImageIcon(type.glossy));
        if (!gameOver) {
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    Mastermind.playSound("src/main/res/sound/click.wav");
                    loop = type.value;
                    loop++;
                    int temp = loop % 4;
                    switch (temp) {
                        case 0:
                            Mastermind.codemakerGrid[i] = Mastermind.Code.RED;
                            paintCodemakerScreen(true);
                            break;
                        case 1:
                            Mastermind.codemakerGrid[i] = Mastermind.Code.BLUE;
                            paintCodemakerScreen(true);
                            break;
                        case 2:
                            Mastermind.codemakerGrid[i] = Mastermind.Code.GREEN;
                            paintCodemakerScreen(true);
                            break;
                        case 3:
                            Mastermind.codemakerGrid[i] = Mastermind.Code.YELLOW;
                            paintCodemakerScreen(true);
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
        return label;
    }

    public JLabel addBreakerLabel(int x, int y, int i, int j, int stage) {

        JLabel label = new JLabel();
        label.setBounds(x, y, 40, 40);
        Mastermind.Code type = Mastermind.codebreakerGrid[j][i];
        if (stage == stageCheck) {
            label.setIcon(new ImageIcon(type.glossy));
            if (gameOver) {
                return label;
            }
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    Mastermind.playSound("src/main/res/sound/click.wav");
                    loop = type.value;
                    loop++;
                    int temp = loop % 5;
                    switch (temp) {
                        case 0:
                            Mastermind.codebreakerGrid[j][i] = Mastermind.Code.BLANK;
                            paintCodebreakerScreen();
                            break;
                        case 1:
                            Mastermind.codebreakerGrid[j][i] = Mastermind.Code.BLUE;
                            paintCodebreakerScreen();
                            break;
                        case 2:
                            Mastermind.codebreakerGrid[j][i] = Mastermind.Code.GREEN;
                            paintCodebreakerScreen();
                            break;
                        case 3:
                            Mastermind.codebreakerGrid[j][i] = Mastermind.Code.YELLOW;
                            paintCodebreakerScreen();
                            break;
                        case 4:
                            Mastermind.codebreakerGrid[j][i] = Mastermind.Code.RED;
                            paintCodebreakerScreen();
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        } else {
            label.setIcon(new ImageIcon(type.regular));
        }
        return label;
    }

    public JButton addButton(String title, int x, int y) {

        JButton button = new JButton(title);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        button.setBounds(x, y, 170, 25);
        button.setForeground(new Color(10, 10, 10));
        button.setFont(Mastermind.font.deriveFont(22f));
        button.setIcon(new ImageIcon(Mastermind.buttonReleased));
        button.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                AbstractButton button = (AbstractButton) e.getSource();
                ButtonModel buttonModel = button.getModel();
                boolean isPressed = buttonModel.isPressed();
                boolean isArmed = buttonModel.isArmed();
                if (isPressed && isArmed) {
                    Mastermind.playSound("src/main/res/sound/click.wav");
                    button.setIcon(new ImageIcon(Mastermind.buttonPressed));
                    int newY = y + 2;
                    button.setBounds(x, newY, 170, 23);
                } else {
                    buttonModel.setPressed(false);
                    button.setIcon(new ImageIcon(Mastermind.buttonReleased));
                    button.setBounds(x, y, 170, 25);
                }
            }
        });
        return button;
    }

    public void paintStartScreen() {

        int yPos = 192;
        int xPos = 65;

        setSize(317, 338);
        getContentPane().removeAll();

        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 300, 300);
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(0, 150, 250));
        Mastermind.mainWindow.add(mainPanel);

        JLabel masterLabel = new JLabel("Master");
        masterLabel.setFont(Mastermind.plainFont.deriveFont(80f));
        masterLabel.setBounds(23, 48, 300, 50);
        masterLabel.setForeground(Color.WHITE);
        mainPanel.add(masterLabel);

        JLabel mindLabel = new JLabel("Mind");
        mindLabel.setFont(Mastermind.plainFont.deriveFont(80f));
        mindLabel.setBounds(61, 110, 200, 50);
        mindLabel.setForeground(Color.WHITE);
        mainPanel.add(mindLabel);

        JButton singlePlayer = addButton("Single player", xPos, yPos);
        singlePlayer.addActionListener(this);
        mainPanel.add(singlePlayer);

        JButton multiplayer = addButton("Multiplayer", xPos, yPos + 28);
        multiplayer.addActionListener(this);
        mainPanel.add(multiplayer);

        JButton quit = addButton("Quit", xPos, yPos + 56);
        quit.addActionListener(this);
        mainPanel.add(quit);

        repaint();
        setVisible(true);
    }

    public void paintChoiceScreen() {

        int xPos = 65;
        int yPos = 220;

        setSize(317, 338);
        getContentPane().removeAll();

        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 300, 700);
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(0, 150, 250));
        Mastermind.mainWindow.add(mainPanel);

        ButtonGroup makerOrBreaker = new ButtonGroup();

        JRadioButton maker = new JRadioButton();
        maker.setIcon(new ImageIcon(Mastermind.unselected));
        maker.setSelectedIcon(new ImageIcon(Mastermind.selected));
        maker.setBounds(xPos - 5, yPos - 177, 25, 20);
        maker.setBackground(new Color(0, 150, 250));
        makerOrBreaker.add(maker);
        mainPanel.add(maker);
        maker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mastermind.playSound("src/main/res/sound/click.wav");
                isCodemaker = true;
            }
        });

        JLabel makerLabel = new JLabel("Codemaker");
        makerLabel.setFont(Mastermind.font.deriveFont(25f));
        makerLabel.setForeground(Color.WHITE);
        makerLabel.setBounds(xPos + 25, yPos - 175, 300, 20);
        mainPanel.add(makerLabel);

        JRadioButton breaker = new JRadioButton();
        breaker.setIcon(new ImageIcon(Mastermind.unselected));
        breaker.setSelectedIcon(new ImageIcon(Mastermind.selected));
        breaker.setBounds(xPos - 5, yPos - 149, 25, 20);
        breaker.setBackground(new Color(0, 150, 250));
        breaker.setSelected(true);
        makerOrBreaker.add(breaker);
        mainPanel.add(breaker);
        breaker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mastermind.playSound("src/main/res/sound/click.wav");
                isCodemaker = false;
            }
        });

        JLabel breakerLabel = new JLabel("Codebreaker");
        breakerLabel.setFont(Mastermind.font.deriveFont(25f));
        breakerLabel.setForeground(Color.WHITE);
        breakerLabel.setBounds(xPos + 25, yPos - 147, 300, 20);
        mainPanel.add(breakerLabel);

        ButtonGroup difficulty = new ButtonGroup();

        JRadioButton easy = new JRadioButton();
        easy.setIcon(new ImageIcon(Mastermind.unselected));
        easy.setSelectedIcon(new ImageIcon(Mastermind.selected));
        easy.setBounds(xPos - 5, yPos - 95, 25, 20);
        easy.setBackground(new Color(0, 150, 250));
        difficulty.add(easy);
        mainPanel.add(easy);
        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mastermind.playSound("src/main/res/sound/click.wav");
                difficultyLevel = 2;
            }
        });

        JLabel easyLabel = new JLabel("Easy");
        easyLabel.setFont(Mastermind.font.deriveFont(25f));
        easyLabel.setForeground(Color.WHITE);
        easyLabel.setBounds(xPos + 25, yPos - 93, 300, 20);
        mainPanel.add(easyLabel);

        JRadioButton medium = new JRadioButton();
        medium.setIcon(new ImageIcon(Mastermind.unselected));
        medium.setSelectedIcon(new ImageIcon(Mastermind.selected));
        medium.setBounds(xPos - 5, yPos - 67, 25, 20);
        medium.setBackground(new Color(0, 150, 250));
        medium.setSelected(true);
        difficulty.add(medium);
        mainPanel.add(medium);
        medium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mastermind.playSound("src/main/res/sound/click.wav");
                difficultyLevel = 3;
            }
        });

        JLabel mediumLabel = new JLabel("Medium");
        mediumLabel.setFont(Mastermind.font.deriveFont(25f));
        mediumLabel.setForeground(Color.WHITE);
        mediumLabel.setBounds(xPos + 25, yPos - 65, 300, 20);
        mainPanel.add(mediumLabel);

        JRadioButton hard = new JRadioButton();
        hard.setIcon(new ImageIcon(Mastermind.unselected));
        hard.setSelectedIcon(new ImageIcon(Mastermind.selected));
        hard.setBounds(xPos - 5, yPos - 39, 25, 20);
        hard.setBackground(new Color(0, 150, 250));
        hard.setSelected(true);
        difficulty.add(hard);
        mainPanel.add(hard);
        hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mastermind.playSound("src/main/res/sound/click.wav");
                difficultyLevel = 4;
            }
        });

        JLabel hardLabel = new JLabel("Hard");
        hardLabel.setFont(Mastermind.font.deriveFont(25f));
        hardLabel.setForeground(Color.WHITE);
        hardLabel.setBounds(xPos + 25, yPos - 37, 300, 20);
        mainPanel.add(hardLabel);

        JButton play = addButton("Play", xPos, yPos);
        play.addActionListener(this);
        mainPanel.add(play);

        JButton back = addButton("Back", xPos, yPos + 28);
        back.addActionListener(this);
        mainPanel.add(back);

        repaint();
    }

    public void paintCodemakerScreen(boolean validData) {

        int xPos = 65;
        int yPos = 192;
        int xPanePos = 42;
        int yPanePos = 80;

        setSize(317, 338);
        getContentPane().removeAll();

        JLayeredPane mainPanel = new JLayeredPane();
        mainPanel.setBounds(0, 0, 300, 300);
        mainPanel.setLayout(null);
        Mastermind.mainWindow.add(mainPanel);

        JPanel background = new JPanel();
        background.setBounds(0, 0, 300, 300);
        background.setLayout(null);
        background.setBackground(new Color(0, 150, 250));
        mainPanel.add(background, JLayeredPane.DEFAULT_LAYER);

        JLabel hintMessage = new JLabel();
        hintMessage.setText("Set your code below:");
        hintMessage.setBounds(43, 40, 250, 30);
        hintMessage.setForeground(Color.WHITE);
        hintMessage.setFont(Mastermind.font.deriveFont(25f));
        background.add(hintMessage);

        JLabel panel = new JLabel();
        panel.setBounds(xPanePos, yPanePos, 210, 62);
        panel.setIcon(new ImageIcon(Mastermind.panel));
        mainPanel.add(panel, JLayeredPane.PALETTE_LAYER);

        JLabel firstLabel = addMakerLabel(xPanePos + 10, yPanePos + 10, 0);
        mainPanel.add(firstLabel, JLayeredPane.MODAL_LAYER);

        JLabel secondLabel = addMakerLabel(xPanePos + 60, yPanePos + 10, 1);
        mainPanel.add(secondLabel, JLayeredPane.MODAL_LAYER);

        JLabel thirdLabel = addMakerLabel(xPanePos + 110, yPanePos + 10, 2);
        mainPanel.add(thirdLabel, JLayeredPane.MODAL_LAYER);

        JLabel fourthLabel = addMakerLabel(xPanePos + 160, yPanePos + 10, 3);
        mainPanel.add(fourthLabel, JLayeredPane.MODAL_LAYER);

        if (!validData) {
            JLabel invalidDataMessage = new JLabel();
            invalidDataMessage.setText("Blank is not allowed!");
            invalidDataMessage.setBounds(30, 155, 300, 30);
            invalidDataMessage.setForeground(new Color(255, 50, 50));
            invalidDataMessage.setFont(Mastermind.font.deriveFont(25f));
            background.add(invalidDataMessage);
        }

        JButton confirm = addButton("Confirm", xPos, yPos);
        confirm.addActionListener(this);
        background.add(confirm);

        JButton randomize = addButton("Randomize", xPos, yPos + 28);
        randomize.addActionListener(this);
        background.add(randomize);

        JButton mainMenu = addButton("Restart", xPos, yPos + 56);
        mainMenu.addActionListener(this);
        background.add(mainMenu);

        repaint();
    }

    public void paintCodebreakerScreen() {

        int xPos = 33;
        int yPos = 570;
        int xPanePos = 10;
        int yPanePos = 44;

        setSize(317, 676);
        getContentPane().removeAll();

        JLayeredPane mainPanel = new JLayeredPane();
        mainPanel.setBounds(0, 0, 300, 700);
        mainPanel.setLayout(null);
        Mastermind.mainWindow.add(mainPanel);

        JPanel background = new JPanel();
        background.setBounds(0, 0, 300, 700);
        background.setLayout(null);
        background.setBackground(new Color(0, 150, 250));
        mainPanel.add(background, JLayeredPane.DEFAULT_LAYER);

        JLabel panelLeft = new JLabel();
        panelLeft.setBounds(xPanePos, yPanePos, 210, 514);
        panelLeft.setIcon(new ImageIcon(Mastermind.extendedPanelLeft));
        mainPanel.add(panelLeft, JLayeredPane.PALETTE_LAYER);

        JLabel panelRight = new JLabel();
        panelRight.setBounds(xPanePos + 220, yPanePos, 60, 514);
        panelRight.setIcon(new ImageIcon(Mastermind.extendedPanelRight));
        mainPanel.add(panelRight, JLayeredPane.PALETTE_LAYER);

        for (int i = 0; i < 10; i++) {
            JLabel firstLabel = addBreakerLabel(xPanePos + 10, yPanePos + 10 + (i * 50), 0, i, i);
            mainPanel.add(firstLabel, JLayeredPane.MODAL_LAYER);

            JLabel secondLabel = addBreakerLabel(xPanePos + 60, yPanePos + 10 + (i * 50), 1, i, i);
            mainPanel.add(secondLabel, JLayeredPane.MODAL_LAYER);

            JLabel thirdLabel = addBreakerLabel(xPanePos + 110, yPanePos + 10 + (i * 50), 2, i, i);
            mainPanel.add(thirdLabel, JLayeredPane.MODAL_LAYER);

            JLabel fourthLabel = addBreakerLabel(xPanePos + 160, yPanePos + 10 + (i * 50), 3, i, i);
            mainPanel.add(fourthLabel, JLayeredPane.MODAL_LAYER);
        }

        for (int i = 0; i < 10; i++) {
            JLabel whitePegLabel = addCheckLabel(xPanePos + 230, yPanePos + 17 + (i * 50), i, 0);
            whitePegLabel.setForeground(Color.WHITE);
            mainPanel.add(whitePegLabel, JLayeredPane.MODAL_LAYER);

            JLabel redPegLabel = addCheckLabel(xPanePos + 254, yPanePos + 17 + (i * 50), i, 1);
            redPegLabel.setForeground(Color.RED);
            mainPanel.add(redPegLabel, JLayeredPane.MODAL_LAYER);
        }

        if (!gameOver) {
            JLabel stageMessage = new JLabel();
            String stage = String.valueOf(stageCheck + 1);
            stageMessage.setText("Stage " + stage);
            stageMessage.setForeground(Color.WHITE);
            stageMessage.setFont(Mastermind.font.deriveFont(25f));
            stageMessage.setBounds(xPanePos + 5, yPanePos - 35, 250, 30);
            background.add(stageMessage);
        } else {
            JLabel overMessage = new JLabel();
            overMessage.setText("Game over!");
            overMessage.setForeground(Color.WHITE);
            overMessage.setFont(Mastermind.font.deriveFont(25f));
            overMessage.setBounds(xPanePos + 5, yPanePos - 35, 250, 30);
            background.add(overMessage);
        }

        JLabel pinsMessage = new JLabel();
        pinsMessage.setText("Pins:");
        pinsMessage.setForeground(Color.WHITE);
        pinsMessage.setFont(Mastermind.font.deriveFont(25f));
        pinsMessage.setBounds(xPanePos + 225, yPanePos - 35, 250, 30);
        background.add(pinsMessage);

        if (!gameOver) {
            JButton check = addButton("Check", xPos, yPos);
            check.addActionListener(this);
            background.add(check);

            JButton restart = addButton("Restart", xPos, yPos + 28);
            restart.addActionListener(this);
            background.add(restart);
        } else {
            JButton back = addButton("Back", xPos, yPos);
            back.addActionListener(this);
            background.add(back);
        }

        if (!gameOver) {
            JLabel arrowRightLabel = new JLabel();
            arrowRightLabel.setIcon(new ImageIcon(Mastermind.rightArrow));
            arrowRightLabel.setBounds(xPanePos - 12, yPanePos + 18 + stageCheck * 50, 25, 25);
            mainPanel.add(arrowRightLabel, JLayeredPane.POPUP_LAYER);

            JLabel arrowLeftLabel = new JLabel();
            arrowLeftLabel.setIcon(new ImageIcon(Mastermind.leftArrow));
            arrowLeftLabel.setBounds(xPanePos + 200, yPanePos + 18 + stageCheck * 50, 25, 25);
            mainPanel.add(arrowLeftLabel, JLayeredPane.POPUP_LAYER);
        }

        repaint();
    }

    public void paintEndScreen() {

        int xPos = 65;
        int yPos = 192;
        int xPanePos = 42;
        int yPanePos = 80;

        setSize(317, 338);
        getContentPane().removeAll();

        JLayeredPane mainPanel = new JLayeredPane();
        mainPanel.setBounds(0, 0, 300, 300);
        mainPanel.setLayout(null);
        Mastermind.mainWindow.add(mainPanel);

        JPanel background = new JPanel();
        background.setBounds(0, 0, 300, 300);
        background.setLayout(null);
        background.setBackground(new Color(0, 150, 250));
        mainPanel.add(background, JLayeredPane.DEFAULT_LAYER);

        JLabel gameOverLabel = new JLabel();
        gameOverLabel.setText("Game Over");
        gameOverLabel.setBounds(41, 22, 250, 45);
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setFont(Mastermind.menuFont.deriveFont(45f));
        background.add(gameOverLabel);

        JLabel panel = new JLabel();
        panel.setBounds(xPanePos, yPanePos, 210, 62);
        panel.setIcon(new ImageIcon(Mastermind.panel));
        mainPanel.add(panel, JLayeredPane.PALETTE_LAYER);

        JLabel firstLabel = addMakerLabel(xPanePos + 10, yPanePos + 10, 0);
        mainPanel.add(firstLabel, JLayeredPane.MODAL_LAYER);

        JLabel secondLabel = addMakerLabel(xPanePos + 60, yPanePos + 10, 1);
        mainPanel.add(secondLabel, JLayeredPane.MODAL_LAYER);

        JLabel thirdLabel = addMakerLabel(xPanePos + 110, yPanePos + 10, 2);
        mainPanel.add(thirdLabel, JLayeredPane.MODAL_LAYER);

        JLabel fourthLabel = addMakerLabel(xPanePos + 160, yPanePos + 10, 3);
        mainPanel.add(fourthLabel, JLayeredPane.MODAL_LAYER);

        JLabel outcomeMessage = new JLabel();
        if (codemakerWin == 0) {
            outcomeMessage.setText("Codebreaker win!");
            outcomeMessage.setBounds(56, 155, 300, 30);
        } else if (codemakerWin == 1) {
            outcomeMessage.setText("Codemaker win!");
            outcomeMessage.setBounds(66, 155, 300, 30);
        }
        outcomeMessage.setForeground(Color.WHITE);
        outcomeMessage.setFont(Mastermind.font.deriveFont(25f));
        background.add(outcomeMessage);

        JButton singlePlayer = addButton("Play again", xPos, yPos);
        singlePlayer.addActionListener(this);
        background.add(singlePlayer);

        JButton multiplayer = addButton("View grid", xPos, yPos + 28);
        multiplayer.addActionListener(this);
        background.add(multiplayer);

        JButton quit = addButton("Quit", xPos, yPos + 56);
        quit.addActionListener(this);
        background.add(quit);

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AbstractButton button = (AbstractButton) e.getSource();
        switch (button.getText()) {
            case "Single player":
                paintChoiceScreen();
                break;
            case "Multiplayer":
                paintCodemakerScreen(true);
                break;
            case "Quit":
            case "Close":
                dispose();
                break;
            case "Play":
                if (isCodemaker) {
                    paintCodemakerScreen(true);
                    //ai engine tbd
                } else {
                    Mastermind.randomizeGrid(difficultyLevel);
                    paintCodebreakerScreen();
                }
                break;
            case "Confirm":
                boolean condition = true;
                for (int i = 0; i < 4; i++) {
                    if (Mastermind.codemakerGrid[i].value == 0) {
                        condition = false;
                        break;
                    }
                }
                if (condition) {
                    paintCodebreakerScreen();
                } else {
                    paintCodemakerScreen(false);
                }
                break;
            case "Randomize":
                Mastermind.randomizeGrid(4);
                paintCodemakerScreen(true);
                break;
            case "Back":
                if (gameOver) {
                    paintEndScreen();
                } else {
                    paintStartScreen();
                }
                break;
            case "Restart":
            case "Play again":
                gameOver = false;
                isCodemaker = false;
                difficultyLevel = 3;
                codemakerScore = 0;
                codebreakerScore = 0;
                loop = 0;
                stageCheck = 0;
                Mastermind.main(null);
                break;
            case "Check":
                Mastermind.compareGrids();
                if (Arrays.equals(Mastermind.codebreakerGrid[stageCheck], Mastermind.codemakerGrid)) {
                    stageCheck++;
                    codemakerWin = 0;
                    gameOver = true;
                    codebreakerScore += 11 - stageCheck;
                    paintEndScreen();
                } else if (stageCheck == 9) {
                    stageCheck++;
                    codemakerWin = 1;
                    gameOver = true;
                    codemakerScore += 1 + stageCheck;
                    paintEndScreen();
                } else {
                    stageCheck++;
                    paintCodebreakerScreen();
                }
                break;
            case "View grid":
                paintCodebreakerScreen();
        }
    }
}
