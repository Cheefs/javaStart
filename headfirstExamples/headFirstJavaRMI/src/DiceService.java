import javax.swing.*;

public class DiceService implements Service {
    JLabel label;
    JComboBox<Object> numOfDice;

    public JPanel getGuiPanel() {
        JPanel panel = new JPanel();
        JButton button = new JButton("Roll `em!");
        String[] choices = {"1", "2", "3", "4", "5"};
        numOfDice = new JComboBox<>(choices);
        label = new JLabel("dice values here");
        button.addActionListener(( event ) -> roll());

        panel.add(numOfDice);
        panel.add(button);
        panel.add(label);
        return panel;
    }

    public void roll() {
        StringBuilder diceOutput = new StringBuilder();
        String selection = (String) numOfDice.getSelectedItem();
        int numOfDiceToRoll = 0;
        if ( selection != null ) {
            numOfDiceToRoll = Integer.parseInt(selection);
        }

        for ( int i = 0; i < numOfDiceToRoll; i++ ) {
            int r = (int) ( Math.random() * 6 ) + 1;
            diceOutput.append(" ").append(r);
        }
        label.setText( diceOutput.toString() );
    }
}
