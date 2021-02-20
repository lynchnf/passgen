package norman.password.ui;

import norman.password.core.Generator;
import norman.password.core.Generator.Optionality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import static norman.password.core.Generator.Optionality.MANDATORY;
import static norman.password.core.Generator.Optionality.PROHIBITED;

public class MainFrame extends JFrame implements ActionListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainFrame.class);
    private ResourceBundle bundle;
    private Properties appProps;
    private Container desktop;
    private JMenuItem optionsFileItem;
    private JMenuItem exitFileItem;

    private JSpinner nbrOfPasswordsSpinner;
    private JSpinner lengthSpinner;
    private JCheckBox numberForFirstCheckBox;
    private JCheckBox lowerCaseCheckBox;
    private JCheckBox upperCaseCheckBox;
    private JCheckBox numericCheckBox;
    private JCheckBox specialCheckBox;
    private JButton generateButton;
    private JTextArea passwordsTextArea;
    private JButton clipboardButton;

    public MainFrame(Properties appProps) throws HeadlessException {
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.appProps = appProps;
        Locale.setDefault(Locale.forLanguageTag(appProps.getProperty("main.frame.language")));

        initComponents();
/*
        int width = Integer.parseInt(appProps.getProperty("main.frame.width"));
        int height = Integer.parseInt(appProps.getProperty("main.frame.height"));
        setSize(width, height);
*/
        pack();
        int x = Integer.parseInt(appProps.getProperty("main.frame.location.x"));
        int y = Integer.parseInt(appProps.getProperty("main.frame.location.y"));
        setLocation(x, y);
    }

    private void initComponents() {
        LOGGER.debug("Initializing window components. Locale = " + Locale.getDefault());
        bundle = ResourceBundle.getBundle("norman.password.ui.MainFrame");
        setTitle(bundle.getString("title"));
        desktop = new JPanel(new GridBagLayout());
        setContentPane(desktop);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu(bundle.getString("menu.file"));
        menuBar.add(fileMenu);
        optionsFileItem = new JMenuItem(bundle.getString("menu.file.options"));
        fileMenu.add(optionsFileItem);
        optionsFileItem.addActionListener(this);
        fileMenu.add(new JSeparator());
        exitFileItem = new JMenuItem(bundle.getString("menu.file.exit"));
        fileMenu.add(exitFileItem);
        exitFileItem.addActionListener(this);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        desktop.add(new JLabel("Number of Passwords"), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        nbrOfPasswordsSpinner = new JSpinner(new SpinnerNumberModel(88, 1, 99, 1));
        desktop.add(nbrOfPasswordsSpinner, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;
        desktop.add(new JLabel("Password Length"), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;
        lengthSpinner = new JSpinner(new SpinnerNumberModel(88, 1, 99, 1));
        desktop.add(lengthSpinner, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_START;
        desktop.add(new JLabel("Allow Number for First Character"), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_START;
        numberForFirstCheckBox = new JCheckBox();
        desktop.add(numberForFirstCheckBox, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.LINE_START;
        desktop.add(new JLabel("Include Lower Case Characters"), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 3;
        c.anchor = GridBagConstraints.LINE_START;
        lowerCaseCheckBox = new JCheckBox();
        desktop.add(lowerCaseCheckBox, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 4;
        c.anchor = GridBagConstraints.LINE_START;
        desktop.add(new JLabel("Include Upper Case Characters"), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 4;
        c.anchor = GridBagConstraints.LINE_START;
        upperCaseCheckBox = new JCheckBox();
        desktop.add(upperCaseCheckBox, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 5;
        c.anchor = GridBagConstraints.LINE_START;
        desktop.add(new JLabel("Include Numeric Characters"), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 5;
        c.anchor = GridBagConstraints.LINE_START;
        numericCheckBox = new JCheckBox();
        desktop.add(numericCheckBox, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 6;
        c.anchor = GridBagConstraints.LINE_START;
        desktop.add(new JLabel("Include Special Characters"), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 6;
        c.anchor = GridBagConstraints.LINE_START;
        specialCheckBox = new JCheckBox();
        desktop.add(specialCheckBox, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        generateButton = new JButton("Generate Password");
        desktop.add(generateButton, c);
        generateButton.addActionListener(this);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 8;
        c.anchor = GridBagConstraints.LINE_START;
        passwordsTextArea = new JTextArea(2, 20);
        passwordsTextArea.setEditable(false);
        desktop.add(passwordsTextArea, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 8;
        c.anchor = GridBagConstraints.LINE_START;
        clipboardButton = new JButton("Clipboard");
        clipboardButton.setToolTipText("Copy To Clipboard");
        desktop.add(clipboardButton, c);
        clipboardButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(exitFileItem)) {
            LOGGER.debug("Processing exit menu item.");
            processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        } else if (actionEvent.getSource().equals(optionsFileItem)) {
            options();
        } else if (actionEvent.getSource().equals(generateButton)) {
            int nbrOfPasswords = (int) nbrOfPasswordsSpinner.getValue();
            int length = (int) lengthSpinner.getValue();
            boolean allowNumberForFirstChar = numberForFirstCheckBox.isSelected();
            Optionality includeLows = lowerCaseCheckBox.isSelected() ? MANDATORY : PROHIBITED;
            Optionality includeCaps = upperCaseCheckBox.isSelected() ? MANDATORY : PROHIBITED;
            Optionality includeNumbers = numericCheckBox.isSelected() ? MANDATORY : PROHIBITED;
            Optionality includeSpecials = specialCheckBox.isSelected() ? MANDATORY : PROHIBITED;
            if (includeLows == PROHIBITED && includeCaps == PROHIBITED && includeNumbers == PROHIBITED &&
                    includeSpecials == PROHIBITED) {
                passwordsTextArea.setText(null);
            } else {
                StringBuilder sb = null;
                for (int i = 0; i < nbrOfPasswords; i++) {

                    String password = Generator
                            .generatePassword(length, allowNumberForFirstChar, includeLows, includeCaps, includeNumbers,
                                    includeSpecials);
                    if (sb == null) {
                        sb = new StringBuilder(password);
                    } else {
                        sb.append(System.lineSeparator());
                        sb.append(password);
                    }
                }
                passwordsTextArea.setText(sb.toString());
            }
        }
    }

    @Override
    protected void processWindowEvent(WindowEvent windowEvent) {
        if (windowEvent.getSource() == this && windowEvent.getID() == WindowEvent.WINDOW_CLOSING) {

            // Get current size and position of UI and remember it.
            Dimension size = getSize();
            appProps.setProperty("main.frame.width", Integer.toString(size.width));
            appProps.setProperty("main.frame.height", Integer.toString(size.height));
            Point location = getLocation();
            appProps.setProperty("main.frame.location.x", Integer.toString(location.x));
            appProps.setProperty("main.frame.location.y", Integer.toString(location.y));

            // Save the properties file to a local file.
            try {
                Application.storeProps(appProps);
            } catch (LoggingException e) {
                JOptionPane.showMessageDialog(this, bundle.getString("error.message.saving.window.size.and.location"),
                        bundle.getString("error.dialog.title"), JOptionPane.ERROR_MESSAGE);
            }
        }
        super.processWindowEvent(windowEvent);
    }

    private void options() {
        // Put up an options panel to change language.
        JFrame optionsFrame = new JFrame();
        optionsFrame.setTitle(bundle.getString("options.title"));
        optionsFrame.setResizable(false);

        JPanel optionsPanel = new JPanel();
        optionsFrame.add(optionsPanel);
        optionsPanel.setOpaque(false);

        JLabel langLabel = new JLabel(bundle.getString("options.language"));
        optionsPanel.add(langLabel);

        LocaleWrapper[] locales =
                {new LocaleWrapper(Locale.ENGLISH), new LocaleWrapper(Locale.FRENCH), new LocaleWrapper(Locale.GERMAN)};
        JComboBox langComboBox = new JComboBox(locales);
        optionsPanel.add(langComboBox);
        langComboBox.setSelectedItem(new LocaleWrapper());
        MainFrame mainFrame = this;
        langComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                LocaleWrapper newLang = (LocaleWrapper) langComboBox.getSelectedItem();
                appProps.setProperty("main.frame.language", newLang.getLocale().toLanguageTag());
                try {
                    Application.storeProps(appProps);
                } catch (LoggingException e) {
                    JOptionPane.showMessageDialog(mainFrame,
                            bundle.getString("error.message.saving.window.size.and.location"),
                            bundle.getString("error.dialog.title"), JOptionPane.ERROR_MESSAGE);
                }

                Locale.setDefault(newLang.getLocale());
                initComponents();
                optionsFrame.dispose();
            }
        });
        optionsFrame.pack();

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int optionsWidth = optionsFrame.getWidth();
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int optionsHeight = optionsFrame.getHeight();
        optionsFrame.setLocation((screenWidth - optionsWidth) / 2, (screenHeight - optionsHeight) / 2);
        optionsFrame.setVisible(true);
    }
}
