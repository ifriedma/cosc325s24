import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CelestialSimulation extends JFrame {
    private JPanel controlPanel;
    private SimulationPanel simulationPanel;
    private JButton playButton, pauseButton, stepButton, backstepButton; // Added backstepButton
    private JLabel dayCounterLabel;
    private Timer timer;
    private int dayCounter = 0;

    public CelestialSimulation() {
        setTitle("Celestial Simulation");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        controlPanel = new JPanel();
        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        stepButton = new JButton("Step");
        backstepButton = new JButton("Backstep"); // Created backstepButton
        dayCounterLabel = new JLabel("Day: 0");
        controlPanel.add(playButton);
        controlPanel.add(pauseButton);
        controlPanel.add(stepButton);
        controlPanel.add(backstepButton); // Added backstepButton
        controlPanel.add(dayCounterLabel);

        simulationPanel = new SimulationPanel();
        add(simulationPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                play();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pause();
            }
        });

        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                step();
            }
        });

        backstepButton.addActionListener(new ActionListener() { // Added ActionListener for backstepButton
            public void actionPerformed(ActionEvent e) {
                backstep();
            }
        });

        timer = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulationPanel.update();
                dayCounter++;
                dayCounterLabel.setText("Day: " + dayCounter);
            }
        });
    }

    private void play() {
        timer.start();
    }

    private void pause() {
        timer.stop();
    }

    private void step() {
        simulationPanel.update();
        dayCounter++;
        dayCounterLabel.setText("Day: " + dayCounter);
    }

    private void backstep() {
        simulationPanel.reverseUpdate(); // Invoke reverseUpdate method in SimulationPanel
        dayCounter--;
        dayCounterLabel.setText("Day: " + dayCounter);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CelestialSimulation simulation = new CelestialSimulation();
                simulation.setVisible(true);
            }
        });
    }
}

class SimulationPanel extends JPanel {
    private int earthX, earthY, moonX, moonY;
    private double earthAngle = 0, moonAngle = 0;

    private List<Point> stars = new ArrayList<>(); // Store the positions of the stars

    public SimulationPanel() {
        earthX = 450 + 275;
        earthY = 300 + 275;
        moonX = 0;
        moonY = 0;
        generateStars(); // Generate star positions
    }

    private void generateStars() {
        int numStars = 400; // Number of stars

        // Generate random positions for the stars
        for (int i = 0; i < numStars; i++) {
            Random random = new Random();
            int x = (int) (random.nextInt(1001));
            int y = (int) (random.nextInt(801));
            stars.add(new Point(x, y));
        }
    }

    public void update() {
        // Simulate Earth's orbit
        double earthOrbitRadius = 275;
        earthAngle += 2 * Math.PI / 365;
        earthX = (int) (450 + earthOrbitRadius * Math.cos(earthAngle));
        earthY = (int) (300 + earthOrbitRadius * Math.sin(earthAngle));

        // Simulate Moon's orbit around Earth
        double moonOrbitRadius = 50;
        moonAngle += 2 * Math.PI / 30;
        moonX = (int) (earthX + moonOrbitRadius * Math.cos(moonAngle));
        moonY = (int) (earthY + moonOrbitRadius * Math.sin(moonAngle));

        repaint();
    }

    public void reverseUpdate() { // Method to reverse the update
        // Simulate Earth's orbit
        double earthOrbitRadius = 275;
        earthAngle -= 2 * Math.PI / 365; // Reversing the angle change
        earthX = (int) (450 + earthOrbitRadius * Math.cos(earthAngle));
        earthY = (int) (300 + earthOrbitRadius * Math.sin(earthAngle));

        // Simulate Moon's orbit around Earth
        double moonOrbitRadius = 50;
        moonAngle -= 2 * Math.PI / 30;
        moonX = (int) (earthX + moonOrbitRadius * Math.cos(moonAngle));
        moonY = (int) (earthY + moonOrbitRadius * Math.sin(moonAngle));

        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(Color.BLACK);

        // Draw stars
        g.setColor(Color.WHITE);
        for (Point star : stars) {
            g.fillRect(star.x, star.y, 1, 1); // Draw a single pixel for each star
        }

        // Draw Earth
        g.setColor(Color.BLUE);
        g.fillOval(earthX - 20, earthY - 20, 40, 40);

        // Draw Moon
        g.setColor(Color.GRAY);
        g.fillOval(moonX - 10, moonY - 10, 10, 10);

        // Draw Sun
        g.setColor(Color.YELLOW);
        g.fillOval(450, 300, 150, 150);
    }
}
