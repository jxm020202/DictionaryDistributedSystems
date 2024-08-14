package client;

import javax.swing.*;

import shared.MessageProtocol;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DictionaryClientGUI extends JFrame {
    private JTextField wordField;
    private JTextField meaningField;
    private JTextArea resultArea;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public DictionaryClientGUI() {
        setTitle("Dictionary Client");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Setup fields and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        inputPanel.add(new JLabel("Word:"));
        wordField = new JTextField();
        inputPanel.add(wordField);

        inputPanel.add(new JLabel("Meaning:"));
        meaningField = new JTextField();
        inputPanel.add(meaningField);

        JButton addButton = new JButton("Add Word");
        JButton queryButton = new JButton("Query Word");
        JButton removeButton = new JButton("Remove Word");

        inputPanel.add(addButton);
        inputPanel.add(queryButton);
        inputPanel.add(removeButton);

        add(inputPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Add action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendRequest(MessageProtocol.ADD + " " + wordField.getText() + " " + meaningField.getText());
            }
        });

        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendRequest(MessageProtocol.QUERY + " " + wordField.getText());
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendRequest(MessageProtocol.REMOVE + " " + wordField.getText());
            }
        });

        // Setup network connection
        try {
            socket = new Socket("127.0.0.1", 1234);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sendRequest(String request) {
        if (out != null) {
            out.println(request);
            try {
                String response = in.readLine();
                resultArea.append(response + "\n");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DictionaryClientGUI().setVisible(true);
            }
        });
    }
}
