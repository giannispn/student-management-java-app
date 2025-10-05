import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class MainFrame {
    private static ArrayList<Student> studentList = new ArrayList<>(); // Λίστα μαθητών
    public static void main(String[] args){

        JFrame frame = new JFrame(); // Κύριο παράθυρο
        frame.setLayout(new BorderLayout());
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Εισαγωγή στοιχείων στο Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        JLabel firstNameLabel = new JLabel("Όνομα:");
        JTextField firstNameField = new JTextField();
        inputPanel.add(firstNameLabel); inputPanel.add(firstNameField);
        JLabel lastNameLabel = new JLabel("Επίθετο:");
        JTextField lastNameField = new JTextField();
        inputPanel.add(lastNameLabel); inputPanel.add(lastNameField);
        JLabel ageLabel = new JLabel("Ηλικία:");
        JTextField ageField = new JTextField();
        inputPanel.add(ageLabel); inputPanel.add(ageField);
        JLabel gradeLabel = new JLabel("Βαθμός:");
        JTextField gradeField = new JTextField();
        inputPanel.add(gradeLabel); inputPanel.add(gradeField);

        // Κουμπιά
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Προσθήκη Μαθητή");
        JButton saveButton = new JButton("Αποθήκευση σε Αρχείο");
        JButton loadButton = new JButton("Φόρτωση απο Αρχείο");
        JButton searchButton = new JButton("Αναζήτηση μαθητή με βάση το επίθετο");
        buttonPanel.add(addButton); buttonPanel.add(saveButton); buttonPanel.add(loadButton); buttonPanel.add(searchButton);

        // Δημιουργεία περιοχής εξόδου
        JTextArea outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Προσθήκη μαθητή
        addButton.addActionListener(e -> {
            try {
                String first_name = firstNameField.getText();
                String last_name = lastNameField.getText();
                int age = Integer.parseInt(ageField.getText());
                double grade = Double.parseDouble(gradeField.getText());

                if (age < 5 || age > 100)
                    throw new InvalidAgeException("Ηλικία πρέπει να είναι μεταξύ 5 και 100.");
                if (grade < 0 || grade > 20)
                    throw new InvalidGradeException("Βαθμός πρέπει να είναι μεταξύ 0 και 20.");

                Student student = new Student(first_name, last_name, age, grade);
                studentList.add(student);
                outputArea.append(student.toString() + "\n");
                JOptionPane.showMessageDialog(frame, "Μαθητής προστέθηκε επιτυχώς!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Μη έγκυρη αριθμητική τιμή.");
            } catch (InvalidAgeException | InvalidGradeException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }

            }
        );

        // Αποθήκευση μαθητή (λίστα σε αρχείο)
        saveButton.addActionListener(e -> {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("student.dat"))){
            out.writeObject(studentList);
            JOptionPane.showMessageDialog(frame, "Αποθήκευση επιτυχής");
                } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Σφάλμα αποθήκευσης" + ex.getMessage());
            }
        });

        // Φόρτωση λίστας από αρχείο
        loadButton.addActionListener(e -> {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("student.dat"))) {
                studentList = (ArrayList<Student>) in.readObject();
                outputArea.setText("");
                for (Student s : studentList)
                    outputArea.append(s.toString() + "\n");
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(frame, "Σφάλμα φόρτωσης: " + ex.getMessage());
            }
        });

        // Αναζήτηση μαθητή με βασή το επώνυμο
        searchButton.addActionListener(e -> {
            String searchTerm = JOptionPane.showInputDialog("Εισάγετε επώνυμο:");
            if (searchTerm == null || searchTerm.isEmpty()) return;
            outputArea.setText("");
            boolean found = false;
            for (Student s : studentList) {
                if (s.getLastName().equalsIgnoreCase(searchTerm)) {
                    outputArea.append(s.toString() + "\n");
                    found = true;
                }
            }
            if (!found)
                outputArea.setText("Δεν βρέθηκε μαθητής με αυτό το επώνυμο.");
        });

        // Τοποθέτηση του Panel στο frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}