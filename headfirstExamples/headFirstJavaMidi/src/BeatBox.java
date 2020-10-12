import java.awt.*;
import javax.swing.*;
import javax.sound.midi.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;

public class BeatBox {
    JFrame theFrame;
    JPanel mainPanel;
    ArrayList<JCheckBox> checkBoxList;
    Sequencer sequencer;
    Sequence sequence;
    Track track;

    String[] instrumentNames = {"Bass Drum", "Closed Hit-Hat", "Open Hit-Hat", "Acoustic Snare",
        "Crash Cymbal", "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga",
        "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", "Open Hi Conga"
    };
    int[] instruments = { 35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63 };

    public static void main( String[] args ) {
        new BeatBox().BuildGUI();
    }

    public void BuildGUI() {
        theFrame = new JFrame("Cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();

        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        checkBoxList = new ArrayList<>();
        Box buttonBox = new Box( BoxLayout.Y_AXIS );

        JButton start = new JButton("Start");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);

        JButton stop = new JButton("Stop");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        JButton upTempo = new JButton("Tempo Up");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("Tempo Down");
        downTempo.addActionListener( new MyDownTempoListener());
        buttonBox.add(downTempo);

        JButton serializeIt = new JButton("serializeIt");
        serializeIt.addActionListener((ActionEvent event) -> {
            boolean[] checkboxState = new boolean[256];
            for (int i = 0; i < 256; i++ ) {
                JCheckBox check = checkBoxList.get(i);
                if ( check.isSelected() ) {
                    checkboxState[i] = true;
                }
            }

            try {
                FileOutputStream fs = new FileOutputStream( new File("Checkbox.ser"));
                ObjectOutputStream os = new ObjectOutputStream(fs);
                os.writeObject( checkboxState );
            } catch (Exception ex ) {
                ex.printStackTrace();
            }
        });
        buttonBox.add(serializeIt);

        JButton restore = new JButton("restore");
        restore.addActionListener((ActionEvent event) -> {
            boolean[] checkboxState = null;
            try {
                FileInputStream fs = new FileInputStream( new File("Checkbox.ser") );
                ObjectInputStream is = new ObjectInputStream( fs );
                checkboxState = (boolean[]) is.readObject();
            } catch ( Exception ex ) {
                ex.printStackTrace();
            }

            for (int i = 0; i < 256; i++ ) {
                JCheckBox check = checkBoxList.get( i );
                if ( checkboxState != null && checkboxState[i] ) {
                    check.setSelected( true );
                } else {
                    check.setSelected( false );
                }
            }

            sequencer.stop();
            buildTrackAndStart();
        });
        buttonBox.add(restore);

        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (String instrumentName : instrumentNames) {
            nameBox.add(new Label(instrumentName));
        }

        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);

        theFrame.getContentPane().add(background);

        GridLayout grid = new GridLayout(16,16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);


        for( int i = 0; i < 256; i++ ) {
            JCheckBox c = new JCheckBox();
            c.setSelected( false );
            checkBoxList.add(c);
            mainPanel.add(c);
        }

        setUpMidi();

        theFrame.setBounds(50,50,300,300);
        theFrame.pack();
        theFrame.setVisible(true);
    }

    public void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void buildTrackAndStart() {
        int[] trackList = null;
        sequence.deleteTrack( track );
        track = sequence.createTrack();
        int instrumentsLength = instruments.length;
        for ( int i = 0; i < instrumentsLength; i++ ) {
            trackList = new int[ instrumentsLength ];
            int key = instruments[i];

            for ( int j = 0; j < instrumentsLength; j++ ) {
                JCheckBox jc = checkBoxList.get( j + ( instrumentsLength * i ) );
                if ( jc.isSelected() ) {
                    trackList[j] = key;
                } else {
                    trackList[j] = 0;
                }
            }
            makeTracks( trackList );
            track.add( makeEvent( 176, 1, 127, 0, 16 ) );
        }

        track.add( makeEvent(192, 9, 1, 0, 15 ));

        try {
            sequencer.setSequence( sequence );
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM( 120 );
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public class MyStartListener implements ActionListener {
        @Override
        public void actionPerformed( ActionEvent e ) {
            buildTrackAndStart();
        }
    }

    public class MyStopListener implements ActionListener {
        @Override
        public void actionPerformed( ActionEvent e ) {
            sequencer.stop();
        }
    }

    public class MyUpTempoListener implements ActionListener {
        @Override
        public void actionPerformed( ActionEvent e ) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor( tempoFactor * 1.03f );
        }
    }

    public class MyDownTempoListener implements ActionListener {
        @Override
        public void actionPerformed( ActionEvent e ) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor( tempoFactor * .97f );
        }
    }

    public void makeTracks( int[] list ) {
        for (int i = 0; i < list.length; i++) {
            int key = list[i];
            if ( key == 0 ) {
                continue;
            }

            track.add(makeEvent(144, 9, key, 100, i));
            track.add(makeEvent(128, 9, key, 100, i + 1));
        }
    }

    public MidiEvent makeEvent( int cmd, int ch, int one, int two, int tick ) {
        MidiEvent event = null;

        try {
            ShortMessage msg = new ShortMessage();
            msg.setMessage( cmd, ch, one, two );
            event = new MidiEvent(msg, tick);
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }

        return event;
    }
}
