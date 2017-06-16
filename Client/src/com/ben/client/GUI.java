package com.ben.client;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by ben on 5/11/17.
 */
public class GUI extends JFrame{
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JTextArea txtChatLog;
    private JTextField txtChatInput;
    private JButton btnSend;
    private Color bgColor;

    public JTextArea getChatLog(){
        return txtChatLog;
    }

    public GUI(){
        bgColor = Color.decode("#efe4b0");
        setupUI();
        addListeners();
    }

    private void setupUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setMaximumSize(new Dimension(677, 343));
        mainPanel.setMinimumSize(new Dimension(677, 343));
        mainPanel.setPreferredSize(new Dimension(677, 343));

        scrollPane = new JScrollPane();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0.2;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollPane, gbc);

        txtChatLog = new JTextArea();
        txtChatLog.setFont(txtChatLog.getFont().deriveFont(14f));
        txtChatLog.setForeground(Color.BLACK);
        txtChatLog.setLineWrap(true);
        txtChatLog.setEditable(false);
        txtChatLog.setBackground(bgColor);
        DefaultCaret caret = (DefaultCaret)txtChatLog.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        scrollPane.setViewportView(txtChatLog);

        txtChatInput = new JTextField();
        txtChatInput.setFont(txtChatInput.getFont().deriveFont(14f));
        txtChatInput.setBackground(Color.decode("#c3c3c3"));
        txtChatInput.setForeground(Color.BLACK);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(txtChatInput, gbc);

        btnSend = new JButton();
        btnSend.setText("Send");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(btnSend, gbc);

        add(mainPanel);
        setSize(new Dimension(677, 343));
        setMaximumSize(new Dimension(677, 343));
        setMinimumSize(new Dimension(677, 343));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Chat Client");
        setResizable(false);
        setVisible(true);
    }

    private void addListeners(){
        btnSend.addActionListener(e -> {
            String s = txtChatInput.getText();
            if(s.equalsIgnoreCase("/reconnect")){
                Main.reconnect();
            } else {
                Main.getPrintStream().println(txtChatInput.getText());
            }

            txtChatInput.setText("");
        });
        txtChatInput.addKeyListener(new KeyChecker());
    }

    private class KeyChecker extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent event){
            if(event.getKeyCode() == 10){
                btnSend.doClick();
            }
        }
    }
}
