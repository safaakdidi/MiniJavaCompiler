import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.CharBuffer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CodeGeneratorInterface {
    private JFrame frame;
    private JTextArea inputField;
    private JTextArea outputArea;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CodeGeneratorInterface window = new CodeGeneratorInterface();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CodeGeneratorInterface() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("");
        titlePanel.add(titleLabel);

        // Add other components to inputPanel and outputPanel as needed

        JPanel inputPanel = new JPanel();
        JPanel outputPanel = new JPanel();
        frame.getContentPane().add(outputPanel, BorderLayout.SOUTH);
        frame.getContentPane().add(titlePanel, BorderLayout.NORTH);
        frame.getContentPane().add(inputPanel, BorderLayout.CENTER);

        JLabel title = new JLabel("Code Generator");

        JLabel inputLabel1 = new JLabel("Programme:");
        JLabel inputLabel2 = new JLabel("Code Machine:");

        inputField = new JTextArea();
        titlePanel.add(title);
        inputPanel.add(inputLabel1);

        inputPanel.add(inputField);

        inputField.setColumns(30);
        inputField.setRows(30);

        JButton generateButton = new JButton("Generer");
        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputCode = inputField.getText();


                String generatedCode = runAaaExe(inputCode);
                outputArea.setText("");
        
   
        
                outputArea.append("\n" + generatedCode);
            }
        });
        

        outputArea = new JTextArea();
        outputArea.setColumns(30);
        outputArea.setRows(30);
        outputPanel.add(generateButton);
        inputPanel.add(inputLabel2);

        inputPanel.add(outputArea);

    }


    private String runAaaExe(String inputCode) {
        String output = "";
        try {
            Process process = Runtime.getRuntime().exec("aaa.exe");
            OutputStream stdin = process.getOutputStream();
            InputStream stdout = process.getInputStream();
            InputStream stderr = process.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(stderr));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
            writer.write(inputCode);
            writer.newLine();
            writer.flush();
            writer.close();
            CharBuffer buffer = CharBuffer.allocate(4096); // 4 KB buffer size
            int read;
            while ((read = reader.read(buffer)) > 0) {
                buffer.flip();
                output += buffer.toString();
                buffer.clear();
            }
            while ((read = errorReader.read(buffer)) > 0) {
                buffer.flip();
                output += buffer.toString();
                buffer.clear();
            }
            reader.close();
            errorReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
    
}
