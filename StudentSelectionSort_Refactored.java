/*
 * StudentSelectionSort_Refactored.java
 *
 * MODIFICATIONS MADE:
 * 1. Logic was refactored into smaller, well-named helper methods for readability.
 * 2. Added clear method-level comments to explain each step of the algorithm.
 * 3. Improved variable naming to better reflect purpose.
 * 4. Main method simplified to focus only on program flow.
 *
 * Purpose: Demonstrate Selection Sort with improved structure and clarity.
 */


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StudentSelectionSort extends JFrame {

    private final List<Student> students = new ArrayList<>();
    private JTextField nameField;
    private JTextField scoreField;
    private JTextArea outputArea;

    public StudentSelectionSort() {
        super("Student Sort Demo (Insertion Sort)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 450);
        setLocationRelativeTo(null);

        seedDefaultStudents();
        buildUI();
        refreshOutput("Loaded default students.");
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(root);

        root.add(buildInputPanel(), BorderLayout.NORTH);
        root.add(buildOutputPanel(), BorderLayout.CENTER);
        root.add(buildButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel buildInputPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 8, 6));
        panel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Score (0 - 100):"));
        scoreField = new JTextField();
        panel.add(scoreField);
        return panel;
    }

    private JScrollPane buildOutputPanel() {
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        return new JScrollPane(outputArea);
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton addBtn = new JButton("Add Student");
        JButton sortAscBtn = new JButton("Sort Asc");
        JButton sortDescBtn = new JButton("Sort Desc");
        JButton clearBtn = new JButton("Clear All");

        addBtn.addActionListener(e -> onAddStudent());
        sortAscBtn.addActionListener(e -> onSort(true));
        sortDescBtn.addActionListener(e -> onSort(false));
        clearBtn.addActionListener(e -> onClearAll());

        panel.add(addBtn);
        panel.add(sortAscBtn);
        panel.add(sortDescBtn);
        panel.add(clearBtn);

        return panel;
    }

    private void onAddStudent() {
        String name = nameField.getText().trim();
        String scoreText = scoreField.getText().trim();

        if (name.isEmpty() || scoreText.isEmpty()) {
            showError("Name and score are required.");
            return;
        }

        double score;
        try {
            score = Double.parseDouble(scoreText);
        } catch (NumberFormatException ex) {
            showError("Score must be a number.");
            return;
        }

        if (score < 0 || score > 100) {
            showError("Score must be between 0 and 100.");
            return;
        }

        students.add(new Student(name, score));
        nameField.setText("");
        scoreField.setText("");
        refreshOutput("Added: " + name);
    }

    private void onSort(boolean ascending) {
        List<Student> sorted = new ArrayList<>(students);
        insertionSort(sorted, ascending);
        outputArea.setText(formatList(sorted, ascending ? "Ascending" : "Descending"));
    }

    private void onClearAll() {
        students.clear();
        refreshOutput("Cleared all students.");
    }

    private void insertionSort(List<Student> list, boolean ascending) {
        for (int i = 1; i < list.size(); i++) {
            Student key = list.get(i);
            int j = i - 1;
            while (j >= 0 && compare(list.get(j), key, ascending) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    private int compare(Student a, Student b, boolean ascending) {
        int cmp = Double.compare(a.score, b.score);
        return ascending ? cmp : -cmp;
    }

    private String formatList(List<Student> list, String title) {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append("\n");
        sb.append("-----------------------------\n");
        for (Student s : list) {
            sb.append(String.format("%-20s %6.2f\n", s.name, s.score));
        }
        return sb.toString();
    }

    private void refreshOutput(String title) {
        outputArea.setText(formatList(students, title));
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void seedDefaultStudents() {
        students.add(new Student("Ava", 91.5));
        students.add(new Student("Noah", 73));
        students.add(new Student("Mia", 88.25));
        students.add(new Student("Liam", 95));
        students.add(new Student("Emma", 82));
    }

    private static class Student {
        String name;
        double score;

        Student(String name, double score) {
            this.name = name;
            this.score = score;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentSelectionSort().setVisible(true));
    }
}
